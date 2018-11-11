package connector;

import model.Connector;
import model.Response;
import model.Transaction;
import model.Type;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ApachePoiConnection implements Connector {

    @Override
    public Response readAllTransaction() {
        double totalIncome = 0, totalExpense = 0;
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            FileInputStream input = new FileInputStream("./src/main/resources/account.xlsx");
            Workbook workbook = WorkbookFactory.create(input);
            Sheet sheet = workbook.getSheet("Sheet1");

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                int id = (int) row.getCell(0).getNumericCellValue();
                LocalDate date = LocalDate.parse(row.getCell(1).getDateCellValue().toString());
                Type type = null;
                if (row.getCell(2).toString().equals("INCOME"))
                    type = Type.INCOME;
                else if (row.getCell(2).toString().equals("EXPENSE"))
                    type = Type.EXPENSE;
                String desc = row.getCell(3).toString();
                int amount = (int) row.getCell(4).getNumericCellValue();

                transactions.add(new Transaction(id, date, type, desc, amount));
                if (type == Type.INCOME)
                    totalIncome += amount;
                else if (type == Type.EXPENSE)
                    totalExpense += amount;
            }

            workbook.close();
            input.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }

        return new Response(transactions, totalIncome, totalExpense);
    }

    @Override
    public void writeTransaction(Transaction transaction) {
        try {
            FileInputStream input = new FileInputStream("./src/main/resources/account.xlsx");
            Workbook workbook = WorkbookFactory.create(input);
            Sheet sheet = workbook.getSheet("Sheet1");

            Row row = sheet.createRow(transaction.getId());
            row.createCell(0).setCellValue(transaction.getId());
            row.createCell(1).setCellValue(transaction.getDate().toString());
            row.createCell(2).setCellValue(transaction.getType().toString());
            row.createCell(3).setCellValue(transaction.getDescription());
            row.createCell(4).setCellValue(transaction.getAmount());

            FileOutputStream out = new FileOutputStream("./src/main/resources/account.xlsx");
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();
            input.close();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void editTransaction(Transaction transaction) {
        try {
            FileInputStream input = new FileInputStream("./src/main/resources/account.xlsx");
            Workbook workbook = WorkbookFactory.create(input);
            Sheet sheet = workbook.getSheet("Sheet1");

            Row row = sheet.getRow(transaction.getId());
            row.getCell(1).setCellValue(transaction.getDate().toString());
            row.createCell(3).setCellValue(transaction.getDescription());
            row.createCell(4).setCellValue(transaction.getAmount());

            FileOutputStream out = new FileOutputStream("./src/main/resources/account.xlsx");
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();
            input.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class ApachePoiConnection {

    private static ApachePoiConnection instance;

    private ApachePoiConnection() { }

    public Response readTransactionFromFile() throws IOException {
        FileInputStream input = new FileInputStream("./expense-income.xlsx");
        Workbook workbook = WorkbookFactory.create(input);
        Sheet sheet = workbook.getSheet("Sheet1");

        double totalIncome = 0, totalExpense = 0;
        ArrayList<Transaction> transactions = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            int id = (int) row.getCell(0).getNumericCellValue();
            LocalDate date = row.getCell(1).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Transaction.Type type = null;
            if (row.getCell(2).toString().equals("INCOME"))
                type = Transaction.Type.INCOME;
            else if (row.getCell(2).toString().equals("EXPENSE"))
                type = Transaction.Type.EXPENSE;
            String desc = row.getCell(3).toString();
            int amount = (int) row.getCell(4).getNumericCellValue();

            transactions.add(new Transaction(id, date, type, desc, amount));
            if (type == Transaction.Type.INCOME)
                totalIncome += amount;
            else if (type == Transaction.Type.EXPENSE)
                totalExpense += amount;
        }

        workbook.close();
        input.close();

        return new Response(transactions, totalIncome, totalExpense);
    }

    public void writeTransactionToFile(Transaction transaction) throws IOException {
        FileInputStream input = new FileInputStream("./expense-income.xlsx");
        Workbook workbook = WorkbookFactory.create(input);
        Sheet sheet = workbook.getSheet("Sheet1");

        Row row = sheet.createRow(transaction.getId());
        row.createCell(0).setCellValue(transaction.getId());
        row.createCell(1).setCellValue(Date.from(transaction.getDate().atStartOfDay().toInstant(OffsetDateTime.now().getOffset())));
        row.createCell(2).setCellValue(transaction.getType().toString());
        row.createCell(3).setCellValue(transaction.getDescription());
        row.createCell(4).setCellValue(transaction.getAmount());

        FileOutputStream out = new FileOutputStream("./expense-income.xlsx");
        workbook.write(out);
        out.flush();
        out.close();
        workbook.close();
        input.close();
    }

    public void editTransactionToFile(Transaction transaction) throws IOException {
        FileInputStream input = new FileInputStream("./expense-income.xlsx");
        Workbook workbook = WorkbookFactory.create(input);
        Sheet sheet = workbook.getSheet("Sheet1");

        Row row = sheet.getRow(transaction.getId());
        row.getCell(1).setCellValue(Date.from(transaction.getDate().atStartOfDay().toInstant(OffsetDateTime.now().getOffset())));
        row.createCell(3).setCellValue(transaction.getDescription());
        row.createCell(4).setCellValue(transaction.getAmount());

        FileOutputStream out = new FileOutputStream("./expense-income.xlsx");
        workbook.write(out);
        out.flush();
        out.close();
        workbook.close();
        input.close();
    }

    public static ApachePoiConnection getInstance() throws IOException {
        if (instance == null)
            instance = new ApachePoiConnection();
        return instance;
    }

}

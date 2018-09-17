import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;


public class ExpenseIncomeAccount {

    private ArrayList<Transaction> transactions;
    private int balance;

    public ExpenseIncomeAccount() throws Exception {
        this.balance = 0;
        readFile();
    }

    public ExpenseIncomeAccount(int balance) throws Exception {
        this.balance = balance;
        readFile();
    }

    private void readFile() throws Exception {
        FileInputStream input = new FileInputStream("./expense-income.xlsx");
        Workbook workbook = WorkbookFactory.create(input);
        Sheet sheet = workbook.getSheet("Sheet1");
        this.transactions = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            int id = (int) row.getCell(0).getNumericCellValue();
            LocalDateTime date = row.getCell(1).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            Transaction.Type type = (row.getCell(2).toString().equals("EXPENSE")) ? Transaction.Type.EXPENSE: Transaction.Type.INCOME;
            String desc = row.getCell(3).toString();
            int amount = (int) row.getCell(4).getNumericCellValue();
            this.transactions.add(new Transaction(id, date, type, desc, amount));
            if (type == Transaction.Type.INCOME) this.balance += amount;
            else if (type == Transaction.Type.EXPENSE) this.balance -= amount;
        }

        input.close();
    }

    public void writeFile(Transaction transaction) throws Exception {
        FileInputStream input = new FileInputStream("./expense-income.xlsx");
        Workbook workbook = WorkbookFactory.create(input);
        Sheet sheet = workbook.getSheet("Sheet1");
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        row.createCell(0).setCellValue(sheet.getLastRowNum());
        row.createCell(1).setCellValue(Date.from(transaction.getDate().atZone(ZoneId.systemDefault()).toInstant()));
        row.createCell(2).setCellValue(transaction.getType().toString());
        row.createCell(3).setCellValue(transaction.getDescription());
        row.createCell(4).setCellValue(transaction.getAmount());
        FileOutputStream out = new FileOutputStream("./expense-income.xlsx");
        workbook.write(out);
        out.flush();
        out.close();
        input.close();
    }


    public void addExpense(String desc, int amount) throws Exception {
        Transaction transaction = new Transaction(transactions.size() + 1, LocalDateTime.now(), Transaction.Type.EXPENSE, desc, amount);

        this.balance -= transaction.getAmount();
        transactions.add(transaction);

        writeFile(transaction);
    }

    public void addIncome(String desc, int amount) throws Exception {
        Transaction transaction = new Transaction(transactions.size() + 1, LocalDateTime.now(), Transaction.Type.INCOME, desc, amount);

        this.balance += transaction.getAmount();
        transactions.add(transaction);

        writeFile(transaction);
    }

    public int getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public int getTransactionsSize() {
        return transactions.size();
    }

}


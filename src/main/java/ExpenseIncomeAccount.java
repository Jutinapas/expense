import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;


public class ExpenseIncomeAccount {

    private ArrayList<Transaction> transactions;
    private int balance;

    public ExpenseIncomeAccount() throws Exception {
        this.balance = 0;
        this.transactions = new ArrayList<>();
        readFile();
    }

    private void readFile() throws Exception {
        FileInputStream input = new FileInputStream("./expense-income.xlsx");
        Workbook workbook = WorkbookFactory.create(input);
        Sheet sheet = workbook.getSheet("Sheet1");

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            int id = (int) row.getCell(0).getNumericCellValue();
            LocalDate date = row.getCell(1).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
        input.close();
    }


    public void addExpense(String desc, int amount) throws Exception {
        Transaction transaction = new Transaction(transactions.size() + 1, LocalDateTime.now().toLocalDate(), Transaction.Type.EXPENSE, desc, amount);

        this.balance -= transaction.getAmount();
        transactions.add(transaction);

        writeFile(transaction);
    }

    public void addIncome(String desc, int amount) throws Exception {
        Transaction transaction = new Transaction(transactions.size() + 1, LocalDateTime.now().toLocalDate(), Transaction.Type.INCOME, desc, amount);

        this.balance += transaction.getAmount();
        transactions.add(transaction);

        writeFile(transaction);
    }

    public void editTransaction(Transaction transaction) throws Exception {
        Transaction old = this.transactions.get(transaction.getId() - 1);

        if (old.getType() == Transaction.Type.INCOME) balance -= old.getAmount();
        else if (old.getType() == Transaction.Type.EXPENSE) balance += old.getAmount();
        this.transactions.set(transaction.getId() - 1, transaction);
        if (transaction.getType() == Transaction.Type.INCOME) this.balance += transaction.getAmount();
        else if (transaction.getType() == Transaction.Type.EXPENSE) this.balance -= transaction.getAmount();

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
        input.close();
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


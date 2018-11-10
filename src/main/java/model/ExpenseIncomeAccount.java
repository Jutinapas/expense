package model;

import connector.ApachePoiConnection;
import connector.JdbcSQLiteConnection;

import java.io.IOException;
import java.time.*;
import java.util.ArrayList;

public class ExpenseIncomeAccount {

    private static ExpenseIncomeAccount instance;

    private ArrayList<Transaction> transactions;
    private double totalIncome;
    private double totalExpense;

    enum Datasource { DATABASE, FILE };
    private Datasource datasource = Datasource.FILE;

    private ExpenseIncomeAccount() {
        this.totalExpense = 0;
        this.totalIncome = 0;
        this.transactions = new ArrayList<>();

        readTransaction();
    }

    private void readTransaction() {
        Response response = null;
        if (datasource == Datasource.DATABASE)
            response = JdbcSQLiteConnection.getInstance().readTransactionFromFromDB();
        else if (datasource == Datasource.FILE) {
            try {
                response = ApachePoiConnection.getInstance().readTransactionFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        transactions = response.getTransactions();
        totalIncome = response.getTotalIncome();
        totalExpense = response.getTotalExpense();
    }

    private void writeTransaction(Transaction transaction) {
        if (datasource == Datasource.DATABASE)
            JdbcSQLiteConnection.getInstance().writeTransactionToDB(transaction);
        else if (datasource == Datasource.FILE) {
            try {
                ApachePoiConnection.getInstance().writeTransactionToFile(transaction);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void editTransaction(Transaction transaction) {
        Transaction old = transactions.get(transaction.getId() - 1);

        if (old.getType() == Transaction.Type.INCOME)
            totalIncome -= old.getAmount();
        else if (old.getType() == Transaction.Type.EXPENSE)
            totalExpense -= old.getAmount();

        transactions.set(transaction.getId() - 1, transaction);

        if (transaction.getType() == Transaction.Type.INCOME)
            this.totalIncome += transaction.getAmount();
        else if (transaction.getType() == Transaction.Type.EXPENSE)
            this.totalExpense += transaction.getAmount();

        if (datasource == Datasource.DATABASE)
            JdbcSQLiteConnection.getInstance().editTransactionToDB(transaction);
        else if (datasource == Datasource.FILE) {
            try {
                ApachePoiConnection.getInstance().editTransactionToFile(transaction);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addExpense(String desc, double amount) throws Exception {
        Transaction transaction = new Transaction(transactions.size() + 1, LocalDate.now(), Transaction.Type.EXPENSE, desc, amount);
        totalExpense += transaction.getAmount();
        transactions.add(transaction);
        writeTransaction(transaction);
    }

    public void addIncome(String desc, double amount) throws Exception {
        Transaction transaction = new Transaction(transactions.size() + 1, LocalDate.now(), Transaction.Type.INCOME, desc, amount);
        totalIncome += transaction.getAmount();
        transactions.add(transaction);
        writeTransaction(transaction);
    }

    public static ExpenseIncomeAccount getInstance() {
        if (instance == null) {
            try {
                instance = new ExpenseIncomeAccount();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public double getBalance() {
        return totalIncome - totalExpense;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public int getTransactionsSize() {
        return transactions.size();
    }

}


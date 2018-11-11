package model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.*;
import java.util.ArrayList;

public class Account {

    private static Account instance;

    private ArrayList<Transaction> transactions;
    private double totalIncome;
    private double totalExpense;

    private Connector connector;

    private Account(Connector connector) {
        this.totalExpense = 0;
        this.totalIncome = 0;
        this.transactions = new ArrayList<>();
        this.connector = connector;

        readTransaction();
    }

    private void readTransaction() {
        Response response = null;
        response = connector.readAllTransaction();

        transactions = response.getTransactions();
        totalIncome = response.getTotalIncome();
        totalExpense = response.getTotalExpense();
    }

    private void writeTransaction(Transaction transaction) {
        connector.writeTranaction(transaction);
    }

    public void editTransaction(Transaction transaction) {
        Transaction old = transactions.get(transaction.getId() - 1);

        if (old.getType() == Type.INCOME)
            totalIncome -= old.getAmount();
        else if (old.getType() == Type.EXPENSE)
            totalExpense -= old.getAmount();

        transactions.set(transaction.getId() - 1, transaction);

        if (transaction.getType() == Type.INCOME)
            this.totalIncome += transaction.getAmount();
        else if (transaction.getType() == Type.EXPENSE)
            this.totalExpense += transaction.getAmount();

        connector.editTransaction(transaction);
    }

    public void addExpense(String desc, double amount) {
        Transaction transaction = new Transaction(transactions.size() + 1, LocalDate.now(), Type.EXPENSE, desc, amount);
        totalExpense += transaction.getAmount();
        transactions.add(transaction);
        writeTransaction(transaction);
    }

    public void addIncome(String desc, double amount) {
        Transaction transaction = new Transaction(transactions.size() + 1, LocalDate.now(), Type.INCOME, desc, amount);
        totalIncome += transaction.getAmount();
        transactions.add(transaction);
        writeTransaction(transaction);
    }

    public static Account getInstance() {
        if (instance == null) {
            ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
            instance = (Account) context.getBean("account");
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


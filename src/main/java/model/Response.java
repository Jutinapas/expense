package model;

import java.util.ArrayList;

public class Response {

    private ArrayList<Transaction> transactions;
    private double totalIncome;
    private double totalExpense;

    public Response(ArrayList<Transaction> transactions, double totalIncome, double totalExpense) {
        this.transactions = transactions;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

}

import java.util.ArrayList;

public class ExpenseIncomeAccount {

    private ArrayList<Transaction> transactions;
    private int balance;

    public ExpenseIncomeAccount() {
        this.transactions = new ArrayList<>();
        this.balance = 0;
    }

    public ExpenseIncomeAccount(int balance) {
        this.transactions = new ArrayList<>();
        this.balance = balance;
    }

    public void addExpense(Transaction transaction) {
        this.balance -= transaction.getAmount();
        transactions.add(transaction);
    }

    public void addIncome(Transaction transaction) {
        this.balance += transaction.getAmount();
        transactions.add(transaction);
    }

    public int getBalance() {
        return balance;
    }

}


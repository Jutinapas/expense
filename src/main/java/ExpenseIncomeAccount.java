import java.time.*;
import java.util.ArrayList;

public class ExpenseIncomeAccount {

    private static ExpenseIncomeAccount instance;

    private ArrayList<Transaction> transactions;
    private double totalIncome;
    private double totalExpense;

    private ExpenseIncomeAccount() throws Exception {
        this.totalExpense = 0;
        this.totalIncome = 0;
        this.transactions = new ArrayList<>();
        readTransactionFromFile();
    }

    private void readTransactionFromFile() throws Exception {
        Response response = ApachePoiConnection.getInstance().readTransactionFromFile();
        transactions = response.getTransactions();
        totalIncome = response.getTotalIncome();
        totalExpense = response.getTotalExpense();
    }

    public void writeTransactionToFile(Transaction transaction) throws Exception {
        ApachePoiConnection.getInstance().writeTransactionToFile(transaction);
    }

    public void addExpense(String desc, double amount) throws Exception {
        Transaction transaction = new Transaction(transactions.size() + 1, LocalDate.now(), Transaction.Type.EXPENSE, desc, amount);
        totalExpense += transaction.getAmount();
        transactions.add(transaction);
        writeTransactionToFile(transaction);
    }

    public void addIncome(String desc, double amount) throws Exception {
        Transaction transaction = new Transaction(transactions.size() + 1, LocalDate.now(), Transaction.Type.INCOME, desc, amount);
        totalIncome += transaction.getAmount();
        transactions.add(transaction);
        writeTransactionToFile(transaction);
    }

    public void editTransaction(Transaction transaction) throws Exception {
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

        ApachePoiConnection.getInstance().editTransactionToFile(transaction);
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


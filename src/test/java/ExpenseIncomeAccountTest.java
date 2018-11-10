import model.ExpenseIncomeAccount;
import model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseIncomeAccountTest {

    private ExpenseIncomeAccount account;

    @BeforeEach
    void init() throws Exception {
        account = ExpenseIncomeAccount.getInstance();
        account.addExpense("initExpense", 500);
        account.addIncome("intiIncome", 250);
    }

    @Test
    void testAddIncome() throws Exception {
        double balance = account.getBalance();
        int size = account.getTransactionsSize();
        account.addIncome("testAddIncome", 250);
        assertEquals(balance + 250, account.getBalance());
        assertEquals(size + 1, account.getTransactionsSize());
    }

    @Test
    void testAddExpense() throws Exception {
        double balance = account.getBalance();
        int size = account.getTransactionsSize();
        account.addExpense("testAddExpense", 250);
        assertEquals(balance - 250, account.getBalance());
        assertEquals(size + 1, account.getTransactionsSize());
    }

    @Test
    void testEditTransaction() throws Exception {
        LocalDate temp = LocalDateTime.now().toLocalDate();
        int size = account.getTransactionsSize();
        account.editTransaction(new Transaction(2, temp, Transaction.Type.INCOME, "editedInitIncome", 500));
        Transaction editedTransaction = account.getTransactions().get(1);
        assertEquals(size, account.getTransactionsSize());
        assertEquals("editedInitIncome", editedTransaction.getDescription());
        assertEquals(500, editedTransaction.getAmount());

        account.editTransaction(new Transaction(1, temp, Transaction.Type.EXPENSE, "editedInitExpense", 250));
        editedTransaction = account.getTransactions().get(0);
        assertEquals(size, account.getTransactionsSize());
        assertEquals("editedInitExpense", editedTransaction.getDescription());
        assertEquals(250, editedTransaction.getAmount());
    }

}
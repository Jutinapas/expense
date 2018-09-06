import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseIncomeAccountTest {

    private ExpenseIncomeAccount account;
    private int initialBalance = 2000;

    @BeforeEach
    void init() { account = new ExpenseIncomeAccount(initialBalance); }

    @Test
    void testAddIncome() {
        account.addIncome(new Transaction("testAddIncome", 150, Transaction.Type.INCOME));
        assertEquals(2150, account.getBalance());
    }

    @Test
    void testAddExpense() {
        account.addExpense(new Transaction("testAddExpense", 150, Transaction.Type.EXPENSE));
        assertEquals(1850, account.getBalance());
    }

}
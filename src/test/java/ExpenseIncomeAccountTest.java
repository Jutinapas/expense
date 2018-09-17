import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseIncomeAccountTest {

    private ExpenseIncomeAccount account;
    private int initialBalance = 2000;

    @BeforeEach
    void init() throws Exception { account = new ExpenseIncomeAccount(initialBalance); }

    @Test
    void testAddIncome() throws Exception {
        account.addIncome("testAddIncome", 150);
        assertEquals(2150, account.getBalance());
    }

    @Test
    void testAddExpense() throws Exception {
        account.addExpense("testAddExpense", 150);
        assertEquals(1850, account.getBalance());
    }

}
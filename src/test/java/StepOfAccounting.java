import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.lexer.Tr;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class StepOfAccounting {

    private ExpenseIncomeAccount account;
    private int previousBalance;
    private int previousNumberOfTransaction;
    private Transaction previousTransactionBeforeEdited;
    private Transaction editedTransaction;

    @Given("^a user with balance depend on transactions in expense-income\\.xlsx file$")
    public void account_balance_depend_on_xlsx_file() throws Exception { account = new ExpenseIncomeAccount(); }

    @Given("^a user with balance depend on transactions in expense-income\\.xlsx file having (.*) transaction id (\\d+), description (.*) and amount (\\d+) and (.*) transaction id (\\d+), description (.*) and amount (\\d+)")
    public void account_balance_depend_on_xlsx_file_and_having_transaction(String type1, int id1, String desc1, int amount1, String type2, int id2, String desc2, int amount2) throws Exception {
        account = new ExpenseIncomeAccount();
        if (type1.equals("income"))
            account.getTransactions().add(new Transaction(id1, LocalDateTime.now().toLocalDate(), Transaction.Type.INCOME, desc1, amount1));
        else if (type1.equals("expense"))
            account.getTransactions().add(new Transaction(id1, LocalDateTime.now().toLocalDate(), Transaction.Type.EXPENSE, desc1, amount1));
        if (type2.equals("income"))
            account.getTransactions().add(new Transaction(id2, LocalDateTime.now().toLocalDate(), Transaction.Type.INCOME, desc2, amount2));
        else if (type2.equals("expense"))
            account.getTransactions().add(new Transaction(id2, LocalDateTime.now().toLocalDate(), Transaction.Type.EXPENSE, desc2, amount2));
    }

    @When("^I accounting expense for (.*) with (\\d+) bath$")
    public void add_expense(String desc, int amount) throws Exception {
        previousBalance = account.getBalance();
        account.addExpense(desc, amount);
    }

    @When("^I accounting income about (.*) with (\\d+) bath$")
    public void add_income(String desc, int amount) throws Exception {
        previousBalance = account.getBalance();
        account.addIncome(desc, amount);
    }

    @When("^I edit description and amount of (.*) transaction in my account that have id (\\d+) to (.*) and (\\d+)$")
    public void transaction_in_my_account_have_been_edited(Transaction.Type type, int id, String desc, int amount) throws Exception {
        previousTransactionBeforeEdited = account.getTransactions().get(id - 1);
        previousNumberOfTransaction = account.getTransactionsSize();
        account.editTransaction(new Transaction(id, previousTransactionBeforeEdited.getDate(), type, desc, amount));
        editedTransaction = account.getTransactions().get(id - 1);
    }


    @Then("^my account balance is previous balance \\- (.*)$")
    public void my_account_balance_is_decrease(int amount) {
        assertEquals(previousBalance - amount, account.getBalance());
    }

    @Then("^my account balance is previous balance \\+ (\\d+)$")
    public void my_account_balance_is_increase(int amount) {
        assertEquals(previousBalance + amount, account.getBalance());
    }

    @Then("^description and amount of (.*) transaction in my account that have id (\\d+) is (.*) and (\\d+)$")
    public void transaction_that_edited_have_been_edited(String type, int id, String desc, int amount) {
        assertEquals(previousNumberOfTransaction, account.getTransactionsSize());
        assertEquals(desc, account.getTransactions().get(id - 1).getDescription());
        assertEquals(amount, account.getTransactions().get(id - 1).getAmount());
    }

}

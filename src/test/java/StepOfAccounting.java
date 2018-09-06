import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class StepOfAccounting {

    private ExpenseIncomeAccount account;

    @Given("a user with no balance remain")
    public void with_no_balance_remain() { account = new ExpenseIncomeAccount(); }

    @Given("a user with (\\d+) balance exists")
    public void with_balance_exitst(int balance) { account = new ExpenseIncomeAccount(balance); }

    @When("I accounting expense for (.*) with (\\d+) bath")
    public void add_expense(String desc, int amount) { account.addExpense(new Transaction(desc, amount, Transaction.Type.EXPENSE)); }

    @When("I accounting income about (.*) with (\\d+) bath")
    public void add_income(String desc, int amount) { account.addIncome(new Transaction(desc, amount, Transaction.Type.INCOME)); }

    @Then("my account balance is (.*)")
    public void my_account_balance_is(int balance) { assertEquals(balance, account.getBalance()); }


}

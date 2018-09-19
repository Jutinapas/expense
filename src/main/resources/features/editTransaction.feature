Feature: add income
    As a user
    I want to edit my income or expense transaction

Background:
    Given a user with balance depend on transactions in expense-income.xlsx file having income transaction id 1, description salary and amount 20000 and expense transaction id 2, description eating lunch and amount 500

Scenario: editing income
    When I edit description and amount of income transaction in my account that have id 1 to first salary and 25000
    Then description and amount of income transaction in my account that have id 1 is first salary and 25000

Scenario: editing expense
    When I edit description and amount of expense transaction in my account that have id 2 to eating lunch in the morning and 200
    Then description and amount of expense transaction in my account that have id 2 is eating lunch in the morning and 200


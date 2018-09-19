Feature: add expense
    As a user
    I want to accounting my expense

Background:
    Given a user with balance depend on transactions in expense-income.xlsx file

Scenario: accounting expense
    When I accounting expense for eating lunch with 500 bath
    Then my account balance is previous balance - 500
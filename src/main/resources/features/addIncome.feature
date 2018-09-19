Feature: add income
    As a user
    I want to accounting my income

Background:
    Given a user with balance depend on transactions in expense-income.xlsx file

Scenario: accounting income
    When I accounting income about salary with 20000 bath
    Then my account balance is previous balance + 20000
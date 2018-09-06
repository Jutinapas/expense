Feature: add expense
    As a user
    I want to accounting my expense

Background:
    Given a user with 2000 balance exists

Scenario: accounting expense
    When I accounting expense for eating lunch with 500 bath
    Then my account balance is 1500
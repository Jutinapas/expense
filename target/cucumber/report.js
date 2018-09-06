$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("features/addExpense.feature");
formatter.feature({
  "line": 1,
  "name": "add expense",
  "description": "  As a user\r\n  I want to accounting my expense",
  "id": "add-expense",
  "keyword": "Feature"
});
formatter.background({
  "line": 5,
  "name": "",
  "description": "",
  "type": "background",
  "keyword": "Background"
});
formatter.step({
  "line": 6,
  "name": "a user with 2000 balance exists",
  "keyword": "Given "
});
formatter.match({
  "arguments": [
    {
      "val": "2000",
      "offset": 12
    }
  ],
  "location": "StepOfAccounting.with_balance_exitst(int)"
});
formatter.result({
  "duration": 94979021,
  "status": "passed"
});
formatter.scenario({
  "line": 8,
  "name": "accounting expense",
  "description": "",
  "id": "add-expense;accounting-expense",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 9,
  "name": "I accounting expense for eating lunch with 500 bath",
  "keyword": "When "
});
formatter.step({
  "line": 10,
  "name": "my account balance is 1500",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "eating lunch",
      "offset": 25
    },
    {
      "val": "500",
      "offset": 43
    }
  ],
  "location": "StepOfAccounting.add_expense(String,int)"
});
formatter.result({
  "duration": 6789611,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "1500",
      "offset": 22
    }
  ],
  "location": "StepOfAccounting.my_account_balance_is(int)"
});
formatter.result({
  "duration": 40450287,
  "status": "passed"
});
});
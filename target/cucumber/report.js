$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("features/editTransaction.feature");
formatter.feature({
  "line": 1,
  "name": "add income",
  "description": "  As a user\r\n  I want to edit my income or expense transaction",
  "id": "add-income",
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
  "name": "a user with balance depend on transactions in expense-income.xlsx file having income transaction id 1, description salary and amount 20000 and expense transaction id 2, description eating lunch and amount 500",
  "keyword": "Given "
});
formatter.match({
  "arguments": [
    {
      "val": "income",
      "offset": 78
    },
    {
      "val": "1",
      "offset": 100
    },
    {
      "val": "salary",
      "offset": 115
    },
    {
      "val": "20000",
      "offset": 133
    },
    {
      "val": "expense",
      "offset": 143
    },
    {
      "val": "2",
      "offset": 166
    },
    {
      "val": "eating lunch",
      "offset": 181
    },
    {
      "val": "500",
      "offset": 205
    }
  ],
  "location": "StepOfAccounting.account_balance_depend_on_xlsx_file_and_having_transaction(String,int,String,int,String,int,String,int)"
});
formatter.result({
  "duration": 680330736,
  "status": "passed"
});
formatter.scenario({
  "line": 8,
  "name": "editing income",
  "description": "",
  "id": "add-income;editing-income",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 9,
  "name": "I edit description and amount of income transaction in my account that have id 1 to first salary and 25000",
  "keyword": "When "
});
formatter.step({
  "line": 10,
  "name": "description and amount of income transaction in my account that have id 1 is first salary and 25000",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "income",
      "offset": 33
    },
    {
      "val": "1",
      "offset": 79
    },
    {
      "val": "first salary",
      "offset": 84
    },
    {
      "val": "25000",
      "offset": 101
    }
  ],
  "location": "StepOfAccounting.transaction_in_my_account_have_been_edited(Transaction$Type,int,String,int)"
});
formatter.result({
  "duration": 100267266,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "income",
      "offset": 26
    },
    {
      "val": "1",
      "offset": 72
    },
    {
      "val": "first salary",
      "offset": 77
    },
    {
      "val": "25000",
      "offset": 94
    }
  ],
  "location": "StepOfAccounting.transaction_that_edited_have_been_edited(String,int,String,int)"
});
formatter.result({
  "duration": 3702716,
  "status": "passed"
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
  "name": "a user with balance depend on transactions in expense-income.xlsx file having income transaction id 1, description salary and amount 20000 and expense transaction id 2, description eating lunch and amount 500",
  "keyword": "Given "
});
formatter.match({
  "arguments": [
    {
      "val": "income",
      "offset": 78
    },
    {
      "val": "1",
      "offset": 100
    },
    {
      "val": "salary",
      "offset": 115
    },
    {
      "val": "20000",
      "offset": 133
    },
    {
      "val": "expense",
      "offset": 143
    },
    {
      "val": "2",
      "offset": 166
    },
    {
      "val": "eating lunch",
      "offset": 181
    },
    {
      "val": "500",
      "offset": 205
    }
  ],
  "location": "StepOfAccounting.account_balance_depend_on_xlsx_file_and_having_transaction(String,int,String,int,String,int,String,int)"
});
formatter.result({
  "duration": 18112473,
  "status": "passed"
});
formatter.scenario({
  "line": 12,
  "name": "editing expense",
  "description": "",
  "id": "add-income;editing-expense",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 13,
  "name": "I edit description and amount of expense transaction in my account that have id 2 to eating lunch in the morning and 200",
  "keyword": "When "
});
formatter.step({
  "line": 14,
  "name": "description and amount of expense transaction in my account that have id 2 is eating lunch in the morning and 200",
  "keyword": "Then "
});
formatter.match({
  "arguments": [
    {
      "val": "expense",
      "offset": 33
    },
    {
      "val": "2",
      "offset": 80
    },
    {
      "val": "eating lunch in the morning",
      "offset": 85
    },
    {
      "val": "200",
      "offset": 117
    }
  ],
  "location": "StepOfAccounting.transaction_in_my_account_have_been_edited(Transaction$Type,int,String,int)"
});
formatter.result({
  "duration": 36567753,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "expense",
      "offset": 26
    },
    {
      "val": "2",
      "offset": 73
    },
    {
      "val": "eating lunch in the morning",
      "offset": 78
    },
    {
      "val": "200",
      "offset": 110
    }
  ],
  "location": "StepOfAccounting.transaction_that_edited_have_been_edited(String,int,String,int)"
});
formatter.result({
  "duration": 113310,
  "status": "passed"
});
});
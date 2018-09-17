import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.format.DateTimeFormatter;

public class ExpenseIncomeAccountController {

    private ExpenseIncomeAccount account;
    private ObservableList<Record> records;

    @FXML
    private Label balanceLabel;
    @FXML
    private TableView<Record> tableView;
    @FXML
    private TableColumn<Record, String> dateColumn, typeColumn, descColumn, amountColumn;
    @FXML
    private TextField descField, amountField;

    public class Record {

        private String description, amount, type, date;

        public Record(Transaction transaction) {
            this.description = transaction.getDescription();
            this.amount = transaction.getAmount() + "";
            this.type = transaction.getType().toString();
            this.date = transaction.getDate().format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy"));
        }

        public String getDescription() {
            return description;
        }

        public String getAmount() {
            return amount;
        }

        public String getType() {
            return type;
        }

        public String getDate() {
            return date;
        }

    }

    public void initialize() throws Exception {
        account = new ExpenseIncomeAccount(0);
        records = FXCollections.observableArrayList();
        for (Transaction transaction: account.getTransactions()) {
            records.add(new Record(transaction));
        }
        dateColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("date"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("type"));
        descColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("description"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("amount"));
        tableView.setItems(records);
        setBalanceLabel();
    }

    @FXML
    private void setBalanceLabel() {
        balanceLabel.setText("Current Balance : " + account.getBalance());
    }

    @FXML
    private void updateTable(Transaction transaction) {
        records.add(new Record(transaction));
        tableView.setItems(records);
        setBalanceLabel();
    }

    @FXML
    public void handleIncomeButton() throws Exception {
        if (!descField.getText().trim().equals("") && !amountField.getText().trim().equals("")) {
            account.addIncome(descField.getText(), Integer.parseInt(amountField.getText()));
            updateTable(account.getTransactions().get(account.getTransactionsSize() - 1));
            clearTextField();
        }
    }

    @FXML
    public void handleExpenseButton() throws Exception {
        if (!descField.getText().trim().equals("") && !amountField.getText().trim().equals("")) {
            account.addExpense(descField.getText(), Integer.parseInt(amountField.getText()));
            updateTable(account.getTransactions().get(account.getTransactionsSize() - 1));
            clearTextField();
        }
    }

    @FXML
    private void clearTextField() {
        descField.setText(""); amountField.setText("");
    }

}

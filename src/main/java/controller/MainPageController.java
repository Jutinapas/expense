package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Account;
import model.Transaction;

import java.io.IOException;

public class MainPageController {

    private ObservableList<Record> records;

    @FXML
    private Pane mainPagePane;
    @FXML
    private Label balanceLabel;
    @FXML
    private Label totalExpenseLabel;
    @FXML
    private Label totalIncomeLabel;
    @FXML
    private TableView<Record> tableView;
    @FXML
    private TableColumn<Record, String> dateColumn, typeColumn, descColumn, amountColumn;
    @FXML
    private TextField descField;
    @FXML
    private Spinner<Double> amountField;

    public static class Record {

        private String description, amount, type, date;

        public Record(Transaction transaction) {
            this.description = transaction.getDescription();
            this.amount = transaction.getAmount() + "";
            this.type = transaction.getType().toString();
            this.date = transaction.getDateToString();
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

    public void initialize() {
        records = FXCollections.observableArrayList();
        for (Transaction transaction: Account.getInstance().getTransactions()) {
            records.add(new Record(transaction));
        }
        dateColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("date"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("type"));
        descColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("description"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("amount"));
        tableView.setItems(records);
        setBalanceLabel();
        tableView.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                onClickedRecord();
            }
        });
        SpinnerValueFactory factory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE);
        amountField.setValueFactory(factory);
        amountField.setEditable(true);
        amountField.getValueFactory().setValue(0.00);
        TextFormatter formatter = new TextFormatter(factory.getConverter(), factory.getValue());
        amountField.getEditor().setTextFormatter(formatter);
        factory.valueProperty().bindBidirectional(formatter.valueProperty());
    }

    @FXML
    private void setBalanceLabel() {
        balanceLabel.setText("Current Balance : " + Account.getInstance().getBalance());
        totalIncomeLabel.setText("Total Income  : " + Account.getInstance().getTotalIncome());
        totalExpenseLabel.setText("Total Expense : " + Account.getInstance().getTotalExpense());
    }

    @FXML
    private void updateTable(Transaction transaction) {
        records = FXCollections.observableArrayList();
        for (Transaction temp: Account.getInstance().getTransactions()) {
            records.add(new Record(temp));
        }
        tableView.setItems(records);
        setBalanceLabel();
    }

    private void onClickedRecord() {
        ObservableList<Record>  allRecords = tableView.getItems(), selectedRecord = tableView.getSelectionModel().getSelectedItems();
        if (selectedRecord.size() > 0)
            popupTransaction(Account.getInstance().getTransactions().get(allRecords.indexOf(selectedRecord.get(0))));
    }

    private void popupTransaction(Transaction transaction) {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editPage.fxml"));
        try {
            stage.setScene(new Scene((Parent) loader.load()));
            stage.setResizable(false);
            EditPageController controller = loader.getController();
            controller.setTransaction(transaction);
            stage.initOwner(mainPagePane.getScene().getWindow());
            stage.setTitle(transaction.getDescription());
            stage.initModality(Modality.NONE);
            stage.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleIncomeButton() {
        if (!descField.getText().trim().equals("") && amountField.getValue() != null) {
            Account.getInstance().addIncome(descField.getText(), amountField.getValue());
            updateTable(Account.getInstance().getTransactions().get(Account.getInstance().getTransactionsSize() - 1));
            clearTextField();
        }
    }

    @FXML
    public void handleExpenseButton() {
        if (!descField.getText().trim().equals("") && amountField.getValue() != null ) {
            Account.getInstance().addExpense(descField.getText(), amountField.getValue());
            updateTable(Account.getInstance().getTransactions().get(Account.getInstance().getTransactionsSize() - 1));
            clearTextField();
        }
    }

    @FXML
    public void handleRefreshButton() {
        records = FXCollections.observableArrayList();
        for (Transaction transaction: Account.getInstance().getTransactions()) {
            records.add(new Record(transaction));
        }
        tableView.setItems(records);
        setBalanceLabel();
    }

    @FXML
    private void clearTextField() {
        descField.setText("");
        amountField.getValueFactory().setValue(0.00);
    }

}

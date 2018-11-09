import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EditPageController {

    private Transaction transaction;

    @FXML
    private Pane editPagePane;
    @FXML
    private DatePicker dateField;
    @FXML
    private TextArea descField;
    @FXML
    private Spinner<Double> amountField;
    @FXML
    private Button editButton;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @FXML
    public void initialize() {
        Platform.runLater(new Runnable() {
            public void run() {
                dateField.setValue(transaction.getDate());
                descField.setText(transaction.getDescription());
                SpinnerValueFactory factory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE);
                amountField.setValueFactory(factory);
                amountField.setEditable(true);
                amountField.getValueFactory().setValue(transaction.getAmount());
                TextFormatter formatter = new TextFormatter(factory.getConverter(), factory.getValue());
                amountField.getEditor().setTextFormatter(formatter);
                factory.valueProperty().bindBidirectional(formatter.valueProperty());
            }
        });
    }

    @FXML
    public void handleEditButton() throws Exception {
        if (!dateField.getValue().toString().trim().equals("") && !descField.getText().trim().equals("") && amountField != null) {
            ExpenseIncomeAccount.getInstance().editTransaction(new Transaction(transaction.getId(), dateField.getValue(), transaction.getType(), descField.getText(), amountField.getValueFactory().getValue()));
            Stage stage = (Stage) editPagePane.getScene().getWindow();
            stage.close();
        }
    }

}

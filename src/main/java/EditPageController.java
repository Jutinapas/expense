import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private TextField amountField;
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
                amountField.setText(transaction.getAmount() + "");
            }
        });
    }

    @FXML
    public void handleEditButton() throws Exception {
        if (!dateField.getValue().toString().trim().equals("") && !descField.getText().trim().equals("") && !amountField.getText().trim().equals("")) {
            Main.account.editTransaction(new Transaction(transaction.getId(), dateField.getValue(), transaction.getType(), descField.getText(), Integer.parseInt(amountField.getText())));
            Stage stage = (Stage) editPagePane.getScene().getWindow();
            stage.close();
        }
    }

}

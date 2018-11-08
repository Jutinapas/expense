import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    static ExpenseIncomeAccount account;

    @Override
    public void start(Stage primaryStage) throws Exception{
        account = new ExpenseIncomeAccount();
        Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Expense-Income Account");
        primaryStage.setScene(new Scene(root, 1000, 780));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

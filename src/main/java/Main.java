import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Account;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/mainPage.fxml"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Expense-Income Account");
        primaryStage.setScene(new Scene(root, 1000, 780));
        primaryStage.show();
    }

    public static Account instance;

    public static void main(String[] args) {
        launch(args);
    }

}

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class JdbcSQLiteConnection {

    private static Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:account.db";
            connection = DriverManager.getConnection(dbURL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public ArrayList<Transaction> getAllTransaction() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        Connection connection = connect();
        try {
            if (connection != null) {
                String query = "Select * from account_transaction";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    LocalDate date = LocalDate.parse(resultSet.getString(2));
                    Transaction.Type type = (resultSet.getInt(3) == 1) ? Transaction.Type.EXPENSE: Transaction.Type.INCOME;
                    String desc = resultSet.getString(4);
                    Double amount = resultSet.getDouble(5);
                    transactions.add(new Transaction(id, date, type, desc, amount));
                }
                connection.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return transactions;
    }

}
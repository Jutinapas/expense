package connector;

import model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class JdbcSQLiteConnection implements Connector {

    private static Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:./src/main/resources/account.db";
            connection = DriverManager.getConnection(dbURL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public Response readAllTransaction() {
        double totalIncome = 0, totalExpense = 0;
        ArrayList<Transaction> transactions = new ArrayList<>();

        Connection connection = connect();
        try {
            if (connection != null) {
                String query = "SELECT * FROM account_transaction";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    LocalDate date = LocalDate.parse(resultSet.getString(2));
                    Type type = null;
                    if (resultSet.getInt(3) == 0)
                        type = Type.INCOME;
                    else if (resultSet.getInt(3) == 1)
                        type = Type.EXPENSE;
                    String desc = resultSet.getString(4);
                    Double amount = resultSet.getDouble(5);
                    transactions.add(new Transaction(id, date, type, desc, amount));

                    if (type == Type.INCOME)
                        totalIncome += amount;
                    else if (type == Type.EXPENSE)
                        totalExpense += amount;
                }
                connection.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }

        return new Response(transactions, totalIncome, totalExpense);
    }

    @Override
    public void writeTransaction(Transaction transaction) {
        Connection connection = connect();
        try {
            if (connection != null) {
                String query = "INSERT INTO account_transaction(id, date, type, description, amount) VALUES(?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, transaction.getId());
                statement.setString(2, transaction.getDate().toString());
                if (transaction.getType() == Type.INCOME)
                    statement.setInt(3, 0);
                else if (transaction.getType() == Type.EXPENSE)
                    statement.setInt(3, 1);
                statement.setString(4, transaction.getDescription());
                statement.setDouble(5, transaction.getAmount());
                statement.executeUpdate();
                connection.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    @Override
    public void editTransaction(Transaction transaction) {
        Connection connection = connect();
        try {
            if (connection != null) {
                String query = "UPDATE account_transaction SET id = ? , date = ? , description = ? , amount = ? WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, transaction.getId());
                statement.setString(2, transaction.getDate().toString());
                statement.setString(3, transaction.getDescription());
                statement.setDouble(4, transaction.getAmount());
                statement.setInt(5, transaction.getId());
                statement.executeUpdate();
                connection.close();
            }
        }catch (SQLException e){
            System.out.println(e);
        }
    }

}
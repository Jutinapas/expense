package connector;

import model.Connector;
import model.Response;
import model.Transaction;
import model.Type;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class SpringJdbcConnection implements Connector {

    private JdbcTemplate jdbcTemplate;

    public SpringJdbcConnection(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Response readAllTransaction() {
        String query = "SELECT * FROM account_transaction";
        double totalIncome = 0, totalExpense = 0;
        ArrayList<Transaction> transactions = (ArrayList<Transaction>) jdbcTemplate.query(query, new TransactionRowMapper());

        for (Transaction transaction : transactions) {
            if (transaction.getType() == Type.INCOME)
                totalIncome += transaction.getAmount();
            else if (transaction.getType() == Type.EXPENSE)
                totalExpense += transaction.getAmount();
        }

        return new Response(transactions, totalIncome, totalExpense);
    }

    @Override
    public void writeTransaction(Transaction transaction) {
        String query = "INSERT INTO account_transaction(id, date, type, description, amount) VALUES(?, ?, ?, ?, ?)";
        Object[] data = new Object[] {transaction.getId(), transaction.getDate(), transaction.getType(), transaction.getDescription(), transaction.getAmount() };
        jdbcTemplate.update(query, data);
    }

    @Override
    public void editTransaction(Transaction transaction) {
        String query = "UPDATE account_transaction SET id = ? , date = ? , description = ? , amount = ? WHERE id = ?";
        Object[] data = new Object[] { transaction.getId(), transaction.getDate(), transaction.getDescription(), transaction.getAmount(), transaction.getId() };
        jdbcTemplate.update(query, data);
    }

    class TransactionRowMapper implements RowMapper<Transaction> {
        public Transaction mapRow(ResultSet resultSet, int rowNum)
                throws SQLException {
            int id = resultSet.getInt(1);
            LocalDate date = LocalDate.parse(resultSet.getString(2));
            Type type = null;
            if (resultSet.getInt(3) == 0)
                type = Type.INCOME;
            else if (resultSet.getInt(3) == 1)
                type = Type.EXPENSE;
            String desc = resultSet.getString(4);
            Double amount = resultSet.getDouble(5);
            return new Transaction(id, date, type, desc, amount);
        }
    }

}

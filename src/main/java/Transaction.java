import java.time.LocalDate;

public class Transaction {

    enum Type { EXPENSE, INCOME };

    private int id;
    private LocalDate date;
    private Type type;
    private String description;
    private double amount;

    public Transaction(int id, LocalDate date, Type type, String description, double amount) {
        this.id = id; this.date = date; this.type = type; this.description = description; this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public Type getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

}

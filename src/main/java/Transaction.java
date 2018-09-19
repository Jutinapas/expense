import java.time.LocalDate;

public class Transaction {

    enum Type { EXPENSE, INCOME };

    private int id;
    private LocalDate date;
    private Type type;
    private String description;
    private int amount;

    public Transaction(int id, LocalDate date, Type type, String description, int amount) {
        this.id = id; this.date = date; this.type = type; this.description = description; this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public Type getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

}

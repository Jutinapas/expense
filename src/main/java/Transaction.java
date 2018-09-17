import java.time.LocalDateTime;

public class Transaction {

    enum Type { EXPENSE, INCOME };

    private int id;
    private LocalDateTime date;
    private Type type;
    private String description;
    private int amount;

    public Transaction(int id, LocalDateTime date, Type type, String description, int amount) {
        this.id = id; this.date = date; this.type = type; this.description = description; this.amount = amount;
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

    public LocalDateTime getDate() {
        return date;
    }

}

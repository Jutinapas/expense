import java.time.LocalDateTime;

public class Transaction {

    enum Type { EXPENSE, INCOME };

    private String description;
    private int amount;
    private Type type;
    private LocalDateTime date;

    public Transaction(String description, int amount, Type type) {
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.date = LocalDateTime.now();
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

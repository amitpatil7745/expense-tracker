package expenseTracker;

import java.time.LocalDate;

public class Transaction {
    private String type;      // Income or Expense
    private String category;  // e.g., Salary, Rent
    private double amount;
    private LocalDate date;

    public Transaction(String type, String category, double amount, LocalDate date) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String toFileFormat() {
        return type + "," + category + "," + amount + "," + date;
    }

    @Override
    public String toString() {
        return String.format("%s - %s: %.2f on %s", type, category, amount, date);
    }
}

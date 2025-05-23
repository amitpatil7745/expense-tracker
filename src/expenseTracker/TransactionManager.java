package expenseTracker;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TransactionManager {
    private List<Transaction> transactions;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public TransactionManager() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        System.out.println("Transaction added!");
    }

   public void loadFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String type = parts[0];
                String category = parts[1];
                double amount = Double.parseDouble(parts[2]);
                LocalDate date = LocalDate.parse(parts[3], formatter);

                Transaction transaction = new Transaction(type, category, amount, date);
                transactions.add(transaction);
            }
            System.out.println("Transactions loaded from file.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
  
/*
    public void saveToFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Transaction t : transactions) {
                writer.println(t.toFileFormat());
            }
            System.out.println("Transactions saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
*/
    public void saveToFile(String filePath) {
    	//filePath = "db/transaction.txt";
        if (transactions.isEmpty()) {
            System.out.println(" No transactions to save.");
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, false))) {
            for (Transaction t : transactions) {
                writer.println(t.toFileFormat());  
            }
            System.out.println(" Transactions saved to file: " + filePath);
        } catch (IOException e) {
            System.out.println(" Error saving file: " + e.getMessage());
        }
    }

    public void showMonthlySummary() {
        Map<String, Double> incomeMap = new HashMap<>();
        Map<String, Double> expenseMap = new HashMap<>();
        double totalIncome = 0, totalExpense = 0;
/*
        for (Transaction t : transactions) {
            String key = t.getDate().getMonth() + " " + t.getDate().getYear();
            if (t.getType().equalsIgnoreCase("Income")) {
                incomeMap.put(key, incomeMap.getOrDefault(key, 0.0) + t.getAmount());
                totalIncome += t.getAmount();
            } else {
                expenseMap.put(key, expenseMap.getOrDefault(key, 0.0) + t.getAmount());
                totalExpense += t.getAmount();
            }
        }
*/
        for (Transaction t : transactions) {
            System.out.println("DEBUG: " + t.getType() + " | " + t.getCategory() + " | " + t.getAmount() + " | " + t.getDate());

            String monthKey = t.getDate().getMonth().toString() + " " + t.getDate().getYear();

            if (t.getType().equalsIgnoreCase("Income")) {
                incomeMap.put(monthKey, incomeMap.getOrDefault(monthKey, 0.0) + t.getAmount());
                totalIncome += t.getAmount();
            } else if (t.getType().equalsIgnoreCase("Expense")) {
                expenseMap.put(monthKey, expenseMap.getOrDefault(monthKey, 0.0) + t.getAmount());
                totalExpense += t.getAmount();
            } else {
                System.out.println(" Unknown type: " + t.getType());
            }
        }

        System.out.println("\n--- Monthly Summary ---");
        for (String month : incomeMap.keySet()) {
            System.out.println(month + ": Income = " + incomeMap.get(month)
                    + ", Expense = " + expenseMap.getOrDefault(month, 0.0));
        }

        System.out.println("\nTotal Income: " + totalIncome);
        System.out.println("Total Expense: " + totalExpense);
        System.out.println("Net Savings: " + (totalIncome - totalExpense));
    }
}

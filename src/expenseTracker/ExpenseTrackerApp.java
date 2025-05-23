package expenseTracker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ExpenseTrackerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TransactionManager manager = new TransactionManager();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        boolean exit = false;

        while (!exit) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Transaction");
            System.out.println("2. Load from File");
            System.out.println("3. View Monthly Summary");
            System.out.println("4. Save and Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter type (Income/Expense): ");
                    String type = scanner.nextLine();

                    System.out.print("Enter category (e.g., Salary, Rent): ");
                    String category = scanner.nextLine();

                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // consume newline

                    System.out.print("Enter date (yyyy-MM-dd): ");
                    String dateStr = scanner.nextLine();
                    LocalDate date = LocalDate.parse(dateStr, formatter);

                    Transaction t = new Transaction(type, category, amount, date);
                    manager.addTransaction(t);
                    System.out.println("Transaction added.");
                    break;

                case 2:
                    System.out.print("Enter file path to load: ");
                    String path = scanner.nextLine();
                    manager.loadFromFile(path);
                    break;

                case 3:
                    manager.showMonthlySummary();
                    break;

               /* case 4:
                    manager.saveToFile("saved_transactions.txt");
                    exit = true;
                    break;*/
                	
                case 4:
                    System.out.print("Enter file name to save (e.g., saved_transactions.txt): ");
                    String savePath = scanner.nextLine();
                    manager.saveToFile(savePath);
                    exit = true;
                    break;


                default:
                    System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}

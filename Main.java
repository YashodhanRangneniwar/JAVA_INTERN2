import java.io.*;
import java.util.*;

class User 
{
    private String username;
    private String password;

    public User(String username, String password) 
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername() 
    {
        return username;
    }

    public boolean checkPassword(String password) 
    {
        return this.password.equals(password);
    }
}

class Expense 
{
    private String date;
    private String category;
    private double amount;

    public Expense(String date, String category, double amount) 
    {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() 
    {
        return category;
    }

    public double getAmount() 
    {
        return amount;
    }

    //@Override
    public String toString() 
    {
        return "Date: " + date + ", Category: " + category + ", Amount: $" + amount;
    }
}

class ExpenseTracker 
{
    private Map<String, User> users = new HashMap<>();
    private List<Expense> expenses = new ArrayList<>();
    private User loggedInUser;
    public boolean register(String username, String password) 
    {
        if (users.containsKey(username)) 
        {
            return false; 
        }
        users.put(username, new User(username, password));
        return true;
    }
    public boolean login(String username, String password) 
    {
        if (users.containsKey(username) && users.get(username).checkPassword(password)) 
        {
            loggedInUser = users.get(username);
            return true;
        }
        return false;
    }
    public void addExpense(String date, String category, double amount) 
    {
        expenses.add(new Expense(date, category, amount));
    }
    public void listExpenses() 
    {
        for (Expense expense : expenses) 
        {
            System.out.println(expense);
        }
    }
    public void calculateCategorySum() 
    {
        Map<String, Double> categorySums = new HashMap<>();
        for (Expense expense : expenses) 
        {
            categorySums.put(expense.getCategory(), categorySums.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
        }

        System.out.println("Category-wise total expenses:");
        for (String category : categorySums.keySet()) 
        {
            System.out.println(category + ": $" + categorySums.get(category));
        }
    }
    public void saveToFile(String fileName) throws IOException 
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (Expense expense : expenses) 
        {
            writer.write(expense.toString() + "\n");
        }
        writer.close();
    }
    public void loadFromFile(String fileName) throws IOException 
    {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) 
        {
            System.out.println(line);
        }
        reader.close();
    }
}

public class Main 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        ExpenseTracker tracker = new ExpenseTracker();
        boolean running = true;

        while (running) 
        {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Add Expense");
            System.out.println("4. List Expenses");
            System.out.println("5. Calculate Category-wise Summation");
            System.out.println("6. Save Expenses");
            System.out.println("7. Load Expenses");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) 
            {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    if (tracker.register(username, password)) 
                    {
                        System.out.println("User registered successfully!");
                    } else 
                    {
                        System.out.println("User already exists.");
                    }
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                    if (tracker.login(username, password)) 
                    {
                        System.out.println("Login successful!");
                    } else 
                    {
                        System.out.println("Invalid username or password.");
                    }
                    break;

                case 3:
                    System.out.print("Enter date (yyyy-mm-dd): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); 
                    tracker.addExpense(date, category, amount);
                    System.out.println("Expense added successfully.");
                    break;

                case 4:
                    tracker.listExpenses();
                    break;

                case 5:
                    tracker.calculateCategorySum();
                    break;

                case 6:
                    try 
                    {
                        tracker.saveToFile("expenses.txt");
                        System.out.println("Expenses saved to file.");
                    } catch (IOException e) 
                    {
                        System.out.println("Error saving to file.");
                    }
                    break;

                case 7:
                    try 
                    {
                        tracker.loadFromFile("expenses.txt");
                    } catch (IOException e) 
                    {
                        System.out.println("Error loading from file.");
                    }
                    break;

                case 8:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}

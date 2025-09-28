package atm;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

// Main system controller
public class Menu {
    private utilities.Scanner  input;// Input reader
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private Map<Integer, Customer> customers = new HashMap<>(); // Customer database
    private static final String DATA_FILE = "customers.txt"; // Storage file

    public Menu() {
        input = new utilities.Scanner (System.in);         // Initialize input
    }

    // Load customers from text file
    public void loadCustomers() {
        try (utilities.Scanner scanner = new utilities.Scanner(new FileReader(DATA_FILE))) {
            String line;
            Customer currentCustomer = null;

            while ((line = scanner.nextLine()) != null) {
                String[] parts = line.split(":");

                if (parts[0].equals("CUSTOMER")) {
                    int custNumber = Integer.parseInt(parts[1]);
                    int pin = Integer.parseInt(parts[2]);
                    currentCustomer = new Customer(custNumber, pin);
                    customers.put(custNumber, currentCustomer);
                }
                else if (parts[0].equals("ACCOUNT")) {
                    String type = parts[1];
                    double balance = Double.parseDouble(parts[2]);

                    Account acc = type.equals("CHECKING") ?
                            new CheckingAccount(balance) : new SavingsAccount(balance);
                    currentCustomer.addAccount(acc);
                }
            }
            System.out.println("Data loaded successfully. Customers: " + customers.size());
        } catch (FileNotFoundException e) {
            System.out.println("No existing data found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Data format error. Starting fresh.");
        }
    }

    // Save customers to text file
    public void saveCustomers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Customer customer : customers.values()) {
                // Write customer info
                writer.println("CUSTOMER:" + customer.getCustomerNumber() + ":" + customer.getPinNumber());

                // Write all accounts
                for (Account acc : customer.getAccounts()) {
                    String type = (acc instanceof CheckingAccount) ? "CHECKING" : "SAVINGS";
                    writer.println("ACCOUNT:" + type + ":" + acc.getBalance());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Get valid integer input with retry
    private int getValidInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return input.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    // Get valid double input with retry
    private double getValidDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return input.nextDouble();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid amount.");
            }
        }
    }

    // Main menu loop
    public void mainMenu() {
        boolean end = false;
        while (!end) {
            System.out.println("\n=== ATM Main Menu ===");
            System.out.println("1 - Login");
            System.out.println("2 - Create Account");
            System.out.println("3 - Exit");
            
            int choice = getValidInt("\nChoice: ");
            
            switch (choice) {
                case 1: login(); break;             // Go to login
                case 2: createAccount(); break;     // Go to account creation
                case 3: 
                    end = true;                     // Exit program
                    System.out.println("Thank you for using our ATM!  ");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Handle customer login
    private void login() {
        int attempts = 0;

        while (attempts < MAX_LOGIN_ATTEMPTS) {
            int customerNumber = getValidInt("Enter customer number: ");
            int pinNumber = getValidInt("Enter PIN: ");

            Customer customer = customers.get(customerNumber);
            if (customer != null && customer.getPinNumber() == pinNumber) {
                System.out.println("Login successful!");
                selectAccount(customer);
                return;
            } else {
                attempts++;
                int remaining = MAX_LOGIN_ATTEMPTS - attempts;

                if (remaining > 0) {
                    System.out.println("Invalid credentials. " + remaining + " attempts remaining.");
                } else {
                    System.out.println("Too many failed attempts. Returning to main menu.");
                }
            }
        }
    }

    // Create new customer account
    private void createAccount() {
        int customerNumber = getValidInt("Enter customer number: ");
        if (customerNumber <= 0) {
            System.out.println("Customer number must be positive.");
            return;
        }
        
        int pin = getValidInt("Enter PIN (4 digits): ");
        if (pin < 1000 || pin > 9999) {             // Validate 4-digit PIN
            System.out.println("PIN must be 4 digits.");
            return;
        }

        Customer customer = customers.get(customerNumber);
        if (customer == null) {                     // New customer
            customer = new Customer(customerNumber, pin);
            customers.put(customerNumber, customer);
        } else if (customer.getPinNumber() != pin) { // Existing customer, wrong PIN
            System.out.println("Customer exists with different PIN.");
            return;
        }else {
            System.out.println("Welcome back, existing customer!");
        }

        System.out.println("1 - Checking Account");
        System.out.println("2 - Savings Account");
        int type = getValidInt("Choose account type: ");
        
        if (type != 1 && type != 2) {
            System.out.println("Invalid account type.");
            return;
        }
        
        // Create account based on type
        Account newAcc = (type == 1) ? new CheckingAccount(0) : new SavingsAccount(0);
        customer.addAccount(newAcc);
        saveCustomers();                            // Save to file
        System.out.println("Account created successfully!");
    }

    // Let customer select an account
    private void selectAccount(Customer customer) {
        List<Account> accounts = customer.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        
        System.out.println("\nSelect account:");
        for (int i = 0; i < accounts.size(); i++) {
            String accType = accounts.get(i).getClass().getSimpleName();
            String balance = accounts.get(i).getFormattedBalance();
            System.out.println((i+1) + " - " + accType + " (" + balance + ")");
        }
        
        int choice = getValidInt("Choice: ") - 1;   // Convert to zero-based index
        if (choice < 0 || choice >= accounts.size()) {
            System.out.println("Invalid choice.");
            return;
        }
        
        accountMenu(accounts.get(choice), customer); // Go to account menu
    }

    // Account operations menu
    private void accountMenu(Account acc, Customer customer) {
        boolean end = false;
        while (!end) {
            System.out.println("\n=== Account Menu ===");
            System.out.println("1 - View Balance");
            System.out.println("2 - Deposit");
            System.out.println("3 - Withdraw");
            System.out.println("4 - Transfer");
            System.out.println("5 - Back to Main Menu");
            
            int choice = getValidInt("Choice: ");
            
            switch (choice) {
                case 1: // Show balance
                    System.out.println("Balance: " + acc.getFormattedBalance());
                    break;
                case 2: // Deposit money
                    double depositAmount = getValidDouble("Deposit amount: ");
                    if (depositAmount <= 0) {
                        System.out.println("Amount must be positive.");
                    } else {
                        acc.deposit(depositAmount);
                        saveCustomers();            // Save after change
                        System.out.println("New balance: " + acc.getFormattedBalance());
                    }
                    break;
                case 3: // Withdraw money
                    double withdrawAmount = getValidDouble("Withdraw amount: ");
                    try {
                        acc.withdraw(withdrawAmount);
                        saveCustomers();            // Save after change
                        System.out.println("New balance: " + acc.getFormattedBalance());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case 4: // Transfer to another account
                    transfer(acc, customer);
                    break;
                case 5: // Return to main menu
                    end = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Transfer money between accounts
    private void transfer(Account from, Customer customer) {
        List<Account> accounts = customer.getAccounts();
        List<Account> otherAccounts = new ArrayList<>();
        
        // Get all accounts except the source account
        for (Account a : accounts) {
            if (a != from) otherAccounts.add(a);
        }

        if (otherAccounts.isEmpty()) {
            System.out.println("No other accounts for transfer.");
            return;
        }
        
        System.out.println("Select target account:");
        for (int i = 0; i < otherAccounts.size(); i++) {
            String accType = otherAccounts.get(i).getClass().getSimpleName();
            String balance = otherAccounts.get(i).getFormattedBalance();
            System.out.println((i+1) + " - " + accType + " (" + balance + ")");
        }
        
        int targetChoice = getValidInt("Choice: ") - 1; // Convert to zero-based
        if (targetChoice < 0 || targetChoice >= otherAccounts.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Account toAccount = otherAccounts.get(targetChoice);
        double amount = getValidDouble("Transfer amount: ");
        
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }

        try {
            from.withdraw(amount);                  // Take from source
            toAccount.deposit(amount);              // Add to target
            saveCustomers();                        // Save changes
            System.out.println("Transfer successful!");
        } catch (IllegalArgumentException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }
}
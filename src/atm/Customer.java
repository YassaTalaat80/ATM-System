package atm;

import java.util.ArrayList;
import java.util.List;

// Bank customer with accounts
public class Customer {
    private int customerNumber;                     // Unique ID for customer
    private int pinNumber;                          // Security PIN
    private List<Account> accounts = new ArrayList<>(); // List of customer's accounts

    public Customer(int customerNumber,   int pinNumber) {
        this.customerNumber = customerNumber;
        this.pinNumber = pinNumber;
    }

    public int getCustomerNumber() { return customerNumber; } // Get customer ID
    public int getPinNumber() { return pinNumber; }           // Get PIN
    public List<Account> getAccounts() { return accounts; }   // Get all accounts
    public void addAccount(Account acc) { accounts.add(acc); } // Add new account
}
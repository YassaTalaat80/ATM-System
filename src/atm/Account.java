package atm;

import java.text.DecimalFormat;

// Base class for all account types
public abstract class Account implements Transaction {
    protected double balance;                       // Current money amount
    private final DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00"); // Money formatter

    public double getBalance() { return balance; }
    public String getFormattedBalance() { return moneyFormat.format(balance); } // Get formatted balance

    // Add money to account
    @Override
    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit must be positive.");
        balance += amount;
    }

    // Remove money from account
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal must be positive.");
        if (balance - amount < 0) throw new IllegalArgumentException("Insufficient funds.");
        balance -= amount;
    }
}
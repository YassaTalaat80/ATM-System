package atm;

// Interface  banking operations
public interface Transaction {
    void deposit(double amount) ;          // Add money to account
    void withdraw(double amount) ;          // Take money from account
}
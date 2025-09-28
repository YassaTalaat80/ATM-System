package atm;

import java.io.IOException;

// Main program entry point
public class ATM {
    public static void main(String[] args) {
        try {
            Menu optionMenu = new Menu();           // Create menu system
            optionMenu.loadCustomers();             // Load existing data
            introduction();                         // Show welcome message
            optionMenu.mainMenu();                  //  main loop
        } catch (Exception e) {
            System.out.println("System error:  " + e.getMessage());
        }
    }

    // Display welcome message
    private static void introduction() {
        System.out.println("====================================");
        System.out.println("      WELCOME TO JAVA ATM");
        System.out.println("====================================");
    }
}
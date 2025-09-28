# Java ATM System

## Overview

The Java ATM System is a comprehensive console-based banking application developed in Java, implementing core Object-Oriented Programming principles to simulate real-world ATM functionality. This robust system provides secure customer authentication, account management, and financial transaction processing with persistent data storage.

## Features

### Banking Operations
• Customer Authentication: Secure login validation with customer number and 4-digit PIN
• Account Management: Support for both Checking and Savings accounts  
• Transaction Processing: Deposit, withdrawal, and inter-account transfer capabilities
• Balance Inquiry: Real-time account balance information

### Technical Features
• Data Persistence: Automatic saving and loading of customer data to text file
• Input Validation: Comprehensive error handling with user-friendly prompts
• Security Measures: Limited login attempts (3 tries) with appropriate security messaging

## Technologies Used
• Java (JDK 8 or higher)
• Object-Oriented Programming (OOP) principles
• Collections Framework (HashMap, ArrayList)
• File I/O for data persistence
• Custom utilities for efficient input handling

## System Architecture

### OOP Implementation
• Inheritance: Account → CheckingAccount, SavingsAccount
• Polymorphism: Unified interface for different account types
• Encapsulation: Private fields with public accessor methods
• Abstraction: Simplified interfaces for complex operations

### Data Management
• HashMap<Integer, Customer>: O(1) complexity for customer lookup
• ArrayList<Account>: Dynamic storage of customer accounts
• Text-based Storage: Custom file formatting for data persistence

## Usage

### Customer Login
1. Enter your customer number and 4-digit PIN
2. The system validates credentials against stored data  
3. Maximum of 3 attempts before temporary lockout

### Account Operations
1. Select from available accounts (Checking/Savings)
2. Choose transaction type: Deposit, Withdraw, or Transfer
3. Enter transaction amount
4. System processes transaction and updates balance

### Account Management
• Create new accounts (Checking or Savings)
• View account balances and transaction history
• Transfer funds between accounts

## Error Handling

The application includes robust error handling mechanisms:
• Input Validation: Ensures numeric values and positive amounts
• Exception Handling: Try-catch blocks for NumberFormatException and custom exceptions
• User Feedback: Clear error messages with guidance for correction
• Transaction Safety: Rollback protection for failed operations

## Project Structure

The system follows a modular design with separated concerns:
• Menu.java: Main system controller and user interface
• Customer.java: Customer data model and account management
• Account.java: Abstract base class for all account types
• CheckingAccount.java: Checking account implementation  
• SavingsAccount.java: Savings account implementation
• Transaction.java: Transaction processing and validation
• Scanner.java: Custom input handling utilities

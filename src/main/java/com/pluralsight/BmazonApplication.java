package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class BmazonApplication {
    // Global Scanner and ArrayList to be used in methods
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Transaction> transactions = new ArrayList<>();

    // Main method
    public static void main(String[] args) {
        transactions = loadTransactions();

        System.out.println("Welcome to the Bmazon application!");

        menuNavigation();
    }

    // Creates ArrayList of transactions.csv
    public static ArrayList<Transaction> loadTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("transactions.csv"));
            String line;

            bufReader.readLine();

            while ((line = bufReader.readLine()) != null) {
                Transaction transaction = getTransaction(line);
                transactions.add(transaction);
            }

            bufReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    // Grabs entries from transactions.csv and splits from "|" character
    public static Transaction getTransaction(String input) {
        String[] transactionInfo = input.split("\\|");

        return new Transaction(
                transactionInfo[0].trim(),
                transactionInfo[1].trim(),
                transactionInfo[2].trim(),
                transactionInfo[3].trim(),
                Double.parseDouble(transactionInfo[4].trim())
        );
    }

    // Menu navigation
    public static void menuNavigation() {
        while (true) {
            System.out.printf("What do you want to do?%n" +
                    "1. Add Deposit%n" +
                    "2. Make Payment (Debit)%n" +
                    "3. Ledger%n" +
                    "0. Exit%n" +
                    "Enter your command: ");

            switch(input.nextLine()) {
                case "1":
                    System.out.println("Add Deposit");
                    addDeposit();
                    break;
                case "2":
                    System.out.println("Make Payment (Debit)");
                    makePayment();
                    break;
                case "3":
                    System.out.println("Ledger");
                    runLedger();
                    break;
                case "0":
                    System.out.println("Exit");
                    return;
            }
        }
    }

    // Asks user for date, time, description, vendor, & amount; adds all values to transactions ArrayList and writes to transactions.csv as deposit
    public static void addDeposit() {

        System.out.print("Enter date (YYYY-MM-DD, type 'today'): ");
        String date = input.nextLine();

        System.out.print("Enter time (HH:mm:ss, type 'now'): ");
        String time = input.nextLine();

        System.out.print("Enter description: ");
        String description = input.nextLine();

        System.out.print("Enter vendor: ");
        String vendor = input.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(input.nextLine());

        LocalDateTime now = LocalDateTime.now();

        if (date.equalsIgnoreCase("today")) {
            date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        if (time.equalsIgnoreCase("now")) {
            time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }

        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        transactions.add(transaction);

        try (FileWriter writer = new FileWriter("transactions.csv", true)) {

            writer.write(String.format("%n%s|%s|%s|%s|%.2f",
                    date, time, description, vendor, amount));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Asks user for date, time, description, vendor, & amount; adds all values to transactions ArrayList and writes to transactions.csv as payment
    public static void makePayment() {

        System.out.print("Enter date (YYYY-MM-DD, type 'today'): ");
        String date = input.nextLine();

        System.out.print("Enter time (HH:mm:ss, type 'now'): ");
        String time = input.nextLine();

        System.out.print("Enter description: ");
        String description = input.nextLine();

        System.out.print("Enter vendor: ");
        String vendor = input.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(input.nextLine()) * -1;

        LocalDateTime now = LocalDateTime.now();

        if (date.equalsIgnoreCase("today")) {
            date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        if (time.equalsIgnoreCase("now")) {
            time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }

        Transaction transaction = new Transaction(date, time, description, vendor, amount);

        transactions.add(transaction);

        try (FileWriter writer = new FileWriter("transactions.csv", true)) {

            writer.write(String.format("%n%s|%s|%s|%s|%.2f",
                    date, time, description, vendor, amount));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Menu navigation for ledger options
    public static void runLedger() {
        while (true) {
            System.out.printf("What do you want to do?%n" +
                    "1. All%n" +
                    "2. Deposits%n" +
                    "3. Payments%n" +
                    "4. Reports%n" +
                    "0. Home%n" +
                    "Enter your command: ");

            switch(input.nextLine()) {
                case "1":
                    System.out.println("All");
                    showAllTransactions();
                    break;
                case "2":
                    System.out.println("Deposits");
                    showDeposits();
                    break;
                case "3":
                    System.out.println("Payments");
                    showPayments();
                    break;
                case "4":
                    System.out.println("Reports");
                    runReports();
                    break;
                case "0":
                    System.out.println("Home");
                    return;
            }
        }
    }

    // Method to print transactions for redundancy
    public static void printTransaction(Transaction transaction) {
        System.out.printf(
                "Date: %s Time: %s Description: %s Vendor: %s Amount: $%.2f%n",
                transaction.getDate(),
                transaction.getTime(),
                transaction.getDescription(),
                transaction.getVendor(),
                transaction.getAmount()
        );
    }

    // Starts at beginning of transaction ArrayList and calls printTransaction(); loops from last entry
    public static void showAllTransactions() {
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);

            printTransaction(transaction);
        }
    }

    // Loops through transactions ArrayList starting from the last entry and prints transactions with positive amount
    public static void showDeposits() {
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);

            if (transaction.getAmount() > 0) {
                printTransaction(transaction);
            }
        }
    }

    // Loops through transactions ArrayList starting from the last entry and prints transactions with negative amount
    public static void showPayments() {
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);

            if (transaction.getAmount() < 0) {
                printTransaction(transaction);
            }
        }
    }

    // Menu navigation for reports options
    public static void runReports() {
        while (true) {
            System.out.printf("What do you want to do?%n" +
                    "1. Month To Date%n" +
                    "2. Previous Month%n" +
                    "3. Year To Date%n" +
                    "4. Previous Year%n" +
                    "5. Search by Vendor%n" +
                    "0. Back%n" +
                    "Enter your command: ");

            switch(input.nextLine()) {
                case "1":
                    System.out.println("Month To Date");
                    showMonthToDate();
                    break;
                case "2":
                    System.out.println("Previous Month");
                    showPreviousMonth();
                    break;
                case "3":
                    System.out.println("Year To Date");
                    showYearToDate();
                    break;
                case "4":
                    System.out.println("Previous Year");
                    showPreviousYear();
                    break;
                case "5":
                    System.out.println("Search by Vendor");
                    showByVendor();
                    break;
                case "0":
                    System.out.println("Exit");
                    return;
            }
        }
    }

    // Initialize time; finds current year and month; sets date format as "yyyy-MM-dd";
    // Loops through transactions ArrayList starting from the last entry and prints transactions with current month
    public static void showMonthToDate() {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), formatter);

            if (transactionDate.getYear() == currentYear &&
                    transactionDate.getMonthValue() == currentMonth) {

                printTransaction(transaction);
            }
        }
    }

    // Initialize time; finds current year and month; sets date format as "yyyy-MM-dd";
    // Loops through transactions ArrayList starting from the last entry and prints transactions with previous year and month
    public static void showPreviousMonth() {
        LocalDate today = LocalDate.now();
        LocalDate previousMonthDate = today.minusMonths(1);

        int previousMonth = previousMonthDate.getMonthValue();
        int previousYear = previousMonthDate.getYear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), formatter);

            if (transactionDate.getMonthValue() == previousMonth &&
                    transactionDate.getYear() == previousYear) {

                printTransaction(transaction);
            }
        }
    }

    // Initialize time; finds current year and month; sets date format as "yyyy-MM-dd";
    // Loops through transactions ArrayList starting from the last entry and prints transactions with current year
    public static void showYearToDate() {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), formatter);

            if (transactionDate.getYear() == currentYear) {
                printTransaction(transaction);
            }
        }
    }

    // Initialize time; finds current year and month; sets date format as "yyyy-MM-dd";
    // Loops through transactions ArrayList starting from the last entry and prints transactions with previous year
    public static void showPreviousYear() {
        LocalDate today = LocalDate.now();
        int previousYear = today.minusYears(1).getYear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), formatter);

            if (transactionDate.getYear() == previousYear) {
                printTransaction(transaction);
            }
        }
    }

    // Prompts user to enter Vendor name
    // Loops through transactions ArrayList starting from the last entry and prints transactions with user input vendor name
    public static void showByVendor() {
        System.out.print("Enter vendor name: ");
        String vendorInput = input.nextLine().trim();

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);

            if (transaction.getVendor().equalsIgnoreCase(vendorInput)) {
                printTransaction(transaction);
            }
        }
    }
}

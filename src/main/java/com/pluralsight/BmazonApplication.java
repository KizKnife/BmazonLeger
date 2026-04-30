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
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ArrayList<Transaction> transactions = loadTransactions();

        System.out.println("Welcome to the Bmazon application!");

        menuNavigation(input, transactions);
    }

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

    public static void menuNavigation(Scanner input, ArrayList<Transaction> transactions) {
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
                    addDeposit(input, transactions);
                    break;
                case "2":
                    System.out.println("Make Payment (Debit)");
                    makePayment(input, transactions);
                    break;
                case "3":
                    System.out.println("Ledger");
                    runLedger(input, transactions);
                    break;
                case "0":
                    System.out.println("Exit");
                    return;
            }
        }
    }

    public static void addDeposit(Scanner input, ArrayList<Transaction> transactions) {

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

    public static void makePayment(Scanner input, ArrayList<Transaction> transactions) {

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

    public static void runLedger(Scanner input, ArrayList<Transaction> transactions) {
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
                    showAllTransactions(transactions);
                    break;
                case "2":
                    System.out.println("Deposits");
                    showDeposits(transactions);
                    break;
                case "3":
                    System.out.println("Payments");
                    showPayments(transactions);
                    break;
                case "4":
                    System.out.println("Reports");
                    runReports(input, transactions);
                    break;
                case "0":
                    System.out.println("Home");
                    return;
            }
        }
    }

    public static void showAllTransactions(ArrayList<Transaction> transactions) {
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);

            System.out.printf(
                    "Date: %s Time: %s Description: %s Vendor: %s Amount: $%.2f%n",
                    transaction.getDate(),
                    transaction.getTime(),
                    transaction.getDescription(),
                    transaction.getVendor(),
                    transaction.getAmount()
            );
        }
    }

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

    public static void showDeposits(ArrayList<Transaction> transactions) {
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);

            if (transaction.getAmount() > 0) {
                System.out.printf(
                        "Date: %s Time: %s Description: %s Vendor: %s Amount: $%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount()
                );
            }
        }
    }

    public static void showPayments(ArrayList<Transaction> transactions) {
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);

            if (transaction.getAmount() < 0) {
                System.out.printf(
                        "Date: %s Time: %s Description: %s Vendor: %s Amount: $%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount()
                );
            }
        }
    }

    public static void runReports(Scanner input, ArrayList<Transaction> transactions) {
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
                    showMonthToDate(transactions);
                    break;
                case "2":
                    System.out.println("Previous Month");
                    showPreviousMonth(transactions);
                    break;
                case "3":
                    System.out.println("Year To Date");
                    showYearToDate(transactions);
                    break;
                case "4":
                    System.out.println("Previous Year");
                    showPreviousYear(transactions);
                    break;
                case "5":
                    System.out.println("Search by Vendor");
                    showByVendor(input, transactions);
                    break;
                case "0":
                    System.out.println("Exit");
                    return;
            }
        }
    }

    public static void showMonthToDate(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), formatter);

            if (transactionDate.getYear() == currentYear &&
                    transactionDate.getMonthValue() == currentMonth) {

                System.out.printf(
                        "Date: %s Time: %s Description: %s Vendor: %s Amount: $%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount()
                );
            }
        }
    }

    public static void showPreviousMonth(ArrayList<Transaction> transactions) {
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

                System.out.printf(
                        "Date: %s Time: %s Description: %s Vendor: %s Amount: $%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount()
                );
            }
        }
    }

    public static void showYearToDate(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), formatter);

            if (transactionDate.getYear() == currentYear) {
                System.out.printf(
                        "Date: %s Time: %s Description: %s Vendor: %s Amount: $%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount()
                );
            }
        }
    }

    public static void showPreviousYear(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        int previousYear = today.minusYears(1).getYear();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
            LocalDate transactionDate = LocalDate.parse(transaction.getDate(), formatter);

            if (transactionDate.getYear() == previousYear) {
                System.out.printf(
                        "Date: %s Time: %s Description: %s Vendor: %s Amount: $%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount()
                );
            }
        }
    }

    public static void showByVendor(Scanner input, ArrayList<Transaction> transactions) {
        System.out.print("Enter vendor name: ");
        String vendorInput = input.nextLine().trim();

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);

            if (transaction.getVendor().equalsIgnoreCase(vendorInput)) {
                System.out.printf(
                        "Date: %s Time: %s Description: %s Vendor: %s Amount: $%.2f%n",
                        transaction.getDate(),
                        transaction.getTime(),
                        transaction.getDescription(),
                        transaction.getVendor(),
                        transaction.getAmount()
                );
            }
        }
    }
}

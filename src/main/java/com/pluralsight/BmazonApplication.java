package com.pluralsight;

import java.util.Scanner;

public class BmazonApplication {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to the Bmazon application!");

        menuNavigation(input);
    }

    public static void menuNavigation(Scanner input) {
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
                    break;
                case "2":
                    System.out.println("Make Payment (Debit)");
                    break;
                case "3":
                    System.out.println("Ledger");
                    runLedger(input);
                    break;
                case "0":
                    System.out.println("Exit");
                    return;
            }
        }
    }

    public static void runLedger(Scanner input) {
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
                    break;
                case "2":
                    System.out.println("Deposits");
                    break;
                case "3":
                    System.out.println("Payments");
                    break;
                case "4":
                    System.out.println("Reports");
                    runReports(input);
                    break;
                case "0":
                    System.out.println("Home");
                    return;
            }
        }
    }

    public static void runReports(Scanner input) {
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
                    break;
                case "2":
                    System.out.println("Previous Month");
                    break;
                case "3":
                    System.out.println("Year To Date");
                    break;
                case "4":
                    System.out.println("Previous Year");
                    break;
                case "5":
                    System.out.println("Search by Vendor");
                    break;
                case "0":
                    System.out.println("Exit");
                    return;
            }
        }
    }
}

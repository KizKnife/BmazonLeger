# Bmazon Ledger Application

A simple Java console-based financial ledger application that allows users to track deposits and payments, store them in a CSV file, and generate basic financial reports.

---

## 📌 Features

- Add **deposits** (positive transactions)
- Record **payments** (negative transactions)
- Persistent storage using `transactions.csv`
- View transactions:
    - All (latest first)
    - Deposits only
    - Payments only
- Generate reports:
    - Month-to-date
    - Previous month
    - Year-to-date
    - Previous year
    - Search by vendor

---

## 🧱 Project Structure
com.pluralsight
│
├── BmazonApplication.java # Main application logic
├── Transaction.java # Transaction model class
└── transactions.csv # Data storage file


---

## 📂 Data Format

Transactions are stored in a pipe-separated format:
date|time|description|vendor|amount


### Example:
2026-05-01|14:30:00|Groceries|Walmart|45.67\
2026-05-01|15:00:00|Coffee|Starbucks|-5.25


- Deposits → positive values
- Payments → negative values

---

## ▶️ How to Run

### 1. Compile

### 2. Run

---

## 🧭 Menu Navigation

### Main Menu

1. Add Deposit
2. Make Payment (Debit)
3. Ledger
0. Exit


### Ledger Menu

1. All
2. Deposits
3. Payments
4. Reports
0. Home

### Reports Menu

1. Month To Date
2.  Previous Month
3. Year To Date
4. Previous Year
5. Search by Vendor
0. Back

---

## ⏱ Shortcuts

- Type `today` for the current date
- Type `now` for the current time

---

## ⚙️ How It Works

- Transactions are loaded from `transactions.csv` at startup
- Stored in an `ArrayList<Transaction>`
- New entries are:
    1. Added to the list
    2. Appended to the CSV file
- Transactions are displayed in reverse order (most recent first)

---

## 💡 Summary

This project demonstrates:

- File I/O in Java
- Working with ArrayLists
- Basic date/time handling
- Command-line user interaction
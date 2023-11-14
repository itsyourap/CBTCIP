import database.DatabaseManager;
import model.AccountModel;
import model.TransactionModel;

import java.util.ArrayList;
import java.util.Scanner;

public class ATM {
    private static AccountModel currentAccount = null;
    private static final DatabaseManager dbManager = DatabaseManager.getInstance();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to itsyourap's ATM");
        System.out.println("---------------------------");
        System.out.print("Please Enter User ID: ");
        String userId = sc.next();
        System.out.print("Please Enter your PIN: ");
        int pin = sc.nextInt();

        currentAccount = dbManager.getAccount(userId, pin);
        if (currentAccount == null) {
            System.out.println("Invalid User ID or PIN");
            System.out.println("Exiting...");
            return;
        }
        System.out.println();

        outer:
        while (true) {
            System.out.println("---------------------------");
            System.out.println("Welcome " + currentAccount.getUserName());
            System.out.println("Your current balance is: " + currentAccount.getBalance());
            System.out.println("---------------------------");
            System.out.println("What would you like to do?");
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Transfer");
            System.out.println("4. Show Transaction History");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            System.out.println("---------------------------");
            switch (choice) {
                case 1:
                    withdrawCash();
                    break;

                case 2:
                    depositCash();
                    break;

                case 3:
                    transferMoney();
                    break;

                case 4:
                    showTransactionHistory();
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break outer;

                default:
                    System.out.println("Invalid choice");
            }
        }
        sc.close();
    }

    private static void withdrawCash(){
        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();
        if (amount > currentAccount.getBalance()) {
            System.out.println("Insufficient balance");
            return;
        }
        TransactionModel withdrawTransaction = new TransactionModel();
        withdrawTransaction.setAmount(amount);
        withdrawTransaction.setTransactionType(TransactionModel.TransactionType.WITHDRAW);
        withdrawTransaction.setAccountId(currentAccount.getId());
        if (currentAccount.addTransaction(withdrawTransaction)){
            System.out.println("Withdraw successful");
            System.out.println("Please collect your cash");
        } else {
            System.out.println("Withdraw failed");
        }
    }

    private static void depositCash(){
        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();
        TransactionModel depositTransaction = new TransactionModel();
        depositTransaction.setAmount(amount);
        depositTransaction.setTransactionType(TransactionModel.TransactionType.DEPOSIT);
        depositTransaction.setAccountId(currentAccount.getId());
        if (currentAccount.addTransaction(depositTransaction))
            System.out.println("Deposit successful");
        else
            System.out.println("Deposit failed");
    }

    private static void transferMoney(){
        System.out.print("Enter User ID of recipient: ");
        String recipientId = sc.next();
        AccountModel recipientAccount = dbManager.getAccount(recipientId);
        if (recipientAccount == null) {
            System.out.println("Invalid User ID");
            return;
        }
        System.out.print("Enter amount to transfer: ");
        double amount = sc.nextDouble();
        if (amount > currentAccount.getBalance()) {
            System.out.println("Insufficient balance");
            return;
        }
        TransactionModel transaction = new TransactionModel();
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionModel.TransactionType.TRANSFER_WITHDRAW);
        transaction.setAccountId(currentAccount.getId());
        transaction.setOtherPartyAccountId(recipientAccount.getId());
        if (currentAccount.addTransaction(transaction))
            System.out.println("Transfer successful");
        else
            System.out.println("Transfer failed");
    }

    private static void showTransactionHistory(){
        System.out.println("Transaction History");
        System.out.println("---------------------------");
        ArrayList<TransactionModel> transactionHistory = currentAccount.getTransactions();
        for (int i = transactionHistory.size() - 1; i >= 0; i--) {
            TransactionModel t = transactionHistory.get(i);
            System.out.println(t.getTransactionType().toString());
            System.out.println("Amount: " + t.getAmount());
            if (t.getTransactionType() == TransactionModel.TransactionType.TRANSFER_DEPOSIT || t.getTransactionType() == TransactionModel.TransactionType.TRANSFER_WITHDRAW) {
                AccountModel otherParty = dbManager.getAccount(t.getOtherPartyAccountId());
                if (t.getTransactionType() == TransactionModel.TransactionType.TRANSFER_DEPOSIT)
                    System.out.print("From");
                else
                    System.out.print("To");

                System.out.println(": " + otherParty.getUserName() + " [" + otherParty.getUserId() + "]");
            }
            System.out.println();
        }
    }

}

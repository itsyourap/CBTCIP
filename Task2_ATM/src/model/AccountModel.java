package model;

import database.DatabaseManager;

import java.util.ArrayList;

public class AccountModel {
    private long id;
    private String userId;
    private String userName;
    private double balance;
    private ArrayList<TransactionModel> transactions;

    public AccountModel() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<TransactionModel> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<TransactionModel> transactions) {
        this.transactions = transactions;
    }

    public boolean addTransaction(TransactionModel transaction) {
        if (this.transactions == null)
            setTransactions(new ArrayList<>());

        if (DatabaseManager.getInstance().addTransaction(transaction)) {
            if (transaction.getTransactionType() == TransactionModel.TransactionType.WITHDRAW ||
                    transaction.getTransactionType() == TransactionModel.TransactionType.TRANSFER_WITHDRAW)
                this.balance -= transaction.getAmount();
            else
                this.balance += transaction.getAmount();

            this.transactions.add(transaction);
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "AccountModel{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", balance=" + balance +
                ", transactions=" + transactions +
                '}';
    }
}

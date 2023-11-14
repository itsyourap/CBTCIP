package model;

import java.util.ArrayList;

public class AccountModel {
    private long id;
    private String userId;
    private String userName;
    private double balance;
    private ArrayList<TransactionModel> transactions;

    public AccountModel() {

    }

    public AccountModel(long id, String userId, String userName, double balance) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.balance = balance;
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

    public void addTransaction(TransactionModel transaction) {
        if (this.transactions == null)
            setTransactions(new ArrayList<>());

        this.transactions.add(transaction);
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

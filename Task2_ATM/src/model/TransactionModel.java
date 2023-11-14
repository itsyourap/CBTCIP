package model;

public class TransactionModel {
    public enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFER_DEPOSIT,
        TRANSFER_WITHDRAW
    }

    private long id;
    private long accountId;
    private TransactionType transactionType;
    private double amount;
    private long otherPartyAccountId;

    public TransactionModel() {

    }

    public TransactionModel(long id, long accountId, TransactionType transactionType, double amount, long otherPartyAccountId) {
        this.id = id;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.otherPartyAccountId = otherPartyAccountId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getOtherPartyAccountId() {
        return otherPartyAccountId;
    }

    public void setOtherPartyAccountId(long otherPartyAccountId) {
        this.otherPartyAccountId = otherPartyAccountId;
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", otherPartyAccountId=" + otherPartyAccountId +
                '}';
    }
}

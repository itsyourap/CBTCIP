package model;

public class TransactionModel {
    public enum TransactionType {
        DEPOSIT(1),
        WITHDRAW(2),
        TRANSFER_DEPOSIT(3),
        TRANSFER_WITHDRAW(4);

        private final int value;

        TransactionType(int i) {
            this.value = i;
        }

        public int getValue() {
            return value;
        }
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

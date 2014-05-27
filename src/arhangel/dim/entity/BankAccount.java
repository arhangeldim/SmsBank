package arhangel.dim.entity;

import java.math.BigInteger;
import java.util.Set;

/**
 *
 */
public class BankAccount {
    private String bankName;
    private int id;
    private int cardNumber;
    private BigInteger amount;
    private Set<Transaction> transactionHistory;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public Set<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(Set<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }
}

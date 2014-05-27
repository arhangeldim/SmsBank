package arhangel.dim.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

/**
 *
 */
public class Transaction implements Parcelable {
    private int messageId;
    private BigDecimal amount;
    private Date date;
    private boolean isPut;
    private String description;
    private Currency currency;
    private String cardNumber;

    private BankAccount account;
    private String category;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isPut() {
        return isPut;
    }

    public void setPut(boolean isPut) {
        this.isPut = isPut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", date=" + date +
                ", type=" + (isPut ? "Put" : "Call") +
                ", description='" + description + '\'' +
                ", currency=" + currency +
                ", cardNumber='" + cardNumber + '\'' +
                ", account=" + account +
                ", category=" + category +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(messageId);
        dest.writeString(amount.toString());
        dest.writeValue(date);
        dest.writeString(String.valueOf(isPut));
        dest.writeString(description);
        dest.writeString(cardNumber);
        dest.writeString(category);
        dest.writeValue(currency);
    }
}

package banking;

import java.io.Serializable;
import java.util.Random;

public class CreditCard implements Serializable {
    private int id = -1;
    private long account;
    private long balance = 0;
    private boolean freezed = false;

    CreditCard() {
        this.account = Math.abs(new Random().nextInt(100000));
    }

    private CreditCard(long account, long balance, boolean freezed) {
        this.account = account;
        this.balance = balance;
        this.freezed = freezed;
    }

    public CreditCard(int id, long account, long balance, boolean freezed) {
        this(account, balance, freezed);
        this.id = id;
    }

    public boolean makePayment(long amount) {
        if (balance - amount < 0 || amount <= 0 || freezed) {
            return false;
        }
        balance -= amount;
        return true;
    }

    public void freeze() {
        freezed = true;
    }

    public void unfreeze() {
        freezed = false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public long getAccount() {
        return account;
    }

    public boolean makeDeposit(long amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isFreezed() {
        return freezed;
    }
}

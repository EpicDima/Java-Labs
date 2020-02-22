package banking;

import java.io.Serializable;
import java.util.Random;

public class CreditCard implements Serializable {

    private static int counter = 0;

    private int id = counter++;
    private long account;
    private long balance = 0;
    private boolean freezed = false;

    CreditCard() {
        this.account = Math.abs(new Random().nextInt(100000));
    }

    public CreditCard(int id, long account, long balance, boolean freezed) {
        this.id = id;
        this.account = account;
        this.balance = balance;
        this.freezed = freezed;
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

package database;

import database.base.PaymentSystem;

public class PaymentSystemFactory {

    public enum PaymentSystemType {
        SIMPLEFILE, MYSQL
    }

    public static PaymentSystem getPaymentSystem(PaymentSystemType type) {
        switch (type) {
            case SIMPLEFILE:
                return new SimpleFilePaymentSystem();
            case MYSQL:
                return new MySqlPaymentSystem();
            default:
                return null;
        }
    }
}
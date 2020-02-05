package storage;

import storage.base.PaymentSystem;

public class PaymentSystemFactory {

    public enum PaymentSystemType {
        SIMPLEFILE
    }

    public static PaymentSystem getPaymentSystem(PaymentSystemType type) {
        if (type == PaymentSystemType.SIMPLEFILE) {
            return new SimpleFilePaymentSystem();
        }
        return null;
    }
}
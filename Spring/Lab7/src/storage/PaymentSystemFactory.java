package storage;

import storage.base.PaymentSystem;

public class PaymentSystemFactory {

    public enum PaymentSystemType {
        XML
    }

    public static PaymentSystem getPaymentSystem(PaymentSystemType type) {
        if (type == PaymentSystemType.XML) {
            return new XmlPaymentSystem();
        }
        return null;
    }
}
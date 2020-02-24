package storage;

import storage.base.PaymentSystem;
import storage.xml.DomXmlPaymentSystem;
import storage.xml.SaxXmlPaymentSystem;
import storage.xml.StaxXmlPaymentSystem;

public class PaymentSystemFactory {

    public enum PaymentSystemType {
        SAX_XML, DOM_XML, STAX_XML
    }

    public static PaymentSystem getPaymentSystem(PaymentSystemType type) {
        switch (type) {
            case SAX_XML:
                return new SaxXmlPaymentSystem();
            case DOM_XML:
                return new DomXmlPaymentSystem();
            case STAX_XML:
                return new StaxXmlPaymentSystem();
            default:
                return null;
        }
    }
}
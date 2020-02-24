package storage.xml;

import storage.base.AbstractPaymentSystem;

public abstract class XmlPaymentSystem extends AbstractPaymentSystem {

    static final String FILENAME = "storage.xml";

    static final String CLIENT_LIST_ELEMENT = "Clients";
    static final String CLIENT_ELEMENT = "Client";
    static final String CREDIT_CARD_LIST_ELEMENT = "CreditCards";
    static final String CREDIT_CARD_ELEMENT = "CreditCard";

    static final String ID_ATTRIBUTE = "id";

    static final String LOGIN_ATTRIBUTE = "login";
    static final String PASSWORD_ATTRIBUTE = "password";

    static final String ACCOUNT_ATTRIBUTE = "account";
    static final String BALANCE_ATTRIBUTE = "balance";
    static final String FREEZED_ATTRIBUTE = "freezed";
}

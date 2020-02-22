package storage.base;

import banking.Client;
import banking.CreditCard;

public interface PaymentSystem {
    Client findClientByLogin(String login);

    CreditCard findCreditCardByAccount(long account);

    void addClient(Client client);

    void saveChanges();
}

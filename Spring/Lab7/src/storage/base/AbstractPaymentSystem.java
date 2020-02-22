package storage.base;

import banking.Client;
import banking.CreditCard;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPaymentSystem implements PaymentSystem {
    protected List<Client> clients = new ArrayList<>();

    protected abstract void loadClients();

    protected abstract void saveClients();

    public AbstractPaymentSystem() {
        loadClients();
    }

    @Override
    public void addClient(Client client) {
        clients.add(client);
    }

    @Override
    public void saveChanges() {
        saveClients();
    }

    @Override
    public Client findClientByLogin(String login) {
        for (Client client : clients) {
            if (client.getLogin().equals(login)) {
                return client;
            }
        }
        return null;
    }

    @Override
    public CreditCard findCreditCardByAccount(long account) {
        for (Client client : clients) {
            CreditCard card = client.getCreditCardByAccount(account);
            if (card != null) {
                return card;
            }
        }
        return null;
    }
}

package storage;

import banking.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storage.base.AbstractPaymentSystem;

import java.io.*;
import java.util.ArrayList;


public class SimpleFilePaymentSystem extends AbstractPaymentSystem {

    private static final Logger logger = LoggerFactory.getLogger(SimpleFilePaymentSystem.class);

    private String filename;

    SimpleFilePaymentSystem() {
        super();
        filename = "client_database.txt";
        loadClients();
    }

    @Override
    public void addClient(Client client) {
        clients.add(client);
    }

    @Override
    protected void loadClients() {
        File file = new File(filename);
        if (file.exists()) {
            try {
                try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file))) {
                    int size = stream.readInt();
                    clients = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        clients.add((Client) stream.readObject());
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    protected void saveClients() {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new IOException("Ошибка при создании файла!");
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        try {
            try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
                stream.writeInt(clients.size());
                for (Client client : clients) {
                    stream.writeObject(client);
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

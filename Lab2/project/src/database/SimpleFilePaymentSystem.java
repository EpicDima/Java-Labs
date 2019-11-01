package database;

import banking.Client;
import database.base.AbstractPaymentSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigurationManager;

import java.io.*;
import java.util.ArrayList;


public class SimpleFilePaymentSystem extends AbstractPaymentSystem {

    private static final Logger logger = LoggerFactory.getLogger(SimpleFilePaymentSystem.class);

    private String filename;

    SimpleFilePaymentSystem() {
        super();
        filename = ConfigurationManager.getInstance().simpleFileName;
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
                ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
                int size = stream.readInt();
                clients = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    clients.add((Client) stream.readObject());
                }
                stream.close();
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
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
            stream.writeInt(clients.size());
            for (Client client : clients) {
                stream.writeObject(client);
            }
            stream.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}

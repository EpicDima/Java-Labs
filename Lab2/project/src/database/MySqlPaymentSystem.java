package database;

import banking.Client;
import banking.CreditCard;
import database.base.AbstractPaymentSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigurationManager;

import java.sql.*;


public class MySqlPaymentSystem extends AbstractPaymentSystem {

    private static final Logger logger = LoggerFactory.getLogger(MySqlPaymentSystem.class);

    private String url;
    private String username;
    private String password;

    private Connection connection = null;

    MySqlPaymentSystem() {
        super();
        this.url = ConfigurationManager.getInstance().mysqlConnection;
        this.username = ConfigurationManager.getInstance().mysqlUsername;
        this.password = ConfigurationManager.getInstance().mysqlPassword;
        loadClients();
    }

    private Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, username, password);
        }
        return connection;
    }

    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        connection = null;
    }

    @Override
    public void saveChanges() {
        super.saveChanges();
        closeConnection();
    }

    @Override
    protected void loadClients() {
        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(ConfigurationManager.getInstance().selectClientsSql)) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("Id");
                    Client client = new Client(id, resultSet.getString("Login"),
                            resultSet.getString("Password"));
                    clients.add(client);
                }
            }
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
        loadCreditCards();
    }

    private void loadCreditCards() {
        try (PreparedStatement statement = getConnection().prepareStatement(ConfigurationManager.getInstance().selectCreditcardsSql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int clientId = resultSet.getInt("ClientsId");
                    for (Client client : clients) {
                        if (client.getId() == clientId) {
                            client.addCreditCard(new CreditCard(resultSet.getInt("Id"),
                                    resultSet.getLong("Account"), resultSet.getLong("Balance"),
                                    resultSet.getBoolean("Freezed")));
                            break;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
    }

    @Override
    public void addClient(Client client) {
        if (insertClient(client) != -1) {
            clients.add(client);
        }
    }

    private int insertClient(Client client) {
        int returnId = -1;
        try (PreparedStatement statement = getConnection()
                .prepareStatement(ConfigurationManager.getInstance().insertClientSql, new String[]{"ID"})) {
            statement.setString(1, client.getLogin());
            statement.setString(2, client.getPassword());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                int insertId = 0;
                while (resultSet.next()) {
                    insertId = resultSet.getInt(1);
                }
                returnId = insertId;
                client.setId(insertId);
            }
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
        return returnId;
    }

    @Override
    protected void saveClients() {
        updateClients();
        updateCreditCards();
    }

    private void updateClients() {
        for (Client client : clients) {
            updateClient(client);
        }
    }

    private void updateCreditCards() {
        for (Client client : clients) {
            for (CreditCard creditCard : client.getCreditCards()) {
                if (creditCard.getId() == -1) {
                    insertCreditCard(creditCard, client.getId());
                } else {
                    updateCreditCard(creditCard);
                }
            }
        }
    }

    private void updateClient(Client client) {
        try (PreparedStatement statement = getConnection().prepareStatement(ConfigurationManager.getInstance().updateClientSql)) {
            statement.setString(1, client.getLogin());
            statement.setString(2, client.getPassword());
            statement.setInt(3, client.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
    }

    private void insertCreditCard(CreditCard creditCard, int id) {
        try (PreparedStatement statement = getConnection().prepareStatement(ConfigurationManager.getInstance().insertCreditcardSql, new String[]{"ID"})) {
            statement.setLong(1, creditCard.getAccount());
            statement.setLong(2, creditCard.getBalance());
            statement.setBoolean(3, creditCard.isFreezed());
            statement.setInt(4, id);
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                int insertId = 0;
                while (resultSet.next()) {
                    insertId = resultSet.getInt(1);
                }
                creditCard.setId(insertId);
            }
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
    }

    private void updateCreditCard(CreditCard creditCard) {
        try (PreparedStatement statement = getConnection().prepareStatement(ConfigurationManager.getInstance().updateCreditcardSql)) {
            statement.setLong(1, creditCard.getAccount());
            statement.setLong(2, creditCard.getBalance());
            statement.setBoolean(3, creditCard.isFreezed());
            statement.setInt(4, creditCard.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
    }
}

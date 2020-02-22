package storage.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigurationManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlConnectionPool {

    private static final Logger logger = LoggerFactory.getLogger(MySqlConnectionPool.class);

    private static final String URL_KEY = "MYSQL_CONNECTION_STRING";
    private static final String USERNAME_KEY = "MYSQL_USERNAME";
    private static final String PASSWORD_KEY = "MYSQL_PASSWORD";
    private static final String NUMBER_OF_CONNECTIONS_KEY = "MYSQL_CONNECTIONS";

    private List<Connection> connections;

    MySqlConnectionPool() {
        int numberOfConnections = Integer.parseInt(ConfigurationManager.getInstance().get(NUMBER_OF_CONNECTIONS_KEY));
        connections = new ArrayList<>(numberOfConnections);

        String url = ConfigurationManager.getInstance().get(URL_KEY);
        String username = ConfigurationManager.getInstance().get(USERNAME_KEY);
        String password = ConfigurationManager.getInstance().get(PASSWORD_KEY);

        for (int i = 0; i < numberOfConnections; i++) {
            addConnection(url, username, password);
        }
    }

    private void addConnection(String url, String username, String password) {
        try {
            connections.add(DriverManager.getConnection(url, username, password));
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    private void addAdditionalConnection() {
        String url = ConfigurationManager.getInstance().get(URL_KEY);
        String username = ConfigurationManager.getInstance().get(USERNAME_KEY);
        String password = ConfigurationManager.getInstance().get(PASSWORD_KEY);
        addConnection(url, username, password);
    }

    public Connection getConnection() {
        if (connections.size() == 0) {
            addAdditionalConnection();
        }
        Connection connection = connections.get(0);
        connections.remove(0);
        return connection;
    }

    public void releaseConnection(Connection connection) {
        connections.add(connection);
    }
}

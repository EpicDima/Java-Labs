package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);

    private static final String DB_RESOURCES_FILEPATH = "src/util/db_resources.properties";

    public String simpleFileName = "client_database.txt";

    public String mysqlConnection = "jdbc:mysql://localhost:3306/javadb?useLegacyDatetimeCode=false&serverTimezone=UTC";
    public String mysqlUsername = "root";
    public String mysqlPassword = "root";

    public String selectClientsSql = "SELECT * FROM clients;";
    public String selectCreditcardsSql = "SELECT * FROM creditcards;";
    public String insertClientSql = "INSERT INTO clients(Login, Password) VALUES (?, ?);";
    public String insertCreditcardSql = "INSERT INTO creditcards(Account, Balance, Freezed, ClientsId) VALUES (?, ?, ?, ?);";
    public String updateClientSql = "UPDATE clients SET Login = ?, Password = ? WHERE Id = ?;";
    public String updateCreditcardSql = "UPDATE creditcards SET Account = ?, Balance = ?, Freezed = ? WHERE Id = ?;";

    private static final ConfigurationManager configurationManager = new ConfigurationManager();

    private ConfigurationManager() {
        try (FileInputStream inputStream = new FileInputStream(DB_RESOURCES_FILEPATH)) {
            Properties properties = new Properties();
            properties.load(inputStream);

            simpleFileName = properties.getProperty("SIMPLE_FILENAME");

            mysqlConnection = properties.getProperty("MYSQL_CONNECTION");
            mysqlUsername = properties.getProperty("MYSQL_USERNAME");
            mysqlPassword = properties.getProperty("MYSQL_PASSWORD");

            selectClientsSql = properties.getProperty("SELECT_CLIENTS_SQL");
            selectCreditcardsSql = properties.getProperty("SELECT_CREDITCARDS_SQL");
            insertClientSql = properties.getProperty("INSERT_CLIENT_SQL");
            insertCreditcardSql = properties.getProperty("INSERT_CREDITCARD_SQL");
            updateClientSql = properties.getProperty("UPDATE_CLIENT_SQL");
            updateCreditcardSql = properties.getProperty("UPDATE_CREDITCARD_SQL");
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
    }

    public static ConfigurationManager getInstance() {
        return configurationManager;
    }
}

package storage.mysql.dao.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storage.mysql.MySqlConnectionPool;
import util.ConfigurationManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class MySqlBaseDAO {

    private static final Logger logger = LoggerFactory.getLogger(MySqlBaseDAO.class);

    protected MySqlConnectionPool connectionPool;

    protected MySqlBaseDAO(MySqlConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    protected boolean deleteItem(int itemId, String deleteKey) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    ConfigurationManager.getInstance().get(deleteKey));

            statement.setInt(1, itemId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return false;
    }
}

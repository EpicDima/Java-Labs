package storage.mysql.dao;

import model.users.management.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storage.base.dao.AdminDAO;
import storage.mysql.MySqlConnectionPool;
import storage.mysql.dao.base.MySqlBaseDAO;
import util.ConfigurationManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlAdminDAO extends MySqlBaseDAO implements AdminDAO {

    private static final Logger logger = LoggerFactory.getLogger(MySqlAdminDAO.class);

    private static final String INSERT_KEY = "MYSQL_INSERT_ADMIN";
    private static final String SELECT_KEY = "MYSQL_SELECT_ADMINS";
    private static final String UPDATE_KEY = "MYSQL_UPDATE_ADMIN";
    private static final String DELETE_KEY = "MYSQL_DELETE_ADMIN";

    private static final String SELECT_BY_LOGIN_KEY = "MYSQL_SELECT_ADMIN_BY_LOGIN";

    public MySqlAdminDAO(MySqlConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public boolean create(Admin item) {
        Connection connection = connectionPool.getConnection();
        try {
            CallableStatement statement = connection.prepareCall(
                    ConfigurationManager.getInstance().get(INSERT_KEY));

            statement.setString(1, item.getLogin());
            statement.setString(2, item.getPassword());
            statement.registerOutParameter(3, Types.INTEGER);

            if (statement.executeUpdate() > 0) {
                item.setId(statement.getInt(3));
                return true;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return false;
    }

    @Override
    public List<Admin> read() {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    ConfigurationManager.getInstance().get(SELECT_KEY));

            List<Admin> admins = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Admin admin = new Admin(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3));
                    admins.add(admin);
                }
            }
            return admins;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public boolean update(Admin item) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    ConfigurationManager.getInstance().get(UPDATE_KEY));

            statement.setString(1, item.getLogin());
            statement.setString(2, item.getPassword());
            statement.setInt(3, item.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return false;
    }

    @Override
    public boolean delete(Admin item) {
        return deleteItem(item.getId(), DELETE_KEY);
    }

    @Override
    public Admin getByLogin(String login) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    ConfigurationManager.getInstance().get(SELECT_BY_LOGIN_KEY));
            statement.setString(1, login);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Admin(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3));
                }
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return null;
    }
}

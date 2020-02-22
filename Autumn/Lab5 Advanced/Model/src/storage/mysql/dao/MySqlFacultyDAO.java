package storage.mysql.dao;

import model.Faculty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storage.base.dao.FacultyDAO;
import storage.mysql.MySqlConnectionPool;
import storage.mysql.dao.base.MySqlBaseDAO;
import util.ConfigurationManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlFacultyDAO extends MySqlBaseDAO implements FacultyDAO {

    private static final Logger logger = LoggerFactory.getLogger(MySqlFacultyDAO.class);

    private static final String INSERT_KEY = "MYSQL_INSERT_FACULTY";
    private static final String SELECT_KEY = "MYSQL_SELECT_FACULTIES";
    private static final String UPDATE_KEY = "MYSQL_UPDATE_FACULTY";
    private static final String DELETE_KEY = "MYSQL_DELETE_FACULTY";

    private static final String SELECT_BY_ID_KEY = "MYSQL_SELECT_FACULTY_BY_ID";
    private static final String SELECT_ACTIVE_KEY = "MYSQL_SELECT_ACTIVE_FACULTIES";

    public MySqlFacultyDAO(MySqlConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public boolean create(Faculty item) {
        Connection connection = connectionPool.getConnection();
        try {
            CallableStatement statement = connection.prepareCall(
                    ConfigurationManager.getInstance().get(INSERT_KEY));

            statement.setString(1, item.getName());
            statement.setString(2, item.getFirstDiscipline());
            statement.setString(3, item.getSecondDiscipline());
            statement.setString(4, item.getThirdDiscipline());
            statement.setInt(5, item.getPlan());
            statement.setBoolean(6, item.isActive());
            statement.registerOutParameter(7, Types.INTEGER);

            if (statement.executeUpdate() > 0) {
                item.setId(statement.getInt(7));
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
    public List<Faculty> read() {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    ConfigurationManager.getInstance().get(SELECT_KEY));

            List<Faculty> faculties = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Faculty faculty = new Faculty(
                            resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4),
                            resultSet.getString(5), resultSet.getInt(6),
                            resultSet.getBoolean(7));
                    faculties.add(faculty);
                }
            }
            return faculties;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public boolean update(Faculty item) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    ConfigurationManager.getInstance().get(UPDATE_KEY));

            statement.setString(1, item.getName());
            statement.setString(2, item.getFirstDiscipline());
            statement.setString(3, item.getSecondDiscipline());
            statement.setString(4, item.getThirdDiscipline());
            statement.setInt(5, item.getPlan());
            statement.setBoolean(6, item.isActive());
            statement.setInt(7, item.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return false;
    }

    @Override
    public boolean delete(Faculty item) {
        return deleteItem(item.getId(), DELETE_KEY);
    }

    @Override
    public Faculty getById(int id) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    ConfigurationManager.getInstance().get(SELECT_BY_ID_KEY));
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Faculty(
                            resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4),
                            resultSet.getString(5), resultSet.getInt(6),
                            resultSet.getBoolean(7));
                }
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public List<Faculty> getActiveFaculties() {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    ConfigurationManager.getInstance().get(SELECT_ACTIVE_KEY));

            List<Faculty> faculties = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Faculty faculty = new Faculty(
                            resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4),
                            resultSet.getString(5), resultSet.getInt(6),
                            resultSet.getBoolean(7));
                    faculties.add(faculty);
                }
            }
            return faculties;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return null;
    }
}

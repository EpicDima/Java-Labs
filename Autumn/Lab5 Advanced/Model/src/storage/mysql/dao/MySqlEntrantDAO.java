package storage.mysql.dao;

import model.users.study.Entrant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storage.base.dao.EntrantDAO;
import storage.mysql.MySqlConnectionPool;
import storage.mysql.dao.base.MySqlBaseDAO;
import util.ConfigurationManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlEntrantDAO extends MySqlBaseDAO implements EntrantDAO {

    private static final Logger logger = LoggerFactory.getLogger(MySqlEntrantDAO.class);

    private static final String INSERT_KEY = "MYSQL_INSERT_ENTRANT";
    private static final String SELECT_KEY = "MYSQL_SELECT_ENTRANTS";
    private static final String UPDATE_KEY = "MYSQL_UPDATE_ENTRANT";
    private static final String DELETE_KEY = "MYSQL_DELETE_ENTRANT";

    private static final String SELECT_BY_LOGIN_KEY = "MYSQL_SELECT_ENTRANT_BY_LOGIN";
    private static final String SELECT_BY_FACULTY_AND_ENROLLED_KEY = "MYSQL_SELECT_ENTRANTS_BY_FACULTY_AND_ENROLLED";
    private static final String DELETE_BY_ENROLLED_KEY = "MYSQL_DELETE_ENTRANTS_BY_ENROLLED";

    public MySqlEntrantDAO(MySqlConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public boolean create(Entrant item) {
        Connection connection = connectionPool.getConnection();
        try {
            CallableStatement statement = connection.prepareCall(
                    ConfigurationManager.getInstance().get(INSERT_KEY));

            statement.setString(1, item.getLogin());
            statement.setString(2, item.getPassword());
            statement.setString(3, item.getSurname());
            statement.setString(4, item.getName());
            statement.setString(5, item.getMiddleName());
            statement.setInt(6, item.getCertificate());
            statement.setInt(7, item.getFirstDiscipline());
            statement.setInt(8, item.getSecondDiscipline());
            statement.setInt(9, item.getThirdDiscipline());
            statement.setInt(10, item.getEnrolled().ordinal());
            statement.setInt(11, item.getFacultyId());
            statement.registerOutParameter(12, Types.INTEGER);

            if (statement.executeUpdate() > 0) {
                item.setId(statement.getInt(12));
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
    public List<Entrant> read() {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    ConfigurationManager.getInstance().get(SELECT_KEY));
            return getEntrants(statement);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public boolean update(Entrant item) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    ConfigurationManager.getInstance().get(UPDATE_KEY));

            statement.setString(1, item.getLogin());
            statement.setString(2, item.getPassword());
            statement.setString(3, item.getSurname());
            statement.setString(4, item.getName());
            statement.setString(5, item.getMiddleName());
            statement.setInt(6, item.getCertificate());
            statement.setInt(7, item.getFirstDiscipline());
            statement.setInt(8, item.getSecondDiscipline());
            statement.setInt(9, item.getThirdDiscipline());
            statement.setInt(10, item.getEnrolled().ordinal());
            statement.setInt(11, item.getFacultyId());
            statement.setInt(12, item.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return false;
    }

    @Override
    public boolean delete(Entrant item) {
        return deleteItem(item.getId(), DELETE_KEY);
    }

    @Override
    public Entrant getByLogin(String login) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    ConfigurationManager.getInstance().get(SELECT_BY_LOGIN_KEY));
            statement.setString(1, login);
            List<Entrant> entrants = getEntrants(statement);
            if (entrants.size() == 1) {
                return entrants.get(0);
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public List<Entrant> getEntrantsByFacultyIdAndEnrolled(int facultyId, Entrant.Enrolled enrolled) {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    ConfigurationManager.getInstance().get(SELECT_BY_FACULTY_AND_ENROLLED_KEY));

            statement.setInt(1, facultyId);
            statement.setInt(2, enrolled.ordinal());

            return getEntrants(statement);
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return null;
    }

    private List<Entrant> getEntrants(PreparedStatement statement) throws SQLException {
        List<Entrant> entrants = new ArrayList<>();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Entrant entrant = new Entrant(
                        resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getInt(7), resultSet.getInt(8),
                        resultSet.getInt(9), resultSet.getInt(10),
                        Entrant.Enrolled.fromInt(resultSet.getInt(11)),
                        resultSet.getInt(12));
                entrants.add(entrant);
            }
        }
        return entrants;
    }

    @Override
    public boolean deleteAllByEnrolled(Entrant.Enrolled enrolled) {
        Connection connection = connectionPool.getConnection();
        try {
            CallableStatement statement = connection.prepareCall(
                    ConfigurationManager.getInstance().get(DELETE_BY_ENROLLED_KEY));
            statement.setInt(1, enrolled.ordinal());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return false;
    }
}

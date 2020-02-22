package storage.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storage.base.dao.AdminDAO;
import storage.base.DAOFactory;
import storage.base.dao.EntrantDAO;
import storage.base.dao.FacultyDAO;
import storage.mysql.dao.MySqlAdminDAO;
import storage.mysql.dao.MySqlEntrantDAO;
import storage.mysql.dao.MySqlFacultyDAO;

public class MySqlDAOFactory extends DAOFactory {

    private static final Logger logger = LoggerFactory.getLogger(MySqlDAOFactory.class);

    private MySqlConnectionPool connectionPool;

    public MySqlDAOFactory() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        connectionPool = new MySqlConnectionPool();
    }

    @Override
    public AdminDAO getAdminDAO() {
        return new MySqlAdminDAO(connectionPool);
    }

    @Override
    public FacultyDAO getFacultyDAO() {
        return new MySqlFacultyDAO(connectionPool);
    }

    @Override
    public EntrantDAO getEntrantDAO() {
        return new MySqlEntrantDAO(connectionPool);
    }
}

package storage.base;

import storage.base.dao.AdminDAO;
import storage.base.dao.EntrantDAO;
import storage.base.dao.FacultyDAO;
import storage.mysql.MySqlDAOFactory;

public abstract class DAOFactory {
    public enum Type {
        MYSQL
    }

    public static DAOFactory create(Type type) {
        switch (type) {
            case MYSQL:
                return new MySqlDAOFactory();
            default:
                throw new IllegalArgumentException();
        }
    }

    public abstract AdminDAO getAdminDAO();
    public abstract FacultyDAO getFacultyDAO();
    public abstract EntrantDAO getEntrantDAO();
}

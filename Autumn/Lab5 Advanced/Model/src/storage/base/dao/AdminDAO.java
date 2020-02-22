package storage.base.dao;

import model.users.management.Admin;
import storage.base.dao.base.BaseDAO;

public interface AdminDAO extends BaseDAO<Admin> {
    Admin getByLogin(String login);
}

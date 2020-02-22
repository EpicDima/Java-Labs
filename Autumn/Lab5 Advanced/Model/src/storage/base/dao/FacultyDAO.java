package storage.base.dao;

import model.Faculty;
import storage.base.dao.base.BaseDAO;

import java.util.List;

public interface FacultyDAO extends BaseDAO<Faculty> {
    Faculty getById(int id);
    List<Faculty> getActiveFaculties();
}

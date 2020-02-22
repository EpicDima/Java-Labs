package storage.base.dao;

import model.users.study.Entrant;
import storage.base.dao.base.BaseDAO;

import java.util.List;

public interface EntrantDAO extends BaseDAO<Entrant> {
    Entrant getByLogin(String login);
    List<Entrant> getEntrantsByFacultyIdAndEnrolled(int facultyId, Entrant.Enrolled enrolled);
    boolean deleteAllByEnrolled(Entrant.Enrolled enrolled);
}

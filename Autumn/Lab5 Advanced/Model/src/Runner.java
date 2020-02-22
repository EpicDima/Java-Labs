import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import model.Faculty;
import model.users.management.Admin;
import model.users.study.Entrant;
import storage.base.DAOFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Runner {

    public static void main(String[] args) {

        final DAOFactory factory = DAOFactory.create(DAOFactory.Type.MYSQL);

        final Admin admin = new Admin("admin", "admin");
        final Faculty faculty = new Faculty("Факультет автоматизированных и информационных систем",
                "Математика", "Физика", "Русский язык", 50, true);

        throwErrorIfTrue(!factory.getAdminDAO().create(admin));
        throwErrorIfTrue(!factory.getFacultyDAO().create(faculty));

        final Entrant entrant = new Entrant("Dima", "qwerty", "Дашкевич", "Дмитрий",
                "Александрович", 80, 90, 90, 90,
                Entrant.Enrolled.UNKNOWN, faculty.getId());

        throwErrorIfTrue(!factory.getEntrantDAO().create(entrant));

        throwErrorIfTrue(factory.getAdminDAO().create(admin));
        throwErrorIfTrue(factory.getFacultyDAO().create(faculty));
        throwErrorIfTrue(factory.getEntrantDAO().create(entrant));

        admin.setPassword("very hard password");
        faculty.setActive(true);
        entrant.setEnrolled(Entrant.Enrolled.YES);

        throwErrorIfTrue(!factory.getAdminDAO().update(admin));
        throwErrorIfTrue(!factory.getFacultyDAO().update(faculty));
        throwErrorIfTrue(!factory.getEntrantDAO().update(entrant));

        List<Admin> admins = factory.getAdminDAO().read();
        List<Faculty> faculties = factory.getFacultyDAO().read();
        List<Entrant> entrants = factory.getEntrantDAO().read();
        throwErrorIfTrue(admins == null || admins.size() == 0 || !admins.get(0).equals(admin));
        throwErrorIfTrue(faculties == null || faculties.size() == 0 || !faculties.get(0).equals(faculty));
        throwErrorIfTrue(entrants == null || entrants.size() == 0 || !entrants.get(0).equals(entrant));

        Admin adminDromDb = factory.getAdminDAO().getByLogin(admin.getLogin());
        throwErrorIfTrue(!admin.equals(adminDromDb));

        Entrant entrantFromDb = factory.getEntrantDAO().getByLogin(entrant.getLogin());
        throwErrorIfTrue(!entrant.equals(entrantFromDb));

        List<Entrant> entrants2 = factory.getEntrantDAO()
                .getEntrantsByFacultyIdAndEnrolled(faculty.getId(), Entrant.Enrolled.YES);
        throwErrorIfTrue(entrants2 == null || entrants2.size() != 1 || !entrant.equals(entrants2.get(0)));
        throwErrorIfTrue(!factory.getEntrantDAO().deleteAllByEnrolled(Entrant.Enrolled.UNKNOWN));

        throwErrorIfTrue(!factory.getAdminDAO().delete(admin));
        throwErrorIfTrue(!factory.getEntrantDAO().delete(entrant));
        throwErrorIfTrue(!factory.getFacultyDAO().delete(faculty));
    }

    private static void throwErrorIfTrue(boolean test) {
        if (test) {
            throw new AssertionError();
        }
    }
}

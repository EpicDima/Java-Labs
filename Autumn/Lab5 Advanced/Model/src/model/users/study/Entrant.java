package model.users.study;

import model.users.base.User;

public class Entrant extends User {

    public enum Enrolled {
        UNKNOWN, YES, NO, LONG_AGO;

        public static Enrolled fromInt(int code) {
            return Enrolled.values()[code];
        }
    }


    private final String surname;
    private final String name;
    private final String middleName;

    private final int certificate;
    private final int firstDiscipline;
    private final int secondDiscipline;
    private final int thirdDiscipline;
    private final int facultyId;

    private Enrolled enrolled;

    public Entrant(int id, String login, String password, String surname, String name, String middleName,
                   int certificate, int firstDiscipline, int secondDiscipline, int thirdDiscipline,
                   Enrolled enrolled, int facultyId) {
        super(id, login, password);
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.certificate = certificate;
        this.firstDiscipline = firstDiscipline;
        this.secondDiscipline = secondDiscipline;
        this.thirdDiscipline = thirdDiscipline;
        this.enrolled = enrolled;
        this.facultyId = facultyId;
    }

    public Entrant(String login, String password, String surname, String name, String middleName,
                   int certificate, int firstDiscipline, int secondDiscipline, int thirdDiscipline,
                   Enrolled enrolled, int facultyId) {
        this(-1, login, password, surname, name, middleName, certificate, firstDiscipline,
                secondDiscipline, thirdDiscipline, enrolled, facultyId);
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public int getCertificate() {
        return certificate;
    }

    public int getFirstDiscipline() {
        return firstDiscipline;
    }

    public int getSecondDiscipline() {
        return secondDiscipline;
    }

    public int getThirdDiscipline() {
        return thirdDiscipline;
    }

    public Enrolled getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(Enrolled enrolled) {
        this.enrolled = enrolled;
    }

    public int getFacultyId() {
        return facultyId;
    }
}

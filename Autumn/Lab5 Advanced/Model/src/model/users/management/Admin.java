package model.users.management;

import model.users.base.User;

public class Admin extends User {
    public Admin(int id, String login, String password) {
        super(id, login, password);
    }

    public Admin(String login, String password) {
        this(-1, login, password);
    }
}

package main.models;

import main.utils.PasswordManager;

import java.util.List;

public class Users {
    private List<User> users;
    private String username;
    private String hashedPassword;

    public Users(List<User> users, String username, String password) {
        this.users = users;
        this.username = username;
        this.hashedPassword = PasswordManager.getHashedPassword(password);
    }
    public Users() {}

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}

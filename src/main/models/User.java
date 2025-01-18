package main.models;

import main.utils.PasswordManager;

public class User {
    private String username;
    private String hashedPassword;

    public User(String username, String password) {
        this.username = username;
        this.hashedPassword = PasswordManager.getHashedPassword(password);
    }

    public User() {}

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

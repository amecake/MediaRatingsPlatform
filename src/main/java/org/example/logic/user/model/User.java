package org.example.logic.user.model;

public class User {
    private int id; // Primary key
    private String username;
    private String password;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Username: " + username + "\nPassword: " + password;
    }

    public int getId() {
        return id;
    }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public void setId(int id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

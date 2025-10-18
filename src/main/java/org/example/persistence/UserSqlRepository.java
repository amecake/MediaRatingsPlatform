package org.example.persistence;

import org.example.model.User;

import java.util.ArrayList;

public class UserSqlRepository implements IUserRepository {
    private static UserSqlRepository instance;
    private final ArrayList<User> userList = new ArrayList<>();

    public boolean register(User newUser) {
        // First check if username already exists
        for (int i = 0; i < userList.size(); i++) {
            // In case new username matches with an existing, return false
            if (newUser.getUsername().equals(userList.get(i).getUsername())) {
                return true;
            }
        }

        // Has to first set username and password with given items
        newUser.setUsername(newUser.getUsername());
        newUser.setPassword(newUser.getPassword());
        userList.add(newUser);
        System.out.println(newUser.getUsername() + " has been registered!");

        // Print currently registered users
        for (int i = 0; i < userList.size(); i++) {
            System.out.println("User " + (i+1) + ":\n" + newUser + "\n");
        }

        return false;
    }

    public void login(User user) {
        // Check if username exists
        String correctUser;
        for (int i = 0; i < userList.size(); i++) {
            if (!userList.get(i).getUsername().equals(user.getUsername())) {
                System.out.println("User doesn't exist");
                return;
            } else {
                break;
            }
        }

        correctUser = user.getUsername();
        // If username was found, check if password matches
        if (!correctUser.equals(user.getPassword())) {
            System.out.println("Incorrect password");
        } else {
            System.out.println("You have been logged in");
        }
    }
}

package org.example.persistence;

import org.example.model.User;

import java.util.ArrayList;

public class UserSqlRepository implements IUserRepository {
    private final ArrayList<User> userList = new ArrayList<>();

    public void register(User user) {
        // Has to first set username and password with given items
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        userList.add(user);
        System.out.println(user.getUsername() + " has been registered!");

        // Print currently registered users
        for (int i = 1; i <= userList.size(); i++) {
            System.out.println("User " + i + ": " + user + "\n");
        }
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

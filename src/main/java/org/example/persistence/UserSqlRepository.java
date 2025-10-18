package org.example.persistence;

import org.example.model.User;

import java.util.ArrayList;

public class UserSqlRepository implements IUserRepository {
    private static UserSqlRepository instance;
    private static final ArrayList<User> userList = new ArrayList<>();

    public static UserSqlRepository getInstance() {
        if (instance == null) {
            instance = new UserSqlRepository();
        }
        return instance;
    }

    public void register(User newUser) {
        // Add user to the user list
        UserSqlRepository.userList.add(newUser);
        System.out.println(newUser.getUsername() + " has been registered!");

        // Print currently registered users
        for (int i = 0; i < UserSqlRepository.userList.size(); i++) {
            System.out.println("User " + (i+1) + ":\n" + UserSqlRepository.userList.get(i) + "\n");
        }
    }

    public boolean isUniqueUsername(String username) {
        // Check if username is unique
        for (int i = 0; i < UserSqlRepository.userList.size(); i++) {
            // In case new username matches with an existing, return false
            if (username.equals(UserSqlRepository.userList.get(i).getUsername())) {
                return false;
            }
        }
        return true;
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

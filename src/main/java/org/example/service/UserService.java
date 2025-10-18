package org.example.service;

import org.example.model.User;
import org.example.persistence.IUserRepository;
import org.example.persistence.UserSqlRepository;

public class UserService implements IUserService {
    private final IUserRepository repository = UserSqlRepository.getInstance();

    public boolean register(User newUser) {
        // Check if username is unique
        if (!repository.isUniqueUsername(newUser.getUsername())) {
            return false;
        }

        repository.register(newUser);

        return true;
    }

    public String login(User checkUser) {
        // Check if username doesn't exist
        if (!repository.usernameExists(checkUser.getUsername())) {
            return "Username doesn't exist";
        }

        // Check if password matches with username
        if (!repository.usernameMatchPw(checkUser.getUsername(), checkUser.getPassword())) {
            return "Password doesn't match username";
        }

        repository.login(checkUser);

        return "success";
    }
}

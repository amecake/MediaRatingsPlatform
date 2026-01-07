package org.example.logic.user.service;

import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.user.model.User;
import org.example.logic.user.persistence.IUserRepository;

import java.util.Map;

public class UserService implements IUserService {
    private final IUserRepository repository;

    public UserService(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean register(User newUser) {
        // Check if username is unique
        if (!repository.isUniqueUsername(newUser.getUsername())) {
            return false;
        }

        int id = repository.register(newUser);
        newUser.setId(id);

        return true;
    }

    @Override
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

    @Override
    public User getUserByUsername(String username) {
        return repository.getUserByUsername(username);
    }

    @Override
    public Map<String, Object> getProfileStats(User user) {
        return repository.getProfileStats(user);
    }
}

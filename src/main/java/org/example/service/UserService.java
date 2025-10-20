package org.example.service;

import org.example.model.MediaEntry;
import org.example.model.User;
import org.example.persistence.IUserRepository;
import org.example.persistence.UserSqlRepository;

public class UserService implements IUserService {
    private final IUserRepository repository = UserSqlRepository.getInstance();

    @Override
    public boolean register(User newUser) {
        // Check if username is unique
        if (!repository.isUniqueUsername(newUser.getUsername())) {
            return false;
        }

        repository.register(newUser);

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
    public String createMediaEntry(User user, MediaEntry mediaEntry) {
        repository.createMediaEntry(user, mediaEntry);

        return "success";
    }

    @Override
    public String listMediaEntries(User user) {
        repository.listMediaEntries(user);

        return "success";
    }

    @Override
    public String viewMediaEntry(User user, int index) {
        repository.viewMediaEntry(user, index);

        return "success";
    }

    @Override
    public MediaEntry getMediaEntryByIndex(User user, int index) {
        return repository.getMediaEntryByIndex(user, index);
    }

    @Override
    public String updateMediaEntry(User user, MediaEntry mediaEntry, int index) {
        repository.updateMediaEntry(user, mediaEntry, index);

        return "success";
    }

    @Override
    public String deleteMediaEntry(User user, int index) {
        repository.deleteMediaEntry(user, index);

        return "success";
    }
}

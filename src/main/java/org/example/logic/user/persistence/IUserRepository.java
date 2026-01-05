package org.example.logic.user.persistence;

// Always need to import files (like include in C++)
import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.user.model.User;

public interface IUserRepository {
    int register(User user);
    boolean isUniqueUsername(String username);
    void login(User user);
    boolean usernameExists(String username);
    boolean usernameMatchPw(String username, String password);
    User getUserByUsername(String username);

    void listMediaEntries(User user);
    void viewMediaEntry(User user, int index);
}

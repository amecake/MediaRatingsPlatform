package org.example.persistence;

// Always need to import files (like include in C++)
import org.example.model.User;

public interface IUserRepository {
    void register(User user);
    boolean isUniqueUsername(String username);
    void login(User user);
    boolean usernameExists(String username);
    boolean usernameMatchPw(String username, String password);
}

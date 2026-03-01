package org.example.logic.user.persistence;

// Always need to import files (like include in C++)
import org.example.logic.user.model.User;

import java.util.Map;

public interface IUserRepository {
    int register(User user);
    boolean isUniqueUsername(String username);
    boolean usernameExists(String username);
    boolean usernameMatchPw(String username, String password);
    User getUserByUsername(String username);
    Map<String, Object> getProfileStats(User user);
}

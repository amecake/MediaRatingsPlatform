package org.example.persistence;

// Always need to import files (like include in C++)
import org.example.model.User;

public interface IUserRepository {
    boolean register(User user);
    void login(User user);
}

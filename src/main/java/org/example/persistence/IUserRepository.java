package org.example.persistence;

// Always need to import files (like include in C++)
import org.example.model.MediaEntry;
import org.example.model.User;

public interface IUserRepository {
    void register(User user);
    boolean isUniqueUsername(String username);
    void login(User user);
    boolean usernameExists(String username);
    boolean usernameMatchPw(String username, String password);

    void createMediaEntry(User user, MediaEntry mediaEntry);
    void listMediaEntries(User user);
    void viewMediaEntry(User user, int index);
    MediaEntry getMediaEntryByIndex(User user, int index);
    void updateMediaEntry(User user, MediaEntry mediaEntry, int index);
    void deleteMediaEntry(User user, int index);
}

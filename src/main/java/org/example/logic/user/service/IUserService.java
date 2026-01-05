package org.example.logic.user.service;

import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.user.model.User;

public interface IUserService {
    boolean register(User user);
    String login(User user);
    User getUserByUsername(String username);
    String listMediaEntries(User user);
    String viewMediaEntry(User user, int index);
}

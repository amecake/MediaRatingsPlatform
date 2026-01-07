package org.example.logic.user.service;

import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.user.model.User;

import java.util.Map;

public interface IUserService {
    boolean register(User user);
    String login(User user);
    User getUserByUsername(String username);
    Map<String, Object> getProfileStats(User user);
}

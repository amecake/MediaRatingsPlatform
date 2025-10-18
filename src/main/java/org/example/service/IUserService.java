package org.example.service;

import org.example.model.User;

public interface IUserService {
    boolean register(User user);
    String login(User user);
}

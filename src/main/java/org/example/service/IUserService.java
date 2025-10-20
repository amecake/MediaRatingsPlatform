package org.example.service;

import org.example.model.MediaEntry;
import org.example.model.User;

public interface IUserService {
    boolean register(User user);
    String login(User user);
    String createMediaEntry(User user, MediaEntry mediaEntry);
    String listMediaEntries(User user);
    String viewMediaEntry(User user, int index);
    MediaEntry getMediaEntryByIndex(User user, int index);
    String updateMediaEntry(User user, MediaEntry mediaEntry, int index);
    String deleteMediaEntry(User user, int index);
}

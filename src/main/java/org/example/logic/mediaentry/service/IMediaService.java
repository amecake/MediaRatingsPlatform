package org.example.logic.mediaentry.service;

import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.user.model.User;

public interface IMediaService {
    String createMediaEntry(User user, MediaEntry mediaEntry);
    String listMediaEntries(User user);
    String viewMediaEntry(User user, int index);
    MediaEntry getMediaEntryByIndex(int id);
    String updateMediaEntry(User loggedInUser, MediaEntry mediaEntry);
    String deleteMediaEntry(User loggedInUser, int id);
}

package org.example.logic.mediaentry.service;

import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.user.model.User;

public interface IMediaService {
    boolean createMediaEntry(User user, MediaEntry mediaEntry);
    String listMediaEntries(User user);
    MediaEntry getMediaEntryByIndex(int id);
    boolean updateMediaEntry(User loggedInUser, MediaEntry mediaEntry);
    String deleteMediaEntry(User loggedInUser, int id);
    float getAverageScore(int media_id);
}

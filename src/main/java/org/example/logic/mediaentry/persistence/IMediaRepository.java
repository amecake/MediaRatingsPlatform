package org.example.logic.mediaentry.persistence;

import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.user.model.User;

public interface IMediaRepository {
    void createMediaEntry(User user, MediaEntry mediaEntry);
    void listMediaEntries(User user);
    void viewMediaEntry(User user, int index);
    MediaEntry getMediaEntryById(int id);
    void updateMediaEntry(MediaEntry mediaEntry);
    void deleteMediaEntry(int id);
    float getAverageScore(int media_id);
}

package org.example.logic.mediaentry.service;

import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.mediaentry.persistence.IMediaRepository;
import org.example.logic.user.model.User;

public class MediaService implements IMediaService {
    private final IMediaRepository repository;

    public MediaService(IMediaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean createMediaEntry(User user, MediaEntry mediaEntry) {
        // Creator has to be the logged-in user
        mediaEntry.setCreatorId(user.getId());

        repository.createMediaEntry(user, mediaEntry);

        return true;
    }

    @Override
    public String listMediaEntries(User user) {
        repository.listMediaEntries(user);

        return "success";
    }

    @Override
    public MediaEntry getMediaEntryByIndex(int id) {
        return repository.getMediaEntryById(id);
    }

    @Override
    public boolean updateMediaEntry(User loggedInUser, MediaEntry mediaEntry) {

        // Validate ownership
        if (mediaEntry.getCreatorId() != loggedInUser.getId())
            return false;

        repository.updateMediaEntry(mediaEntry);

        return true;
    }

    @Override
    public String deleteMediaEntry(User loggedInUser, int id) {

        // Find media entry that needs to be deleted
        MediaEntry mediaEntry = repository.getMediaEntryById(id);

        // If media entry doesn't exist
        if (mediaEntry == null)
            return "not found";

        // Verify ownership
        if (mediaEntry.getCreatorId() != loggedInUser.getId())
            return "forbidden";

        repository.deleteMediaEntry(id);

        return "success";
    }

    @Override
    public float getAverageScore(int media_id) {
        return repository.getAverageScore(media_id);
    }
}

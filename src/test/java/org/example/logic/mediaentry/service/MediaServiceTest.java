package org.example.logic.mediaentry.service;

import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.mediaentry.persistence.IMediaRepository;
import org.example.logic.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MediaServiceTest {

    @Mock
    private IMediaRepository mediaRepository;

    @InjectMocks
    private MediaService mediaService;

    @Test
    void user_creates_media_entry() {
        User user = new User();
        MediaEntry mediaEntry = new MediaEntry();

        boolean result = mediaService.createMediaEntry(user, mediaEntry);

        assertTrue(result);

        verify(mediaRepository).createMediaEntry(user, mediaEntry);
    }

    @Test
    void user_lists_media_entries() {
        User user = new User();

        String result = mediaService.listMediaEntries(user);

        assertEquals("success", result);

        verify(mediaRepository).listMediaEntries(user);
    }

    @Test
    void user_gets_media_entry_by_id() {
        int id = 1;

        MediaEntry mediaEntry = new MediaEntry();

        when(mediaRepository.getMediaEntryById(id)).thenReturn(mediaEntry);

        MediaEntry result = mediaService.getMediaEntryByIndex(id);

        assertEquals(mediaEntry, result);

        verify(mediaRepository).getMediaEntryById(id);
    }

    @Test
    void user_attempts_updating_someone_elses_media_entry() {

        // Set a logged in user with different ID from media entry creator id
        User loggedInUser = new User("amelia", "pw");
        loggedInUser.setId(1);

        MediaEntry mediaEntry = new MediaEntry();
        mediaEntry.setCreatorId(2);

        boolean result = mediaService.updateMediaEntry(loggedInUser, mediaEntry);

        // It should return false
        assertFalse(result);

        // It should never reach this function
        verify(mediaRepository, never()).updateMediaEntry(any());

    }

    @Test
    void user_attempts_deleting_someone_elses_media_entry() {

        // Set a logged in user with different ID from media entry creator id
        User loggedInUser = new User("amelia", "pw");
        loggedInUser.setId(1);

        // This ID is the one of which the owner has to be verified
        int id = 2;

        // This is the media entry ID. Idk why i made this 2 times
        MediaEntry mediaEntry = new MediaEntry();
        mediaEntry.setCreatorId(2);

        when(mediaRepository.getMediaEntryById(id)).thenReturn(mediaEntry);

        String result = mediaService.deleteMediaEntry(loggedInUser, id);

        // It should return false
        assertEquals("forbidden", result);

        // It should never reach this function
        verify(mediaRepository, never()).deleteMediaEntry(id);
    }

    @Test
    void deleting_non_existing_media_entry() {

        User loggedInUser = new User("amelia", "pw");
        loggedInUser.setId(1);

        int id = 1;

        MediaEntry mediaEntry = new MediaEntry();
        mediaEntry.setCreatorId(id);

        when(mediaRepository.getMediaEntryById(id)).thenReturn(null);

        String result = mediaService.deleteMediaEntry(loggedInUser, id);

        assertEquals("not found", result);

        verify(mediaRepository, never()).deleteMediaEntry(id);

    }

    @Test
    void user_deletes_media_entry() {

        User loggedInUser = new User("amelia", "pw");
        loggedInUser.setId(1);

        int id = 1;

        MediaEntry mediaEntry = new MediaEntry();
        mediaEntry.setCreatorId(id);

        when(mediaRepository.getMediaEntryById(id)).thenReturn(mediaEntry);

        String result = mediaService.deleteMediaEntry(loggedInUser, id);

        assertEquals("success", result);

        verify(mediaRepository).deleteMediaEntry(id);
    }

    @Test
    void get_average_score() {
        int media_id = 1;

        float avgScore = 2.5f;

        when(mediaRepository.getAverageScore(media_id)).thenReturn(avgScore);

        float result = mediaService.getAverageScore(media_id);

        assertEquals(avgScore, result);

        verify(mediaRepository).getAverageScore(media_id);
    }
}

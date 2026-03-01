package org.example.logic.favorite.service;

import org.example.logic.favorite.repository.IFavoriteRepository;
import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceTest {

    @Mock
    private IFavoriteRepository favoriteRepository;

    @InjectMocks
    private FavoriteService favoriteService;

    @Test
    void mark_media_as_favorite_error() {
        User user = new User();

        int media_id = 1;

        // Simulate exception with doThrow
        doThrow(new RuntimeException()).when(favoriteRepository).markMediaAsFavorite(user, media_id);

        String result = favoriteService.markMediaAsFavorite(user, media_id);

        assertEquals("sql error", result);

        verify(favoriteRepository).markMediaAsFavorite(user, media_id);
    }

    @Test
    void mark_media_as_favorite_success() {
        User user = new User();

        int media_id = 1;

        String result = favoriteService.markMediaAsFavorite(user, media_id);

        assertEquals("success", result);

        verify(favoriteRepository).markMediaAsFavorite(user, media_id);
    }

    @Test
    void remove_media_from_favorite_error() {
        User user = new User();

        int media_id = 1;

        // Simulate exception with doThrow
        doThrow(new RuntimeException()).when(favoriteRepository).removeMediaFromFavorites(user, media_id);

        String result = favoriteService.removeMediaFromFavorites(user, media_id);

        assertEquals("sql error", result);

        verify(favoriteRepository).removeMediaFromFavorites(user, media_id);
    }

    @Test
    void remove_media_from_favorite_success() {
        User user = new User();

        int media_id = 1;

        String result = favoriteService.removeMediaFromFavorites(user, media_id);

        assertEquals("success", result);

        verify(favoriteRepository).removeMediaFromFavorites(user, media_id);
    }

    @Test
    void view_favorites_list_failure() {
        User user = new User();

        when(favoriteRepository.viewFavoritesList(user)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            favoriteService.viewFavoritesList(user);
        });

        verify(favoriteRepository).viewFavoritesList(user);
    }

    @Test
    void view_favorites_list_success() {
        User user = new User();
        MediaEntry mediaEntry = new MediaEntry();

        List<MediaEntry> list = new ArrayList<>();
        list.add(mediaEntry);

        when(favoriteRepository.viewFavoritesList(user)).thenReturn(list);

        List<MediaEntry> result = favoriteRepository.viewFavoritesList(user);

        assertEquals(list, result);

        verify(favoriteRepository).viewFavoritesList(user);
    }
}

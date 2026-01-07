package org.example.logic.favorite.repository;

import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.user.model.User;

import java.util.List;

public interface IFavoriteRepository {
    void markMediaAsFavorite(User user, int media_id);
    void removeMediaFromFavorites(User user, int media_id);
    List<MediaEntry> viewFavoritesList(User user);
}

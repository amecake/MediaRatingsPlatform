package org.example.logic.favorite.repository;

import org.example.logic.user.model.User;

public interface IFavoriteRepository {
    void markMediaAsFavorite(User user, int media_id);
    void removeMediaFromFavorites(User user, int media_id);
}

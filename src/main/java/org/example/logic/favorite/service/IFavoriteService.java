package org.example.logic.favorite.service;

import org.example.logic.user.model.User;

public interface IFavoriteService {
    String markMediaAsFavorite(User user, int media_id);
    String removeMediaFromFavorites(User user, int media_id);
}

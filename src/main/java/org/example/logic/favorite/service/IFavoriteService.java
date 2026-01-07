package org.example.logic.favorite.service;

import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.user.model.User;

import java.util.List;

public interface IFavoriteService {
    String markMediaAsFavorite(User user, int media_id);
    String removeMediaFromFavorites(User user, int media_id);
    List<MediaEntry> viewFavoritesList(User user);
}

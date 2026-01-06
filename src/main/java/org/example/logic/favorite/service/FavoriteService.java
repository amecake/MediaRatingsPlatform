package org.example.logic.favorite.service;

import org.example.logic.favorite.repository.IFavoriteRepository;
import org.example.logic.user.model.User;

public class FavoriteService implements IFavoriteService {
    public final IFavoriteRepository repository;

    public FavoriteService (IFavoriteRepository repository) {
        this.repository = repository;
    }

    @Override
    public String markMediaAsFavorite(User user, int media_id) {

        try {
            repository.markMediaAsFavorite(user, media_id);
        } catch (RuntimeException e) {
            return "sql error";
        }

        return "success";
    }

    @Override
    public String removeMediaFromFavorites(User user, int media_id) {

        try {
            repository.removeMediaFromFavorites(user, media_id);
        } catch (RuntimeException e) {
            return "sql error";
        }

        return "success";
    }
}

package org.example.logic.rating.persistence;

import org.example.logic.rating.model.Rating;
import org.example.logic.user.model.User;

public interface IRatingRepository {
    void addRating(User user, Rating rating, int media_id);
}

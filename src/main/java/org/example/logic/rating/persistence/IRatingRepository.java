package org.example.logic.rating.persistence;

import org.example.logic.rating.model.Rating;
import org.example.logic.user.model.User;

public interface IRatingRepository {
    void addRatingToMedia(User user, Rating rating, int media_id);
    void likeRating(int rating_id);
}

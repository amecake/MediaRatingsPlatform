package org.example.logic.rating.persistence;

import org.example.logic.rating.model.Rating;
import org.example.logic.user.model.User;

public interface IRatingRepository {
    void addRatingToMedia(User user, Rating rating, int media_id);
    Rating getRatingById(int rating_id);
    void updateRating(Rating rating);
    void deleteRating(int rating_id);
    void likeRating(int rating_id);
}

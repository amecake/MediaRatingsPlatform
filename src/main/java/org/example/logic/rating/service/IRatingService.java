package org.example.logic.rating.service;

import org.example.logic.rating.model.Rating;
import org.example.logic.user.model.User;

public interface IRatingService {
    String addRatingToMedia(User user, Rating rating, int media_id);
    Rating getRatingByIndex(int rating_id);
    boolean updateRating(User loggedInUser, Rating rating);
    String deleteRating(User loggedInUser, int id);
    String likeRating(int rating_id);
}

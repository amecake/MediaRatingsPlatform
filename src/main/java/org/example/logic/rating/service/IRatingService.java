package org.example.logic.rating.service;

import org.example.logic.rating.model.Rating;
import org.example.logic.user.model.User;

public interface IRatingService {
    String addRatingToMedia(User user, Rating rating, int media_id);
    String likeRating(int rating_id);
}

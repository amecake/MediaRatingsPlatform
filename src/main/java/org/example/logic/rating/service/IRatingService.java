package org.example.logic.rating.service;

import org.example.logic.rating.model.Rating;
import org.example.logic.user.model.User;

public interface IRatingService {
    String addRating(User user, Rating rating, int media_id);
}

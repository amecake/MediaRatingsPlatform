package org.example.logic.rating.service;

import org.example.logic.rating.model.Rating;
import org.example.logic.rating.persistence.IRatingRepository;
import org.example.logic.user.model.User;

public class RatingService implements IRatingService {
    private final IRatingRepository repository;

    public RatingService(IRatingRepository repository) {
        this.repository = repository;
    }

    @Override
    public String addRatingToMedia(User user, Rating rating, int media_id) {
        if (rating.getStars() < 1 || rating.getStars() > 5)
            return "stars have to be between 1 and 5";

        try {
            repository.addRatingToMedia(user, rating, media_id);
            return "success";
        } catch (RuntimeException e) {
            return "sql error";
        }
    }

    @Override
    public String likeRating(int rating_id) {

        try {
            repository.likeRating(rating_id);
            return "success";
        } catch (RuntimeException e) {
            return "sql error";
        }
    }
}

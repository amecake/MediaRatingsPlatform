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

        repository.addRatingToMedia(user, rating, media_id);

        return "success";
    }

    @Override
    public Rating getRatingByIndex(int rating_id) {
        return repository.getRatingById(rating_id);
    }

    @Override
    public boolean updateRating(User loggedInUser, Rating rating) {
        // Validate ownership
        if (rating.getUserId() != loggedInUser.getId())
            return false;

        repository.updateRating(rating);

        return true;
    }

    @Override
    public String deleteRating(User loggedInUser, int id) {
        // Find media entry that needs to be deleted
        Rating rating = repository.getRatingById(id);

        // If media entry doesn't exist
        if (rating == null)
            return "not found";

        // Verify ownership
        if (rating.getUserId() != loggedInUser.getId())
            return "forbidden";

        repository.deleteRating(id);

        return "success";
    }

    @Override
    public String likeRating(int rating_id) {
        repository.likeRating(rating_id);

        return "success";
    }
}

package org.example.logic.rating.service;

import org.example.logic.rating.model.Rating;
import org.example.logic.rating.persistence.IRatingRepository;
import org.example.logic.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private IRatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @Test
    void add_rating_when_stars_too_low() {
        User user = new User("merlin", "pw");

        Rating rating = new Rating();
        rating.setStars(0);

        int media_id = 1;

        String result = ratingService.addRatingToMedia(user, rating, media_id);

        assertEquals("stars have to be between 1 and 5", result);

        verify(ratingRepository, never()).addRatingToMedia(user, rating, media_id);

    }

    @Test
    void add_rating_when_stars_too_high() {
        User user = new User("merlin", "pw");

        Rating rating = new Rating();
        rating.setStars(6);

        int media_id = 1;

        String result = ratingService.addRatingToMedia(user, rating, media_id);

        assertEquals("stars have to be between 1 and 5", result);

        verify(ratingRepository, never()).addRatingToMedia(user, rating, media_id);

    }

    @Test
    void add_rating_correctly() {
        User user = new User("merlin", "pw");

        Rating rating = new Rating();
        rating.setStars(3);

        int media_id = 1;

        String result = ratingService.addRatingToMedia(user, rating, media_id);

        assertEquals("success", result);

        verify(ratingRepository).addRatingToMedia(user, rating, media_id);

    }

    @Test
    void get_rating_by_index() {
        int rating_id = 1;

        Rating rating = new Rating();

        when(ratingRepository.getRatingById(rating_id)).thenReturn(rating);

        Rating result = ratingService.getRatingByIndex(rating_id);

        assertEquals(rating, result);

        verify(ratingRepository).getRatingById(rating_id);
    }

    @Test
    void update_rating_incorrect_user() {

        User loggedInUser = new User();
        loggedInUser.setId(1);

        Rating rating = new Rating();
        rating.setUserId(2);

        boolean result = ratingService.updateRating(loggedInUser, rating);

        assertFalse(result);

        verify(ratingRepository, never()).updateRating(rating);
    }

    @Test
    void update_rating_correctly() {

        User loggedInUser = new User();
        loggedInUser.setId(1);

        Rating rating = new Rating();
        rating.setUserId(1);

        boolean result = ratingService.updateRating(loggedInUser, rating);

        assertTrue(result);

        verify(ratingRepository).updateRating(rating);
    }

    @Test
    void delete_rating_incorrect_user() {

        User loggedInUser = new User();
        loggedInUser.setId(1);

        int id = 2;

        Rating rating = new Rating();

        when(ratingRepository.getRatingById(id)).thenReturn(rating);

        String result = ratingService.deleteRating(loggedInUser, id);

        assertEquals("forbidden", result);

        verify(ratingRepository, never()).deleteRating(id);

    }

    @Test
    void delete_rating_invalid_id() {

        User loggedInUser = new User();
        loggedInUser.setId(1);

        int id = 2;

        when(ratingRepository.getRatingById(id)).thenReturn(null);

        String result = ratingService.deleteRating(loggedInUser, id);

        assertEquals("not found", result);

        verify(ratingRepository, never()).deleteRating(id);

    }

    @Test
    void like_rating() {
        int rating_id = 1;

        String result = ratingService.likeRating(rating_id);

        assertEquals("success", result);

        verify(ratingRepository).likeRating(rating_id);
    }
}

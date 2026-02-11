package org.example.logic.rating.persistence;

import org.example.logic.DatabaseManager;
import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.mediaentry.model.MediaType;
import org.example.logic.rating.model.Rating;
import org.example.logic.user.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingSqlRepository implements IRatingRepository {
    @Override
    public void addRatingToMedia(User user, Rating rating, int media_id) {
        String sql =
                "INSERT INTO ratings(user_id, media_id, stars, comment) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, user.getId());
            stmt.setInt(2, media_id);
            stmt.setInt(3, rating.getStars());
            stmt.setString(4, rating.getComment());

            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getMessage().contains("ratings_user_id_media_id_key")) {
                throw new IllegalStateException("User already rated this media.");
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public Rating getRatingById(int rating_id) {
        String sql =
                "SELECT rating_id, user_id, media_id, stars, comment, created_at, confirmed, likes " +
                "FROM ratings " +
                "WHERE rating_id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, rating_id);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                Rating rating = new Rating();
                rating.setId(result.getInt("rating_id"));
                rating.setUserId(result.getInt("user_id"));
                rating.setMediaId(result.getInt("media_id"));
                rating.setStars(result.getInt("stars"));
                rating.setComment(result.getString("comment"));
                rating.setTimestamp(result.getTimestamp("created_at").toLocalDateTime());
                rating.setConfirmed(result.getBoolean("confirmed"));
                rating.setLikes(result.getInt("likes"));

                return rating;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void updateRating(Rating rating) {
        String sql =
                "UPDATE ratings " +
                "SET stars = ?, comment = ? " +
                "WHERE rating_id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, rating.getStars());
            stmt.setString(2, rating.getComment());

            stmt.setInt(3, rating.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteRating(int rating_id) {
        String sql =
                "DELETE FROM ratings WHERE rating_id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, rating_id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void likeRating(int rating_id) {
        String sql =
                "UPDATE ratings " +
                "SET likes = likes + 1 " +
                "WHERE rating_id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, rating_id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                // No rating with this ID exists
                throw new RuntimeException("Rating not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

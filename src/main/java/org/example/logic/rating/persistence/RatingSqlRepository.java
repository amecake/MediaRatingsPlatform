package org.example.logic.rating.persistence;

import org.example.logic.DatabaseManager;
import org.example.logic.rating.model.Rating;
import org.example.logic.user.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RatingSqlRepository implements IRatingRepository {
    @Override
    public void addRating(User user, Rating rating, int media_id) {
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
            throw new RuntimeException(e);
        }
    }
}

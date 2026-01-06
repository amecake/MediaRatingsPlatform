package org.example.logic.favorite.repository;

import org.example.logic.DatabaseManager;
import org.example.logic.user.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FavoriteSqlRepository implements IFavoriteRepository {

    @Override
    public void markMediaAsFavorite(User user, int media_id) {
        String sql =
                "INSERT INTO favorites(user_id, media_id) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, user.getId());
            stmt.setInt(2, media_id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeMediaFromFavorites(User user, int media_id) {
        String sql =
                "DELETE FROM favorites WHERE user_id = ? AND media_id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, user.getId());
            stmt.setInt(2, media_id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

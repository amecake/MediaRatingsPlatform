package org.example.logic.favorite.repository;

import org.example.logic.DatabaseManager;
import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.mediaentry.model.MediaType;
import org.example.logic.user.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<MediaEntry> viewFavoritesList(User user) {
        String sql =
                "SELECT m.media_id, m.title, m.description, m.type, m.release_year, m.genre, m.age_restriction " +
                "FROM favorites f " +
                "JOIN media_entries m ON f.media_id = m.media_id " +
                "WHERE f.user_id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, user.getId());

            ResultSet resultSet = stmt.executeQuery();

            List<MediaEntry> favoritesList = new ArrayList<>();

            while (resultSet.next()) {
                MediaEntry mediaEntry = new MediaEntry();
                mediaEntry.setId(resultSet.getInt("media_id"));
                mediaEntry.setTitle(resultSet.getString("title"));
                mediaEntry.setDescription(resultSet.getString("description"));
                mediaEntry.setType(MediaType.valueOf(resultSet.getString("type")));
                mediaEntry.setReleaseYear(resultSet.getInt("release_year"));
                mediaEntry.setGenre(resultSet.getString("genre"));
                mediaEntry.setAgeRestriction(resultSet.getBoolean("age_restriction"));

                favoritesList.add(mediaEntry);
            }

            return favoritesList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

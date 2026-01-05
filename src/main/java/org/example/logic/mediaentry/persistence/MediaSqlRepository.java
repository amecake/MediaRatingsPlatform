package org.example.logic.mediaentry.persistence;

import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.mediaentry.model.MediaType;
import org.example.logic.user.model.User;
import org.example.logic.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MediaSqlRepository implements IMediaRepository {

    @Override
    public void createMediaEntry(User user, MediaEntry mediaEntry) {
        String sql =
                "INSERT INTO media_entries(title, description, type, release_year, genre, age_restriction, creator_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, mediaEntry.getTitle());
            stmt.setString(2, mediaEntry.getDescription());
            stmt.setString(3, mediaEntry.getType().name());
            stmt.setInt(4, mediaEntry.getReleaseYear());
            stmt.setString(5, mediaEntry.getGenre());
            stmt.setBoolean(6, mediaEntry.isAgeRestriction());
            stmt.setInt(7, mediaEntry.getCreatorId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void listMediaEntries(User user) {
        user.listMediaEntries();
    }

    @Override
    public void viewMediaEntry(User user, int index) {
        user.viewMediaEntry(index);
    }

    @Override
    public MediaEntry getMediaEntryById(int id) {
        String sql =
                "SELECT media_id, creator_id, title, description, type, release_year, genre, age_restriction " +
                "FROM media_entries " +
                "WHERE media_id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                MediaEntry mediaEntry = new MediaEntry();
                mediaEntry.setId(result.getInt("media_id"));
                mediaEntry.setCreatorId(result.getInt("creator_id"));
                mediaEntry.setTitle(result.getString("title"));
                mediaEntry.setDescription(result.getString("description"));
                mediaEntry.setType(MediaType.valueOf(result.getString("type")));
                mediaEntry.setReleaseYear(result.getInt("release_year"));
                mediaEntry.setGenre(result.getString("genre"));
                mediaEntry.setAgeRestriction(result.getBoolean("age_restriction"));

                return mediaEntry;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public void updateMediaEntry(MediaEntry mediaEntry) {
        String sql =
                "UPDATE media_entries " +
                "SET title = ?, description = ?, type = ?, release_year = ?, genre = ?, age_restriction = ? " +
                "WHERE media_id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, mediaEntry.getTitle());
            stmt.setString(2, mediaEntry.getDescription());
            stmt.setString(3, mediaEntry.getType().toString());
            stmt.setInt(4, mediaEntry.getReleaseYear());
            stmt.setString(5, mediaEntry.getGenre());
            stmt.setBoolean(6, mediaEntry.isAgeRestriction());

            stmt.setInt(7, mediaEntry.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteMediaEntry(int id) {
        String sql =
                "DELETE FROM media_entries WHERE media_id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

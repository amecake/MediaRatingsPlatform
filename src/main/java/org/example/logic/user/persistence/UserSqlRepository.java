package org.example.logic.user.persistence;

import org.example.logic.user.model.User;
import org.example.logic.DatabaseManager;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class UserSqlRepository implements IUserRepository {
    @Override
    public int register(User newUser) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             // This returns the ID (serial)
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, newUser.getUsername());
            stmt.setString(2, newUser.getPassword());

            int affectedRows = stmt.executeUpdate();
            // Check if the row was added
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            // Get the generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1); // First row -> ID (= keys)
                    newUser.setId(id);                // set it into the newUser object
                    return id;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isUniqueUsername(String username) {
        String sql =
                "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
               int count = result.getInt(1);

               if (count == 0)
                   return true;
               else
                   return false;

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean usernameExists(String checkUser) {
        String sql =
                "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, checkUser);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                int count = result.getInt(1);

                // Has to be one, else the username doesn't exist
                if (count == 1)
                    return true;
                else
                    return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public boolean usernameMatchPw(String checkUsername, String checkPassword) {
        String sql =
                "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, checkUsername);
            stmt.setString(2, checkPassword);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                int count = result.getInt(1);

                // This means return the truthfulness of this condition
                return count == 1;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        String sql =
                "SELECT user_id, username, password FROM users WHERE username = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                User user = new User();
                user.setId(result.getInt("user_id"));
                user.setUsername(result.getString("username"));
                user.setPassword(result.getString("password"));
                return user;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> getProfileStats(User user) {
        String sql =
                "SELECT COUNT(*) AS total, avg(stars) AS average " +
                "FROM ratings " +
                "WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, user.getId());

            ResultSet resultSet = stmt.executeQuery();

            Map<String, Object> stats = new HashMap<>();

            if (resultSet.next()) {

                stats.put("totalRatings", resultSet.getInt("total"));

                // If there is no ratings, average would divide by 0
                double avg = resultSet.getDouble("average");
                if (resultSet.wasNull()) {
                    avg = 0;
                }
                stats.put("averageRating", avg);
            }

            return stats;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

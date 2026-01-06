package org.example.logic.user.persistence;

import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.user.model.User;
import org.example.logic.DatabaseManager;

import java.sql.*;

public class UserSqlRepository implements IUserRepository {
    @Override
    public int register(User newUser) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, newUser.getUsername());
            stmt.setString(2, newUser.getPassword());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            // Get the generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1); // DB-generated ID
                    newUser.setId(id);                // set it in the User object
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

        return false; // ?
    }

    @Override
    public void login(User user) {
        // Login user

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
                user.setId(result.getInt("id"));
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
    public void listMediaEntries(User user) {
        user.listMediaEntries();
    }

    @Override
    public void viewMediaEntry(User user, int index) {
        user.viewMediaEntry(index);
    }
}

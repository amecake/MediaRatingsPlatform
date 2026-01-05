package org.example.logic.user.model;

import org.example.logic.mediaentry.model.MediaEntry;
import org.example.logic.rating.model.Rating;

import java.util.List;
import java.util.ArrayList;

public class User {
    private int id; // Primary key
    private String username;
    private String password;
    private final List<MediaEntry> mediaEntries = new ArrayList<>();
    private final List<Rating> ratings = new ArrayList<>();
    private final List<MediaEntry> favoriteMedia = new ArrayList<>();

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Username: " + username + "\nPassword: " + password;
    }

    public static void viewProfile() {
        // See rating history
        // See list of favorites
    }

    public static void editProfile() {
        // Change password (and username? would require an ID i think)
    }

    public void createMediaEntry(MediaEntry mediaEntry) {
        mediaEntries.add(mediaEntry);
    }

    public void listMediaEntries() {
        System.out.println(mediaEntries);
    }

    public void viewMediaEntry(int index) {
        System.out.println(mediaEntries.get(index));
    }

    public MediaEntry getMediaEntryByIndex(int index) {
        return mediaEntries.get(index);
    }

    public static void rateMediaEntry(Rating rating, String comment) {
        // Rate media entry (and optionally comment)
    }

    public static void editRating() {
        // Edit their rating
    }

    public static void deleteRating() {
        // Delete their rating
    }

    public static void likeRating() {
        // Like other users' rating
    }

    public static void markFavorite() {
        // Mark a media entry
    }

    public static void unmarkFavorite() {
        // Unmark a media entry
    }

    public static void showRecommendations() {
        // Based on rating behavior (highly rated media),
        // content similarity (matching genres, media type, age restriction)
    }

    public static void searchMediaEntryByTitle(String title) {
        // Partial matching
    }

    public static void filterMediaEntryBy(String type) {
        // Which property to filter with?
        // Afterwards sort
    }

    public static void sortMediaEntriesBy(String type) {
        // Which property to sort with?
    }

    public static void viewLeaderboard() {
        // Show most active users
    }

    public static void confirmRating() {
        // Confirm others' ratings
    }

    public int getId() {
        return id;
    }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public List<MediaEntry> getMediaEntries() { return mediaEntries; }
    public List<Rating> getRatings() { return ratings; }
    public List<MediaEntry> getFavoriteMedia() { return favoriteMedia; }

    public void setId(int id) {
        this.id = id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

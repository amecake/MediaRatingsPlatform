package org.example;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;
    private String password;
    private List<MediaEntry> mediaEntries = new ArrayList<>();
    private List<Rating> ratings = new ArrayList<>();

    public void register(String username, String password) {
        // Make new account
        this.username = username;
        this.password = password;
        // Add to a collection (needs to check if username doesn't exist yet)
    }

    public static void login(String username, String password) {
        // Checks if username exists, and then checks if password is correct
    }

    public static void viewProfile() {
        // See media entries, total ratings (history apparently),
        // average score, favorite genre
    }

    public static void searchMediaEntryByTitle(String title) {

    }

    public static void filterMediaEntryBy(String type) {

    }

    public static void sortMediaEntriesBy(String type) {

    }

    public static void editProfile() {
        // Change password (and username? would require an ID i think)
    }

    public static void createMediaEntry() {
        // Add a member to list
    }

    public static void updateMediaEntry() {
        // Edit a member of list
    }

    public static void deleteMediaEntry() {
        // Remove a member from list
    }

    public static void rateMediaEntry(String comment) {
        // Rate media entry (and optionally comment)
    }

    public static void editRating() {

    }

    public static void deleteRating() {

    }

    public static void likeRating() {

    }

    public static void markFavorite() {
        // Mark a media entry
    }

    public static void unmarkFavorite() {
        // Unmark a media entry
    }

    public static void viewLeaderboard() {
        // Show most active users
    }

    public static void showRecommendations() {
        // Based on rating behavior (highly rated media),
        // content similarity (matching genres, media type, age restriction)
    }

    public static void confirmRating() {
        // Confirm others' ratings
    }
}

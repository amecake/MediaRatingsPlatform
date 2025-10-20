package org.example.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import org.example.persistence.UserSqlRepository;

import javax.print.attribute.standard.Media;

public class User {
    private int userID;
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

    public void register(String username, String password) {
        // Make new account
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Username: " + username + "\nPassword: " + password;
    }

    public static void login(String username, String password) {
        // Checks if username exists, and then checks if password is correct
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

    public void updateMediaEntry(MediaEntry updatedMediaEntry, int index) {
        MediaEntry existingEntry = mediaEntries.get(index);

        existingEntry.setTitle(updatedMediaEntry.getTitle());
        existingEntry.setDescription(updatedMediaEntry.getDescription());
        existingEntry.setType(updatedMediaEntry.getType());
        existingEntry.setReleaseYear(updatedMediaEntry.getReleaseYear());
        existingEntry.setGenres(updatedMediaEntry.getGenres());
        existingEntry.setAgeRestriction(updatedMediaEntry.isAgeRestriction());

        System.out.println("The existing media entry is now: " + existingEntry);
    }

    public void deleteMediaEntry(int index) {
        // Remove a media entry from list
        mediaEntries.remove(index);
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

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public List<MediaEntry> getMediaEntries() { return mediaEntries; }
    public List<Rating> getRatings() { return ratings; }
    public List<MediaEntry> getFavoriteMedia() { return favoriteMedia; }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

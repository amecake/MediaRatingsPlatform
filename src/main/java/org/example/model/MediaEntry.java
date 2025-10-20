package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class MediaEntry {
    private String title;
    private String description;
    private MediaType type;
    private int releaseYear;
    // Enum, also array?
    private List<String> genres = new ArrayList<>();
    private boolean ageRestriction;
    private List<Integer> ratingList = new ArrayList<>();
    private int averageScore;

    // For Jackson serialization
    public MediaEntry() {}

    public MediaEntry(String title, String description,
                      MediaType type, int releaseYear,
                      List<String> genres, boolean ageRestriction) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.releaseYear = releaseYear;
        this.genres = genres;
        this.ageRestriction = ageRestriction;
    }

    // For better overview what's stored in an object
    @Override
    public String toString() {
        return "\nTitle: " + title + "\nDescription: " + description + "\nType: " + type + "\nRelease year: " + releaseYear + "\nGenres: " + genres + "\nAge restriction: " + ageRestriction + "\n";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public boolean isAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(boolean ageRestriction) {
        this.ageRestriction = ageRestriction;
    }
}

package org.example.logic.mediaentry.model;

import java.util.ArrayList;
import java.util.List;

public class MediaEntry {
    private int id; // Primary key
    private String title;
    private String description;
    private MediaType type; // enum read as string in sql
    private int releaseYear;
    private String genre; // Single genre only
    private boolean ageRestriction;

    private int creatorId; // Foreign key

    private int averageScore;

    // For Jackson serialization
    public MediaEntry() {}

    public MediaEntry(String title, String description,
                      MediaType type, int releaseYear,
                      String genre, boolean ageRestriction) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.ageRestriction = ageRestriction;
    }

    // For better overview what's stored in an object
    @Override
    public String toString() {
        return "\nTitle: " + title + "\nDescription: " + description + "\nType: " + type + "\nRelease year: " + releaseYear + "\nGenres: " + genre + "\nAge restriction: " + ageRestriction + "\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(boolean ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }
}

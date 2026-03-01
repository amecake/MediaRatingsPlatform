package org.example.logic.rating.model;

import java.time.LocalDateTime;

public class Rating {
    private int id; // Primary key
    private int userId; // Foreign key
    private int mediaId; // Foreign key
    private int stars;
    private String comment;
    // From library
    private LocalDateTime timestamp;

    private boolean confirmed;
    private int likes;

    // For Jackson ObjectMapper
    public Rating() {}

    public Rating(int stars, String comment) {
        this.stars = stars;
        this.comment = comment;
    }

    // For better overview what's stored in an object
    @Override
    public String toString() {
        return "\nMedia_ID: " + mediaId + "\nStars: " + stars + "\nComment: " + comment + "\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}

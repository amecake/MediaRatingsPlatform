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
}

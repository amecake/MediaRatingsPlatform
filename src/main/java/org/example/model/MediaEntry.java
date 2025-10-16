package org.example.model;

import java.util.ArrayList;

public class MediaEntry {
    private String title;
    private String description;
    private MediaType type;
    private int releaseYear;
    // Enum, also array?
    private String genre;
    private boolean ageRestriction;
    private ArrayList<Integer> ratingList;
    private int averageScore;
}

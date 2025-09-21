package org.example;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

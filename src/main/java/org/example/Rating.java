package org.example;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    private int starValue;
    private String comment;
    // From library
    private LocalDateTime timestamp;


}

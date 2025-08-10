package edu.neu.csye7374.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private String instructorName;
}

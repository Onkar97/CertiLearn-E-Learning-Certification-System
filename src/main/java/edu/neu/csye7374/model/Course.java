package edu.neu.csye7374.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
}

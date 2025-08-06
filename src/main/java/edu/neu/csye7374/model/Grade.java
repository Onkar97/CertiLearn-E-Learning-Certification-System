package edu.neu.csye7374.model;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    private double score;
}
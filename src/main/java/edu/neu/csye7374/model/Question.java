package edu.neu.csye7374.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    private String text;
    private String correctAnswer;
    private String type;
    private String options;

    private Long quizId;
}

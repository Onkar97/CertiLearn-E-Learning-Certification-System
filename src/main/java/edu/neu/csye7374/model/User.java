package edu.neu.csye7374.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_users")
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;
    private String role; // STUDENT, INSTRUCTOR
    private String password;

}

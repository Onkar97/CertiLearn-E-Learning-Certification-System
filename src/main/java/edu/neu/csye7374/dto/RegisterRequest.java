package edu.neu.csye7374.dto;

import lombok.Data;

@Data

public class RegisterRequest {
    private String email;
    private String name;
    private String role;
    private String password;


}

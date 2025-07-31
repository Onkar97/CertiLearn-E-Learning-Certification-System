package edu.neu.csye7374.controller;

import edu.neu.csye7374.dto.LoginRequest;
import edu.neu.csye7374.dto.RegisterRequest;
import edu.neu.csye7374.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        boolean authenticated = authService.authenticate(request);
        if (authenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}

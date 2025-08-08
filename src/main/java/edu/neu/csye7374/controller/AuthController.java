package edu.neu.csye7374.controller;

import edu.neu.csye7374.dto.LoginRequest;
import edu.neu.csye7374.dto.LoginResponse;
import edu.neu.csye7374.dto.RegisterRequest;
import edu.neu.csye7374.model.User;
import edu.neu.csye7374.repository.UserRepository;
import edu.neu.csye7374.util.JwtUtil;
import edu.neu.csye7374.session.ActiveSessionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final ActiveSessionStore activeSessionStore;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        // optional: basic checks
        if (req.getEmail() == null || req.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and password are required");
        }
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already registered");
        }

        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setRole(req.getRole() == null ? "STUDENT" : req.getRole());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        User saved = userRepository.save(u);

        return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(request.getPassword())) {
                String token = jwtUtil.generateToken(user);
                activeSessionStore.addSession(user.getEmail(), token);
                return ResponseEntity.ok(new LoginResponse(token));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);
            activeSessionStore.removeSession(username); // invalidate
            return ResponseEntity.ok("Logged out successfully");
        }
        return ResponseEntity.badRequest().body("No token provided");
    }

}

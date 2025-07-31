package edu.neu.csye7374.service;

import edu.neu.csye7374.dto.LoginRequest;
import edu.neu.csye7374.dto.RegisterRequest;
import edu.neu.csye7374.model.User;
import edu.neu.csye7374.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already registered.";
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setRole(request.getRole());
        user.setPassword(request.getPassword());

        userRepository.save(user);
        return "User registered successfully.";
    }

    public boolean authenticate(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        return userOptional
                .map(user -> user.getPassword().equals(loginRequest.getPassword()))
                .orElse(false);
    }

}

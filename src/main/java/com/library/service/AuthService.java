package com.library.service;

import com.library.dto.LoginRequest;
import com.library.dto.RegisterRequest;
import com.library.exception.DuplicateResourceException;
import com.library.exception.InvalidCredentialsException;
import com.library.model.User;
import com.library.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for authentication operations
 */
@Service
public class AuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Register a new user
     */
    @Transactional
    public User register(RegisterRequest request) {
        logger.info("Attempting to register user with email: {}", request.getEmail());
        
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            logger.error("Registration failed: Email already exists - {}", request.getEmail());
            throw new DuplicateResourceException("Email already registered: " + request.getEmail());
        }
        
        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(hashPassword(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : "USER");
        
        User savedUser = userRepository.save(user);
        logger.info("User registered successfully with ID: {}", savedUser.getId());
        
        return savedUser;
    }
    
    /**
     * Login user
     */
    public User login(LoginRequest request) {
        logger.info("Login attempt for email: {}", request.getEmail());
        
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.error("Login failed: User not found - {}", request.getEmail());
                    return new InvalidCredentialsException("Invalid email or password");
                });
        
        // Verify password
        if (!verifyPassword(request.getPassword(), user.getPassword())) {
            logger.error("Login failed: Invalid password for user - {}", request.getEmail());
            throw new InvalidCredentialsException("Invalid email or password");
        }
        
        logger.info("User logged in successfully: {}", user.getEmail());
        return user;
    }
    
    /**
     * Hash password using BCrypt
     */
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));
    }
    
    /**
     * Verify password against hash
     */
    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            logger.error("Error verifying password", e);
            return false;
        }
    }
}

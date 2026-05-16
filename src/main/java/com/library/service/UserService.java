package com.library.service;

import com.library.exception.ResourceNotFoundException;
import com.library.model.User;
import com.library.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserRepository userRepository;
    
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }
    
    public User getUserById(Long id) {
        logger.info("Fetching user with ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }
    
    public User getUserByEmail(String email) {
        logger.info("Fetching user with email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }
    
    @Transactional
    public User updateUser(Long id, User userDetails) {
        logger.info("Updating user with ID: {}", id);
        
        User user = getUserById(id);
        user.setName(userDetails.getName());
        
        if (!user.getEmail().equals(userDetails.getEmail())) {
            if (userRepository.existsByEmail(userDetails.getEmail())) {
                throw new RuntimeException("Email already exists: " + userDetails.getEmail());
            }
            user.setEmail(userDetails.getEmail());
        }
        
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(BCrypt.hashpw(userDetails.getPassword(), BCrypt.gensalt(10)));
        }
        
        if (userDetails.getRole() != null) {
            user.setRole(userDetails.getRole());
        }
        
        User updatedUser = userRepository.save(user);
        logger.info("User updated successfully: {}", updatedUser.getId());
        
        return updatedUser;
    }
    
    @Transactional
    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        
        User user = getUserById(id);
        userRepository.delete(user);
        
        logger.info("User deleted successfully: {}", id);
    }
    
    public List<User> getUsersByRole(String role) {
        logger.info("Fetching users with role: {}", role);
        return userRepository.findByRole(role);
    }
    
    public long countUsers() {
        return userRepository.countByRole("USER");
    }
}

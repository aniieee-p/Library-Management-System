package com.library.repository;

import com.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if email already exists
     */
    boolean existsByEmail(String email);
    
    /**
     * Find users by role
     */
    List<User> findByRole(String role);
    
    /**
     * Count users by role
     */
    long countByRole(String role);
}

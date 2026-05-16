package com.library.repository;

import com.library.model.IssuedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for IssuedBook entity
 */
@Repository
public interface IssuedBookRepository extends JpaRepository<IssuedBook, Long> {
    
    /**
     * Find all issued books by user ID
     */
    List<IssuedBook> findByUserId(Long userId);
    
    /**
     * Find all issued books by status
     */
    List<IssuedBook> findByStatus(String status);
    
    /**
     * Find all issued books by user ID and status
     */
    List<IssuedBook> findByUserIdAndStatus(Long userId, String status);
    
    /**
     * Count issued books by status
     */
    long countByStatus(String status);
    
    /**
     * Find overdue books
     */
    @Query("SELECT ib FROM IssuedBook ib WHERE ib.status = 'ISSUED' AND ib.dueDate < :currentDate")
    List<IssuedBook> findOverdueBooks(LocalDate currentDate);
    
    /**
     * Count overdue books
     */
    @Query("SELECT COUNT(ib) FROM IssuedBook ib WHERE ib.status = 'ISSUED' AND ib.dueDate < :currentDate")
    long countOverdueBooks(LocalDate currentDate);
}

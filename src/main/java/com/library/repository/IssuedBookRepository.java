package com.library.repository;

import com.library.model.IssuedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IssuedBookRepository extends JpaRepository<IssuedBook, Long> {
    
    List<IssuedBook> findByUserId(Long userId);
    
    List<IssuedBook> findByStatus(String status);
    
    List<IssuedBook> findByUserIdAndStatus(Long userId, String status);
    
    long countByStatus(String status);
    
    @Query("SELECT ib FROM IssuedBook ib WHERE ib.status = 'ISSUED' AND ib.dueDate < :currentDate")
    List<IssuedBook> findOverdueBooks(LocalDate currentDate);
    
    @Query("SELECT COUNT(ib) FROM IssuedBook ib WHERE ib.status = 'ISSUED' AND ib.dueDate < :currentDate")
    long countOverdueBooks(LocalDate currentDate);
}

package com.library.repository;

import com.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Book entity
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    /**
     * Search books by title, author, or category
     */
    @Query("SELECT b FROM Book b WHERE " +
           "LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(b.category) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Book> searchBooks(@Param("query") String query);
    
    /**
     * Find books by category
     */
    List<Book> findByCategory(String category);
    
    /**
     * Find books by author
     */
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    /**
     * Find books by title
     */
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    /**
     * Count total available books
     */
    @Query("SELECT SUM(b.availableQuantity) FROM Book b")
    Long countTotalAvailableBooks();
}

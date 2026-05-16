package com.library.service;

import com.library.exception.ResourceNotFoundException;
import com.library.model.Book;
import com.library.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for book management operations
 */
@Service
public class BookService {
    
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    
    @Autowired
    private BookRepository bookRepository;
    
    /**
     * Get all books
     */
    public List<Book> getAllBooks() {
        logger.info("Fetching all books");
        return bookRepository.findAll();
    }
    
    /**
     * Get book by ID
     */
    public Book getBookById(Long id) {
        logger.info("Fetching book with ID: {}", id);
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }
    
    /**
     * Add new book
     */
    @Transactional
    public Book addBook(Book book) {
        logger.info("Adding new book: {}", book.getTitle());
        
        // Set available quantity equal to total quantity
        if (book.getAvailableQuantity() == null) {
            book.setAvailableQuantity(book.getQuantity());
        }
        
        Book savedBook = bookRepository.save(book);
        logger.info("Book added successfully with ID: {}", savedBook.getId());
        
        return savedBook;
    }
    
    /**
     * Update book
     */
    @Transactional
    public Book updateBook(Long id, Book bookDetails) {
        logger.info("Updating book with ID: {}", id);
        
        Book book = getBookById(id);
        
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setCategory(bookDetails.getCategory());
        book.setIsbn(bookDetails.getIsbn());
        book.setPublisher(bookDetails.getPublisher());
        book.setPublishedYear(bookDetails.getPublishedYear());
        
        // Update quantities
        int difference = bookDetails.getQuantity() - book.getQuantity();
        book.setQuantity(bookDetails.getQuantity());
        book.setAvailableQuantity(book.getAvailableQuantity() + difference);
        
        Book updatedBook = bookRepository.save(book);
        logger.info("Book updated successfully: {}", updatedBook.getId());
        
        return updatedBook;
    }
    
    /**
     * Delete book
     */
    @Transactional
    public void deleteBook(Long id) {
        logger.info("Deleting book with ID: {}", id);
        
        Book book = getBookById(id);
        bookRepository.delete(book);
        
        logger.info("Book deleted successfully: {}", id);
    }
    
    /**
     * Search books by query
     */
    public List<Book> searchBooks(String query) {
        logger.info("Searching books with query: {}", query);
        return bookRepository.searchBooks(query);
    }
    
    /**
     * Get books by category
     */
    public List<Book> getBooksByCategory(String category) {
        logger.info("Fetching books by category: {}", category);
        return bookRepository.findByCategory(category);
    }
    
    /**
     * Get total available books count
     */
    public Long getTotalAvailableBooks() {
        Long count = bookRepository.countTotalAvailableBooks();
        return count != null ? count : 0L;
    }
}

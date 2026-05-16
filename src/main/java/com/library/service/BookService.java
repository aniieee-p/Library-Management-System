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

@Service
public class BookService {
    
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    
    @Autowired
    private BookRepository bookRepository;
    
    public List<Book> getAllBooks() {
        logger.info("Fetching all books");
        return bookRepository.findAll();
    }
    
    public Book getBookById(Long id) {
        logger.info("Fetching book with ID: {}", id);
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }
    
    @Transactional
    public Book addBook(Book book) {
        logger.info("Adding new book: {}", book.getTitle());
        
        if (book.getAvailableQuantity() == null) {
            book.setAvailableQuantity(book.getQuantity());
        }
        
        Book savedBook = bookRepository.save(book);
        logger.info("Book added successfully with ID: {}", savedBook.getId());
        
        return savedBook;
    }
    
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
        
        int difference = bookDetails.getQuantity() - book.getQuantity();
        book.setQuantity(bookDetails.getQuantity());
        book.setAvailableQuantity(book.getAvailableQuantity() + difference);
        
        Book updatedBook = bookRepository.save(book);
        logger.info("Book updated successfully: {}", updatedBook.getId());
        
        return updatedBook;
    }
    
    @Transactional
    public void deleteBook(Long id) {
        logger.info("Deleting book with ID: {}", id);
        
        Book book = getBookById(id);
        bookRepository.delete(book);
        
        logger.info("Book deleted successfully: {}", id);
    }
    
    public List<Book> searchBooks(String query) {
        logger.info("Searching books with query: {}", query);
        return bookRepository.searchBooks(query);
    }
    
    public List<Book> getBooksByCategory(String category) {
        logger.info("Fetching books by category: {}", category);
        return bookRepository.findByCategory(category);
    }
    
    public Long getTotalAvailableBooks() {
        Long count = bookRepository.countTotalAvailableBooks();
        return count != null ? count : 0L;
    }
}

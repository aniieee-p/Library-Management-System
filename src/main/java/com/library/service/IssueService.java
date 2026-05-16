package com.library.service;

import com.library.dto.DashboardStats;
import com.library.exception.BookNotAvailableException;
import com.library.exception.ResourceNotFoundException;
import com.library.model.Book;
import com.library.model.IssuedBook;
import com.library.model.User;
import com.library.repository.BookRepository;
import com.library.repository.IssuedBookRepository;
import com.library.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class IssueService {
    
    private static final Logger logger = LoggerFactory.getLogger(IssueService.class);
    private static final int DUE_DAYS = 14; // Books due in 14 days
    private static final BigDecimal FINE_PER_DAY = new BigDecimal("10.00"); // ₹10 per day
    
    @Autowired
    private IssuedBookRepository issuedBookRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public IssuedBook issueBook(Long userId, Long bookId) {
        logger.info("Issuing book {} to user {}", bookId, userId);
        
        // Fetch user and book
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", bookId));
        
        // Check if book is available
        if (book.getAvailableQuantity() <= 0) {
            logger.error("Book not available: {}", book.getTitle());
            throw new BookNotAvailableException("Book is not available: " + book.getTitle());
        }
        
        // Create issued book record
        IssuedBook issuedBook = new IssuedBook();
        issuedBook.setUser(user);
        issuedBook.setBook(book);
        issuedBook.setIssueDate(LocalDate.now());
        issuedBook.setDueDate(LocalDate.now().plusDays(DUE_DAYS));
        issuedBook.setStatus("ISSUED");
        issuedBook.setFine(BigDecimal.ZERO);
        
        // Update book availability
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);
        
        IssuedBook savedIssuedBook = issuedBookRepository.save(issuedBook);
        logger.info("Book issued successfully with ID: {}", savedIssuedBook.getId());
        
        return savedIssuedBook;
    }
    
    @Transactional
    public IssuedBook returnBook(Long issueId) {
        logger.info("Returning book with issue ID: {}", issueId);
        
        IssuedBook issuedBook = issuedBookRepository.findById(issueId)
                .orElseThrow(() -> new ResourceNotFoundException("Issued Book", "id", issueId));
        
        if ("RETURNED".equals(issuedBook.getStatus())) {
            throw new RuntimeException("Book already returned");
        }
        
        // Set return date
        LocalDate returnDate = LocalDate.now();
        issuedBook.setReturnDate(returnDate);
        issuedBook.setStatus("RETURNED");
        
        // Calculate fine if overdue
        if (returnDate.isAfter(issuedBook.getDueDate())) {
            long daysOverdue = ChronoUnit.DAYS.between(issuedBook.getDueDate(), returnDate);
            BigDecimal fine = FINE_PER_DAY.multiply(new BigDecimal(daysOverdue));
            issuedBook.setFine(fine);
            logger.info("Book returned late. Fine calculated: ₹{}", fine);
        }
        
        // Update book availability
        Book book = issuedBook.getBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        bookRepository.save(book);
        
        IssuedBook returnedBook = issuedBookRepository.save(issuedBook);
        logger.info("Book returned successfully: {}", returnedBook.getId());
        
        return returnedBook;
    }
    
    public List<IssuedBook> getAllIssuedBooks() {
        logger.info("Fetching all issued books");
        return issuedBookRepository.findAll();
    }
    
    public List<IssuedBook> getIssuedBooksByUserId(Long userId) {
        logger.info("Fetching issued books for user: {}", userId);
        return issuedBookRepository.findByUserId(userId);
    }
    
    public List<IssuedBook> getCurrentlyIssuedBooks() {
        logger.info("Fetching currently issued books");
        return issuedBookRepository.findByStatus("ISSUED");
    }
    
    public List<IssuedBook> getOverdueBooks() {
        logger.info("Fetching overdue books");
        return issuedBookRepository.findOverdueBooks(LocalDate.now());
    }
    
    public DashboardStats getDashboardStats() {
        logger.info("Fetching dashboard statistics");
        
        long totalBooks = bookRepository.count();
        Long availableBooks = bookRepository.countTotalAvailableBooks();
        long issuedBooks = issuedBookRepository.countByStatus("ISSUED");
        long totalUsers = userRepository.countByRole("USER");
        long overdueBooks = issuedBookRepository.countOverdueBooks(LocalDate.now());
        
        return new DashboardStats(
                totalBooks,
                availableBooks != null ? availableBooks : 0L,
                issuedBooks,
                totalUsers,
                overdueBooks
        );
    }
}

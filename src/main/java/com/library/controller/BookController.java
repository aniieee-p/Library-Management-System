package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.model.Book;
import com.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for book management operations
 */
@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    /**
     * Get all books
     * GET /api/books
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(ApiResponse.success("Books fetched successfully", books));
    }
    
    /**
     * Get book by ID
     * GET /api/books/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(ApiResponse.success("Book fetched successfully", book));
    }
    
    /**
     * Add new book (Admin only)
     * POST /api/books
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Book>> addBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Book added successfully", savedBook));
    }
    
    /**
     * Update book (Admin only)
     * PUT /api/books/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(ApiResponse.success("Book updated successfully", updatedBook));
    }
    
    /**
     * Delete book (Admin only)
     * DELETE /api/books/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(ApiResponse.success("Book deleted successfully", null));
    }
    
    /**
     * Search books
     * GET /api/books/search?query={query}
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Book>>> searchBooks(@RequestParam String query) {
        List<Book> books = bookService.searchBooks(query);
        return ResponseEntity.ok(ApiResponse.success("Search completed", books));
    }
    
    /**
     * Get books by category
     * GET /api/books/category/{category}
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<Book>>> getBooksByCategory(@PathVariable String category) {
        List<Book> books = bookService.getBooksByCategory(category);
        return ResponseEntity.ok(ApiResponse.success("Books fetched successfully", books));
    }
}

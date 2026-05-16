package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.dto.DashboardStats;
import com.library.model.IssuedBook;
import com.library.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin(origins = "*")
public class IssueController {
    
    @Autowired
    private IssueService issueService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<IssuedBook>> issueBook(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        Long bookId = request.get("bookId");
        
        IssuedBook issuedBook = issueService.issueBook(userId, bookId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Book issued successfully", issuedBook));
    }
    
    @PutMapping("/{id}/return")
    public ResponseEntity<ApiResponse<IssuedBook>> returnBook(@PathVariable Long id) {
        IssuedBook returnedBook = issueService.returnBook(id);
        return ResponseEntity.ok(ApiResponse.success("Book returned successfully", returnedBook));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<IssuedBook>>> getAllIssuedBooks() {
        List<IssuedBook> issuedBooks = issueService.getAllIssuedBooks();
        return ResponseEntity.ok(ApiResponse.success("Issued books fetched successfully", issuedBooks));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<IssuedBook>>> getIssuedBooksByUserId(@PathVariable Long userId) {
        List<IssuedBook> issuedBooks = issueService.getIssuedBooksByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success("User's issued books fetched successfully", issuedBooks));
    }
    
    @GetMapping("/current")
    public ResponseEntity<ApiResponse<List<IssuedBook>>> getCurrentlyIssuedBooks() {
        List<IssuedBook> issuedBooks = issueService.getCurrentlyIssuedBooks();
        return ResponseEntity.ok(ApiResponse.success("Currently issued books fetched successfully", issuedBooks));
    }
    
    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse<List<IssuedBook>>> getOverdueBooks() {
        List<IssuedBook> overdueBooks = issueService.getOverdueBooks();
        return ResponseEntity.ok(ApiResponse.success("Overdue books fetched successfully", overdueBooks));
    }
    
    @GetMapping("/dashboard/stats")
    public ResponseEntity<ApiResponse<DashboardStats>> getDashboardStats() {
        DashboardStats stats = issueService.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success("Dashboard stats fetched successfully", stats));
    }
}

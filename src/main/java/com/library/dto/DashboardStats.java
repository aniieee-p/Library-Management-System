package com.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for dashboard statistics
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStats {
    
    private Long totalBooks;
    private Long availableBooks;
    private Long issuedBooks;
    private Long totalUsers;
    private Long overdueBooks;
}

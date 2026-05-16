package com.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Book entity representing library books
 */
@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Column(nullable = false, length = 200)
    private String title;
    
    @NotBlank(message = "Author is required")
    @Column(nullable = false, length = 100)
    private String author;
    
    @NotBlank(message = "Category is required")
    @Column(nullable = false, length = 50)
    private String category;
    
    @Min(value = 0, message = "Quantity cannot be negative")
    @Column(nullable = false)
    private Integer quantity;
    
    @Min(value = 0, message = "Available quantity cannot be negative")
    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;
    
    @Column(length = 20)
    private String isbn;
    
    @Column(length = 100)
    private String publisher;
    
    @Column(name = "published_year")
    private Integer publishedYear;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (availableQuantity == null) {
            availableQuantity = quantity;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

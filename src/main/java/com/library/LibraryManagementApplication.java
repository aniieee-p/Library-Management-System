package com.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class for Library Management System
 * 
 * @author Library Management Team
 * @version 1.0.0
 */
@SpringBootApplication
public class LibraryManagementApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("Library Management System Started!");
        System.out.println("Access the application at: http://localhost:8080");
        System.out.println("========================================\n");
    }
}

package com.library.exception;

/**
 * Exception thrown when a book is not available for issue
 */
public class BookNotAvailableException extends RuntimeException {
    
    public BookNotAvailableException(String message) {
        super(message);
    }
}

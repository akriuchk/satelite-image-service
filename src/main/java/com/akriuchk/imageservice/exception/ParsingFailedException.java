package com.akriuchk.imageservice.exception;

/**
 * Specific exception to designate file parsing error
 */
public class ParsingFailedException extends RuntimeException{

    public ParsingFailedException(String message) {
        super(message);
    }

    public ParsingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

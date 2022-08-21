package ru.otus.exception;

public class CustomJdbcException extends RuntimeException {
    public CustomJdbcException(String message) {
        super(message);
    }
}

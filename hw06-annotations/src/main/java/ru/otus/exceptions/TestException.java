package ru.otus.exceptions;

public class TestException extends RuntimeException {
    public TestException(String message, Exception e) {
        super(message, e);
    }
}

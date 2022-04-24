package ru.otus.exceptions;

public class TestExecutionException extends RuntimeException{
    public TestExecutionException(String message, Throwable e) {
        super(message, e);
    }
}

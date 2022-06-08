package ru.otus.proxy;

public class AppStart {

    public static void main(String[] args) {
        TestLogging testLogging = IoC.getTestClass();
        testLogging.calculation(6);
        testLogging.calculation(6, 300);
        testLogging.calculation(6, "Hello World");
    }
}



package ru.otus.proxy;

public class Proxy {

    public static void main(String[] args) {
        TestLogging testLogging = new TestLogging();
        testLogging.calculation(35, 19, "start");
    }
}

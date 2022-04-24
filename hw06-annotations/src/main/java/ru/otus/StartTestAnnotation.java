package ru.otus;


import ru.otus.processor.TestClassParserProcess;

public class StartTestAnnotation {
    public static void main(String[] args) {
        TestClassParserProcess.doTests(UnitTest.class);
    }
}

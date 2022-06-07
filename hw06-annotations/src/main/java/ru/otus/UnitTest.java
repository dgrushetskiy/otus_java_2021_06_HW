package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class UnitTest {
    private static final Logger log = LoggerFactory.getLogger(UnitTest.class);
    private final int i = 100;

    @Before
    public void beforeTest() {
//        Integer.parseInt("100");
        log.info("Inside before");
    }

    @Test
    public void shouldPassTest1() {
        log.info("inside test 1");
    }

    @Test
    public void shouldPassTest2() {
        log.info("inside test 2");
    }

//    @Test
//    public void shouldThrowException() {
//        throw new NullPointerException();
//    }
//
//    @Test
//    public void shouldThrowExceptionAgain() {
//        throw new IndexOutOfBoundsException();
//    }

    @After
    public void afterTest() {
//        Integer.parseInt("o");
        log.info("inside after");
    }
}

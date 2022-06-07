package ru.otus.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.Method;

public class TestClassParserProcess {
    private static final Logger log = LoggerFactory.getLogger(TestClassParserProcess.class);
    public static void doTests(Class<?> clazz) {

        TestContext testContext = parseClass(clazz);
        TestResult testResult = new TestResult();

        //Итерируемся по тестовым методам
        for (Method testMethod : testContext.getTestMethodsList()) {

            //Для каждого тестового метода создаем новый объект тестового класса
            Object testClassObject = ReflectionHelper.instantiate(testContext.getTestClass());
            boolean isBeforeComplete = true;

            //Вызываем before метод
            if (!testContext.isBeforeMethodNull()) {
                try {
                    testContext.getBeforeMethod().invoke(testClassObject);
                } catch (Exception e) {
                    //если не смогли подготовить окружение - выполнение тестового метода бессмысленно
                    log.error("Exception in before method");
                    isBeforeComplete = false;
                }
            }

            if (isBeforeComplete) {
                try {
                    testMethod.invoke(testClassObject);
                } catch (Exception e) {
                    //Если ловим исключение в тестовом методе - тестирование не прерываем
                    testResult.incrementFailTest();
                    //исключение сохраняем в результатах тестирования
                    testResult.saveTestException(testMethod, e);
                } finally {
                    testResult.incrementCompleteTest();
                }
            }

            //В любом случае, не зависимо от исключений в тестовых методах, вызываем метод after
            if (!testContext.isAfterMethodNull()) {
                try {
                    testContext.getAfterMethod().invoke(testClassObject);
                } catch (Exception e) {
                    //Если ловим исключение в методе after - прерываем тестирование
                    log.error("Exception in after method");
                    throw new RuntimeException(e);
                }
            }
        }
        printResults(testResult);
    }

    private static void printResults(TestResult testResult) {
        log.info("Total tests completed: " + testResult.getCompleteTestCount());
        log.info("Passed tests successfully: " + (testResult.getCompleteTestCount() - testResult.getFailTestCount()));
        log.info("Executed Tests Failed: " + testResult.getFailTestCount());
        if (testResult.hasExceptions()) {
            log.error("Exceptions ");
            testResult.getExceptionMap().forEach((s, e) -> {
                log.error("Exception in method " + s);
                e.printStackTrace();
            });
        }
    }

    private static TestContext parseClass(Class<?> clazz) {
        TestContext context = new TestContext(clazz);
        for (Method method : context.getTestClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                if (context.isBeforeMethodNull()) {
                    context.setBeforeMethod(method);
                } else {
                    throw new RuntimeException("Тестовый класс может содержать только один метод Before");
                }
            } else if (method.isAnnotationPresent(After.class)) {
                if (context.isAfterMethodNull()) {
                    context.setAfterMethod(method);
                } else {
                    throw new RuntimeException("Тестовый класс может содержать только один метод After");
                }
            } else if (method.isAnnotationPresent(Test.class)) {
                context.addTestMethod(method);
            }
        }
        return context;
    }
}

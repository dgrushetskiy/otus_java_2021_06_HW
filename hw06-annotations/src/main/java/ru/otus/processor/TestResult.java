package ru.otus.processor;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class TestResult {
    private int completeTestCount = 0;
    private int failTestCount = 0;
    private Map<String, Exception> exceptionMap = new HashMap<>();

    public void incrementCompleteTest() {
        completeTestCount++;
    }

    public void incrementFailTest() {
        failTestCount++;
    }

    public void saveTestException(Method method, Exception e) {
        exceptionMap.put(method.getName(), e);
    }

    public boolean hasExceptions() {
        return exceptionMap.size() != 0;
    }
}

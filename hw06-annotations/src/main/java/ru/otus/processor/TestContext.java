package ru.otus.processor;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class TestContext {
    private final Class<?> testClass;
    private Method beforeMethod;
    private Method afterMethod;
    private final List<Method> testMethodsList = new ArrayList<>();

    public boolean isBeforeMethodNull() {
        return beforeMethod == null;
    }

    public boolean isAfterMethodNull() {
        return afterMethod == null;
    }

    public void addTestMethod(Method method) {
        testMethodsList.add(method);
    }
}

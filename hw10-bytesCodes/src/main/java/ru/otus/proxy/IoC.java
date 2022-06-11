package ru.otus.proxy;

import ru.otus.Log;
import ru.otus.proxy.impl.TestLoggingImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;

class IoC {
    private IoC() {
    }

    static TestLogging getTestClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(IoC.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLogging myClass;
        private final ArrayList<Method> logAnnotatedMethods = new ArrayList<>();

        DemoInvocationHandler(TestLogging myClass) {
            this.myClass = myClass;
            Class<?> clazz = myClass.getClass();
            for (Method declaredMethod : clazz.getDeclaredMethods()) {
                if (declaredMethod.isAnnotationPresent(Log.class)) {
                    logAnnotatedMethods.add(declaredMethod);
                }
            }
            System.out.println("logAnnotatedMethods.size() " + logAnnotatedMethods.size());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            boolean isNeedToBeLogged = false;
            for (Method logAnnotatedMethod : logAnnotatedMethods) {
                if (logAnnotatedMethod.getName().equals(method.getName()) && Arrays.equals(logAnnotatedMethod.getParameterTypes(), method.getParameterTypes())) {
                    isNeedToBeLogged = true;
                    break;
                }
            }
            if (isNeedToBeLogged) {
                System.out.println("invoking method: " + method.getName() + "; with params: " + Arrays.toString(args));
            }
            return method.invoke(myClass, args);
        }
    }
}

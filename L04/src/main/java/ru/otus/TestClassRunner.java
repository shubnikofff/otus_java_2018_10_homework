package ru.otus;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

class TestClassRunner {
    private Class<?> clazz;
    private Method afterEach;
    private Method beforeEach;
    private Set<Method> tests;

    TestClassRunner(Class<?> clazz) {
        this.clazz = clazz;
    }

    void run() {
        init();
        if (tests.size() == 0) {
            return;
        }

        System.out.println("\nRun tests in " + clazz.getName() + "...\n");

        try {
            Object o = clazz.getDeclaredConstructor().newInstance();

            tests.forEach(method -> {
                try {
                    beforeEach.invoke(o);
                    method.invoke(o);
                    afterEach.invoke(o);
                } catch (Exception e) {
                    System.out.println(e);
                }
                System.out.println("\n");
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void init() {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        tests = new HashSet<>();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(Before.class)) {
                beforeEach = declaredMethod;
                continue;
            }
            if (declaredMethod.isAnnotationPresent(After.class)) {
                afterEach = declaredMethod;
                continue;
            }
            if (declaredMethod.isAnnotationPresent(Test.class)) {
                tests.add(declaredMethod);
            }
        }
    }
}

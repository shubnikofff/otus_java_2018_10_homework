package ru.otus;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

class TestRunner {
    static void run(String className) throws ClassNotFoundException {

        Reflections reflections = new Reflections("ru.otus");

        Class<?> clazz = Class.forName(className);

        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println(method.getName());
            for(Annotation annotation: method.getAnnotations()) {
                System.out.println(annotation);
            }
        }
    }
}

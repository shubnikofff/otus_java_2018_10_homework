package ru.otus;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class TestRunner {
    public static void main(String[] args) throws ClassNotFoundException {

        Class<TestClass> clazz = TestClass.class;

        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println(method.getName());
            for(Annotation annotation: method.getAnnotations()) {
                System.out.println(annotation);
            }
        }
    }
}

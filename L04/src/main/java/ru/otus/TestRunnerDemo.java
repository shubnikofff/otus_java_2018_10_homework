package ru.otus;

public class TestRunnerDemo {
    public static void main(String[] args) {
        try {
            TestRunner.run("ru.otus.TestClass");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}

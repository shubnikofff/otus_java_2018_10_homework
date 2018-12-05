package ru.otus;

public class TestClassB {
    @Before
    void beforeEach() {
        System.out.println("Before test");
    }

    @After
    void afterEach() {
        System.out.println("After test");
    }

    @Test
    void isEqual() {
        System.out.println("test isEqual...");
    }

    @Test
    void isGreater() {
        System.out.println("test isGreater...");
    }

    @Test
    void isLess() {
        System.out.println("test isLess...");
    }
}

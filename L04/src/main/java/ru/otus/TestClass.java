package ru.otus;

class TestClass {
    @Before
    void beforeEach() {
        System.out.println("ru.otus.Before test");
    }

    @After
    void afterEach() {
        System.out.println("ru.otus.After test\n");
    }

    @Test
    boolean isEqual(int a, int b) {
        System.out.println("ru.otus.Test is equal");
        return a == b;
    }

    @Test
    boolean isGreater(int a, int b) {
        System.out.println("ru.otus.Test is greater");
        return a > b;
    }

    @Test
    boolean isLess(int a, int b) {
        System.out.println("ru.otus.Test is less");
        return a < b;
    }
}

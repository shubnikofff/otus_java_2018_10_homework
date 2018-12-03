public class ClassTest {
    @Before
    void beforeEach() {
        System.out.println("Before test");
    }

    @After
    void afterEach() {
        System.out.println("After test\n");
    }

    @Test
    boolean isEqual(int a, int b) {
        System.out.println("Test is equal");
        return a == b;
    }

    @Test
    boolean isGreater(int a, int b) {
        System.out.println("Test is greater");
        return a > b;
    }

    @Test
    boolean isLess(int a, int b) {
        System.out.println("Test is less");
        return a < b;
    }
}

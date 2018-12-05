package ru.otus;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

class TestPackageRunner {
    static void run(String packageName) {

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(packageName))
                .setScanners(new SubTypesScanner(false)));

        Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);
        allClasses.forEach(TestPackageRunner::runForClass);
    }

    private static void runForClass(Class<?> clazz) {
        if (clazz.isAnnotation()) {
            return;
        }

        new TestClassRunner(clazz).run();
    }
}

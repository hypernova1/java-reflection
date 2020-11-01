package com.test.context;

import com.test.annotation.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
/**
* 빈을 생성할 클래스(@Component가 선언된 클래스)를 가져오는 클래스
* */
public class BeanLoader {

    private final List<Class<?>> classes = new ArrayList<>();
    private static final String rootPackage = "com.test";

    public BeanLoader() {
        this.loadClasses();
    }

    public void loadClasses() {
        String path = rootPackage.replace(".", "/");
        try {
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File directory = new File(resource.getFile());
                List<Class<?>> classList = findClasses(directory, rootPackage);
                classes.addAll(classList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<Class<?>> findClasses(File directory, String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) return classes;
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                try {
                    String className = file.getName().substring(0, file.getName().length() - 6);
                    Class<?> clazz = Class.forName(packageName + "." + className);
                    if (clazz.getAnnotation(Component.class) != null) {
                        classes.add(clazz);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }

    public List<Class<?>> getClasses() {
        return classes;
    }
}

package com.pharmc.representation.consoleV2.utils;

import com.pharmc.infrastructure.setup.Container;
import com.pharmc.representation.consoleV2.views.AbstractView;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

public class ViewsScanner {
    public static ArrayList<AbstractView> retrieveViews(Container container) {
        String packageName = ViewsScanner.class.getPackage().getName();
        packageName = packageName.replace(".utils", ".views");

        String path = packageName.replace(".", "/");

        path = ViewsScanner.class.getResource("/" + path).getPath();

        return scanViewsDirectory(packageName, path, container);
    }

    private static ArrayList<AbstractView> scanViewsDirectory(String packageName, String path, Container container) {
        ArrayList<AbstractView> views = new ArrayList<>();
        try {
            File directory = new File(path);
            if (!directory.isDirectory()) {
                return views;
            }
            for (File file : directory.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".class") && !file.getName().contains("AbstractView")) {
                    String className = packageName + "." + file.getName().replace(".class", "");
                    Class<?> clazz = Class.forName(className);
                    if (AbstractView.class.isAssignableFrom(clazz)) {
                        Constructor<?> constructor = clazz.getConstructor(Container.class);
                        Object instance = constructor.newInstance(container);
                        views.add((AbstractView) instance);
                    }
                } else if (file.isDirectory()) {
                    views.addAll(scanViewsDirectory(packageName + "." + file.getName(), file.getPath(), container));
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return views;
    }
}

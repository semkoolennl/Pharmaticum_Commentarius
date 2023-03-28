package com.pharmc.infrastructure.setup;

import java.util.HashMap;
import java.util.SortedMap;

public class Container {
    private HashMap<Class<?>, Object> services = new HashMap<>();

    public void register(Class<?> clazz, Object object) {
        services.put(clazz, object);
    }

    public <T> T resolve(Class<T> clazz) {
        return (T) services.get(clazz);
    }

    public String toString() {
        SortedMap<String, String> map = new java.util.TreeMap<>();
        for (Class<?> clazz : services.keySet()) {
            map.put(clazz.getSimpleName(), services.get(clazz).getClass().getSimpleName());
        }


        StringBuilder builder = new StringBuilder();
        builder.append("Container: {\n");
        for (String key : map.keySet()) {
            builder.append("    ");
            builder.append(key).append(": ").append(map.get(key)).append(",\n");
        }
        builder.delete(builder.length() - 2, builder.length() - 1);
        builder.append("}");

        return builder.toString();
    }
}

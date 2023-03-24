package com.pharmc.infrastructure.setup;

import java.util.HashMap;

public class Container {
    private HashMap<Class<?>, Object> map = new HashMap<>();

    public void register(Class<?> clazz, Object object) {
        map.put(clazz, object);
    }

    public <T> T resolve(Class<T> clazz) {
        return (T) map.get(clazz);
    }
}

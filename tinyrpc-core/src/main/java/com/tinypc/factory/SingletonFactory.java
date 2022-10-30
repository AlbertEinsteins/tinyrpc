package com.tinypc.factory;

import java.util.concurrent.ConcurrentHashMap;

public class SingletonFactory {
    private SingletonFactory() { }

    private static final ConcurrentHashMap<Class<?>, Object> instances = new ConcurrentHashMap<>();

    public static <T> T getInstance(Class<?> cls) {
        Object obj = instances.get(cls);
        if(null == obj) {
            synchronized (SingletonFactory.class) {
                obj = instances.get(cls);
                if (null == obj) {
                    try {
                        obj = cls.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    instances.put(cls, obj);
                }
            }
        }
        return (T) obj;
    }
}

package com.tinyrpc.core.skeleton;

import org.reflections.Reflections;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于反射的调用方法
 */
public class ReflectionSkeleton extends AbstractSkeleton {

    private static final ConcurrentHashMap<Class<?>, Object> instancesMap = new ConcurrentHashMap<>();

    /**
     * 根据反射获取接口的子类实例
     * @param clsName
     * @return
     */
    public Object getSubInstance(String clsName) {
        Object o = instancesMap.get(clsName);
        Class<?> aClass = null;
        try {
            aClass = Class.forName(clsName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(null == o) {
            synchronized (ReflectionSkeleton.class) {
                o = instancesMap.get(clsName);
                if(null == o) {
                    try {
                        Class<?> subClass = getSubClass(aClass);
                        o = subClass.newInstance() ;
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    instancesMap.put(aClass, o);
                }
            }
        }
        return o;
     }

    private Class<?> getSubClass(Class<?> interfaceName) {

        Reflections reflections = new Reflections(interfaceName.getPackage().getName());
        Set<Class<?>> subClasses = reflections.getSubTypesOf((Class<Object>) interfaceName);
        return subClasses.iterator().next();
    }

}

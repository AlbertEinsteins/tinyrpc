package com.tinypc.core.skeleton;

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
      public Object getSubInstance(Class<?> clsName) {
          Object o = instancesMap.get(clsName);
          if(null == o) {
              synchronized (ReflectionSkeleton.class) {
                  o = instancesMap.get(clsName);
                  if(null == o) {
                      try {
                          Class<?> subClass = getSubClass(clsName);
                          o = subClass.newInstance() ;
                      } catch (InstantiationException | IllegalAccessException e) {
                          throw new RuntimeException(e);
                      }
                      instancesMap.put(clsName, o);
                  }
              }
          }
        return o;
    }

    private Class<?> getSubClass(Class<?> interfaceName) {
        Reflections reflections = new Reflections("com.test.service");
        Set<Class<?>> subClasses = reflections.getSubTypesOf((Class<Object>) interfaceName);
        return subClasses.iterator().next();
    }

}

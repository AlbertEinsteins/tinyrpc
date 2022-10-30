package com.tinypc.core.skeleton;

import com.tinyrpc.entity.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbstractSkeleton implements ISkeleton {

   abstract Object getSubInstance(Class<?> clsName);

    @Override
    public Object invokeMethod(RpcRequest request) {
        try {
            Object instance = getSubInstance(request.getClassName());
            Method method = instance.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
            return method.invoke(instance, request.getParameters());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

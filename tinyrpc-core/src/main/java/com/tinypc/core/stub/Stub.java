package com.tinypc.core.stub;

public class Stub <T> implements IStub<T> {
    @Override
    public T getProxy(Class<?> cls) {
        return null;
    }
}

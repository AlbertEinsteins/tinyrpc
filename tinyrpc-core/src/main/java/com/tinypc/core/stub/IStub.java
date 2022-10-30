package com.tinypc.core.stub;

/**
 * 获取对服务接口的代理类，单例对象
 */
public interface IStub<T> {

    T getProxy(Class<?> cls);
}

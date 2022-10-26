package com.tinypc.core;

/**
 * 获取对服务接口的代理类，单例对象
 */
public interface IStub {

    Object getProxy(Class<?> cls);
}

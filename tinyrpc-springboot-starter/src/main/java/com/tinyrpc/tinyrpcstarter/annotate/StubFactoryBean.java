package com.tinyrpc.tinyrpcstarter.annotate;

import com.tinyrpc.registration.stub.Stub;
import org.springframework.beans.factory.FactoryBean;

public class StubFactoryBean implements FactoryBean {
    private Stub stub;

    // 被代理的接口
    private final Class<?> cls;

    // 接口需要用到服务名
    private final String serviceName;

    public StubFactoryBean(Class<?> cls, String serviceName) {
        this.cls = cls;
        this.serviceName = serviceName;
    }

    @Override
    public Object getObject() throws Exception {
        return stub.getProxy(cls, serviceName);
    }

    @Override
    public Class<?> getObjectType() {
        return cls;
    }

    public Stub getStub() {
        return stub;
    }

    public void setStub(Stub stub) {
        this.stub = stub;
    }
}

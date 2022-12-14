package com.tinyrpc.core.entity;

import java.io.Serializable;

public class RpcResponse implements Serializable {

    private Object result;
    private Class<?> returnType;


    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }
}

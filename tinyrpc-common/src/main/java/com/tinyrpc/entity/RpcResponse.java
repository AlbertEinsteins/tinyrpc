package com.tinyrpc.entity;

public class RpcResponse {
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

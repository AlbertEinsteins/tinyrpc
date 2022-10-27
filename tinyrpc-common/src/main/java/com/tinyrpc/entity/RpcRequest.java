package com.tinyrpc.entity;

import com.tinyrpc.entity.enumerate.SerializeType;

import java.io.Serializable;

/***
 * 服务调用时，传递的实体
 *
 */
public class RpcRequest implements Serializable {
    /**
     * 以下四个字段是远程调用的必要字段
     */
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;


    public RpcRequest(String className, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.parameters = parameters;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}

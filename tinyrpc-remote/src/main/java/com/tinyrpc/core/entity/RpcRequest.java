package com.tinyrpc.core.entity;


import java.io.Serializable;

/***
 * 服务调用时，传递的实体
 *
 */
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 以下四个字段是远程调用的必要字段
     */

    private Class<?> className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public RpcRequest() { }
    public RpcRequest(Class<?> className, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        this.className = className;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.parameters = parameters;
    }

    public Class<?> getClassName() {
        return className;
    }

    public void setClassName(Class<?> className) {
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

package com.tinyrpc.core.skeleton;

import com.tinyrpc.core.entity.RpcRequest;

/**
 * 在服务器端，真正执行的服务方法
 */
public interface ISkeleton {
    Object invokeMethod(RpcRequest request);

}

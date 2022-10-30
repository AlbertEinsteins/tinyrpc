package com.tinypc.core.skeleton;

import com.tinyrpc.entity.RpcRequest;
import com.tinyrpc.entity.RpcResponse;

/**
 * 在服务器端，真正执行的服务方法
 */
public interface ISkeleton {
    Object invokeMethod(RpcRequest request);

}

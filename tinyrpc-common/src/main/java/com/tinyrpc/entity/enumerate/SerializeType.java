package com.tinyrpc.entity.enumerate;

/**
 * 通信过程的序列化类型
 */
public enum SerializeType {
    // JSO序列化
    JSON_SERIALIZE,

    // Google Protobuf 序列化
    PROTOBUF_SERIALIZE,

    // JDK 提供的序列化
    JDK_SERIALIZE,

    // Hessian序列化
    HESSIAN_SERIALIZE
}

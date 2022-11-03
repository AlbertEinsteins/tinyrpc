package com.tinyrpc.core.entity.enumerate;

/**
 * 通信过程的序列化类型
 */
public enum SerializeType {
    // JSO序列化
    JSON_SERIALIZE(1),
    // Hessian序列化
    HESSIAN_SERIALIZE(1 << 1),
    // JDK 提供的序列化
    JDK_SERIALIZE(1 << 2),
    // Google Protobuf 序列化
    PROTOBUF_SERIALIZE(1 << 3);


    public byte code;
    SerializeType(byte code) {
        this.code = code;
    }

    SerializeType(int code) {
        this((byte)code);
    }

    public static SerializeType fromByteCode(byte code) {
        switch (code) {
            default:
            case (1 << 2): return SerializeType.JDK_SERIALIZE;
            case (1): return JSON_SERIALIZE;
            case (1 << 1): return HESSIAN_SERIALIZE;
            case (1 << 3): return PROTOBUF_SERIALIZE;
        }
    }
}

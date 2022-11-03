package com.tinyrpc.core.serializer;

public interface Serialization {
    // 将对象序列化
    byte[] serialize(Object data);
}

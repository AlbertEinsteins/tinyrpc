package com.tinypc.serializer;

public interface Serialization<T> {
    // 将对象序列化
    byte[] serialize(T data);
}

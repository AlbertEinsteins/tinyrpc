package com.tinypc.serialize;

public interface Serialization<T> {
    // 将对象序列化
    byte[] serialize(T data);
}

package com.tinypc.serializer;

public interface Deserialization<T> {
    T deserialize(byte[] data);
}

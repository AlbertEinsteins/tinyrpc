package com.tinypc.serialize;

public interface Deserialization<T> {
    T deserialize(byte[] data);
}

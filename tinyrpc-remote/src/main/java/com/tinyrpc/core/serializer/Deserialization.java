package com.tinyrpc.core.serializer;

public interface Deserialization {
    Object deserialize(byte[] data);
}

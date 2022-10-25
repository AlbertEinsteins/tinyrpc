package com.tinypc.serializer;

public class HessianSerializer<T> implements Serializer<T> {
    @Override
    public T deserialize(byte[] data) {
        return null;
    }

    @Override
    public byte[] serialize(T data) {
        return new byte[0];
    }
}

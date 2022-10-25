package com.tinypc.serializer;

import com.tinyrpc.exception.SerializeException;

import java.io.*;

public class JDKSerializer<T> implements Serializer<T> {
    @Override
    public byte[] serialize(T data) {
        if(!(data instanceof Serializable)) {
            throw new SerializeException("class must implement Serialization Interface");
        }
        byte[] bytes;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(data);
            bytes = byteArrayOutputStream.toByteArray();

            byteArrayOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    @Override
    public T deserialize(byte[] bytes) {
        T object = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);

            object = (T) ois.readObject();

            bais.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return object;
    }
}

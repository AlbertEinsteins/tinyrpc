package com.tinyrpc.core.serializer;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.tinyrpc.core.exception.SerializeException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class HessianSerializer implements Serializer {
    @Override
    public Object deserialize(byte[] data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        HessianInput input = new HessianInput(bais);
        Object o;
        try {
            o = input.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return o;
    }

    @Override
    public byte[] serialize(Object obj) {
        if(!(obj instanceof Serializable)) {
            throw new SerializeException("class must implement Serialization Interface");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HessianOutput hessianOutput = new HessianOutput(baos);
        byte[] data;
        try {
            hessianOutput.writeObject(obj);
            data = baos.toByteArray();

            hessianOutput.close();
            baos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}

package com.tinypc.context;

import com.tinypc.factory.SingletonFactory;
import com.tinypc.serializer.HessianSerializer;
import com.tinypc.serializer.JDKSerializer;
import com.tinypc.serializer.JSONSerializer;
import com.tinypc.serializer.Serializer;
import com.tinyrpc.entity.RpcRequest;
import com.tinyrpc.entity.enumerate.SerializeType;

import java.util.HashMap;
import java.util.Map;

public class BodySerializerContext {
    private final Map<SerializeType, Serializer> serializerMap = new HashMap<>();

    public BodySerializerContext() {
        serializerMap.put(SerializeType.JDK_SERIALIZE, SingletonFactory.getInstance(JDKSerializer.class));
        serializerMap.put(SerializeType.HESSIAN_SERIALIZE, SingletonFactory.getInstance(HessianSerializer.class));
        serializerMap.put(SerializeType.JSON_SERIALIZE, SingletonFactory.getInstance(JSONSerializer.class));

    }

    public Object deSerializeBody(byte[] body, SerializeType serializeType) {
        Serializer serializer = serializerMap.get(serializeType);
        return serializer.deserialize(body);
    }

    public byte[] serializeBody(Object obj, SerializeType serializeType) {
        Serializer serializer = serializerMap.get(serializeType);
        return serializer.serialize(obj);
    }
}

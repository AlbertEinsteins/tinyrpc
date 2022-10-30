package com.tinypc.serializer;

import com.google.gson.*;
import com.tinyrpc.entity.RpcRequest;
import lombok.SneakyThrows;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class JSONSerializer implements Serializer {
    static class ClassCodec implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {

        // 反序列化
        @SneakyThrows
        @Override
        public Class<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            String clazz = jsonElement.getAsString();
            return Class.forName(clazz);
        }

        // 序列化
        @Override
        public JsonElement serialize(Class<?> aClass, Type type, JsonSerializationContext jsonSerializationContext) {
            // 将 Class 变为 json
            return new JsonPrimitive(aClass.getName());
        }
    }


    private static final Gson gson;
    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(Class.class, new ClassCodec())
                .create();
    }
    @Override
    public RpcRequest deserialize(byte[] data) {
        String jsonStr = new String(data, StandardCharsets.UTF_8);
        return gson.fromJson(jsonStr, RpcRequest.class);
    }

    @Override
    public byte[] serialize(Object data) {
        String jsonStr = gson.toJson(data);
        return jsonStr.getBytes(StandardCharsets.UTF_8);
    }
}

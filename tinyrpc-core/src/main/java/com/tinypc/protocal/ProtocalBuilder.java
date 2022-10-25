package com.tinypc.protocal;

import java.nio.ByteBuffer;

/**
 * 协议实体类
 * |   1B  |   1B      |   data  ｜
 * verison   序列化类型    数据
 * 第一个字节写入协议版本号
 * 第二个字节写入序列化类型
 * 之后的部分为数据
 */
public class ProtocalBuilder {

    // 写入工具类
    private ByteBuffer buffer;

    // 序列化类型, JSON序列化
    public static final byte SERIALIZE_JSON_TYPE = 1;
    // Hessian 序列化
    public static final byte SERIALIZE_HESSIAN_TYPE = (1 << 1);
    // JDK 序列化
    public static final byte SERIALIZE_JDK_TYPE = (1 << 2);

    private void writeVersion(int version) {
        buffer.put(0, (byte) version);
    }

    private void writeSerializeType(byte serializeType) {
    }
}

package com.tinypc.protocal;

import com.tinyrpc.entity.enumerate.SerializeType;

import java.nio.ByteBuffer;

/**
 * 协议实体类
 * |   1B  |   1B      |   4B  ｜   data  |
 * verison   序列化类型    数据长度
 * 第一个字节写入协议版本号
 * 第二个字节写入序列化类型
 * 之后的部分为数据
 */
public class ProtocalBuilder {
    // 写入工具类，也就是一个包, 用来包裹字节数组，便于操作
    private ByteBuffer buffer;


    // ========================= 序列化类型 =========================
    public static final byte SERIALIZE_JSON_TYPE = 1;
    // Hessian 序列化
    public static final byte SERIALIZE_HESSIAN_TYPE = (1 << 1);
    // JDK 序列化
    public static final byte SERIALIZE_JDK_TYPE = (1 << 2);
    // PROTOBUF
    public static final byte SERIALZIE_PROTOBUF_TYPE = (1 << 3);
    //========================= 序列化类型 =========================

    // 版本号
    private static final int DEFAULT_VERSION = 1;
    // 协议数据体的默认容量
    private static final int DEFAULT_CAPACITY = (1 << 10);

    // 数据体的长度, 在写入数据的时候写入
    private int dataLength;
    // 数据体对协议头偏移字节数
    private final int dataOffset = 1 + 1 + 4;
    public ProtocalBuilder() {
        this.buffer = ByteBuffer.allocate(DEFAULT_CAPACITY + dataOffset);
    }
    public ProtocalBuilder(int bufferCapacity) {
        this.buffer = ByteBuffer.allocate(bufferCapacity + dataOffset);
    }
    //============ 编码协议 =====================
    private void writeVersion(int version) {
        buffer.put(0, (byte) version);
    }

    private void writeSerializeType(byte serializeType) {
        buffer.put(1, serializeType);
    }
    private void writeData(byte[] data) {
        // 检查buffer大小
        if(data.length + dataOffset > buffer.capacity()) {
            throw new RuntimeException("capacity is not enough to store data");
        }

        buffer.putInt(2, data.length);
        buffer.put(data);
    }

    //获取编码后的字节数组
    public byte[] encodeData(byte[] data) {
        return encodeData(data, SerializeType.JDK_SERIALIZE);
    }
    public byte[] encodeData(byte[] data, SerializeType serializeType) {
        // 清空，置为写入模式
        this.buffer.clear();
        writeVersion(DEFAULT_VERSION);
        writeSerializeType(serializeType.code);
        writeData(data);

        byte[] pkg = new byte[data.length];
        int ind = 0;
        // 切换为读模式
        this.buffer.flip();
        for(int i = buffer.position() + dataOffset; i < buffer.limit(); i++) {
            pkg[ind ++] = buffer.get();
        }
        return pkg;
    }

    // ======== 解码协议 =================
    // 提取数据的序列化类型，和数据体
    // 第一个字节为数据序列化方式，第二个字节以后为数据体
    private byte[] decodeData(byte[] pkg) {
        // 提取数据
        byte code;
        byte[] body;
        byte version;
        int dataLen;

        this.buffer = ByteBuffer.wrap(pkg);
        version = buffer.get();
        code = buffer.get();
        dataLen = buffer.getInt();
        body = new byte[dataLen + 1];
        for(int i = 0; i < dataLen; i++) {
            body[i] = buffer.get();
        }
        body[0] = code;
        return body;
    }
}

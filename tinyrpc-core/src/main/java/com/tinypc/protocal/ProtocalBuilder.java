package com.tinypc.protocal;

import com.tinyrpc.entity.RpcRequest;
import com.tinyrpc.entity.TPackage;
import com.tinyrpc.entity.enumerate.PackageType;
import com.tinyrpc.entity.enumerate.SerializeType;

import java.nio.ByteBuffer;

/**
 * 协议实体类
 * ｜   1B  ｜       4B      ｜  1B    ｜       4B        ｜   data         ｜
 * verison         包类型      序列化类型      数据长度          数据
 * 第一个字节写入协议版本号
 * 第二个字节写入序列化类型
 * 之后的部分为数据
 */
public class ProtocalBuilder {
    // 写入工具类，也就是一个包, 用来包裹字节数组，便于操作
    private ByteBuffer buffer;

    // 协议数据体的默认容量
    private static final int DEFAULT_CAPACITY = (1 << 10);

    // 数据体对协议头偏移字节数
    private final int dataOffset = 1 + 4 + 1 + 4;

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
    private void writePackageType(PackageType packageType) {
        buffer.putInt(1, packageType.code);
    }
    private void writeSerializeType(byte serializeType) {
        buffer.put(5, serializeType);
    }
    private void writeData(byte[] data) {
        // 检查buffer大小
        if(data.length + dataOffset > buffer.capacity()) {
            throw new RuntimeException("capacity is not enough to store data");
        }

        buffer.putInt(6, data.length);
        // 从第10个字节起放数据体
        for(int i = dataOffset; i < data.length; i++)
            buffer.put(i, data[i - dataOffset]);
    }

    //获取编码后的字节数组
    public byte[] encodePackage(TPackage pkg) {
        return encodeData(pkg.getBody(), pkg.getVersion(), pkg.getPackageType(),
                pkg.getSerialType());
    }
    private byte[] encodeData(byte[] body, byte version, PackageType packageType, SerializeType serializeType) {
        // 清空，置为写入模式
        this.buffer.clear();
        writeVersion(version);
        writePackageType(packageType);
        writeSerializeType(serializeType.code);
        writeData(body);

        byte[] pkg = new byte[body.length + dataOffset];
        // 切换为读模式
        buffer.get(pkg, 0, dataOffset + body.length);
        return pkg;
    }

    // ======== 解码协议 =================
    // 从数据体中，提取body
    public TPackage decodeData(byte[] tPackage) {
        // 提取数据
        byte[] body;
        byte version;
        int packageType;
        byte serialType;
        int dataLen;

        this.buffer = ByteBuffer.wrap(tPackage);
        version = buffer.get();
        packageType = buffer.getInt();
        serialType = buffer.get();
        dataLen = buffer.getInt();
        body = new byte[dataLen];
        for(int i = 0; i < dataLen; i++) {
            body[i] = buffer.get();
        }
        return TPackage.create(version, PackageType.fromInt(packageType),
                SerializeType.fromByteCode(serialType), body);
    }

    public int getDataOffset() {
        return this.dataOffset;
    }
}

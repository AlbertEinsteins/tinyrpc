package com.tinyrpc.entity;

import com.tinyrpc.entity.enumerate.PackageType;
import com.tinyrpc.entity.enumerate.SerializeType;

import java.io.Serializable;

public class TPackage implements Serializable {
    private byte version;
    private PackageType packageType;
    private SerializeType serialType;
    private byte[] body;
    private static final byte DEFAULT_VERSION = 1;

    public static TPackage create(PackageType packageType, SerializeType serialType, byte[] body) {
        return TPackage.create(DEFAULT_VERSION, packageType, serialType, body);
    }
    public static TPackage create(byte version, PackageType packageType, SerializeType serialType, byte[] body) {
        return new TPackage(version, packageType, serialType, body);
    }

    private TPackage(byte version, PackageType packageType, SerializeType serialType, byte[] body) {
        this.version = version;
        this.packageType = packageType;
        this.serialType = serialType;
        this.body = body;
    }
    private TPackage(PackageType packageType, SerializeType serialType, byte[] body) {
        this(DEFAULT_VERSION, packageType, serialType, body);
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public PackageType getPackageType() {
        return packageType;
    }

    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
    }

    public SerializeType getSerialType() {
        return serialType;
    }

    public void setSerialType(SerializeType serialType) {
        this.serialType = serialType;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}

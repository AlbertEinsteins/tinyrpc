package com.tinyrpc.registration.config;


public class NacosProperty {
    public String nacosAddr;
    private static final String DEFAULT_NACOS_ADDR = "124.220.16.14:8848";

    public NacosProperty() {
        this.nacosAddr = DEFAULT_NACOS_ADDR;
    }
    public NacosProperty(String nacosAddr) {
        this.nacosAddr = nacosAddr;
    }

    public NacosProperty(String serverHost, String serverPort) {
        this(serverHost + ":" + serverPort);
    }

    public String getNacosAddr() {
        return nacosAddr;
    }

    public void setNacosAddr(String nacosAddr) {
        this.nacosAddr = nacosAddr;
    }

}

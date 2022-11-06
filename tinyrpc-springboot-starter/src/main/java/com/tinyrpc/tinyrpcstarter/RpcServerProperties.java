package com.tinyrpc.tinyrpcstarter;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * 服务端干两件事
 * 1.启动rpc服务
 * 2.将basePackages包下的所有有Service注解的实例，
 */
@Configuration
@ConfigurationProperties(prefix = "tinyrpc")
public class RpcServerProperties {
    // 提供rpc服务的端口, 可以自定义
    private Integer port;

    private String host;

    // basePackage, 哪个包下的service需要注册到nacos中
    private String[] basePackages;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String[] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}

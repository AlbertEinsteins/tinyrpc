package com.test;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.tinyrpc.core.server.RpcServer;
import com.tinyrpc.core.utils.SpringUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan
public class ProviderMain {

    static NamingService namingService;

    static {
        try {
            namingService = NamingFactory.createNamingService("124.220.16.14:8848");
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

    private static void start() throws NacosException {
        //启动server
        new Thread(() -> {
            new RpcServer(6701).startServer();
        }).start();
        //注册
        namingService.registerInstance("user-service", "127.0.0.1", 6701);
    }

    public static void main(String[] args) throws NacosException {
        //启动容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProviderMain.class);
        SpringUtils.setApplicationContext(context);


        start();
    }
}

package com.test;

import com.tinypc.factory.SingletonFactory;
import com.tinypc.server.RpcServer;
import com.tinypc.utils.SpringUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan
public class ProviderMain {
    public static void main(String[] args) {
        //启动容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProviderMain.class);

        //将容器注入到工具类
        SpringUtils.setApplicationContext(context);
        //启动server
        new RpcServer(6700).startServer();
    }
}

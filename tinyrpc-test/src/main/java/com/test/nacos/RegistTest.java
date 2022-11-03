package com.test.nacos;


import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.concurrent.TimeUnit;

public class RegistTest {
    public static void main(String[] args) throws NacosException, InterruptedException {
        NamingService namingService = NamingFactory.createNamingService("124.220.16.14:8848");

        Instance instance ;
//        namingService.registerInstance("user-service", "127.0.0.1", 9900);
        namingService.registerInstance("user-service", "127.0.0.1", 8899);
        namingService.registerInstance("user-service", "127.0.0.1", 7100);
        namingService.registerInstance("user-service", "127.0.0.1", 7102);

        TimeUnit.SECONDS.sleep(10);
        namingService.selectInstances("user-service", true).forEach(System.out::println);
    }
}

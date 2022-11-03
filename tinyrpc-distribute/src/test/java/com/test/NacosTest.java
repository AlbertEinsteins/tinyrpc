package com.test;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NacosTest {


    public static void main(String[] args) throws NacosException, InterruptedException {
        NamingService namingService = NamingFactory.createNamingService("124.220.16.14:8848");
        NamingService namingService2 = NamingFactory.createNamingService("124.220.16.14:8848");


        new Thread(() -> {
            try {
                Instance s1 = new Instance();
                s1.setInstanceId("1");
                s1.setIp("127.0.0.1");
                s1.setPort(8900);

                Instance s2 = new Instance();
                s2.setInstanceId("2");
                s2.setIp("127.0.0.1");
                s2.setPort(8901);
                namingService.registerInstance("user-service", s2);
                namingService2.registerInstance("user-service", s1);
                TimeUnit.MINUTES.sleep(1);
            } catch (NacosException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        TimeUnit.SECONDS.sleep(10);
        List<Instance> instances = namingService.selectInstances("user-service", true);
        System.out.println(instances.size());
    }
}

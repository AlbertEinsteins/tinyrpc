package com.test;

import com.test.entity.User;
import com.test.service.IUserService;
import com.tinyrpc.registration.balancer.RoundRobinBalancer;
import com.tinyrpc.registration.config.NacosProperty;
import com.tinyrpc.registration.stub.Stub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class ConsumerMain {

    private static IUserService userService;


    public static void main(String[] args) {
        IUserService proxy = new Stub(new NacosProperty("124.220.16.14:8848"), new RoundRobinBalancer())
                .getProxy(IUserService.class, "user-service");
        User uid = proxy.findByUid(1);
        User uid2 = proxy.findByUid(2);
        System.out.println(uid);
        System.out.println(uid2);
    }
}

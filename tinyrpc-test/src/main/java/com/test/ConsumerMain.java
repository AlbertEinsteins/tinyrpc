package com.test;

import com.test.entity.User;
import com.test.service.IUserService;
import com.tinyrpc.registration.stub.Stub;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class ConsumerMain {

    private static IUserService userService;

    public static void main(String[] args) {
        IUserService proxy = Stub.getProxy(IUserService.class, "user-service");
        User uid = proxy.findByUid(1);
        User uid2 = proxy.findByUid(2);
        System.out.println(uid);
        System.out.println(uid2);
    }
}

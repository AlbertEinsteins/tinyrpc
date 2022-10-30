package com.test;

import com.test.service.IUserService;
import com.tinypc.client.RpcClient;
import com.tinyrpc.entity.RpcRequest;
import com.tinyrpc.entity.RpcResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class ConsumerMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ConsumerMain.class);

        RpcClient rpcClient = new RpcClient(6700);

        RpcResponse resp = rpcClient.getResponse(new RpcRequest(IUserService.class, "findByUid",
                new Class[]{Integer.class}, new Object[]{1}));
        System.out.println(resp.getResult());
    }
}

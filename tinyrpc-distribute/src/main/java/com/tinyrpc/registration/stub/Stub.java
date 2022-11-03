package com.tinyrpc.registration.stub;


import cn.hutool.aop.ProxyUtil;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.tinyrpc.core.entity.RpcRequest;
import com.tinyrpc.core.entity.RpcResponse;
import com.tinyrpc.registration.balancer.LoadBalancer;
import com.tinyrpc.registration.balancer.RoundRobinBalancer;
import com.tinyrpc.registration.config.NacosProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class Stub {
    private static final Logger STUB_LOGGER = LoggerFactory.getLogger(Stub.class);

    private static NacosProperty systemConfig;

    private static final ClientProxy clientProxy;
    private static final NamingService namingService;

    private static LoadBalancer loadBalancer;


    static {
        clientProxy = new ClientProxy();
        loadBalancer = new RoundRobinBalancer();
        systemConfig = new NacosProperty("124.220.16.14:8848");
        try {
            namingService = NamingFactory.createNamingService(systemConfig.nacosAddr);
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }


    public Stub(NacosProperty config) {
        systemConfig = config;
        loadBalancer = new RoundRobinBalancer();
    }

    public static <T> T getProxy(Class<T> cls, String serviceName) {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest request = createRequest(method, args, cls);
                List<Instance> instances = namingService.selectInstances(serviceName, true);
                Instance choose = loadBalancer.choose(instances);
                STUB_LOGGER.info("调用ip: {}, port: {}", choose.getIp(), choose.getPort());

                RpcResponse response = clientProxy.getRpcResponse(request, choose);
                return response.getResult();
            }
        };

        return ProxyUtil.newProxyInstance(handler, cls);
    }



    private static RpcRequest createRequest(Method method, Object[] args, Class<?> cls) {
        RpcRequest request = new RpcRequest();
        request.setClassName(cls);
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        return request;
    }

    public void setSystemConfig(NacosProperty nacosProperty) {
        systemConfig = nacosProperty;
    }
    public void setLoadBalancer(LoadBalancer loadBalancer) {
        Stub.loadBalancer = loadBalancer;
    }
}

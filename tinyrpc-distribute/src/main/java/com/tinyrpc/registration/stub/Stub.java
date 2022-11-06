package com.tinyrpc.registration.stub;


import cn.hutool.aop.ProxyUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.utils.StringUtils;
import com.tinyrpc.core.entity.RpcRequest;
import com.tinyrpc.core.entity.RpcResponse;
import com.tinyrpc.registration.balancer.LoadBalancer;
import com.tinyrpc.registration.balancer.RoundRobinBalancer;
import com.tinyrpc.registration.config.NacosProperty;
import org.apache.http.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class Stub {
    private static final Logger STUB_LOGGER = LoggerFactory.getLogger(Stub.class);

    private NacosProperty nacosConfig;

    private ClientProxy clientProxy;
    private NamingService namingService;

    private LoadBalancer loadBalancer;


    public Stub(NacosProperty config, LoadBalancer loadBalancer) {
        this.nacosConfig = config;
        this.loadBalancer = loadBalancer;
        this.init();
    }
    public Stub(NacosProperty config) {
        this(config, new RoundRobinBalancer());
    }

    private void init() {
        try {
            namingService = NamingFactory.createNamingService(nacosConfig.nacosAddr);
            this.clientProxy = new ClientProxy();
        } catch (NacosException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getProxy(Class<T> cls, String serviceName) {
        Assert.notNull(loadBalancer, "Load Balancer could not be null");
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                List<Instance> instances = namingService.selectInstances(serviceName, true);
                Instance choose = loadBalancer.choose(instances);
                String interfaceNameinProvider = findAvailableClassName(choose, cls);
                RpcRequest request = createRequest(method, args, interfaceNameinProvider);
                if(STUB_LOGGER.isDebugEnabled()) {
                    STUB_LOGGER.debug("调用ip: {}, port: {}", choose.getIp(), choose.getPort());
                }

                RpcResponse response = clientProxy.getRpcResponse(request, choose);
                return response.getResult();
            }
        };

        return ProxyUtil.newProxyInstance(handler, cls);
    }

    /**
     * 从该实例的metadata.interfaces查找相配的服务接口名
     * @param instance
     * @return
     */
    private String findAvailableClassName(Instance instance, Class<?> consumerInterface) {
        String interfaces = instance.getMetadata().get("interfaces");
        for (String s : interfaces.split("@")) {
            if(matchInterfaceName(s, consumerInterface.getName())) {
                return s;
            }
        }
        //一定得匹配
        throw new RuntimeException(
                String.format("The provider does not provide the serviceName for %s",
                        consumerInterface.getSimpleName()));
    }
    private boolean matchInterfaceName(String provideName, String localName) {
        return StringUtils.equals(extractInterfaceSimpleName(provideName),
                extractInterfaceSimpleName(localName));
    }

    // 获取接口全路径类名
    private String extractInterfaceSimpleName(String classpathName) {
        String[] strings = classpathName.split("\\.");
        return strings[strings.length - 1];
    }


    private RpcRequest createRequest(Method method, Object[] args, String className) {
        RpcRequest request = new RpcRequest();
        request.setClassName(className);
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        return request;
    }

    public NacosProperty getNacosConfig() {
        return nacosConfig;
    }

    public void setNacosConfig(NacosProperty nacosConfig) {
        this.nacosConfig = nacosConfig;
    }

    public LoadBalancer getLoadBalancer() {
        return loadBalancer;
    }

    public void setLoadBalancer(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public NamingService getNamingService() {
        return this.namingService;
    }
}

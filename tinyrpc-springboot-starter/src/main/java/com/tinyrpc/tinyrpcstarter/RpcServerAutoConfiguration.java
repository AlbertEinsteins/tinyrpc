package com.tinyrpc.tinyrpcstarter;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.tinyrpc.core.server.RpcServer;
import com.tinyrpc.core.utils.SpringUtils;
import com.tinyrpc.registration.balancer.LoadBalancer;
import com.tinyrpc.registration.balancer.RoundRobinBalancer;
import com.tinyrpc.registration.config.NacosProperty;
import com.tinyrpc.registration.stub.Stub;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@EnableConfigurationProperties(RpcServerProperties.class)
@Import(ServiceMetaDataRegistry.class)
public class RpcServerAutoConfiguration implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Autowired
    private RpcServerProperties rpcServerProperties;

    @Autowired
    private ServiceMetaDataRegistry serviceMetaDataRegistry;

    @Autowired
    private Stub stub;

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    public RpcServer rpcServer() {
        //服务器方的rpc调用依赖于容器
        SpringUtils.setApplicationContext(applicationContext);
        RpcServer rpcServer = new RpcServer(rpcServerProperties.getHost(), rpcServerProperties.getPort());
        serviceMetaDataRegistry.registryLocalService();

        //todo: 这里不能单纯的用线程启动
        //todo
        Instance instance = InstanceHolder.getInstance();
        instance.setIp(rpcServerProperties.getHost());
        instance.setPort(rpcServerProperties.getPort());
        new Thread(rpcServer).start();
        new Thread(() -> {
            try {
                getNamingService()
                        .registerInstance(appName, instance);
            } catch (NacosException e) {
                throw new RuntimeException(e);
            }
        }).start();
        return rpcServer;
    }

    private NamingService getNamingService() {
        return stub.getNamingService();
    }
}

package com.tinyrpc.tinyrpcstarter;

import com.tinyrpc.registration.balancer.LoadBalancer;
import com.tinyrpc.registration.balancer.RoundRobinBalancer;
import com.tinyrpc.registration.config.NacosProperty;
import com.tinyrpc.registration.stub.Stub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(NacosProperties.class)
public class RpcClientAutoConfiguration {
    @Autowired
    private NacosProperties nacosProperties;

    @Bean
    public LoadBalancer loadBalancer() {
        return new RoundRobinBalancer();
    }

    @Bean
    public Stub getStub() {
        NacosProperty nacosProperty = new NacosProperty();
        nacosProperty.setNacosAddr(nacosProperties.getNacosAddr());
        return new Stub(nacosProperty, loadBalancer());
    }

}

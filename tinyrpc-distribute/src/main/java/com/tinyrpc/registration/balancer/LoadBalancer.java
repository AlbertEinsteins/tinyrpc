package com.tinyrpc.registration.balancer;


import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * 均衡策略
 */
public interface LoadBalancer {
    Instance choose(List<Instance> instances);
}

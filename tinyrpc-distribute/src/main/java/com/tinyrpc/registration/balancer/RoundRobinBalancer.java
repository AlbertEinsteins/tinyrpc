package com.tinyrpc.registration.balancer;

import com.alibaba.nacos.api.naming.pojo.Instance;
import org.apache.http.util.Asserts;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinBalancer implements LoadBalancer {

    private final AtomicInteger atomicCount = new AtomicInteger(0);

    @Override
    public Instance choose(List<Instance> instances) {
        Asserts.check(instances.size() > 0, "the size of instance for rpc call is empty.");
        int ind = atomicCount.getAndIncrement() % instances.size();
        return instances.get(ind);
    }

    private void reset(int val) {
        int retry = 3;
        while(retry > 0) {
            if(atomicCount.compareAndSet(Integer.MAX_VALUE, val)) {
                break;
            }
            retry --;
        }
    }
}

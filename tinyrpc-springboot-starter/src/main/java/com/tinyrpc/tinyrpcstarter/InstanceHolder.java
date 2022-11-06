package com.tinyrpc.tinyrpcstarter;

import com.alibaba.nacos.api.naming.pojo.Instance;

public class InstanceHolder {
    private static final Instance instance = new Instance();

    // 该类不允许实例化
    private InstanceHolder() { }

    public static Instance getInstance() {
        return instance;
    }
}

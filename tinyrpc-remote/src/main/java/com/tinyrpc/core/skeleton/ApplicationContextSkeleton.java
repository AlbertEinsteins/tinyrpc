package com.tinyrpc.core.skeleton;

import com.tinyrpc.core.utils.SpringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


/**
 * 基于Spring容器的方法
 * todo
 */
public class ApplicationContextSkeleton extends AbstractSkeleton {
    private ApplicationContext applicationContext;

    public ApplicationContextSkeleton() {
        this.applicationContext = SpringUtils.getApplcationContext();
    }
    //从容器获取类实例
    @Override

    Object getSubInstance(String clsName) {
        try {
            return this.applicationContext.getBean(Class.forName(clsName));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

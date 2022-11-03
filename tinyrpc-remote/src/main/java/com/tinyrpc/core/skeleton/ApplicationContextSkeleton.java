package com.tinyrpc.core.skeleton.skeleton;

import com.tinyrpc.core.utils.SpringUtils;
import org.springframework.context.ApplicationContext;

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
    Object getSubInstance(Class<?> clsName) {
        Object bean = this.applicationContext.getBean(clsName);

        return this.applicationContext.getBean(clsName);
    }
}

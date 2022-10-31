package com.tinypc.core.skeleton;

import com.tinypc.utils.SpringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

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

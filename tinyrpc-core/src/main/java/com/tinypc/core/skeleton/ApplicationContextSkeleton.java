package com.tinypc.core.skeleton;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 基于Spring容器的方法
 * todo
 */
@Component
public class ApplicationContextSkeleton extends AbstractSkeleton implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //从容器获取类实例
    @Override
    Object getSubInstance(Class<?> clsName) {
        return this.applicationContext.getBean(clsName);
    }
}

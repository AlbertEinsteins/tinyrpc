package com.tinyrpc.tinyrpcstarter.annotate;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.tinyrpc.registration.stub.Stub;
import com.tinyrpc.tinyrpcstarter.InstanceHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class RpcAnnotationProcessor extends ClassPathBeanDefinitionScanner {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcAnnotationProcessor.class);
    public RpcAnnotationProcessor(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        //添加过滤条件,找RpcReference注解的类
        addIncludeFilter(new AnnotationTypeFilter(RpcReference.class));
        //Spring 扫描器
        Set<BeanDefinitionHolder> definitionHolders = super.doScan(basePackages);
        if(definitionHolders.size() > 0) {
            processBeanDefinitions(definitionHolders);
        }
        return definitionHolders;
    }

    /**
     * 代理接口，生成真正的实例，也就是FactoryBean，放入容器
     */
    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            String beanClassName = beanDefinition.getBeanClassName();
            try {
                //获取类（接口）模版
                Class<?> aClass = Class.forName(beanClassName);
                beanDefinition.setBeanClass(StubFactoryBean.class);
                beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(aClass);
                beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(getServiceName(aClass));

                //在bean解析阶段，还没生成实例，没法注入，需要用RuntimeBeanReference表示
                beanDefinition.getPropertyValues().add("stub", new RuntimeBeanReference(Stub.class));
                beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    private String getServiceName(Class<?> cls) {
        RpcReference rpcReference = cls.getAnnotation(RpcReference.class);
        if(!StringUtils.hasLength(rpcReference.value())) {
            return rpcReference.serviceName();
        }
        return rpcReference.value();
    }
}

package com.tinyrpc.tinyrpcstarter.annotate;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Map;
import java.util.Objects;

public class RpcAnnotationPackageScan implements ImportBeanDefinitionRegistrar, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(ComponentScan.class.getName());
        String[] basePackages = Objects.requireNonNull(AnnotationAttributes.fromMap(annotationAttributes))
                .getStringArray("basePackages");

        RpcAnnotationProcessor annotationProcessor = new RpcAnnotationProcessor(registry, false);
        annotationProcessor.doScan(basePackages);
    }
}

package com.tinyrpc.tinyrpcstarter;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.google.common.collect.Lists;
import org.apache.http.util.Asserts;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ServiceMetaDataRegistry implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    public void registryLocalService() {
        getServiceNameWithServiceAnnotation().forEach(this::registryServiceName);
    }

    private void registryServiceName(String interfaceName) {
        Instance instance = InstanceHolder.getInstance();
        String interfaces = instance.getMetadata().get("interfaces");
        if(!StringUtils.hasLength(interfaces)) {
            instance.getMetadata().put("interfaces", interfaceName);
        }
        else {
            instance.getMetadata().put("interfaces", "@" + interfaceName);
        }
    }


    /**
     * 获取实现类的接口名
     * @return 所有服务的接口名
     */
    private List<String> getServiceNameWithServiceAnnotation() {
        Map<String, Object> beansImpls = applicationContext.getBeansWithAnnotation(Service.class);
        List<String> interfaceNames = Lists.newArrayList();
        beansImpls.forEach((beanName, bean) -> {
            Class<?>[] interfaces = bean.getClass().getInterfaces();

            Asserts.check(interfaces.length > 0,
                    "The service class must implment a interface {}", bean.getClass().getName());

            interfaceNames.add(interfaces[0].getName());
        });
        return interfaceNames;
    }

}

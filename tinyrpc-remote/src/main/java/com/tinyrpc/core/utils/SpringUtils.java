package com.tinyrpc.core.utils;

import org.springframework.context.ApplicationContext;

/**
 * 工具类
 * 用来获取Spring容器（项目实现不依赖于Spring，需要外部提供）
 */
public class SpringUtils {
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplcationContext() {
        return SpringUtils.applicationContext;
    }
}

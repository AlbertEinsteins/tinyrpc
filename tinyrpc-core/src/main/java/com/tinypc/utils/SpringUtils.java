package com.tinypc.utils;

import org.springframework.context.ApplicationContext;

/**
 * 工具类，会放入单例工厂
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

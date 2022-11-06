package com.tinyrpc.tinyrpcstarter.annotate;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcAnnotationPackageScan.class})
@Documented
public @interface ComponentScan {
    String[] basePackages() default {};
}

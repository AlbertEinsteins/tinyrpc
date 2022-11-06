package com.tinyrpc.tinyrpcstarter.annotate;


import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcReference {
    @AliasFor("value")
    String serviceName() default "";

    @AliasFor("serviceName")
    String value() default "";
}

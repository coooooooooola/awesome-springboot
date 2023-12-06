package com.zeburan.cache.config;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {

    //数据源标识参数的位置，从0开始计
    int dbidIndex() default 0;
}

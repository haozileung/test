package com.haozileung.infra.dal.annotation;

import java.lang.annotation.*;

/**
 * 用来获取表名
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {

    String value() default "";
}

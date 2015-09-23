package com.haozileung.infra.dao.annotation;

import java.lang.annotation.*;

/**
 * 用来获取主键名称
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    String value() default "";
}

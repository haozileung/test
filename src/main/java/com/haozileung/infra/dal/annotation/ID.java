package com.haozileung.infra.dal.annotation;

import java.lang.annotation.*;

/**
 * 用来获取主键名称
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ID {

}

package com.haozileung.infra.dao.annotation;

import java.lang.annotation.*;

/**
 * Created by Haozi on 2015/9/23.
 */
@Retention(RetentionPolicy.RUNTIME)//注解会在class中存在，运行时可通过反射获取
@Target(ElementType.METHOD)//目标是方法
@Documented//文档生成时，该注解将被包含在javadoc中，可去掉
public @interface Tx {
}

package com.evan.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName RequestMapping
 * @Description
 * @Author EvanWang
 * @Version 1.0.0
 * @Date 2019/11/14 23:42
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String value() default "";
}

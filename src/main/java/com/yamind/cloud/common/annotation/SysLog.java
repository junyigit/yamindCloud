package com.yamind.cloud.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * @date 2017年8月14日 下午8:00:45
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}

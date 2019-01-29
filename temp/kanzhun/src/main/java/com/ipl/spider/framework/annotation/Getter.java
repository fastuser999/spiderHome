package com.ipl.spider.framework.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD,
         ElementType.LOCAL_VARIABLE,
         ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Getter {

    String fieldName() default "";

    Class<?> fieldType() default String.class;
}

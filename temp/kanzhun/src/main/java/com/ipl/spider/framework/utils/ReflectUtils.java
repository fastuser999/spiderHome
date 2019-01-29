package com.ipl.spider.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dev
 */
public class ReflectUtils {

    private static Logger logger = LoggerFactory.getLogger(ReflectUtils.class);

    public static Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return null;
    }

    public static <T> T getInstance(Class<T> c, Class<?>[] argTypes, Object[] args) {
        try {
            if (argTypes.length != args.length) {
                throw new IllegalArgumentException("参数类型和个数不相等");
            }
            Constructor<T> constructor = c.getDeclaredConstructor(argTypes);
            return (T) constructor.newInstance(args);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return null;
    }

    public static <R> R getFieldValue(Object obj, String fieldName, Class<R> returnType) {
        return getFieldValue(obj.getClass(), obj, fieldName, returnType);
    }

    public static <R, C> R getFieldValue(Class<C> c, Object obj, String fieldName, Class<R> returnType) {
        try {
            Field field = c.getField(fieldName);
            field.setAccessible(true);
            return (R) field.get(obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static void setFieldValue(Object target, String fieldName, Object value) {
        try {
            setFieldValue(target, target.getClass().getDeclaredField(fieldName), value);
        } catch (Exception e) {

        }
    }

    public static void setFieldValue(Object target, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    /**
     * 获取被注解标记的字段
     * @param targetClass 目标类
     * @param annotationClass 注解
     * @return
     */
    public static List<Field> getAnnotatedFiled(Class targetClass, Class annotationClass) {
        return (List<Field>) Arrays.asList(targetClass.getDeclaredFields()).stream().
                filter(field -> field.getDeclaredAnnotation(annotationClass) != null).
                collect(Collectors.toList());
    }

    public static <T extends Annotation> T getTypeAnnotation(Class targetClass, Class<T> annotationClass) {
        try {
            T t = (T) targetClass.getAnnotation(annotationClass);
            if (t == null) {
               throw new IllegalArgumentException("未获取指定的注解");
            }
            return t;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        return null;
    }

}

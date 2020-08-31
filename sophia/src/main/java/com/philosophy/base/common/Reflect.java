package com.philosophy.base.common;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lizhe
 * @date 2019/10/10:14:34
 */
@Slf4j
public class Reflect {
    /**
     * 查找该对象的指定方法
     *
     * @param objClass       对象类型
     * @param name           方法名称
     * @param parameterTypes 方法参数类型数组
     * @return 如果找到则返回方法;没找到返回null
     */
    public static Method getMethod(Class<?> objClass, String name, Class<?>[] parameterTypes) {
        Method method = null;
        try {
            method = objClass.getMethod(name, parameterTypes);
        } catch (Throwable t) {
            if (null != objClass.getSuperclass()) {
                method = getMethod(objClass.getSuperclass(), name, parameterTypes);
            }
            if (null == method && null != objClass.getInterfaces()) {
                for (Class<?> iface : objClass.getInterfaces()) {
                    method = getMethod(iface, name, parameterTypes);
                    if (null != method) {
                        return method;
                    }
                }
            }
        }
        return method;
    }

    /**
     * 获取所有的方法列表
     *
     * @param objClass 对象类型
     * @return 返回对象的所有方法
     */
    public static List<Method> getAllMethods(Class<?> objClass) {
        List<Method> methods = new ArrayList<>(Arrays.asList(objClass.getDeclaredMethods()));
        if (null != objClass.getSuperclass()) {
            methods.addAll(getAllMethods(objClass.getSuperclass()));
        }
        Class<?>[] interfaces = objClass.getInterfaces();
        if (null != interfaces && interfaces.length > 0) {
            for (Class<?> iface : interfaces) {
                methods.addAll(getAllMethods(iface));
            }
        }
        return methods;
    }

    /**
     * 深拷贝
     *
     * @param src    源对象
     * @param target 目标对象
     */
    public static void copy(Object src, Object target) {
        copy(src, target);
    }

    /**
     * 深拷贝
     *
     * @param src            源对象
     * @param target         目标对象
     * @param convert2String 是否转换为string
     */
    public static void copy(Object src, Object target, boolean convert2String) {
        List<Method> methods = getAllMethods(src.getClass());
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get")) {
                Method tarSetMethod;
                tarSetMethod = getMethod(target.getClass(), "s" + methodName.substring(1),
                        new Class[]{convert2String ? String.class : method.getReturnType()});
                if (null == tarSetMethod) {
                    continue;
                }
                try {
                    Object param = method.invoke(src);
                    tarSetMethod.invoke(target, convert2String ? (null == param ? "" : param.toString()) : param);
                } catch (Throwable t) {
                    log.error(t.getMessage());
                }
            }
        }
    }

    /**
     * 执行方法
     *
     * @param obj        对象
     * @param methodName 方法名
     * @param paramTypes 参数类型
     * @param paras      参数名
     * @return 执行结果
     */
    public static Object execute(Object obj, String methodName, Class<?>[] paramTypes, Object[] paras) {
        if (null == obj) {
            return null;
        }
        Method method = getMethod(obj.getClass(), methodName, paramTypes);
        if (null == method) {
            throw new RuntimeException("not find method[" + methodName + "] in class[" + obj.getClass() + "].");
        }
        try {
            method.setAccessible(true);
            return method.invoke(obj, paras);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * 调用方法
     *
     * @param cls        class
     * @param methodName 方法名
     * @param paramTypes 参数类型
     * @param paras      参数名
     * @return 执行结果
     */
    public static Object invoke(Class<?> cls, String methodName, Class<?>[] paramTypes, Object[] paras) {
        Method method = Reflect.getMethod(cls, methodName, paramTypes);
        if (null == method) {
            throw new RuntimeException("not find method[" + methodName + "] in class[" + cls + "].");
        }
        try {
            method.setAccessible(true);
            return method.invoke(null, paras);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }


}

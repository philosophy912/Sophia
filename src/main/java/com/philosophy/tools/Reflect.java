package com.philosophy.tools;

import com.philosophy.exception.EntityNotExistException;
import com.philosophy.exception.LowLevelException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:18
 **/
public class Reflect {
    private Reflect() {
    }

    /**
     * 查找该对象的指定方法
     * <br>
     * 如果找到则返回方法;没找到返回null
     *
     * @param objClass       对象类型
     * @param name           方法名称
     * @param parameterTypes 方法参数类型数组
     * @return
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
    public static List<Method> getAllMethod(Class<?> objClass) {
        List<Method> methods = new ArrayList<Method>();
        methods.addAll(Arrays.asList(objClass.getDeclaredMethods()));
        if (null != objClass.getSuperclass()) {
            methods.addAll(getAllMethod(objClass.getSuperclass()));
        }
        Class<?>[] interfaces = objClass.getInterfaces();
        if (null != interfaces && interfaces.length > 0) {
            for (Class<?> iface : interfaces) {
                methods.addAll(getAllMethod(iface));
            }
        }
        return methods;
    }

    public static void copy(Object srcObj, Object tarObj) {
        copy(srcObj, tarObj, false);
    }

    public static void copy(Object srcObj, Object tarObj, boolean convert2String) {
        List<Method> methods = getAllMethod(srcObj.getClass());
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("get")) {
                Method tarSetMethod = null;
                tarSetMethod = getMethod(tarObj.getClass(), "s" + methodName.substring(1), new Class[]
                        {convert2String ? String.class : method.getReturnType()});
                if (null == tarSetMethod) {
                    continue;
                }
                try {
                    Object param = method.invoke(srcObj, new Object[0]);
                    tarSetMethod.invoke(tarObj, convert2String ? (null == param ? "" : param.toString()) : param);
                } catch (Throwable t) {
                    continue;
                }
            }
        }
    }

    public static Object execute(Object obj, String methodName, Class<?>[] paramTypes, Object[] paras) {
        if (null == obj) {
            return null;
        }
        Method method = getMethod(obj.getClass(), methodName, paramTypes);
        if (null == method) {
            throw new EntityNotExistException("not find method[" + methodName + "] in class[" + obj.getClass() + "].");
        }
        try {
            method.setAccessible(true);
            return method.invoke(obj, paras);
        } catch (Throwable t) {
            throw new LowLevelException(t);
        }
    }

    public static Object invoke(Class<?> cls, String methodName, Class<?>[] paramTypes, Object[] paras) {
        Method method = Reflect.getMethod(cls, methodName, paramTypes);
        if (null == method) {
            throw new EntityNotExistException("not find method[" + methodName + "] in class[" + cls + "].");
        }
        try {
            method.setAccessible(true);
            return method.invoke(null, paras);
        } catch (Throwable t) {
            throw new LowLevelException(t);
        }
    }
}

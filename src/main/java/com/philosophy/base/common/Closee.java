package com.philosophy.base.common;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lizhe
 * @date 2019/10/10:14:03
 */
@Slf4j
public class Closee {
    private Closee() {
    }

    /**
     * 通过反射调用实例中的close方法
     * @param objects 要调用的实例
     */
    public static void close(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                continue;
            }
            try {
                Reflect.execute(object, "close", new Class<?>[0], new Object[0]);
            } catch (Throwable t) {
                log.error(t.getMessage());
            }
        }
    }
}

package com.philosophy.tools;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:22
 **/
public class Closee {
    private Closee() {
    }
    public static void close(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                continue;
            }
            try {
                Reflect.execute(obj, "close", new Class<?>[0], new Object[0]);
            } catch (Throwable t) {
            }
        }
    }
}

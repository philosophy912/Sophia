package com.philosophy.base.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lizhe
 * @date 2019/10/10:14:07
 */
public class Pair<T1, T2> implements Serializable {

    private static final long serialVersionUID = -53259419774861123L;
    @Setter
    @Getter
    protected T1 first;
    @Setter
    @Getter
    protected T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    /**
     * 判断两个对象是否相等的方法，可处理null
     *
     * @param object1 对象1
     * @param object2 对象2
     * @return 都为null 或 obj1.equals(obj2)为true
     */
    protected boolean objectEquals(Object object1, Object object2) {
        return object1 == null && object2 == null || object1 != null && object1.equals(object2);
    }


    @Override
    public int hashCode() {
        if (first == null) {
            return second != null ? second.hashCode() + 1 : 0;
        }
        if (second == null) {
            return first.hashCode() + 2;
        }
        return first.hashCode() * 17 + second.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair<?, ?>) {
            Pair<?, ?> other = (Pair<?, ?>) obj;
            return objectEquals(first, other.first) && objectEquals(second, other.second);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Pair(" + first + ", " + second + ")";
    }

    public boolean equalsTo(T1 t1, T2 t2) {
        return objectEquals(first, t1) && objectEquals(second, t2);
    }
}

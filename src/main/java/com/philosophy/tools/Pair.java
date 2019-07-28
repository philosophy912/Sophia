package com.philosophy.tools;

import java.io.Serializable;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:23
 **/
public class Pair<T1, T2> implements Serializable {
    private static final long serialVersionUID = 2815044214343796339L;

    /**
     * 构造函数
     *
     * @param fst 分量1
     * @param snd 分量2
     */
    public Pair(T1 fst, T2 snd) {
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public String toString() {
        return "Pair(" + String.valueOf(fst) + ", " + String.valueOf(snd) + ")";
    }

    @Override
    public int hashCode() {
        if (fst == null)
            return snd != null ? snd.hashCode() + 1 : 0;
        if (snd == null)
            return fst.hashCode() + 2;
        else
            return fst.hashCode() * 17 + snd.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pair<?, ?>) {
            Pair<?, ?> other = (Pair<?, ?>) o;
            return objectEquals(this.fst, other.fst) && objectEquals(this.snd, other.snd);
        }
        return false;
    }

    public boolean equalsTo(T1 o1, T2 o2) {
        return objectEquals(fst, o1) && objectEquals(snd, o2);
    }

    public T1 getFst() {
        return fst;
    }

    public void setFst(T1 fst) {
        this.fst = fst;
    }

    public T2 getSnd() {
        return snd;
    }

    public void setSnd(T2 snd) {
        this.snd = snd;
    }

    /**
     * 判断两个对象是否相等的方法，可处理null
     *
     * @param obj1 对象1
     * @param obj2 对象1
     * @return 都为null 或 obj1.equals(obj2)为true
     */
    protected boolean objectEquals(Object obj1, Object obj2) {
        return obj1 == null && obj2 == null || obj1 != null && obj1.equals(obj2);
    }

    /**
     * 分量1
     */
    protected T1 fst;
    /**
     * 分量2
     */
    protected T2 snd;

}

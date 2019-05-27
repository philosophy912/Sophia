package com.philosophy.tools;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:23
 **/
public class Triple<T1, T2, T3> extends Pair<T1, T2> {
    private static final long serialVersionUID = 2815044214343796339L;

    /**
     * 构造函数
     *
     * @param fst
     *            分量1
     * @param snd
     *            分量2
     */
    public Triple(T1 fst, T2 snd, T3 thd) {
        super(fst, snd);
        this.thd = thd;
    }

    @Override
    public String toString() {
        return "Triple(" + String.valueOf(getFst()) + ", " + String.valueOf(getSnd()) + "," + String.valueOf(getThd())
                + ")";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((thd == null) ? 0 : thd.hashCode());
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Triple other = (Triple) obj;
        if (thd == null) {
            if (other.thd != null)
                return false;
        } else if (!thd.equals(other.thd))
            return false;
        return true;
    }

    public boolean equalsTo(T1 o1, T2 o2, T3 o3) {
        return objectEquals(getFst(), o1) && objectEquals(getSnd(), o2) && objectEquals(getThd(), o3);
    }

    public T3 getThd() {
        return thd;
    }

    public void setThd(T3 thd) {
        this.thd = thd;
    }

    /**
     * 判断两个对象是否相等的方法，可处理null
     *
     * @param obj1
     *            对象1
     * @param obj2
     *            对象1
     * @return 都为null 或 obj1.equals(obj2)为true
     */
    protected boolean objectEquals(Object obj1, Object obj2) {
        return obj1 == null && obj2 == null || obj1 != null && obj1.equals(obj2);
    }

    /** 分量2 */
    private T3 thd;
}

package com.philosophy.base.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2019/10/10:14:16
 */
public class Triple<T1, T2, T3> extends Pair<T1, T2> {
    private static final long serialVersionUID = 8171302485286129090L;
    @Setter
    @Getter
    private T3 third;


    public Triple(T1 first, T2 second, T3 third) {
        super(first, second);
        this.third = third;
    }

    @Override
    protected boolean objectEquals(Object object1, Object object2) {
        return super.objectEquals(object1, object2);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        return prime * result + (third == null ? 0 : third.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
        if (third == null) {
            return other.third == null;
        } else {
            return third.equals(other.third);
        }
    }

    @Override
    public String toString() {
        return "Triple(" + getFirst() + ", " + getSecond() + ", " + getThird() + ")";
    }

    public boolean equalsTo(T1 t1, T2 t2, T3 t3) {
        return objectEquals(getFirst(), t1) && objectEquals(getSecond(), t2) && objectEquals(getThird(), t3);
    }
}

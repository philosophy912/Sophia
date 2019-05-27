package com.philosophy.exception;
/**
 * 代表执行过程中出现不可恢复底层异常
 *
 * @author xiongjitao
 * @version $Rev: 1262 $
 * @since V3_00_50P0B1
 */
public class LowLevelException extends RuntimeException{
    /** serial required */
    private static final long serialVersionUID = 1L;

    /**
     * 说明字符构造方法.
     *
     * @param <code>String</code>
     *            异常原因说明, Not null.
     */
    public LowLevelException(String msg) {
        super(msg);
    }

    /**
     * 消息与原异常的构造
     *
     * @param <code>String</code>
     *            异常说明, Not null.
     * @param <code>Throwable</code>
     *            原异常, Not null.
     */
    public LowLevelException(String msg, Throwable cause) {
        super(msg, cause);
    }

    /**
     * 只有详细异常的构造.
     *
     * @param <code>Throwable</code>
     *            详细异常.
     */
    public LowLevelException(Throwable cause) {
        super(cause);
    }
}

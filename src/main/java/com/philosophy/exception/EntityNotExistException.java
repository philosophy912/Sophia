package com.philosophy.exception;
/**
 * 访问的实体已不存在的异常<br>
 * 在不同的进程间进行实体检索，调用方持有的键值不能绝对保证依然有效。另外， 在多用户都能对同一实体进行访问的情况下， 若一个删除、 而另一个查看。
 *
 * @author xiongjitao
 */
public class EntityNotExistException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = 576326676151302626L;

    /**
     * 基于文本信息构造
     *
     * @param message
     *            详细信息 not null
     */
    public EntityNotExistException(String message) {
        super(message);
    }

    /**
     * 基于底层异常构造
     *
     * @param cause
     *            起因 not null
     */
    public EntityNotExistException(Throwable cause) {
        super(cause);
    }

    /**
     * 基于文本消息与底层异常构造
     *
     * @param message
     *            详细信息 not null
     * @param cause
     *            起因 not null
     */
    public EntityNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}

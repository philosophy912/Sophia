package com.philosophy.character.api;

/**
 * @author lizhe
 * @since V1.0.0 2019/10/11 22:09
 **/
public interface ICharFactory {
    /**
     * create character by type and size
     *
     * @param type 类型
     * @param size 长度
     * @return 创建的字符
     */
    String create(CharEnum type, int size);
}

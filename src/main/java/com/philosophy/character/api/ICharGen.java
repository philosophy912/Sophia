package com.philosophy.character.api;

/**
 * @author lizhe
 * @since V1.0.0 2019/10/11 21:36
 **/
public interface ICharGen {
    /**
     * create character
     * @param size 长度
     * @return 生成的字符
     */
    String generate(int size);
}

package com.philosophy.api.codec;

import com.philosophy.api.IConst;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:48
 **/
public interface IXESCodec extends IConst {
    /**
     * 加密字符串（以AES, DES, 3DES)方式加密字符串
     * @param source 要加密的字符串
     * @param key 秘钥字符串
     * @return 加密后的字符串
     * @throws Exception 抛出的异常
     */
    String encrypt(String source, String key) throws Exception;

    /**
     * 解密字符串（以AES, DES, 3DES)方式解密字符串
     * @param source 加密后的字符串
     * @param key 秘钥的字符串
     * @return 解密后的字符串
     * @throws Exception 抛出的异常
     */
    String decrypt(String source, String key) throws Exception;
}

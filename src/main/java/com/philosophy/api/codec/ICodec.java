package com.philosophy.api.codec;

import com.philosophy.api.IConst;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:41
 **/
public interface ICodec extends IConst {
    /**
     * MD5加密字符串
     *
     * @param source 要加密的字符串
     * @return 返回加密后的字符串
     * @throws Exception 抛出异常
     */
    String md5Hex(String source) throws Exception;
    /**
     * SHA加密字符串
     *
     * @param source 要加密的字符串
     * @return 返回加密后的字符串
     * @throws Exception 抛出异常
     */
    String sha256Hex(String source) throws Exception;
}

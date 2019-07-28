package com.philosophy.api.codec;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:41
 **/
public interface ICodec {

    String MD5 = "MD5";
    String SHA = "SHA";

    /**
     * MD5/SGA加密字符串
     *
     * @param source 要加密的字符串
     * @return 返回加密后的字符串
     */
    String encrypt(String source);


}

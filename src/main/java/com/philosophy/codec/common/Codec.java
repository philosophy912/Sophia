package com.philosophy.codec.common;

import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:49
 **/
@Setter
public class Codec {
    private static final String MD5 = "MD5";
    private static final String SHA = "SHA";
    /**
     * 加密类型，仅支持MD5和SHA
     */
    private String codecType;

    /**
     * MD5/SGA加密字符串
     *
     * @param source 要加密的字符串
     * @return 返回加密后的字符串
     */
    public String encrypt(String source) {
        switch (codecType) {
            case MD5:
                return DigestUtils.md5Hex(source);
            case SHA:
                return DigestUtils.sha256Hex(source);
            default:
                throw new RuntimeException("codec Type only support MD5 or SHA");
        }
    }
}

package com.philosophy.codec;

import com.philosophy.api.codec.ICodec;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:49
 **/
public class Codec implements ICodec {
    @Override
    public String md5Hex(String source) throws Exception {
        return DigestUtils.md5Hex(source);
    }

    @Override
    public String sha256Hex(String source) throws Exception {
        return DigestUtils.sha256Hex(source);
    }
}

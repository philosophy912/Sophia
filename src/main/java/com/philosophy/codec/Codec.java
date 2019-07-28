package com.philosophy.codec;

import com.philosophy.api.codec.ICodec;
import com.philosophy.exception.LowLevelException;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:49
 **/
@Setter
public class Codec implements ICodec {
    // 加密类型，仅支持MD5和SHA
    private String codecType;

    @Override
    public String encrypt(String source) {
        switch (codecType) {
            case MD5:
                return DigestUtils.md5Hex(source);
            case SHA:
                return DigestUtils.sha256Hex(source);
            default:
                throw new LowLevelException("codec Type only support MD5 or SHA");
        }
    }
}

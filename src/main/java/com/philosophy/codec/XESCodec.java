package com.philosophy.codec;

import com.philosophy.api.codec.IXESCodec;
import com.philosophy.tools.Parse;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:50
 **/
@Setter
public class XESCodec implements IXESCodec {
    private static Logger log = LogManager.getLogger(XESCodec.class);
    // 加密方式
    private String codecType;
    private static final String UTF8 = "UTF-8";


    private int getSize() {
        int size;
        switch (codecType) {
            case AES:
                size = 128;
                break;
            case DES:
                size = 56;
                break;
            case DES3:
                size = 168;
                break;
            default:
                throw new RuntimeException("类型错误");
        }
        return size;
    }

    private SecretKey getSecretKey(String keyStr, int size, String type) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        KeyGenerator keyGenerator = KeyGenerator.getInstance(type);
        keyGenerator.init(size, new SecureRandom(keyStr.getBytes(UTF8)));
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    @Override
    public String encrypt(String source, String keyStr) {
        try {
            int size = getSize();
            SecretKey key = getSecretKey(keyStr, size, codecType);
            byte[] encodeFormat = key.getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(encodeFormat, codecType);
            Cipher cipher = Cipher.getInstance(codecType);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] src = source.getBytes(UTF8);
            byte[] resultByte = cipher.doFinal(src);
            return Parse.toString(resultByte);
        } catch (Exception e) {
            log.error("encrypt str{} failed, Error Message[{}]", source, e.getMessage());
            return source;
        }
    }

    @Override
    public String decrypt(String source, String keyStr) {
        try {
            int size = getSize();
            SecretKey key = getSecretKey(keyStr, size, codecType);
            Cipher cipher = Cipher.getInstance(codecType);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] codec = Parse.toBytes(source);
            byte[] resultByte = cipher.doFinal(codec);
            return new String(resultByte, UTF8);
        } catch (Exception e) {
            log.error("decrypt str{} failed, Error Message[{}]", source, e.getMessage());
            return source;
        }
    }

}

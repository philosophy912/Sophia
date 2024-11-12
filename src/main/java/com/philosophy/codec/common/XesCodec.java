package com.philosophy.codec.common;

import com.philosophy.base.util.ParseUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:50
 **/

@Slf4j
public class XesCodec {
    private static final String AES = "AES";
    private static final String DES3 = "DESede";
    private static final String DES = "DES";
    /**
     * 加密方式
     */
    @Setter
    private String codecType;


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
        keyGenerator.init(size, new SecureRandom(keyStr.getBytes(StandardCharsets.UTF_8)));
        return keyGenerator.generateKey();
    }

    /**
     * 加密字符串（以AES, DES, 3DES)方式加密字符串
     *
     * @param source 要加密的字符串
     * @param keyStr    秘钥字符串
     * @return 加密后的字符串
     */
    public String encrypt(String source, String keyStr) {
        try {
            int size = getSize();
            SecretKey key = getSecretKey(keyStr, size, codecType);
            byte[] encodeFormat = key.getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(encodeFormat, codecType);
            Cipher cipher = Cipher.getInstance(codecType);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] src = source.getBytes(StandardCharsets.UTF_8);
            byte[] resultByte = cipher.doFinal(src);
            return ParseUtils.bytesToString(resultByte);
        } catch (Exception e) {
            log.error("encrypt str{} failed, Error Message[{}]", source, e.getMessage());
            return source;
        }
    }

    /**
     * 解密字符串（以AES, DES, 3DES)方式解密字符串
     *
     * @param source 加密后的字符串
     * @param keyStr    秘钥的字符串
     * @return 解密后的字符串
     */
    public String decrypt(String source, String keyStr) {
        try {
            int size = getSize();
            SecretKey key = getSecretKey(keyStr, size, codecType);
            Cipher cipher = Cipher.getInstance(codecType);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] codec = ParseUtils.stringToBytes(source);
            byte[] resultByte = cipher.doFinal(codec);
            return new String(resultByte, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("decrypt str{} failed, Error Message[{}]", source, e.getMessage());
            return source;
        }
    }

}

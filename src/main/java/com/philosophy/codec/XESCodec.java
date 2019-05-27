package com.philosophy.codec;

import com.philosophy.api.codec.IXESCodec;
import com.philosophy.tools.Parse;

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
public class XESCodec implements IXESCodec {
    private int size = 0;

    /**
     * 根据传入的值生成类型
     */
    private String type;

    public XESCodec(ECodecType codecType) {
        switch (codecType) {
            case AES:
                this.size = 128;
                break;
            case DES:
                this.size = 56;
                break;
            case DES3:
                this.size = 168;
                break;
        }
    }

    private boolean checkSizeAndType() {
        return (size == 0 || type == null);
    }

    private SecretKey getSecretKey(String source, String keyStr) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        KeyGenerator keyGenerator = KeyGenerator.getInstance(type);
        keyGenerator.init(size, new SecureRandom(keyStr.getBytes(UTF8)));
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    @Override
    public String encrypt(String source, String keyStr) throws Exception {
        if (checkSizeAndType()) {
            return source;
        }
        SecretKey key = getSecretKey(source, keyStr);
        byte[] encodeFormat = key.getEncoded();
        SecretKeySpec keySpec = new SecretKeySpec(encodeFormat, type);
        Cipher cipher = Cipher.getInstance(type);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] src = source.getBytes(UTF8);
        byte[] resultByte = cipher.doFinal(src);
        return Parse.toString(resultByte);
    }

    @Override
    public String decrypt(String source, String keyStr) throws Exception {
        if (checkSizeAndType()) {
            return source;
        }
        SecretKey key = getSecretKey(source, keyStr);
        Cipher cipher = Cipher.getInstance(type);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] codec = Parse.toBytes(source);
        byte[] resultByte = cipher.doFinal(codec);
        return new String(resultByte, UTF8);
    }
}

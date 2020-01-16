package com.philosophy.codec.util;

import com.philosophy.codec.api.CodecEnum;
import com.philosophy.codec.common.Codec;
import com.philosophy.codec.common.RsaCodec;
import com.philosophy.codec.common.XesCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

/**
 * @author lizhe
 * @date 2019/10/12:17:12
 */
@Slf4j
public class CodecUtils {

    private static Codec codec = new Codec();
    private static RsaCodec rsaCodec = new RsaCodec();
    private static XesCodec xesCodec = new XesCodec();

    /**
     * 支持MD5和SHA加密
     * <p>加密不成功返回原字符串</p>
     *
     * @param codecEnum 加密方式
     * @param source    要加密的字符串
     * @return 加密后的结果
     */
    public static String encrypt(CodecEnum codecEnum, String source) {
        String result;
        switch (codecEnum) {
            case MD5:
            case SHA:
                codec.setCodecType(codecEnum.getValue());
                result = codec.encrypt(source);
                break;
            default:
                result = source;
                break;
        }
        return result;
    }

    /**
     * 支持DES和3DES和AES加密
     * <p>加密不成功返回原字符串</p>
     *
     * @param codecEnum 加密方式
     * @param source    要加密的字符串
     * @param key       加密的秘钥
     * @return 加密后的结果
     */
    public static String encrypt(CodecEnum codecEnum, String source, String key) {
        String result;
        switch (codecEnum) {
            case DES:
            case DES3:
            case AES:
                xesCodec.setCodecType(codecEnum.getValue());
                result = xesCodec.encrypt(source, key);
                break;
            default:
                result = source;
                break;
        }
        return result;
    }

    /**
     * 支持DES和3DES和AES解密
     * <p>解密不成功返回原字符串</p>
     *
     * @param codecEnum 解密方式
     * @param source    要解密的字符串
     * @param key       解密的秘钥
     * @return 解密后的结果
     */
    public static String decrypt(CodecEnum codecEnum, String source, String key) {
        String result;
        switch (codecEnum) {
            case DES:
            case DES3:
            case AES:
                xesCodec.setCodecType(codecEnum.getValue());
                result = xesCodec.decrypt(source, key);
                break;
            default:
                result = source;
                break;
        }
        return result;
    }

    /**
     * 支持RSA加密
     * <p>加密不成功返回原字符串</p>
     *
     * @param source     要加密的字符串
     * @param publicPath 秘钥文件(支持公钥加密）
     * @return 加密后的字符串
     */
    public static String encrypt(String source, Path publicPath) {
        String result;
        try {
            rsaCodec.readPublicKey(publicPath);
            result = rsaCodec.encrypt(source);
        } catch (IOException | ClassNotFoundException e) {
            log.debug("check param failed, will return source, error [{}]", e.getMessage());
            result = source;
        }
        return result;
    }

    /**
     * 支持RSA解密
     * <p>解密不成功返回原字符串</p>
     *
     * @param source      要加密的字符串
     * @param privatePath 秘钥文件(支持私钥解密）
     * @return 加密后的字符串
     */
    public static String decrypt(String source, Path privatePath) {
        String result;
        try {
            rsaCodec.readPrivateKey(privatePath);
            result = rsaCodec.decrypt(source);
        } catch (IOException | ClassNotFoundException e) {
            log.debug("check param failed, will return source, error [{}]", e.getMessage());
            result = source;
        }
        return result;
    }

    /**
     * 生成RSA的key文件
     *
     * @param privatePath 私钥
     * @param publicPath  公钥
     */
    public static boolean genKey(Path privatePath, Path publicPath) {
        boolean flag = true;
        try {
            rsaCodec.genKey(publicPath, privatePath);
        } catch (NoSuchAlgorithmException | IOException e) {
            log.warn("create key failed, error message is [{}]", e.getMessage());
            flag = false;
        }
        return flag;
    }

}

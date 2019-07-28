package com.philosophy.api.codec;

import com.philosophy.tools.Pair;

import java.nio.file.Path;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:48
 **/
public interface IRSACodec {
    String PUBLIC = "public";
    String PRIVATE = "private";
    /**
     * RSA最大加密明文大小
     **/
    int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     **/
    int MAX_DECRYPT_BLOCK = 128;

    /**
     * 根据单个秘钥文件获取公钥和私钥对象
     *
     * @param path 秘钥文件
     * @return 如果能获取则返回True, 如果不能获取则返回False
     */
    boolean getKey(Path path);

    /**
     * 根据公钥私钥文件获取公钥和私钥对象
     *
     * @param publicPath  公钥文件
     * @param privatePath 私钥文件
     * @return 如果能获取则返回True, 如果不能获取则返回False
     */
    boolean getKey(Path publicPath, Path privatePath);

    /**
     * 保存keyPair到单个文件
     *
     * @param path 要保存RSA Key的文件
     * @return 返回保存后的文件
     */
    Path saveKey(Path path);

    /**
     * 保存单独的Private和Public的Key到不同文件
     *
     * @param publicPath  Public的文件路径
     * @param privatePath Private的文件路径
     * @return 返回<code>Pair<Path, Path></code>， 其中First表示Public Key，Second表示Private Path
     */
    Pair<Path, Path> saveKey(Path publicPath, Path privatePath);

    /**
     * RSA方式加密字符串
     *
     * @param source 要加密的字符串
     * @param method 加密方式，以public key还是以private key方式加密
     * @return 加密后的字符串
     */
    String encrypt(String source, String method);

    /**
     * RSA方式解密字符串
     *
     * @param source 要解密的字符串
     * @param method 解密方式，以public key还是以private key方式加密
     *               <br>
     *               当字符串以private key加密则需要用public key方式解密
     *               当字符串以public key加密则需要用private key方式加密
     * @return 解密后的字符串
     */
    String decrypt(String source, String method);
}

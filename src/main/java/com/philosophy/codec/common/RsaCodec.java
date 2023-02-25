package com.philosophy.codec.common;

import com.philosophy.base.common.Closee;
import com.philosophy.base.util.ParseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:49
 **/
@Slf4j
public class RsaCodec {
    private static final String RSA = "RSA";

    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
    /**
     * RSA最大加密明文大小
     **/
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     **/
    private static final int MAX_DECRYPT_BLOCK = 128;


    /**
     * BASE64二进制数据编码为字符串
     *
     * @param bytes bytes数据
     * @return 字符串
     */
    private String encode(byte[] bytes) {
        return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
    }

    /**
     * BASE64字符串解码为二进制数据
     *
     * @param base64 base64字符串
     * @return bytes数据
     */
    private byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 保存key
     *
     * @param keyPath 路径
     * @param rsaKey  rsakey对象
     * @throws IOException 抛出异常
     */
    private void save(Path keyPath, RSAKey rsaKey) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(keyPath));
        oos.writeObject(rsaKey);
        oos.close();
    }

    /**
     * 初始化chiper
     *
     * @param flag True为加密， False为解密
     * @return chiper对象
     * @throws NoSuchAlgorithmException 异常
     * @throws InvalidKeySpecException  异常
     * @throws NoSuchPaddingException   异常
     * @throws InvalidKeyException      异常
     */
    private Cipher init(boolean flag)
            throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Cipher cipher;
        // 加密
        if (flag) {
            String key = encode(publicKey.getEncoded());
            byte[] keyBytes = decode(key);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);
            // 对数据加密
            cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        } else {
            String key = encode(privateKey.getEncoded());
            byte[] keyBytes = decode(key);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            // 对数据加密
            cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
        }
        return cipher;
    }

    /**
     * chiper加密
     *
     * @param cipher cipher对象chiperInit获取。
     * @param str    加密或者解密字符
     * @param flag   True为加密， False为解密
     * @return 加密后的字符串
     * @throws BadPaddingException       异常
     * @throws IllegalBlockSizeException 异常
     */
    private String codec(Cipher cipher, String str, boolean flag)
            throws BadPaddingException, IllegalBlockSizeException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] data;
        if (flag) {
            data = str.getBytes(StandardCharsets.UTF_8);
        } else {
            data = ParseUtils.stringToBytes(str);
        }
        int inputLen = data.length;
        int offSet = 0;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            codecData(inputLen, offSet, cipher, data, out, flag);
            i++;
            if (flag) {
                offSet = i * MAX_ENCRYPT_BLOCK;
            } else {
                offSet = i * MAX_DECRYPT_BLOCK;
            }
        }
        byte[] codecData = out.toByteArray();
        Closee.close(out);
        if (flag) {
            return ParseUtils.bytesToString(codecData);
        } else {
            return new String(codecData, StandardCharsets.UTF_8);
        }
    }

    /**
     * 提取方法
     *
     * @param inputLen 长度
     * @param offSet   offset长度
     * @param cipher   加密器
     * @param data     数据
     * @param out      out流
     * @param type     true加密，false 解密
     * @throws BadPaddingException       异常
     * @throws IllegalBlockSizeException 异常
     */
    private void codecData(int inputLen, int offSet, Cipher cipher, byte[] data, ByteArrayOutputStream out,
                           boolean type) throws BadPaddingException, IllegalBlockSizeException {
        byte[] cache;
        if (type) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
        } else {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
        }

        out.write(cache, 0, cache.length);
    }

    /**
     * 读取一个秘钥
     *
     * @param path 秘钥路径
     * @param type true公钥false私钥
     */
    private Key readKey(Path path, boolean type) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path));
        Key key;
        if (type) {
            key = (RSAPublicKey) objectInputStream.readObject();
        } else {
            key = (RSAPrivateKey) objectInputStream.readObject();
        }
        Closee.close(objectInputStream);
        return key;
    }

    /**
     * 读取公钥
     *
     * @param path 公钥文件
     */
    public PublicKey readPublicKey(Path path) throws IOException, ClassNotFoundException {
        return (PublicKey)readKey(path, true);
    }

    /**
     * 读取私钥
     *
     * @param path 私钥文件
     */
    public PrivateKey readPrivateKey(Path path) throws IOException, ClassNotFoundException {
        return (PrivateKey)readKey(path, false);
    }

    /**
     * 保存单独的Private和Public的Key到不同文件
     *
     * @param publicPath  Public的文件路径
     * @param privatePath Private的文件路径
     */
    public void genKey(Path publicPath, Path privatePath) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGen;
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        keyPairGen = KeyPairGenerator.getInstance(RSA);
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 保存private key
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        save(privatePath, rsaPrivateKey);
        // 保存public key
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        save(publicPath, rsaPublicKey);
    }

    /**
     * RSA方式加密字符串
     *
     * @param source 要加密的字符串
     * @return 加密后的字符串
     */
    public String encrypt(String source, PublicKey publicKey) {
        this.publicKey = (RSAPublicKey) publicKey;
        String result;
        try {
            Cipher cipher = init(true);
            result = codec(cipher, source, true);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("found some issue when encrypt string, Error Message[{}]", e.getMessage());
            result = source;
        }
        return result;
    }

    /**
     * RSA方式解密字符串
     *
     * @param source 要解密的字符串
     * @return 解密后的字符串
     */
    public String decrypt(String source, PrivateKey privateKey) {
        this.privateKey = (RSAPrivateKey) privateKey;
        String result;
        try {
            Cipher cipher = init(false);
            result = codec(cipher, source, false);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("found some issue when encrypt string, Error Message[{}]", e.getMessage());
            result = source;
        }
        return result;
    }
}

package com.philosophy.codec;

import com.philosophy.api.codec.IRSACodec;
import com.philosophy.tools.Closee;
import com.philosophy.tools.Pair;
import com.philosophy.tools.Parse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 23:49
 **/
public class RSACodec implements IRSACodec {
    private static Logger logger = LogManager.getLogger(RSACodec.class);
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
    private static String RSA = "RSA";

    /**
     * 获取公钥和私钥（一个文件方式）
     *
     * @param keyPath 秘钥路径
     * @return 公钥和私钥
     */
    private KeyPair getKeyPair(Path keyPath) {
        ObjectInputStream oi = null;
        KeyPair kePair = null;
        try {
            oi = new ObjectInputStream(Files.newInputStream(keyPath));
            kePair = (KeyPair) oi.readObject();
        } catch (Exception e) {
            logger.error("read key path[" + keyPath + "] failed. " + e.getMessage());
        } finally {
            Closee.close(oi);
            return kePair;
        }
    }

    /**
     * 获取公钥和私钥（两个文件方式）
     *
     * @param publicPath  公钥路径
     * @param privatePath 私钥路径
     * @return 公钥和私钥
     */
    private Pair<RSAPublicKey, RSAPrivateKey> getRSAKey(Path publicPath, Path privatePath) {
        ObjectInputStream oipub = null;
        ObjectInputStream oipri = null;
        Pair<RSAPublicKey, RSAPrivateKey> pair = null;
        try {
            oipub = new ObjectInputStream(Files.newInputStream(publicPath));
            oipri = new ObjectInputStream(Files.newInputStream(privatePath));
            RSAPublicKey publicKey = (RSAPublicKey) oipub.readObject();
            RSAPrivateKey privateKey = (RSAPrivateKey) oipri.readObject();
            pair = new Pair<>(publicKey, privateKey);
        } catch (Exception e) {
            logger.error("read public path[" + publicPath + "] && private path[" + privatePath + "] failed" + e.getMessage());
        } finally {
            Closee.close(oipub, oipri);
            return pair;
        }
    }

    /**
     * @param bytes
     * @return
     * @Title: encode
     * @Description: BASE64二进制数据编码为字符串
     */
    private String encode(byte[] bytes) throws Exception {
        return new String(Base64.getEncoder().encode(bytes), UTF8);
    }

    /**
     * @param base64
     * @return
     * @Title: decode
     * @Description: BASE64字符串解码为二进制数据
     */
    private byte[] decode(String base64) throws Exception {
        return Base64.getDecoder().decode(base64.getBytes(UTF8));
    }

    /**
     * 保存key
     * @param keyPath 路径
     * @param keyPair keypair对象
     * @throws IOException 抛出异常
     */
    private void save(Path keyPath, KeyPair keyPair) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(keyPath));
        oos.writeObject(keyPair);
        oos.close();
    }

    /**
     * 保存key
     * @param keyPath 路径
     * @param rsaKey rsakey对象
     * @throws IOException 抛出异常
     */
    private void save(Path keyPath, RSAKey rsaKey) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(keyPath));
        oos.writeObject(rsaKey);
        oos.close();
    }

    /**
     * 删掉秘钥文件
     *
     * @param paths 文件s
     * @return 如果都删除了为True，如果删除失败则为False
     */
    private boolean deleteKey(Path... paths) {
        boolean flag = true;
        for (Path p : paths) {
            if (Files.exists(p)) {
                try {
                    Files.delete(p);
                    flag = flag && true;
                } catch (IOException e) {
                    logger.error("delete path[" + p + "] failed" + e.getMessage());
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * 初始化chiper
     *
     * @param method 方式，只支持PUBLIC和PRIVATE
     * @param flag   True为加密， False为解密
     * @return chiper对象
     * @throws Exception 抛出异常
     */
    private Cipher chiperInit(ECodecType method, boolean flag) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        Cipher cipher = null;
        if (ECodecType.PUBLIC == method) {
            String key = encode(publicKey.getEncoded());
            byte[] keyBytes = decode(key);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            // 对数据加密
            cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            if (flag) {
                cipher.init(Cipher.ENCRYPT_MODE, publicK);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, publicK);
            }
        } else if (ECodecType.PRIVATE == method) {
            String key = encode(privateKey.getEncoded());
            byte[] keyBytes = decode(key);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            if (flag) {
                cipher.init(Cipher.ENCRYPT_MODE, privateK);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, privateK);
            }
        }
        return cipher;
    }

    /**
     * chiper加密
     * @param cipher cipher对象chiperInit获取。
     * @param str 加密或者解密字符
     * @param flag True为加密， False为解密
     * @return
     * @throws Exception
     */
    private String chiperEncrypt(Cipher cipher, String str, boolean flag) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] data;
        if (flag) {
            data = str.getBytes(UTF8);
        } else {
            data = Parse.toBytes(str);
        }
        int inputLen = data.length;
        int offSet = 0;
        int i = 0;
        if (cipher != null) {
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (flag) {
                    codecData(inputLen, offSet, cipher, data, out, true);
                } else {
                    codecData(inputLen, offSet, cipher, data, out, false);
                }
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] codecData = out.toByteArray();
            Closee.close(out);
            String result;
            if (flag) {
                result = Parse.toString(codecData);
            } else {
                result = new String(codecData, UTF8);
            }
            return result;
        }
        return str;
    }

    /**
     * 提取方法
     * @param inputLen
     * @param offSet
     * @param cipher
     * @param data
     * @param out
     * @param type true加密，false 解密
     * @throws Exception
     */
    private void codecData(int inputLen, int offSet, Cipher cipher, byte[] data, ByteArrayOutputStream out, boolean type) throws Exception{
        byte[] cache;
        if(type){
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
        }else{
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
        }

        out.write(cache, 0, cache.length);
    }

    /**
     * 加密/解密后的字符串
     * @param source 要加密/解密的字符串
     * @param method PRIVATE或者PUBLIC
     * @param flag  True为加密， False为解密
     * @return 加密/解密后的字符串
     */
    private String getString(String source, ECodecType method, boolean flag) {
        String str = null;
        Cipher cipher;
        try {
            if(publicKey==null || privateKey ==null){
                return source;
            }
            cipher = chiperInit(method, flag);
            str = chiperEncrypt(cipher, source, flag);
        } catch (Exception e) {
            logger.error("found some issue when encrypt string" + e.getMessage());
            str = source;
        }finally {
            return str;
        }
    }

    @Override
    public boolean getKey(Path path) {
        KeyPair keyPair = getKeyPair(path);
        publicKey = (RSAPublicKey) keyPair.getPublic();
        privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return !(publicKey == null || privateKey == null);
    }

    @Override
    public boolean getKey(Path publicPath, Path privatePath) {
        Pair<RSAPublicKey, RSAPrivateKey> pair = getRSAKey(publicPath, privatePath);
        publicKey = pair.getFst();
        privateKey = pair.getSnd();
        return !(publicKey == null || privateKey == null);
    }

    @Override
    public Path saveKey(Path path) {
        if (!deleteKey(path)) {
            return null;
        }
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(RSA);
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            save(path, keyPair);
        } catch (Exception e) {
            logger.error("generator key failed" + e.getMessage());
            return null;
        }
        return path;
    }

    @Override
    public Pair<Path, Path> saveKey(Path publicPath, Path privatePath) {
        Pair<Path, Path> pair = null;
        if (!deleteKey(publicPath, privatePath)) {
            return pair;
        }
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(RSA);
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 保存private key
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            save(privatePath, rsaPrivateKey);
            // 保存public key
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            save(publicPath, rsaPublicKey);
            pair = new Pair<>(publicPath, privatePath);
        } catch (Exception e) {
            logger.error("generator key failed" + e.getMessage());
        } finally {
            return pair;
        }
    }

    @Override
    public String encrypt(String source, ECodecType method) {
        return getString(source, method, true);
    }

    @Override
    public String decrypt(String source, ECodecType method) {
        return getString(source, method,false);
    }
}

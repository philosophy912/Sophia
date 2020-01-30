package com.philosophy.codec.util;

import com.philosophy.BaseTestUtils;
import com.philosophy.base.util.FilesUtils;
import com.philosophy.codec.api.CodecEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class CodecUtilsTest {
    private static final String RESOURCES = BaseTestUtils.getResourceFolder();
    private String source;
    private String key;
    private Path publicKey;
    private Path privateKey;
    private CodecUtils codecUtils;

    @BeforeEach
    void setUp() throws IOException, NoSuchAlgorithmException {
        codecUtils = new CodecUtils();
        source = "totti_912@sina.com";
        key = "test";
        publicKey = Paths.get(RESOURCES + "\\public.key");
        privateKey = Paths.get(RESOURCES + "\\private.key");
        FilesUtils.deleteFiles(privateKey, publicKey);
        codecUtils.genKey(privateKey, publicKey);
    }

    @AfterEach
    void tearDown() throws IOException {
        FilesUtils.deleteFiles(publicKey, privateKey);

    }

    @Test
    void encrypt() {
        String result = codecUtils.encrypt(CodecEnum.MD5, source);
        assertNotEquals(source, result);
        String result2 = codecUtils.encrypt(CodecEnum.SHA, source);
        assertNotEquals(source, result2);
        assertNotEquals(result, result2);
    }

    @Test
    void testEncrypt() {
        String result1 = codecUtils.encrypt(CodecEnum.AES, source, key);
        assertNotEquals(source, result1);
        String result2 = codecUtils.encrypt(CodecEnum.DES, source, key);
        assertNotEquals(source, result2);
        String result3 = codecUtils.encrypt(CodecEnum.DES3, source, key);
        assertNotEquals(source, result3);
    }

    @Test
    void decrypt() {
        String result1 = codecUtils.encrypt(CodecEnum.AES, source, key);
        String source1 = codecUtils.decrypt(CodecEnum.AES, result1, key);
        assertEquals(source, source1);
        String result2 = codecUtils.encrypt(CodecEnum.DES, source, key);
        String source2 = codecUtils.decrypt(CodecEnum.DES, result2, key);
        assertEquals(source, source2);
        String result3 = codecUtils.encrypt(CodecEnum.DES3, source, key);
        String source3 = codecUtils.decrypt(CodecEnum.DES3, result3, key);
        assertEquals(source, source3);
    }

    @Test
    void testEncrypt1() {
        String result1 = codecUtils.encrypt(source, publicKey);
        assertNotEquals(source, result1);
    }

    @Test
    void testDecrypt() {
        String target = codecUtils.encrypt(source, publicKey);
        String origin = codecUtils.decrypt(target, privateKey);
        assertEquals(origin, source);
    }
}
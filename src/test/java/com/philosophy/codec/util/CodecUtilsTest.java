package com.philosophy.codec.util;

import com.philosophy.BaseTestUtils;
import com.philosophy.base.util.FilesUtils;
import com.philosophy.codec.api.CodecEnum;
import com.philosophy.codec.common.RsaCodec;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class CodecUtilsTest {
    private static final String RESOURCES = BaseTestUtils.getResourceFolder();
    private String source;
    private String key;
    private Path publicKey;
    private Path privateKey;

    @BeforeEach
    void setUp() throws IOException {
        source = "totti_912@sina.com";
        key = "test";
        publicKey = Paths.get(RESOURCES + "\\public.key");
        privateKey = Paths.get(RESOURCES + "\\private.key");
        FilesUtils.deleteFiles(privateKey, publicKey);
        CodecUtils.genKey(privateKey, publicKey);
    }

    @AfterEach
    void tearDown() throws IOException {
        FilesUtils.deleteFiles(publicKey, privateKey);

    }

    @Test
    void encrypt() {
        String result = CodecUtils.encrypt(CodecEnum.MD5, source);
        assertNotEquals(source, result);
        String result2 = CodecUtils.encrypt(CodecEnum.SHA, source);
        assertNotEquals(source, result2);
        assertNotEquals(result, result2);
    }

    @Test
    void testEncrypt() {
        String result1 = CodecUtils.encrypt(CodecEnum.AES, source, key);
        assertNotEquals(source, result1);
        String result2 = CodecUtils.encrypt(CodecEnum.DES, source, key);
        assertNotEquals(source, result2);
        String result3 = CodecUtils.encrypt(CodecEnum.DES3, source, key);
        assertNotEquals(source, result3);
    }

    @Test
    void decrypt() {
        String result1 = CodecUtils.encrypt(CodecEnum.AES, source, key);
        String source1 = CodecUtils.decrypt(CodecEnum.AES, result1, key);
        assertEquals(source, source1);
        String result2 = CodecUtils.encrypt(CodecEnum.DES, source, key);
        String source2 = CodecUtils.decrypt(CodecEnum.DES, result2, key);
        assertEquals(source, source2);
        String result3 = CodecUtils.encrypt(CodecEnum.DES3, source, key);
        String source3 = CodecUtils.decrypt(CodecEnum.DES3, result3, key);
        assertEquals(source, source3);
    }

    @Test
    void testEncrypt1() {
        String result1 = CodecUtils.encrypt(source, publicKey);
        assertNotEquals(source, result1);
    }

    @Test
    void testDecrypt() {
        String target = CodecUtils.encrypt(source, publicKey);
        String origin = CodecUtils.decrypt(target, privateKey);
        assertEquals(origin, source);
    }
}
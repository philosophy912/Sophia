package com.philosophy.character.types;

import com.philosophy.character.api.ICharGen;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 22:51
 **/
@Slf4j
public final class Chinese implements ICharGen {

    private static final String GBK = "GBK";

    private static String initChinese() {
        Random random = new Random();
        // 获取高位值
        int highPosition = (176 + Math.abs(random.nextInt(39)));
        // 获取低位值
        int lowPosition = (161 + Math.abs(random.nextInt(93)));
        byte[] b = new byte[2];
        b[0] = Integer.valueOf(highPosition).byteValue();
        b[1] = Integer.valueOf(lowPosition).byteValue();
        try {
            return new String(b, GBK);
        } catch (UnsupportedEncodingException e) {
            log.error("Error Message[{}]", e.getMessage());
            throw new RuntimeException("Error Message " + e.getMessage());
        }
    }

    @Override
    public String generate(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String chinese = initChinese();
            sb.append(chinese);
        }
        return sb.toString();
    }
}

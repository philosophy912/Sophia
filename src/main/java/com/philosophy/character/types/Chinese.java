package com.philosophy.character.types;

import com.philosophy.api.character.ICharacterGenerator;
import com.philosophy.exception.LowLevelException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 22:51
 **/
public final class Chinese implements ICharacterGenerator {
    private static Logger log = LogManager.getLogger(Chinese.class);

    private static final String GBK = "GBK";

    private static String initChinese() {
        Random random = new Random();
        int hightPos = (176 + Math.abs(random.nextInt(39)));// 获取高位值
        int lowPos = (161 + Math.abs(random.nextInt(93)));// 获取低位值
        byte[] b = new byte[2];
        b[0] = Integer.valueOf(hightPos).byteValue();
        b[1] = Integer.valueOf(lowPos).byteValue();
        try {
            return new String(b, GBK);
        } catch (UnsupportedEncodingException e) {
            log.error("Error Message[{}]", e.getMessage());
            throw new LowLevelException("Error Message " + e.getMessage());
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

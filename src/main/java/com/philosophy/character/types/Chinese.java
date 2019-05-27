package com.philosophy.character.types;

import com.philosophy.api.character.ICharacterCreater;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 22:51
 **/
public final class Chinese implements ICharacterCreater {
    private static Logger logger = LogManager.getLogger(Chinese.class);


    private static String initChinese() {
        Random random = new Random();
        int hightPos = (176 + Math.abs(random.nextInt(39)));// 获取高位值
        int lowPos = (161 + Math.abs(random.nextInt(93)));// 获取低位值
        byte[] b = new byte[2];
        b[0] = Integer.valueOf(hightPos).byteValue();
        b[1] = Integer.valueOf(lowPos).byteValue();
        try {
            return new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public String create(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String chinese = initChinese();
            sb.append(chinese);
        }
        return sb.toString();
    }
}

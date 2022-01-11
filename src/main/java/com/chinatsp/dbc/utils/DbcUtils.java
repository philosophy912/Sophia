package com.chinatsp.dbc.utils;

import lombok.extern.slf4j.Slf4j;

import static com.chinatsp.dbc.api.IConstant.BLANK;
import static com.chinatsp.dbc.api.IConstant.NULL;
import static com.chinatsp.dbc.api.IConstant.QUOTATION;
import static com.chinatsp.dbc.api.IConstant.SEMICOLON;
import static com.chinatsp.dbc.api.IConstant.TRIM_BLANK;
import static com.chinatsp.dbc.api.IConstant.VAL;

/**
 * @author lizhe
 * @date 2020/5/25 13:55
 **/
@Slf4j
public class DbcUtils {

    public static boolean judgeContent(String content, String... strings) {
        for (String flag : strings) {
            if (content.contains(flag)) {
                return true;
            }
        }
        return false;
    }

    public static String getContent(String content, String type) {
        return content.replaceAll(type, NULL)
                .replaceAll(TRIM_BLANK, BLANK)
                .replaceAll(SEMICOLON, NULL)
                .trim();
    }

    public static String getValContent(String content) {
        return content.replaceAll(VAL, NULL)
                .replaceAll(TRIM_BLANK, BLANK)
                .replaceAll(BLANK + QUOTATION + BLANK, BLANK + QUOTATION)
                .trim();
    }
}

package com.chinatsp.code.utils;

import org.springframework.stereotype.Component;

/**
 * @author lizhe
 * @date 2020/9/7 13:22
 **/
@Component
public class CheckUtils {
    /**
     * 函数名检查是否正确（python）
     *
     * @param name 函数名
     * @return true：正确 false：不正确
     */
    public boolean isFunctionCorrect(String name) {
        int max = 255;
        String regex = "[a-zA-Z]+[a-zA-Z0-9_]*";
        boolean isMatch = name.matches(regex);
        if (name.length() > max) {
            return false;
        } else {
            return isMatch;
        }
    }
}

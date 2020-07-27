package com.philosophy.character.types;

import com.philosophy.base.util.NumericUtils;

/**
 * @author lizhe
 * @since V1.0.0 2019/10/11 22:07
 **/
public class BaseType {
    private String generate(char[] chars) {
        int index = NumericUtils.randomInteger(0, chars.length - 1);
        return String.valueOf(chars[index]);
    }

    protected String append(char[] chars, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String str = generate(chars);
            sb.append(str);
        }
        return sb.toString();
    }
}

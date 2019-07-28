package com.philosophy.character.types;

import com.philosophy.tools.Numeric;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 22:47
 **/
public class Character {

    private String generate(char[] chars) {
        int index = (int) Numeric.random(0, chars.length - 1);
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

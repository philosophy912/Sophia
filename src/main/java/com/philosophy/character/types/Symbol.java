package com.philosophy.character.types;

import com.philosophy.api.character.ICharacterCreater;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 22:51
 **/
public final class Symbol extends Character implements ICharacterCreater {
    private static final char[] chars = {' ', '!', '"', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':',
            ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};


    @Override
    public String create(int length) {
        return append(chars, length);
    }
}

package com.philosophy.character.types;

import com.philosophy.api.character.ICharacterGenerator;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 22:51
 **/
public final class Symbol extends Character implements ICharacterGenerator {
    private static final char[] chars = {' ', '!', '"', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':',
            ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};


    @Override
    public String generate(int length) {
        return append(chars, length);
    }
}

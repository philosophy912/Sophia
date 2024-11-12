package com.philosophy.character.types;

import com.philosophy.character.api.ICharGen;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 22:51
 **/
public final class Symbol extends BaseType implements ICharGen {
    private static final char[] CHARS = {' ', '!', '"', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':',
            ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~'};


    @Override
    public String generate(int length) {
        return append(CHARS, length);
    }
}

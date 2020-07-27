package com.philosophy.character.types;

import com.philosophy.character.api.ICharGen;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 22:50
 **/
public final class Number extends BaseType implements ICharGen {
    private static final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};


    @Override
    public String generate(int size) {
        return append(CHARS, size);
    }
}

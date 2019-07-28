package com.philosophy.character.types;

import com.philosophy.api.character.ICharacterGenerator;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 22:50
 **/
public final class Number extends Character implements ICharacterGenerator {
    private static final char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};


    @Override
    public String generate(int size) {
        return append(chars, size);
    }
}

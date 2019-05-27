package com.philosophy.character.types;

import com.philosophy.api.character.ICharacterCreater;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 22:43
 **/
public final class English extends Character implements ICharacterCreater {
    private static final char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    @Override
    public String create(int length) {
        return append(chars, length);
    }
}

package com.philosophy.api.character;


import com.philosophy.api.IConst;
import com.philosophy.character.ECharacterType;

public interface ICharacterFactory extends IConst {


    /**
     * create character by type and size
     *
     * @param type
     * @param size
     * @return
     */
    String create(ECharacterType type, int size);

}

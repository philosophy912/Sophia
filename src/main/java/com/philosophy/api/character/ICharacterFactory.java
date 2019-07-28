package com.philosophy.api.character;


public interface ICharacterFactory {

    /**
     * create character by type and size
     *
     * @param type
     * @param size
     * @return
     */
    String create(ECharacterType type, int size);

}

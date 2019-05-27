package com.philosophy.character.factory;

import com.philosophy.api.character.ICharacterCreater;
import com.philosophy.api.character.ICharacterFactory;
import com.philosophy.character.ECharacterType;
import com.philosophy.character.types.Chinese;
import com.philosophy.character.types.English;
import com.philosophy.character.types.Number;
import com.philosophy.character.types.Symbol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SignleCharacter implements ICharacterFactory {
    private Logger logger = LogManager.getLogger(SignleCharacter.class);
    private ICharacterCreater creater;

    @Override
    public String create(ECharacterType type, int size) {
        switch (type) {
            case ENGLISH:
                creater = new English();
                break;
            case CHINESE:
                creater = new Chinese();
                break;
            case SYMBOL:
                creater = new Symbol();
                break;
            case NUMBER:
                creater = new Number();
                break;
            default:
                logger.error("type(" + type + ") is incorrect");
        }
        return creater.create(size);
    }
}

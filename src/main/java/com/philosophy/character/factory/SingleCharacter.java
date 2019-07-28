package com.philosophy.character.factory;

import com.philosophy.api.character.ICharacterGenerator;
import com.philosophy.api.character.ICharacterFactory;
import com.philosophy.api.character.ECharacterType;
import com.philosophy.character.types.Chinese;
import com.philosophy.character.types.English;
import com.philosophy.character.types.Number;
import com.philosophy.character.types.Symbol;
import com.philosophy.exception.LowLevelException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SingleCharacter implements ICharacterFactory {
    private static Logger log = LogManager.getLogger(SingleCharacter.class);
    private ICharacterGenerator generator;

    @Override
    public String create(ECharacterType type, int size) {
        switch (type) {
            case ENGLISH:
                generator = new English();
                break;
            case CHINESE:
                generator = new Chinese();
                break;
            case SYMBOL:
                generator = new Symbol();
                break;
            case NUMBER:
                generator = new Number();
                break;
            default:
                log.error("type({}) is incorrect", type);
                throw new LowLevelException("type[" + type + "] is incorrect");
        }
        return generator.generate(size);
    }
}

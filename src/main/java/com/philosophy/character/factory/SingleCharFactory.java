package com.philosophy.character.factory;

import com.philosophy.character.api.CharEnum;
import com.philosophy.character.api.ICharFactory;
import com.philosophy.character.api.ICharGen;
import com.philosophy.character.types.Chinese;
import com.philosophy.character.types.English;
import com.philosophy.character.types.Number;
import com.philosophy.character.types.Symbol;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lizhe
 */
@Slf4j
public class SingleCharFactory implements ICharFactory {

    @Override
    public String create(CharEnum type, int size) {
        ICharGen generator;
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
                throw new RuntimeException("type[" + type + "] is incorrect");
        }
        return generator.generate(size);
    }
}

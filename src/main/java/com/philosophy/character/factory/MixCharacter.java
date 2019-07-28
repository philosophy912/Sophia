package com.philosophy.character.factory;

import com.philosophy.api.character.ICharacterFactory;
import com.philosophy.api.character.ECharacterType;
import com.philosophy.exception.LowLevelException;
import com.philosophy.tools.Numeric;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class MixCharacter implements ICharacterFactory {
    private static Logger log = LogManager.getLogger(MixCharacter.class);
    /**
     * @param size
     * @param types
     * @return
     * @Title: setPool
     * @Description: 建立字符库池
     */
    private List<String> setPool(int size, ECharacterType... types)  {
        ICharacterFactory strategy = new SingleCharacter();
        List<String> pools = new ArrayList<>();
        for (ECharacterType t : types) {
            for (int i = 0; i < size * 100; i++) {
                pools.add(strategy.create(t, 1));
            }
        }
        return pools;
    }

    /**
     * @param size
     * @param types
     * @return
     * @Title: getResultFromPool
     * @Description: 从字符库池中取最终结果
     */
    private String getResultFromPool(int size, ECharacterType... types) {
        StringBuilder sb = new StringBuilder();
        List<String> pools = setPool(size, types);
        for (int i = 0; i < size; i++) {
            int index = (int) Numeric.random(0, pools.size() - 1);
            sb.append(pools.get(index));
        }
        return sb.toString();
    }

    @Override
    public String create(ECharacterType type, int size) {
        switch (type) {
            case CHINESE_ENGLISH:
                return getResultFromPool(size, ECharacterType.CHINESE, ECharacterType.ENGLISH);
            case CHINESE_SYMBOL:
                return getResultFromPool(size, ECharacterType.CHINESE, ECharacterType.SYMBOL);
            case CHINESE_NUMBER:
                return getResultFromPool(size, ECharacterType.CHINESE, ECharacterType.NUMBER);
            case ENGLISH_SYMBOL:
                return getResultFromPool(size, ECharacterType.ENGLISH, ECharacterType.SYMBOL);
            case ENGLISH_NUMBER:
                return getResultFromPool(size, ECharacterType.ENGLISH, ECharacterType.NUMBER);
            case SYMBOL_NUMBER:
                return getResultFromPool(size, ECharacterType.SYMBOL, ECharacterType.NUMBER);
            case CHINESE_ENGLISH_SYMBOL:
                return getResultFromPool(size, ECharacterType.CHINESE, ECharacterType.ENGLISH, ECharacterType.SYMBOL);
            case CHINESE_ENGLISH_NUMBER:
                return getResultFromPool(size, ECharacterType.CHINESE, ECharacterType.ENGLISH, ECharacterType.NUMBER);
            case CHINESE_NUMBER_SYMBOL:
                return getResultFromPool(size, ECharacterType.CHINESE, ECharacterType.NUMBER, ECharacterType.SYMBOL);
            case ENGLISH_SYMBOL_NUMBER:
                return getResultFromPool(size, ECharacterType.ENGLISH, ECharacterType.SYMBOL, ECharacterType.NUMBER);
            case CHINESE_ENGLISH_SYMBOL_NUMBER:
                return getResultFromPool(size, ECharacterType.CHINESE, ECharacterType.ENGLISH, ECharacterType.SYMBOL, ECharacterType.NUMBER);
            default:
                log.error("type({}) is incorrect", type);
                throw new LowLevelException("type[" + type + "] is incorrect");
        }
    }
}

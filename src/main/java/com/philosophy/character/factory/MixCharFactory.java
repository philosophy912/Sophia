package com.philosophy.character.factory;

import com.philosophy.base.util.NumericUtils;
import com.philosophy.character.api.CharEnum;
import com.philosophy.character.api.ICharFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhe
 */
@Slf4j
public class MixCharFactory implements ICharFactory {

    /**
     * 建立字符库池
     *
     * @param size  字符库池容量
     * @param types 字符库池类型
     * @return 字符库池
     */
    private List<String> setPool(int size, CharEnum... types) {
        ICharFactory strategy = new SingleCharFactory();
        List<String> pools = new ArrayList<>();
        for (CharEnum t : types) {
            int poolSize = 100;
            for (int i = 0; i < size * poolSize; i++) {
                pools.add(strategy.create(t, 1));
            }
        }
        return pools;
    }


    /**
     * 从字符库池中取最终结果
     *
     * @param size  字符库池容量
     * @param types 字符库池类型
     * @return 字符串
     */
    private String getResultFromPool(int size, CharEnum... types) {
        StringBuilder sb = new StringBuilder();
        List<String> pools = setPool(size, types);
        for (int i = 0; i < size; i++) {
            int index = NumericUtils.randomInteger(0, pools.size() - 1);
            sb.append(pools.get(index));
        }
        return sb.toString();
    }

    @Override
    public String create(CharEnum type, int size) {
        switch (type) {
            case CHINESE_ENGLISH:
                return getResultFromPool(size, CharEnum.CHINESE, CharEnum.ENGLISH);
            case CHINESE_SYMBOL:
                return getResultFromPool(size, CharEnum.CHINESE, CharEnum.SYMBOL);
            case CHINESE_NUMBER:
                return getResultFromPool(size, CharEnum.CHINESE, CharEnum.NUMBER);
            case ENGLISH_SYMBOL:
                return getResultFromPool(size, CharEnum.ENGLISH, CharEnum.SYMBOL);
            case ENGLISH_NUMBER:
                return getResultFromPool(size, CharEnum.ENGLISH, CharEnum.NUMBER);
            case SYMBOL_NUMBER:
                return getResultFromPool(size, CharEnum.SYMBOL, CharEnum.NUMBER);
            case CHINESE_ENGLISH_SYMBOL:
                return getResultFromPool(size, CharEnum.CHINESE, CharEnum.ENGLISH, CharEnum.SYMBOL);
            case CHINESE_ENGLISH_NUMBER:
                return getResultFromPool(size, CharEnum.CHINESE, CharEnum.ENGLISH, CharEnum.NUMBER);
            case CHINESE_NUMBER_SYMBOL:
                return getResultFromPool(size, CharEnum.CHINESE, CharEnum.NUMBER, CharEnum.SYMBOL);
            case ENGLISH_SYMBOL_NUMBER:
                return getResultFromPool(size, CharEnum.ENGLISH, CharEnum.SYMBOL, CharEnum.NUMBER);
            case CHINESE_ENGLISH_SYMBOL_NUMBER:
                return getResultFromPool(size, CharEnum.CHINESE, CharEnum.ENGLISH,
                        CharEnum.SYMBOL, CharEnum.NUMBER);
            default:
                log.error("type({}) is incorrect", type);
                throw new RuntimeException("type[" + type + "] is incorrect");
        }
    }

}

package com.chinatsp.code.utils;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.collection.Element;
import com.chinatsp.code.enumeration.AndroidLocatorTypeEnum;
import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Triple;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/9/28 16:26
 **/
@Component
public class WriterUtils {

    public List<Triple<String, String, List<Pair<String, String>>>> convertElements(List<BaseEntity> entities) {
        List<Triple<String, String, List<Pair<String, String>>>> triples = new ArrayList<>();
        for (BaseEntity entity : entities) {
            Element element = (Element) entity;
            String name = element.getName();
            String comment = element.getComment();
            List<Pair<String, String>> pairs = new ArrayList<>();
            Map<AndroidLocatorTypeEnum, String> locators = element.getLocators();
            for (Map.Entry<AndroidLocatorTypeEnum, String> entry : locators.entrySet()) {
                String type = entry.getKey().getValue();
                String value = entry.getValue();
                pairs.add(new Pair<>(type, value));
            }
            triples.add(new Triple<>(name, comment, pairs));
        }
        return triples;
    }


}

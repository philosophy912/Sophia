package com.chinatsp.automotive.checker.impl.collection;

import com.chinatsp.automotive.checker.api.BaseChecker;
import com.chinatsp.automotive.checker.api.IChecker;
import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.entity.collection.Element;
import com.chinatsp.automotive.enumeration.ConfigureTypeEnum;
import com.chinatsp.dbc.entity.Message;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ElementChecker extends BaseChecker implements IChecker {


    @Override
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Map<ConfigureTypeEnum, String> configure) {
        List<BaseEntity> entities = getEntity(map, Element.class);
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            Element element = (Element) entities.get(i);
            String name = element.getClass().getSimpleName();
            // 检查名字是否符合python命名规范
            checkUtils.checkPythonFunction(element.getName(), index, name);
        }
        // 检查函数名是否有重名
        checkUtils.findDuplicate(entities, Element.class.getSimpleName());
    }
}

package com.chinatsp.code.checker.impl.action;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IChecker;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.ElementAction;
import com.chinatsp.code.entity.collection.Element;
import com.chinatsp.dbc.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ElementActionChecker extends BaseChecker implements IChecker {


    @Override
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Configure configure) {
        List<BaseEntity> elements = getEntity(map, Element.class);
        List<BaseEntity> entities = getEntity(map, ElementAction.class);
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            ElementAction elementAction = (ElementAction) entities.get(i);
            String name = elementAction.getClass().getSimpleName();
            // 检查名字是否符合python命名规范
            checkUtils.checkPythonFunction(elementAction.getName(), index, name);
            // 检查element名字是否存在于Sheet(Element)中
            checkUtils.checkElementsExist(elementAction.getElements(), index, name, elements);
            // 检查element操作是否正确
            checkUtils.checkElementOperation(elementAction, index, name);
        }
        // 检查函数名是否有重名
        checkUtils.findDuplicate(entities, ElementAction.class.getSimpleName());
    }
}

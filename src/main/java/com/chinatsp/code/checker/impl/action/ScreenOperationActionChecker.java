package com.chinatsp.code.checker.impl.action;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IChecker;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.ScreenOperationAction;
import com.chinatsp.dbc.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ScreenOperationActionChecker extends BaseChecker implements IChecker {


    @Override
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Configure configure) {
        List<BaseEntity> entities = getEntity(map, ScreenOperationAction.class);
        int maxWidth = configure.getMaxWidth();
        int maxHeight = configure.getMaxHeight();
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            ScreenOperationAction screenOperationAction = (ScreenOperationAction) entities.get(i);
            String name = screenOperationAction.getClass().getSimpleName();
            // 检查名字是否符合python命名规范
            checkUtils.checkPythonFunction(screenOperationAction.getName(), index, name);
            // 检查屏幕的高宽
            checkUtils.checkClickPoints(screenOperationAction.getPoints(), index, name, maxWidth, maxHeight);
        }
        // 检查函数名是否有重名
        checkUtils.findDuplicate(entities, ScreenOperationAction.class.getSimpleName());
    }


}

package com.chinatsp.code.checker.impl.action;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IChecker;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.ScreenShotAction;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.character.util.CharUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ScreenShotActionChecker extends BaseChecker implements IChecker {


    @Override
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Configure configure) {
        List<BaseEntity> entities = getEntity(map, ScreenShotAction.class);
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            ScreenShotAction screenShotAction = (ScreenShotAction) entities.get(i);
            String name = screenShotAction.getClass().getName();
            // 检查名字是否符合python命名规范
            checkUtils.checkPythonFunction(screenShotAction.getName(), index, name);
            // 检查截图名是否符合要求
            checkUtils.checkScreenshotName(screenShotAction.getImageName(), index, name);
        }
    }
}

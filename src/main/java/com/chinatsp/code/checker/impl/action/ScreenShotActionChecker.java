package com.chinatsp.code.checker.impl.action;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IChecker;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.ScreenOpsAction;
import com.chinatsp.code.entity.actions.ScreenShotAction;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.dbc.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ScreenShotActionChecker extends BaseChecker implements IChecker {


    @Override
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Map<ConfigureTypeEnum, String[]> configure) {
        List<BaseEntity> entities = getEntity(map, ScreenShotAction.class);
        int maxDisplay = Integer.parseInt(configure.get(ConfigureTypeEnum.MAX_DISPLAY)[0]);
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            ScreenShotAction screenShotAction = (ScreenShotAction) entities.get(i);
            String name = screenShotAction.getClass().getSimpleName();
            // 检查名字是否符合python命名规范
            checkUtils.checkPythonFunction(screenShotAction.getName(), index, name);
            // 检查截图名是否符合要求
            checkUtils.checkScreenshotName(screenShotAction.getImageName(), index, name);
            // 检查屏幕是否符合要求
            checkUtils.checkDisplay(screenShotAction.getDisplayId(), index, name, maxDisplay);
        }
        // 检查函数名是否有重名
        checkUtils.findDuplicate(entities, ScreenShotAction.class.getSimpleName());
        // 检查截图名称是否有重名
        checkUtils.findImageNameDuplicate(entities, ScreenOpsAction.class.getSimpleName());
    }
}

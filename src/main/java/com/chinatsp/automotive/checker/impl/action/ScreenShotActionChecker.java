package com.chinatsp.automotive.checker.impl.action;

import com.chinatsp.automotive.checker.api.BaseChecker;
import com.chinatsp.automotive.checker.api.IChecker;
import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.entity.actions.ScreenOpsAction;
import com.chinatsp.automotive.entity.actions.ScreenShotAction;
import com.chinatsp.automotive.enumeration.ConfigureTypeEnum;
import com.chinatsp.dbc.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ScreenShotActionChecker extends BaseChecker implements IChecker {


    @Override
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Map<ConfigureTypeEnum, String> configure) {
        List<BaseEntity> entities = getEntity(map, ScreenShotAction.class);
        int maxAndroidDisplay = -1;
        int maxQnxDisplay = -1;
        try{
            maxAndroidDisplay = Integer.parseInt(configure.get(ConfigureTypeEnum.MAX_ANDROID_DISPLAY));
        }catch (NumberFormatException e){
            log.debug("no number input error ===> "+ e.getMessage());
        }
        try{
            maxQnxDisplay = Integer.parseInt(configure.get(ConfigureTypeEnum.MAX_QNX_DISPLAY));
        }catch (NumberFormatException e){
            log.debug("no number input error ===> "+ e.getMessage());
        }

        log.debug("maxAndroidDisplay = [{}] and maxQnxDisplay = [{}]", maxAndroidDisplay, maxQnxDisplay);
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            ScreenShotAction screenShotAction = (ScreenShotAction) entities.get(i);
            String name = screenShotAction.getClass().getSimpleName();
            // 检查名字是否符合python命名规范
            checkUtils.checkPythonFunction(screenShotAction.getName(), index, name);
            // 检查截图名是否符合要求
            checkUtils.checkScreenshotName(screenShotAction.getImageName(), index, name);
            // 检查屏幕是否符合要求
            checkUtils.checkDisplay(screenShotAction, index, name, maxAndroidDisplay, maxQnxDisplay);
        }
        // 检查函数名是否有重名
        checkUtils.findDuplicate(entities, ScreenShotAction.class.getSimpleName());
        // 检查截图名称是否有重名
        checkUtils.findImageNameDuplicate(entities, ScreenOpsAction.class.getSimpleName());
    }
}

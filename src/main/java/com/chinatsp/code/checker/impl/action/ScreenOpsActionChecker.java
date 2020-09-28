package com.chinatsp.code.checker.impl.action;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IChecker;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.ScreenOpsAction;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.dbc.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ScreenOpsActionChecker extends BaseChecker implements IChecker {


    @Override
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Map<ConfigureTypeEnum, String> configure) {
        List<BaseEntity> entities = getEntity(map, ScreenOpsAction.class);
        int[] resolutions = getResolution(configure);
        int qnxWidth = resolutions[0];
        int qnxHeight = resolutions[1];
        int androidWidth = resolutions[2];
        int androidHeight = resolutions[3];
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            ScreenOpsAction screenOpsAction = (ScreenOpsAction) entities.get(i);
            String name = screenOpsAction.getClass().getSimpleName();
            // 检查名字是否符合python命名规范
            checkUtils.checkPythonFunction(screenOpsAction.getName(), index, name);
            // 检查屏幕的高宽
            checkUtils.checkClickPoints(screenOpsAction, index, name, qnxWidth, qnxHeight, androidWidth, androidHeight);
            // 检查操作是否合规
            checkUtils.checkOpsType(screenOpsAction, index, name);
        }
        // 检查函数名是否有重名
        checkUtils.findDuplicate(entities, ScreenOpsAction.class.getSimpleName());
    }


}

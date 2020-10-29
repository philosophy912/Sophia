package com.chinatsp.code.checker.impl.common;

import com.chinatsp.code.checker.api.BaseChecker;

import com.chinatsp.code.checker.api.IChecker;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.common.Common;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.dbc.entity.Message;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CommonChecker extends BaseChecker implements IChecker {


    @Override
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Map<ConfigureTypeEnum, String> configure) {
        List<BaseEntity> entities = getEntity(map, Common.class);
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            Common common = (Common) entities.get(i);
            String name = common.getClass().getSimpleName();
            // 检查名字是否符合python命名规范
            checkUtils.checkPythonFunction(common.getName(), index, name);
            // 检查param是否以等号分割
            checkUtils.checkParam(common.getParams(), index, name);
        }
        // 检查函数名是否有重名
        checkUtils.findDuplicate(entities, Common.class.getSimpleName());
    }
}

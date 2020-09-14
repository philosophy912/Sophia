package com.chinatsp.code.checker.impl.common;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IChecker;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.common.Common;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.character.util.CharUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CommonChecker extends BaseChecker implements IChecker {


    @Override
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Configure configure) {
        List<BaseEntity> entities = getEntity(map, Common.class);
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            Common common = (Common) entities.get(i);
            String name = common.getClass().getName();
            // 检查名字是否符合python命名规范
            checkUtils.checkPythonFunction(common.getName(), index, name);
        }
    }
}

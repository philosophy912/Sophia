package com.chinatsp.code.checker.impl.compare;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IChecker;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.compare.CanCompare;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.dbc.entity.Message;
import com.philosophy.character.util.CharUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CanCompareChecker extends BaseChecker implements IChecker {


    @Override
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Map<ConfigureTypeEnum, String> configure) {
        if (messages==null){
            String error = "需要在Sheet[配置(Configure)]中填写[DBC解析后生成的文件名称]内容";
            throw new RuntimeException(error);
        }
        List<BaseEntity> entities = map.get(CharUtils.lowerCase(this.getClass().getSimpleName().replace("Checker", "")));
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            CanCompare canCompare = (CanCompare) entities.get(i);
            String name = canCompare.getClass().getSimpleName();
            // 检查名字是否符合python命名规范
            checkUtils.checkPythonFunction(canCompare.getName(), index, name);
            // 检查CAN的MessageID, signalName, expectValue是否符合要求
            String signalName = canCompare.getSignalName();
            Long expectValue = canCompare.getExpectValue();
            checkUtils.checkExpectMessage(signalName, expectValue, messages, index, name);
        }
        // 检查函数名是否有重名
        checkUtils.findDuplicate(entities, CanCompare.class.getSimpleName());
    }
}

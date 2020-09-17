package com.chinatsp.code.checker.impl.compare;

import com.chinatsp.code.checker.api.BaseChecker;
import com.chinatsp.code.checker.api.IChecker;
import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.compare.InformationCompare;
import com.chinatsp.code.entity.storage.Information;
import com.chinatsp.dbc.entity.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class InformationCompareChecker extends BaseChecker implements IChecker {


    @Override
    public void check(Map<String, List<BaseEntity>> map, List<Message> messages, Configure configure) {
        List<BaseEntity> entities = getEntity(map, InformationCompare.class);
        List<BaseEntity> infos = getEntity(map, Information.class);
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            InformationCompare informationCompare = (InformationCompare) entities.get(i);
            String name = informationCompare.getClass().getSimpleName();
            // 检查名字是否符合python命名规范
            checkUtils.checkPythonFunction(informationCompare.getName(), index, name);
            // 检查原始信息是否在Information中有保存
            checkUtils.checkInformation(informationCompare.getOrigin(), index, name,infos);
        }
        // 检查函数名是否有重名
        checkUtils.findDuplicate(entities, InformationCompare.class.getSimpleName());
    }
}

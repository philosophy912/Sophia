package com.chinatsp.code.checker;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.utils.CheckUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * 校验所有的数据是否符合预期
 *
 * @author lizhe
 * @date 2020/8/28 9:14
 **/
@Component
@Slf4j
public class Validation {

    private CheckUtils checkUtils;


    @Autowired
    public void setCheckUtils(CheckUtils checkUtils) {
        this.checkUtils = checkUtils;
    }

    public void checkDuplicateName(Map<String, List<BaseEntity>> map){
        for(Map.Entry<String, List<BaseEntity>> entry: map.entrySet()){
            String className = entry.getKey();
            List<BaseEntity> entities = entry.getValue();
            int size = entities.size();

        }
    }

}

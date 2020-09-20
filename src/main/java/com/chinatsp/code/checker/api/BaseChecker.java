package com.chinatsp.code.checker.api;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.utils.CheckUtils;
import com.philosophy.character.util.CharUtils;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Setter
public abstract class BaseChecker {

    @Autowired
    protected CheckUtils checkUtils;


    protected List<BaseEntity> getEntity(Map<String, List<BaseEntity>> map, Class<?> clazz) {
        return map.get(CharUtils.lowerCase(CharUtils.lowerCase(clazz.getSimpleName())));
    }
}

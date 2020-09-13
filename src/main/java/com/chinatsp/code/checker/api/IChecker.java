package com.chinatsp.code.checker.api;

import com.chinatsp.code.entity.BaseEntity;

import java.util.Map;

public interface IChecker {
    /**
     * 检查相互是否错误
     *
     * @param entityMap 所有的表格
     */
    void check(Map<String, BaseEntity> entityMap);
}

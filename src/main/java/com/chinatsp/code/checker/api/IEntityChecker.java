package com.chinatsp.code.checker.api;

import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;

import java.util.List;

public interface IEntityChecker{
    /**
     * 检查自身的是否有错误
     *
     * @param entities 表格读出来的内容
     */
    void check(List<BaseEntity> entities, Configure configure);




}

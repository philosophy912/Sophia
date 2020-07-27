package com.chinatsp.automation.api.builder;


import com.chinatsp.automation.entity.base.BaseEntity;
import com.chinatsp.automation.entity.compare.Function;

import java.nio.file.Path;
import java.util.List;

/**
 * @author lizhe
 * @date 2020/5/28 13:35
 **/
public interface IBuilder {

    void build(String templateName, List<? extends BaseEntity> objects, TestCaseTypeEnum type, Path path, Function function);

    void build(String templateName, List<? extends BaseEntity> objects, TestCaseTypeEnum type, Path path, Function function, Function suite);

    void build(String templateName, List<? extends BaseEntity> objects, TestCaseTypeEnum type, Path path);

    void build(String templateName, TestCaseTypeEnum type, Path path);
}

package com.chinatsp.tools.runner.api;

import com.chinatsp.tools.config.ProjectConfig;
import com.chinatsp.tools.service.api.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author lizhe
 * @date 2020/6/3 9:39
 **/
@Slf4j
public abstract class BaseRunner {

    @Autowired
    protected ProjectConfig configure;
    @Autowired
    @Qualifier("airConditionServiceImpl")
    protected IService airConditionService;
    @Autowired
    @Qualifier("clusterServiceImpl")
    protected IService clusterService;

}

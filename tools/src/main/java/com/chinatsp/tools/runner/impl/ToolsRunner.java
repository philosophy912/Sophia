package com.chinatsp.tools.runner.impl;

import com.chinatsp.tools.runner.api.BaseRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.chinatsp.automation.api.IConstant.AIR_CONDITION;
import static com.chinatsp.automation.api.IConstant.CLUSTER;

/**
 * @author lizhe
 * @date 2020/6/3 9:38
 **/
@Slf4j
@Component
public class ToolsRunner extends BaseRunner implements CommandLineRunner {


    @Override
    public void run(String... args) {
        String type = configure.getType();
        log.debug("test case generator type is {}", type);
        try {
            if (type.equalsIgnoreCase(AIR_CONDITION)) {
                airConditionService.generator();
            } else if (type.equalsIgnoreCase(CLUSTER)) {
                clusterService.generator();
            }
            log.info("generator work is done");
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }
}

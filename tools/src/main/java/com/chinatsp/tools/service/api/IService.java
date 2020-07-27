package com.chinatsp.tools.service.api;


/**
 * @author lizhe
 * @date 2020/6/4 9:06
 **/
public interface IService {

    String DISPLAY_INDEX = "displayIndex";
    String EXPECT_FUNCTIONS = "expectFunctions";
    String CAN_COMPARE = "canCompare";
    String PRECONDITION_FUNCTIONS = "preConditionFunctions";
    String STEPS_FUNCTIONS = "stepsFunctions";
    String DEPENDENCY = "dependency";
    String POSITION = "position";
    String CONTINUE_TIME = "continueTime";
    String ACTIONS = "actions";
    String CONDITIONS = "conditions";
    String DIRECTOR = "director";
    String SCREEN_ACTIONS = "screen_actions";
    String SERVICE = "service";
    String IMAGE_PROPERTY = "image_property";
    String SCREEN_SHOT = "screen_shot";
    String IMAGE_COMPARE = "image_compare";
    String ACTION_TESTCASE = "action_testcase";
    String COMPARE_TESTCASE = "compare_testcase";
    String SETUP = "setup";
    String LOGGER = "logger";
    String INSTALL = "install";
    String RUN = "run";

    /**
     * 生成相关文件
     */
    void generator();

}

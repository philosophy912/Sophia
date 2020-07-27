package com.chinatsp.automation.impl.checker;

import com.chinatsp.automation.api.checker.BaseTestCaseCheck;
import com.chinatsp.automation.api.checker.IReceiverCheck;
import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.actions.ScreenAction;
import com.chinatsp.automation.entity.testcase.Receive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author lizhe
 * @date 2020-05-27 21:42
 */
@Slf4j
@Component
public class ReceiverCheck extends BaseTestCaseCheck implements IReceiverCheck {

    private static final String SHEET_NAME = "Sheet【测试用例】中";


    @Override
    public void check(List<Receive> receives, List<CanAction> canActions, List<ScreenAction> screenActions) {
        // 检查函数名是否重名
        checkDuplicateName(receives);
        receives.forEach(receive -> {
            int index = receive.getId();
            checkObject(receive, index, SHEET_NAME, canActions, screenActions);
            // 检查期望结果
            checkExcept(receive.getExpectFunctions(), index, SHEET_NAME);
        });
    }
}

package com.chinatsp.automation.impl.checker;

import com.chinatsp.automation.api.checker.BaseTestCaseCheck;
import com.chinatsp.automation.api.checker.ISenderCheck;
import com.chinatsp.automation.entity.actions.CanAction;
import com.chinatsp.automation.entity.actions.ScreenAction;
import com.chinatsp.automation.entity.compare.CanCompare;
import com.chinatsp.automation.entity.testcase.Send;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.entity.Signal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;


/**
 * @author lizhe
 * @date 2020/5/28 10:17
 **/
@Slf4j
@Component
public class SenderCheck extends BaseTestCaseCheck implements ISenderCheck {

    private static final String SHEET_NAME = "Sheet【屏幕测试用例】中";

    /**
     * 查找signal name
     *
     * @param sigName  信号名字
     * @param messages message列表
     * @return signal所在的message的id信息
     */
    public String getMessageId(String sigName, List<Message> messages) {
        for (Message message : messages) {
            List<Signal> signals = message.getSignals();
            if (signals != null) {
                for (Signal signal : signals) {
                    // 名字大小写不同的问题要忽略
                    if (sigName.equalsIgnoreCase(signal.getName())) {
                        long msgId = message.getId();
                        return String.valueOf(msgId);
                    }
                }
            }
        }
        throw new RuntimeException(sigName + " not found in messages");
    }

    protected int checkMessageIdAndSignalName(int index, String msgId, String signalName, List<Message> messages) {
        String x = "X";
        int count;
        try {
            if (msgId.toUpperCase().contains(x)) {
                String hexStr = msgId.substring(2);
                // 十六进制
                BigInteger bigInteger = new BigInteger(hexStr, 16);
                count = bigInteger.intValue();
            } else {
                count = Integer.parseInt(msgId);
            }
        } catch (Exception e) {
            String error = getError(SHEET_NAME, index) + "数值填写不正确，请检查.当前填写值为[" + msgId + "], info[" + e.getMessage() + "]";
            throw new RuntimeException(error);

        }
        int messageId = (int) Long.parseLong(getMessageId(signalName, messages));
        log.debug("id = {} and messageId = {}", msgId, messageId);
        if (count != messageId) {
            String e = getError(SHEET_NAME, index) + "signalName[" + signalName + "]不属于ID[" + msgId + "]";
            throw new RuntimeException(e);
        }
        return count;
    }

    private void checkCanCompares(List<CanCompare> canCompares, List<Message> messages, int index) {
        canCompares.forEach(canCompare -> {
            String msgId = canCompare.getMessageId();
            String signalName = canCompare.getSignalName();
            // 先检查然后确保msg id正确后，再反向设值回原来的值，避免0x的存在
            int intMsgId = checkMessageIdAndSignalName(index, msgId, signalName, messages);
            canCompare.setMessageId(String.valueOf(intMsgId));
        });

    }

    @Override
    public void check(List<Send> sends, List<CanAction> canActions, List<ScreenAction> screenActions, List<Message> messages) {
        // 检查函数名是否重名
        checkDuplicateName(sends);
        sends.forEach(send -> {
            int index = send.getId();
            checkObject(send, index, SHEET_NAME, canActions, screenActions);
            checkCanCompares(send.getCanCompares(), messages, index);
        });
    }


}

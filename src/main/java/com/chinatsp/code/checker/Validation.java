package com.chinatsp.code.checker;

import com.chinatsp.code.utils.CheckUtils;
import com.chinatsp.code.utils.ConvertUtils;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.entity.Signal;
import com.philosophy.base.common.Pair;
import com.philosophy.character.util.CharUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    private ConvertUtils convertUtils;

    @Autowired
    public void setCheckUtils(CheckUtils checkUtils) {
        this.checkUtils = checkUtils;
    }

    @Autowired
    public void setConvertUtils(ConvertUtils convertUtils) {
        this.convertUtils = convertUtils;
    }

    /**
     * 根据名字获取signal
     *
     * @param messages   can消息列表
     * @param signalName 信号名
     * @return 信号对象
     */
    private Signal getSignal(List<Message> messages, String signalName) {
        for (Message message : messages) {
            for (Signal signal : message.getSignals()) {
                if (signal.getName().equals(signalName)) {
                    return signal;
                }
            }
        }
        return null;
    }

    /**
     * 根据ID获取message
     *
     * @param messages  can消息列表
     * @param messageId 消息ID
     * @return 消息对象
     */
    private Message getMessage(List<Message> messages, Long messageId) {
        for (Message message : messages) {
            if (message.getId() == messageId) {
                return message;
            }
        }
        return null;
    }

    /**
     * 检查CAN的value是否符合要求
     *
     * @param i           第几行
     * @param className   表sheet的名字
     * @param signalName  信号名称
     * @param signalSize  信号长度
     * @param signalValue 信号值
     */
    private void checkCanValue(int i, String className, String signalName, Integer signalSize, Long signalValue) {
        Double max = Math.pow(2, signalSize);
        if (signalValue > max) {
            String error = "Sheet[" + className + "]的第" + i + "行数据填写错误，CAN信号" + signalName + "设置的值超过了最大范围";
            throw new RuntimeException(error);
        }
    }

    /**
     * 检查实体类中的某个属性的值是否符合python对于函数的命名规范
     *
     * @param name      要校验的内容
     * @param index     行号
     * @param className 类名
     */
    public void checkPythonFunction(String name, int index, String className) {
        if (!checkUtils.isFunctionCorrect(name)) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，必须符合python命名规则";
            throw new RuntimeException(error);
        }
    }

    /**
     * 检查点击的点是否在屏幕范围内
     *
     * @param points    点击位置
     * @param index     行号
     * @param className 类名
     * @param maxWidth  最大的宽
     * @param maxHeight 最大的高
     */
    public void checkClickPoints(List<Pair<Integer, Integer>> points, int index, String className, int maxWidth, int maxHeight) {
        for (Pair<Integer, Integer> pair : points) {
            int x = pair.getFirst();
            int y = pair.getSecond();
            if (x <= 0 || x >= maxWidth || y <= 0 || y >= maxHeight) {
                String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，请检查高宽填写是否符合要求";
                throw new RuntimeException(error);
            }
        }
    }

    /**
     * 检查点击的点是否在屏幕范围内
     *
     * @param positions 点击位置
     * @param index     行号
     * @param className 类名
     * @param maxWidth  最大的宽
     * @param maxHeight 最大的高
     */
    public void checkClickPositions(List<Integer[]> positions, int index, String className, int maxWidth, int maxHeight) {
        for (Integer[] position : positions) {
            int x = position[0];
            int y = position[1];
            int width = position[2];
            int height = position[3];
            if (x <= 0 || x >= maxWidth || y <= 0 || y >= maxHeight || x + width > maxWidth || y + height >= maxHeight) {
                String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，请检查坐标点以及高宽填写是否符合要求";
                throw new RuntimeException(error);
            }
        }
    }

    /**
     * 检查屏幕数量是否符合要求
     *
     * @param displayId  屏幕序号
     * @param index      行号
     * @param className  类名
     * @param maxDisplay 最多的屏幕数量
     */
    public void checkDisplay(int displayId, int index, String className, int maxDisplay) {
        if (displayId <= 0 || displayId > maxDisplay) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，屏幕数量必须小于" + maxDisplay + "个";
            throw new RuntimeException(error);
        }
    }

    /**
     * 检查继电器通道数量是否符合要求
     *
     * @param channel    通道数
     * @param index      行号
     * @param className  类名
     * @param maxChannel 最多的通道
     */
    public void checkRelayChannel(int channel, int index, String className, int maxChannel) {
        if (channel <= 0 || channel > maxChannel) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，继电器通道数必须小于" + channel + "个";
            throw new RuntimeException(error);
        }
    }

    /**
     * 检查电源操作的值是否符合要求
     *
     * @param voltages  电源集合
     * @param index     行号
     * @param className 类名
     * @param maxValue  最大值
     * @param minValue  最小值
     */
    public void checkBatteryOperator(Double[] voltages, int index, String className, double minValue, double maxValue) {
        if (voltages.length == 1) {
            Double startVoltage = voltages[0];
            if (startVoltage < minValue || startVoltage > maxValue) {
                String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，设置电压" + startVoltage + "超过了[" + minValue + "," + maxValue + "]";
                throw new RuntimeException(error);
            }
        } else if (voltages.length == 4) {
            Double startVoltage = voltages[0];
            Double endVoltage = voltages[1];
            Double step = voltages[2];
            // Double intervalTime = values[3];
            Double maxStep = maxValue - minValue;
            if (startVoltage < minValue || startVoltage > maxValue) {
                String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，设置电压" + startVoltage + "超过了[" + minValue + "," + maxValue + "]";
                throw new RuntimeException(error);
            }
            if (endVoltage < minValue || endVoltage > maxValue) {
                String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，设置电压" + startVoltage + "超过了[" + minValue + "," + maxValue + "]";
                throw new RuntimeException(error);
            }
            if (step > maxStep) {
                String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，步长不能超过" + maxStep + "V";
                throw new RuntimeException(error);
            }
        } else {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，设置电压或者初始电压-截止电压-步长-间隔时间";
            throw new RuntimeException(error);
        }
    }

    /**
     * 检查signal的名字和值是否正确
     *
     * @param signals   信号
     * @param messages  消息集合
     * @param index     行号
     * @param className 类名
     */
    public void checkSignals(List<Pair<String, String>> signals, List<Message> messages, int index, String className) {
        for (Pair<String, String> pair : signals) {
            String signalName = pair.getFirst();
            String value = pair.getSecond();
            Signal signal = getSignal(messages, signalName);
            if (null == signal) {
                String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，CAN矩阵表中找不到" + signalName + "信号";
                throw new RuntimeException(error);
            } else {
                try {
                    checkCanValue(index, className, signalName, signal.getSignalSize(), convertUtils.convertLong(value));
                } catch (NumberFormatException e) {
                    String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，信号值填写错误[" + e.getMessage() + "]";
                    throw new RuntimeException(error);
                }
            }
        }
    }


    /**
     * 检查Message ID的值是否正确
     *
     * @param messageId 消息ID
     * @param messages  消息集合
     * @param index     行号
     * @param className 类名
     */
    public void checkMessageId(Long messageId, String signalName, Long expectValue, List<Message> messages, int index, String className) {
        Message message = getMessage(messages, messageId);
        if (null == message) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，CAN的消息ID[" + messageId + "]找不到";
            throw new RuntimeException(error);
        } else {
            Signal signal = null;
            for (Signal sig : message.getSignals()) {
                if (sig.getName().equals(signalName)) {
                    signal = sig;
                }
            }
            if (null == signal) {
                String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，CAN矩阵表中信号[" + messageId + "]找不到[" + signalName + "]信号";
                throw new RuntimeException(error);
            }
            checkCanValue(index, className, signalName, signal.getSignalSize(), expectValue);
        }
    }

    /**
     * 检查图片对比中的相似度是否正确
     *
     * @param similarity 相似度
     * @param index      行号
     * @param className  类名
     */
    public void checkSimilarity(Float similarity, int index, String className) {
        if (similarity < 0 || similarity > 100) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，相似度在0-100间， 当前值为" + similarity;
            throw new RuntimeException(error);
        }
    }
}

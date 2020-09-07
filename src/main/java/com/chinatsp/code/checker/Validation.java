package com.chinatsp.code.checker;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.utils.CheckUtils;
import com.chinatsp.code.utils.ConvertUtils;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.entity.Signal;
import com.philosophy.base.common.Pair;
import com.philosophy.character.util.CharUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 校验所有的数据是否符合预期
 *
 * @author lizhe
 * @date 2020/8/28 9:14
 **/
@Component
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
     * @param entities      实体集合
     * @param attributeName 属性名
     */
    @SneakyThrows
    public void checkPythonFunction(List<BaseEntity> entities, String attributeName) {
        int i = 1;
        for (BaseEntity entity : entities) {
            Class<?> clazz = entity.getClass();
            Field field = clazz.getField(attributeName);
            field.setAccessible(true);
            String name = (String) field.get(entity);
            if (!checkUtils.isFunctionCorrect(name)) {
                String error = "Sheet[" + CharUtils.upperCase(clazz.getName()) + "]的第" + i + "行数据填写错误，必须符合python命名规则";
                throw new RuntimeException(error);
            }
            i++;
        }
    }

    /**
     * 检查点击的点是否在屏幕范围内
     *
     * @param entities      实体集合
     * @param attributeName 属性名
     * @param maxHeight     最大的高
     * @param maxWidth      最大的宽
     */
    @SneakyThrows
    public void checkClickPoint(List<BaseEntity> entities, String attributeName, int maxWidth, int maxHeight) {
        int i = 1;
        for (BaseEntity entity : entities) {
            Class<?> clazz = entity.getClass();
            Field field = clazz.getField(attributeName);
            field.setAccessible(true);
            if (attributeName.equalsIgnoreCase("points")) {
                List<Pair<Integer, Integer>> points = (List<Pair<Integer, Integer>>) field.get(entity);
                for (Pair<Integer, Integer> pair : points) {
                    int x = pair.getFirst();
                    int y = pair.getSecond();
                    if (x <= 0 || x >= maxWidth || y <= 0 || y >= maxHeight) {
                        String error = "Sheet[" + CharUtils.upperCase(clazz.getName()) + "]的第" + i + "行数据填写错误，请检查高宽填写是否符合要求";
                        throw new RuntimeException(error);
                    }
                }
            } else if (attributeName.equalsIgnoreCase("positions")) {
                List<Integer[]> positions = (List<Integer[]>) field.get(entity);
                for (Integer[] position : positions) {
                    int x = position[0];
                    int y = position[1];
                    int width = position[2];
                    int height = position[3];
                    if (x <= 0 || x >= maxWidth || y <= 0 || y >= maxHeight || x + width > maxWidth || y + height >= maxHeight) {
                        String error = "Sheet[" + CharUtils.upperCase(clazz.getName()) + "]的第" + i + "行数据填写错误，请检查坐标点以及高宽填写是否符合要求";
                        throw new RuntimeException(error);
                    }
                }
            }
            i++;
        }
    }

    /**
     * 检查屏幕数量是否符合要求
     *
     * @param entities      实体集合
     * @param attributeName 属性名
     * @param maxDisplay    最多的屏幕数量
     */
    @SneakyThrows
    public void checkDisplay(List<BaseEntity> entities, String attributeName, int maxDisplay) {
        int i = 1;
        for (BaseEntity entity : entities) {
            Class<?> clazz = entity.getClass();
            Field field = clazz.getField(attributeName);
            field.setAccessible(true);
            Integer displayId = (Integer) field.get(entity);
            if (displayId <= 0 || displayId > maxDisplay) {
                String error = "Sheet[" + CharUtils.upperCase(clazz.getName()) + "]的第" + i + "行数据填写错误，屏幕数量必须小于" + maxDisplay + "个";
                throw new RuntimeException(error);
            }
            i++;
        }
    }

    /**
     * 检查继电器通道数量是否符合要求
     *
     * @param entities      实体集合
     * @param attributeName 属性名
     * @param maxChannel    最多的通道
     */
    @SneakyThrows
    public void checkRelayChannel(List<BaseEntity> entities, String attributeName, int maxChannel) {
        int i = 1;
        for (BaseEntity entity : entities) {
            Class<?> clazz = entity.getClass();
            Field field = clazz.getField(attributeName);
            field.setAccessible(true);
            Integer relayChannel = (Integer) field.get(entity);
            if (relayChannel <= 0 || relayChannel > maxChannel) {
                String error = "Sheet[" + CharUtils.upperCase(clazz.getName()) + "]的第" + i + "行数据填写错误，继电器通道数必须小于" + relayChannel + "个";
                throw new RuntimeException(error);
            }
            i++;
        }
    }

    /**
     * 检查电源操作的值是否符合要求
     *
     * @param entities      实体集合
     * @param attributeName 属性名
     * @param maxValue      最大值
     * @param minValue      最小值
     */
    @SneakyThrows
    public void checkBatteryOperator(List<BaseEntity> entities, String attributeName, double maxValue, double minValue) {
        int i = 1;
        for (BaseEntity entity : entities) {
            Class<?> clazz = entity.getClass();
            Field field = clazz.getField(attributeName);
            field.setAccessible(true);
            Double[] values = (Double[]) field.get(entity);
            if (values.length == 1) {
                Double startVoltage = values[0];
                if (startVoltage < minValue || startVoltage > maxValue) {
                    String error = "Sheet[" + CharUtils.upperCase(clazz.getName()) + "]的第" + i + "行数据填写错误，设置电压超过了[" + minValue + "," + maxValue + "]";
                    throw new RuntimeException(error);
                }
            } else if (values.length == 4) {
                Double startVoltage = values[0];
                Double endVoltage = values[1];
                Double step = values[2];
                // Double intervalTime = values[3];
                Double maxStep = maxValue - minValue;
                if (startVoltage < minValue || startVoltage > maxValue) {
                    String error = "Sheet[" + CharUtils.upperCase(clazz.getName()) + "]的第" + i + "行数据填写错误，设置电压超过了[" + minValue + "," + maxValue + "]";
                    throw new RuntimeException(error);
                }
                if (endVoltage < minValue || endVoltage > maxValue) {
                    String error = "Sheet[" + CharUtils.upperCase(clazz.getName()) + "]的第" + i + "行数据填写错误，设置电压超过了[" + minValue + "," + maxValue + "]";
                    throw new RuntimeException(error);
                }
                if (step > maxStep) {
                    String error = "Sheet[" + CharUtils.upperCase(clazz.getName()) + "]的第" + i + "行数据填写错误，步长不能超过" + maxStep + "V";
                    throw new RuntimeException(error);
                }
            } else {
                String error = "Sheet[" + CharUtils.upperCase(clazz.getName()) + "]的第" + i + "行数据填写错误，设置电压或者初始电压-截止电压-步长-间隔时间";
                throw new RuntimeException(error);
            }
            i++;
        }
    }

    /**
     * 检查signal的名字和值是否正确
     *
     * @param entities      实体集合
     * @param attributeName 属性名
     * @param messages      CAN信号列表
     */
    @SneakyThrows
    public void checkSignalNameAndValue(List<BaseEntity> entities, String attributeName, List<Message> messages) {
        int i = 1;
        for (BaseEntity entity : entities) {
            Class<?> clazz = entity.getClass();
            String className = clazz.getName();
            Field field = clazz.getField(attributeName);
            field.setAccessible(true);
            if (attributeName.equalsIgnoreCase("signals")) {
                List<Pair<String, String>> signals = (List<Pair<String, String>>) field.get(entity);
                for (Pair<String, String> pair : signals) {
                    String signalName = pair.getFirst();
                    String value = pair.getSecond();
                    Signal signal = getSignal(messages, signalName);
                    if (null == signal) {
                        String error = "Sheet[" + CharUtils.upperCase(clazz.getName()) + "]的第" + i + "行数据填写错误，CAN矩阵表中找不到" + signalName + "信号";
                        throw new RuntimeException(error);
                    } else {
                        checkCanValue(i, className, signalName, signal.getSignalSize(), convertUtils.convertLong(value));
                    }
                }
            } else if (attributeName.equalsIgnoreCase("SignalName")) {
                String signalName = (String) field.get(entity);
                Signal signal = getSignal(messages, signalName);
                if (null == signal) {
                    String error = "Sheet[" + CharUtils.upperCase(clazz.getName()) + "]的第" + i + "行数据填写错误，CAN矩阵表中找不到" + signalName + "信号";
                    throw new RuntimeException(error);
                }
                Field expectValueField = clazz.getField("expectValue");
                expectValueField.setAccessible(true);
                Long expectValue = (Long) expectValueField.get(entity);
                checkCanValue(i, className, signalName, signal.getSignalSize(), expectValue);
            }
            i++;
        }
    }


    /**
     * 检查Message ID的值是否正确
     * 需要检查是否在messages中存在
     *
     * @param entities      实体集合
     * @param attributeName 属性名
     * @param messages      CAN信号列表
     */
    @SneakyThrows
    public void checkMessageId(List<BaseEntity> entities, String attributeName, List<Message> messages) {
        int i = 1;
        for (BaseEntity entity : entities) {
            Class<?> clazz = entity.getClass();
            Field field = clazz.getField(attributeName);
            field.setAccessible(true);
            Long messageId = (Long) field.get(entity);
            Message message = getMessage(messages, messageId);
            if (null == message) {
                String error = "Sheet[" + CharUtils.upperCase(clazz.getName()) + "]的第" + i + "行数据填写错误，CAN的消息ID" + messageId + "找不到";
                throw new RuntimeException(error);
            }
            i++;
        }
    }

    /**
     * 检查图片对比中的相似度是否正确
     *
     * @param entities      实体集合
     * @param attributeName 可用的定位符集合
     */
    @SneakyThrows
    public void checkSimilarity(List<BaseEntity> entities, String attributeName) {
        int i = 1;
        for (BaseEntity entity : entities) {
            Class<?> clazz = entity.getClass();
            Field field = clazz.getField(attributeName);
            field.setAccessible(true);
            Float similarity = (Float) field.get(entity);
            if (similarity < 0 || similarity > 100) {
                String error = "Sheet[" + CharUtils.upperCase(clazz.getName()) + "]的第" + i + "行数据填写错误，相似度在0-100间";
                throw new RuntimeException(error);
            }
            i++;
        }
    }

}

package com.chinatsp.code.utils;

import com.chinatsp.code.configure.Configure;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.ScreenShotAction;
import com.chinatsp.code.entity.collection.Element;
import com.chinatsp.code.entity.storage.Information;
import com.chinatsp.code.entity.testcase.TestCase;
import com.chinatsp.code.enumeration.TestCaseFunctionTypeEnum;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.entity.Signal;
import com.philosophy.base.common.Pair;
import com.philosophy.character.util.CharUtils;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/9/7 13:22
 **/
@Component
public class CheckUtils {


    /**
     * 函数名检查是否正确（python）
     *
     * @param name 函数名
     * @return true：正确 false：不正确
     */
    private boolean isFunctionCorrect(String name) {
        int max = 255;
        String regex = "[a-zA-Z]+[a-zA-Z0-9_]*";
        boolean isMatch = name.matches(regex);
        if (name.length() > max) {
            return false;
        } else {
            return isMatch;
        }
    }

    /**
     * 函数名是否正确
     * 仅支持长度小于100的英文字符和下划线组成的图片名且后缀不能带_
     *
     * @param name 函数名
     * @return true：正确 false：不正确
     */
    private boolean isImageNameCorrect(String name) {
        int max = 100;
        String regex = "^[a-zA-Z]+[a-zA-Z_]*[^_]$";
        boolean isMatch = name.matches(regex);
        if (name.length() > max) {
            return false;
        } else {
            return isMatch;
        }
    }

    /**
     * 图片全名是否正确
     * 仅支持长度小于100的英文字符和下划线组成的JPG、PNG、BMP图片
     *
     * @param name 图片名
     * @return true：正确 false：不正确
     */
    private boolean isFullImageNameCorrect(String name) {
        int max = 100;
        String regex = "^[a-zA-Z]+[a-zA-Z_]*[^_](.)((bmp)|(jpg)|(png))$";
        boolean isMatch = name.matches(regex);
        if (name.length() > max) {
            return false;
        } else {
            return isMatch;
        }
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
        if (!isFunctionCorrect(name)) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，" +
                    "必须符合python命名规则， 当前名字是" + name;
            throw new RuntimeException(error);
        }
    }

    /**
     * 检查截图名称，仅支持英文+下划线方式，不支持数字已经.jpg类似的格式
     */
    public void checkScreenshotName(String name, int index, String className) {
        if (!isImageNameCorrect(name)) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，" +
                    "图片名只能是小于100个字符的英文字符或者下划线且不能以下划线结尾, 当前图片名为" + name;
            throw new RuntimeException(error);
        }
    }

    /**
     * 检查截图名称，图片名必须是完整的图片名，且图片仅支持英文字符和下划线方式命名的JPG、BMG、PNG图片
     */
    public void checkTemplateImage(String name, int index, String className) {
        if (!isFullImageNameCorrect(name)) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，" +
                    "图片名必须是完整的图片名，且图片仅支持英文字符和下划线方式命名的JPG、BMG、PNG图片, 当前图片名为" + name;
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
            if (x <= 0 || x >= maxWidth) {
                String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，" +
                        x + "必须在小于等于" + maxWidth;
                throw new RuntimeException(error);
            }
            if (y <= 0 || y >= maxHeight) {
                String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，" +
                        y + "必须在小于等于" + maxHeight;
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
     * 检查电源操作的电流电压值是否符合要求
     *
     * @param voltages  电流电压值
     * @param index     行号
     * @param className 类名
     * @param maxValue  最大值
     * @param minValue  最小值
     */
    public void checkBatteryValue(Double[] voltages, int index, String className, double minValue, double maxValue) {
        if (voltages.length != 1) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，电流或电压只能设置一个值";
            throw new RuntimeException(error);
        }
        Double startVoltage = voltages[0];
        if (startVoltage < minValue || startVoltage > maxValue) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，设置电压" + startVoltage + "超过了[" + minValue + ", " + maxValue + "]";
            throw new RuntimeException(error);
        }
    }

    /**
     * 检查电源操作的调节值是否符合要求
     *
     * @param voltages  电源集合
     * @param index     行号
     * @param className 类名
     * @param maxValue  最大值
     * @param minValue  最小值
     */
    public void checkBatteryAdjust(Double[] voltages, int index, String className, double minValue, double maxValue) {
        if (voltages.length != 4) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，" +
                    "电压调节设置模式为其实起始电压-终止电压-步进值-间隔时间, 当前值为" + Arrays.toString(voltages);
            throw new RuntimeException(error);
        }
        Double startVoltage = voltages[0];
        Double endVoltage = voltages[1];
        Double step = voltages[2];
        Double maxStep = maxValue - minValue;
        if (startVoltage < minValue || startVoltage > maxValue) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，" +
                    "设置电压" + startVoltage + "超过了[" + minValue + "," + maxValue + "]";
            throw new RuntimeException(error);
        }
        if (endVoltage < minValue || endVoltage > maxValue) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，" +
                    "设置电压" + startVoltage + "超过了[" + minValue + "," + maxValue + "]";
            throw new RuntimeException(error);
        }
        if (step > maxStep) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，" +
                    "步长不能超过" + maxStep + "V";
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
                    checkCanValue(index, className, signalName, signal.getSignalSize(), ConvertUtils.convertLong(value));
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
    public void checkExpectMessage(Long messageId, String signalName, Long expectValue, List<Message> messages, int index, String className) {
        Message message = getMessage(messages, messageId);
        if (null == message) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，" +
                    "CAN的消息ID[" + messageId + "]找不到";
            throw new RuntimeException(error);
        } else {
            Signal signal = null;
            for (Signal sig : message.getSignals()) {
                if (sig.getName().equals(signalName)) {
                    signal = sig;
                }
            }
            if (null == signal) {
                String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，" +
                        "CAN矩阵表中信号[" + messageId + "]找不到[" + signalName + "]信号";
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
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，" +
                    "相似度在0-100间， 当前值为" + similarity;
            throw new RuntimeException(error);
        }
    }

    /**
     * 检查图片对比中的相似度是否正确
     *
     * @param threshold 灰度二值化值
     * @param index     行号
     * @param className 类名
     */
    public void checkThreshold(Integer threshold, int index, String className) {
        if (threshold < 0 || threshold > 255) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，" +
                    "相似度在0-255间， 当前值为" + threshold;
            throw new RuntimeException(error);
        }
    }

    /**
     * 检查定义的element是否能够找到
     *
     * @param elementName 其他表中定义的element
     * @param index       行号
     * @param className   类名
     * @param elements    Sheet【element】读取出来内容
     */
    public void checkElementExist(String elementName, int index, String className, List<BaseEntity> elements) {
        boolean flag = false;
        for (BaseEntity baseEntity : elements) {
            Element element = (Element) baseEntity;
            if (element.getName().equalsIgnoreCase(elementName)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据填写错误，" +
                    "" + elementName + "在Element表格中找不到";
            throw new RuntimeException(error);
        }
    }

    /**
     * 检查configure是否正确
     *
     * @param configure 配置
     */
    public void checkConfigure(Configure configure) {
        String templateImagePath = configure.getTemplateImagePath();
        if (!Files.exists(Paths.get(templateImagePath))) {
            String error = templateImagePath + "路径不存在，请检查";
            throw new RuntimeException(error);
        }
        String dbdFile = configure.getDbcFile();
        if (!Files.exists(Paths.get(dbdFile))) {
            String error = dbdFile + "文件不存在，请检查";
            throw new RuntimeException(error);
        }
    }

    /**
     * 查找重复的函数名
     *
     * @param entities  实体清单
     * @param className 类名
     */
    public void findDuplicate(List<BaseEntity> entities, String className) {
        Map<String, BaseEntity> map = new HashMap<>(12);
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            BaseEntity baseEntity = entities.get(i);
            String name = baseEntity.getName();
            if (map.containsKey(baseEntity.getName())) {
                String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据" + name + "有重复，请检查";
                throw new RuntimeException(error);
            } else {
                map.put(name, baseEntity);
            }
        }
    }

    /**
     * 查找重复的函数名
     *
     * @param entities  实体清单
     * @param className 类名
     */
    public void findImageNameDuplicate(List<BaseEntity> entities, String className) {
        Map<String, BaseEntity> map = new HashMap<>(12);
        for (int i = 0; i < entities.size(); i++) {
            int index = i + 1;
            ScreenShotAction screenShotAction = (ScreenShotAction) entities.get(i);
            String name = screenShotAction.getImageName();
            if (map.containsKey(screenShotAction.getImageName())) {
                String error = "Sheet[" + CharUtils.upperCase(className) + "]的第" + index + "行数据" + name + "有重复，请检查";
                throw new RuntimeException(error);
            } else {
                map.put(name, screenShotAction);
            }
        }
    }

    /**
     * 检查测试用例中的执行部分，即BatteryAction=battery_test1
     *
     * @param pairs 前置条件/操作步骤/期望结果的部分
     * @param index 序号
     * @param map   所有Sheet的集合
     */
    public void checkAction(List<Pair<TestCaseFunctionTypeEnum, String>> pairs, int index, Map<String, List<BaseEntity>> map) {
        for (Pair<TestCaseFunctionTypeEnum, String> pair : pairs) {
            TestCaseFunctionTypeEnum typeEnum = pair.getFirst();
            String functionName = pair.getSecond();
            List<BaseEntity> entities = map.get((CharUtils.lowerCase(typeEnum.getValue())));
            boolean flag = false;
            for (BaseEntity baseEntity : entities) {
                if (baseEntity.getName().equalsIgnoreCase(functionName)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                String error = "Sheet[TestCase]的第" + index + "行数据在[" + CharUtils.upperCase(typeEnum.getValue()) +
                        "]中找不到方法" + functionName;
                throw new RuntimeException(error);
            }
        }
    }

    /**
     * 检查测试用例前置条件的模块是否在测试用例模块中能找到
     *
     * @param moduleName 模块名
     * @param index      序号
     * @param className  类名
     * @param entities   实体清单
     */
    public void checkModule(String moduleName, int index, String className, List<BaseEntity> entities) {
        boolean flag = false;
        for (BaseEntity baseEntity : entities) {
            TestCase testCase = (TestCase) baseEntity;
            if (testCase.getModuleName().equalsIgnoreCase(moduleName)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            String error = "Sheet[" + className + "]的第" + index + "行的类型" + moduleName + "在TestCase中找不到";
            throw new RuntimeException(error);

        }
    }

    /**
     * 检查要保存的信息是否在Information中能找到
     *
     * @param origin    函数名
     * @param index     序号
     * @param className 类名
     * @param entities  实体清单
     */
    public void checkInformation(String origin, int index, String className, List<BaseEntity> entities) {
        boolean flag = false;
        for (BaseEntity baseEntity : entities) {
            Information information = (Information) baseEntity;
            if (origin.equals(information.getName())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            String error = "Sheet[" + className + "]的第" + index + "行的原始信息" + origin + "在Information中找不到";
            throw new RuntimeException(error);

        }
    }
}

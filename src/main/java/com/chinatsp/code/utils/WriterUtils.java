package com.chinatsp.code.utils;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.actions.BatteryAction;
import com.chinatsp.code.entity.actions.CanAction;
import com.chinatsp.code.entity.actions.ElementAction;
import com.chinatsp.code.entity.actions.RelayAction;
import com.chinatsp.code.entity.actions.ScreenOpsAction;
import com.chinatsp.code.entity.actions.ScreenShotAction;
import com.chinatsp.code.entity.collection.Element;
import com.chinatsp.code.enumeration.AndroidLocatorTypeEnum;
import com.chinatsp.code.enumeration.BatteryOperationTypeEnum;
import com.chinatsp.code.enumeration.DeviceTpeEnum;
import com.chinatsp.code.enumeration.ScreenOperationTypeEnum;
import com.chinatsp.code.enumeration.ScreenShotTypeEnum;
import com.philosophy.base.common.Pair;
import com.philosophy.base.common.Triple;
import org.springframework.stereotype.Component;
import sun.plugin2.message.PrintAppletReplyMessage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/9/28 16:26
 **/
@Component
public class WriterUtils {
    /**
     * 转换Element对象到适合于freemarker写入的数据
     *
     * @param entities element集合
     * @return 每行数据
     */
    public List<Triple<String, List<String>, List<Pair<String, String>>>> convertElements(List<BaseEntity> entities) {
        List<Triple<String, List<String>, List<Pair<String, String>>>> triples = new ArrayList<>();
        for (BaseEntity entity : entities) {
            Element element = (Element) entity;
            String name = element.getName();
            List<String> comment = element.getComments();
            List<Pair<String, String>> pairs = new ArrayList<>();
            Map<AndroidLocatorTypeEnum, String> locators = element.getLocators();
            for (Map.Entry<AndroidLocatorTypeEnum, String> entry : locators.entrySet()) {
                String type = entry.getKey().getValue();
                String value = entry.getValue();
                pairs.add(new Pair<>(type, value));
            }
            triples.add(new Triple<>(name, comment, pairs));
        }
        return triples;
    }

    /**
     * 转换BatteryAction对象到适合于freemarker写入的数据
     *
     * @param entities BatteryAction集合
     * @return 每行数据
     */
    public List<Triple<String[], List<String>, List<String>>> convertBatteryAction(List<BaseEntity> entities) {
        List<Triple<String[], List<String>, List<String>>> triples = new ArrayList<>();
        for (BaseEntity entity : entities) {
            BatteryAction batteryAction = (BatteryAction) entity;
            String functionName = batteryAction.getName();
            List<String> comments = batteryAction.getComments();
            // python操作句柄
            String handleName = batteryAction.getBatteryType().getName();
            BatteryOperationTypeEnum type = batteryAction.getBatteryOperationType();
            // 操作的函数
            String handleFunction = type.getName();
            int cycleTime = 0;
            if (type == BatteryOperationTypeEnum.ADJUST_VOLTAGE || type == BatteryOperationTypeEnum.CURVE) {
                cycleTime = batteryAction.getRepeatTimes();
            }
            List<String> params = new LinkedList<>();
            Double[] values = batteryAction.getValues();
            String curveFile = batteryAction.getCurveFile();
            // 电压曲线的时候values会为空，所以要做特殊处理
            if (null != values) {
                for (Double value : values) {
                    params.add(String.valueOf(value));
                }
            }
            String[] info = new String[5];
            // 函数名，句柄名， 句柄函数， 循环次数， 电压曲线文件
            info[0] = functionName;
            info[1] = handleName;
            info[2] = handleFunction;
            info[3] = String.valueOf(cycleTime);
            info[4] = curveFile;
            triples.add(new Triple<>(info, comments, params));
        }
        return triples;
    }

    /**
     * 转换ElementAction对象到适合于freemarker写入的数据
     *
     * @param entities ElementAction集合
     * @return 每行数据
     */
    public List<Triple<String[], List<String>, List<String>>> convertElementAction(List<BaseEntity> entities) {
        List<Triple<String[], List<String>, List<String>>> triples = new ArrayList<>();
        for (BaseEntity entity : entities) {
            ElementAction elementAction = (ElementAction) entity;
            String functionName = elementAction.getName();
            List<String> comments = elementAction.getComments();
            // python操作句柄
            String handleName = "android_service";
            // 操作的函数
            String handleFunction = elementAction.getOperationActionType().getName();
            int slideTimes = elementAction.getSlideTimes();
            List<String> params = elementAction.getElements();
            String[] info = new String[4];
            info[0] = functionName;
            info[1] = handleName;
            info[2] = handleFunction;
            info[3] = String.valueOf(slideTimes);
            triples.add(new Triple<>(info, comments, params));
        }
        return triples;
    }

    /**
     * 转换RelayAction对象到适合于freemarker写入的数据
     *
     * @param entities RelayAction集合
     * @return 每行数据
     */
    public List<Pair<String[], List<String>>> convertRelayAction(List<BaseEntity> entities) {
        List<Pair<String[], List<String>>> pairs = new ArrayList<>();
        for (BaseEntity entity : entities) {
            RelayAction relayAction = (RelayAction) entity;
            String functionName = relayAction.getName();
            List<String> comments = relayAction.getComments();
            // python操作句柄
            String handleName = "relay";
            // 操作的函数
            String handleFunction = relayAction.getRelayOperationType().getName();
            int channelIndex = relayAction.getChannelIndex();
            String[] info = new String[4];
            info[0] = functionName;
            info[1] = handleName;
            info[2] = handleFunction;
            info[3] = String.valueOf(channelIndex);
            pairs.add(new Pair<>(info, comments));
        }
        return pairs;
    }

    /**
     * 转换ScreenOpsAction对象到适合于freemarker写入的数据
     *
     * @param entities ScreenOpsAction集合
     * @return 每行数据
     */
    public List<Triple<String[], List<String>, List<String>>> convertScreenOpsAction(List<BaseEntity> entities) {
        List<Triple<String[], List<String>, List<String>>> triples = new ArrayList<>();
        for (BaseEntity entity : entities) {
            ScreenOpsAction screenOpsAction = (ScreenOpsAction) entity;
            String functionName = screenOpsAction.getName();
            List<String> comments = screenOpsAction.getComments();
            DeviceTpeEnum deviceType = screenOpsAction.getDeviceType();
            // python操作句柄
            StringBuilder handleName = new StringBuilder(deviceType.getName());
            ScreenOperationTypeEnum type = screenOpsAction.getScreenOperationType();
            // 操作的函数
            String handleFunction = type.getName();
            // 屏幕序号
            int displayId = screenOpsAction.getScreenIndex();
            // 持续时间
            Double continueTimes = screenOpsAction.getContinueTimes();
            String[] info = new String[3];
            info[0] = functionName;
            if (deviceType == DeviceTpeEnum.ANDROID) {
                handleName.append(".adb");
            }
            info[1] = handleName.toString();
            info[2] = handleFunction;
            List<Pair<Integer, Integer>> points = screenOpsAction.getPoints();
            // 组合param
            List<String> params = new ArrayList<>();
            if (deviceType == DeviceTpeEnum.ANDROID) {
                params.add("display_id=" + displayId);
                params.add("device_id=android_device_id");
                Pair<Integer, Integer> pair = points.get(0);
                params.add("x=" + pair.getFirst());
                params.add("y=" + pair.getSecond());
            } else {
                params.add("display=" + displayId);
                if (type == ScreenOperationTypeEnum.CLICK) {
                    Pair<Integer, Integer> pair = points.get(0);
                    params.add("x=" + pair.getFirst());
                    params.add("y=" + pair.getSecond());
                } else if (type == ScreenOperationTypeEnum.SLIDE) {
                    Pair<Integer, Integer> pair1 = points.get(0);
                    Pair<Integer, Integer> pair2 = points.get(1);
                    params.add("start_x=" + pair1.getFirst());
                    params.add("end_x=" + pair1.getSecond());
                    params.add("start_y=" + pair2.getFirst());
                    params.add("end_y=" + pair2.getSecond());
                    params.add("continue_time=" + continueTimes);
                } else {
                    Pair<Integer, Integer> pair = points.get(0);
                    params.add("x=" + pair.getFirst());
                    params.add("y=" + pair.getSecond());
                    params.add("continue_time=" + continueTimes);
                }
            }
            triples.add(new Triple<>(info, comments, params));
        }
        return triples;
    }

    /**
     * 转换ScreenOpsAction对象到适合于freemarker写入的数据
     *
     * @param entities ScreenOpsAction集合
     * @return 每行数据
     */
    public List<Triple<String[], List<String>, List<String>>> convertScreenShotAction(List<BaseEntity> entities) {
        List<Triple<String[], List<String>, List<String>>> triples = new ArrayList<>();
        for (BaseEntity entity : entities) {
            ScreenShotAction screenShotAction = (ScreenShotAction) entity;
            String functionName = screenShotAction.getName();
            List<String> comments = screenShotAction.getComments();
            int count = screenShotAction.getCount();
            int displayId = screenShotAction.getDisplayId();
            String imageName = screenShotAction.getImageName();
            ScreenShotTypeEnum type = screenShotAction.getScreenShotType();
            String handleName = type.getName();
            String handleFunction = "screen_shot";
            String[] info = new String[5];
            info[0] = functionName;
            if (type == ScreenShotTypeEnum.ANDROID_DISPLAY || type == ScreenShotTypeEnum.CLUSTER_DISPLAY) {
                handleName = handleName + ".adb";
            }
            info[1] = handleName;
            info[2] = handleFunction;
            info[3] = imageName;
            info[4] = String.valueOf(count);
            // 组合param
            List<String> params = new LinkedList<>();
            if (type == ScreenShotTypeEnum.QNX_DISPLAY) {
                params.add("image_name=image_name");
                params.add("count=" + count);
                params.add("interval_time=interval_time");
                params.add("display=" + displayId);
            } else {
                params.add("display_id=" + displayId);
                params.add("device_id=android_device_id");
            }
            triples.add(new Triple<>(info, comments, params));
        }
        return triples;
    }

    /**
     * 转换CanAction对象到适合于freemarker写入的数据
     *
     * @param entities CanAction集合
     * @return 每行数据
     */
    public List<Triple<String[], List<String>, Pair<String, List<Pair<String, String>>>>> convertCanAction(List<BaseEntity> entities) {
        List<Triple<String[], List<String>, Pair<String, List<Pair<String, String>>>>> triples = new ArrayList<>();
        for (BaseEntity entity : entities) {
            CanAction canAction = (CanAction) entity;
            String functionName = canAction.getName();
            List<String> comments = canAction.getComments();
            // python操作句柄
            String handleName = "can_service";
            // 操作的函数
            String handleFunction = "send_can_signal_message";
            String[] info = new String[3];
            info[0] = functionName;
            info[1] = handleName;
            info[2] = handleFunction;

            Long msgId = canAction.getMessageId();
            List<Pair<String, String>> signals = canAction.getSignals();
            triples.add(new Triple<>(info, comments, new Pair<>(String.valueOf(msgId), signals)));
        }
        return triples;
    }


}

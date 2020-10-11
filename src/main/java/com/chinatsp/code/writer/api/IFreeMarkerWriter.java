package com.chinatsp.code.writer.api;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.dbc.entity.Message;

import java.util.List;

public interface IFreeMarkerWriter {
    String FUNCTION_NAME = "functionName";
    String HANDLE_NAME = "handleName";
    String HANDLE_FUNCTION = "handleFunction";
    String HANDLE_FUNCTION_SUB = "handleFunctionSub";
    String CYCLE_TIME = "cycleTime";
    String VOLTAGE = "voltage";
    String CURRENT = "current";
    String START = "start";
    String END = "end";
    String STEP = "step";
    String INTERVAL = "interval";
    String CURVE = "curve";
    String MESSAGE_ID = "messageId";
    String SLIDE_TIMES = "slideTimes";
    String ELEMENT = "element";
    String CHANNEL_INDEX = "channelIndex";
    String CONTINUE_TIMES = "continueTimes";
    String DISPLAY_ID = "displayId";
    String X = "x";
    String Y = "y";
    String START_X = "startX";
    String START_Y = "startY";
    String END_X = "endX";
    String END_Y = "endY";
    String IMAGE_NAME = "imageName";
    String COUNT = "count";
    String SIGNAL_NAME = "signalName";
    String EXPECT_VALUE = "expectValue";
    String EXACT = "exact";
    String EXIST = "exist";
    String TIMEOUT = "timeout";
    String LOCATOR = "locator";
    String COMPARE_TYPE = "compareType";
    String LIGHT = "light";
    String DARK = "dark";
    String POSITION = "position";
    String SIMILARITY = "similarity";
    String GRAY = "gray";
    String THRESHOLD = "threshold";
    String ELEMENT_ATTRIBUTE = "elementAttribute";
    String TARGET = "target";


    /**
     * 把Sheet对象转换成便于写入freemarker的内容
     *
     * @param entities 对象
     * @return freemarker函数内容
     */
    List<FreeMarker> convert(List<BaseEntity> entities, List<Message> messages);

}

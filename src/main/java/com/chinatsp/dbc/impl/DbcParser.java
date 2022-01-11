package com.chinatsp.dbc.impl;

import com.chinatsp.dbc.api.IParse;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.entity.Signal;
import com.chinatsp.dbc.utils.DbcUtils;
import com.philosophy.txt.util.TxtUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lizhe
 * @date 2020-05-31 11:52
 */
@Slf4j
@Component
public class DbcParser implements IParse {
    private static final Pattern PATTERN = Pattern.compile("\"(.*?)\"");

    @Override
    public List<Message> parse(Path path) {
        List<String> contents = read(path);
        // GenMsgSendType : ["NoSendType", "Cycle", "Event", "IfActive", "Cycle and Event", "Cycle if Active"]
        Map<String, String[]> map = new HashMap<>(20);
        Message message = null;
        List<Signal> signals = null;
        List<Message> messages = new LinkedList<>();
        for (String content : contents) {
            // 处理BO行，及Message
            if (message == null && content.startsWith(BO)) {
                // 第一次找到BO
                message = new Message();
                setMessage(message, content);
            } else if (message != null) {
                if (!content.startsWith(SG)) {
                    message.setSignals(signals);
                    messages.add(message);
                    message = null;
                    signals = null;
                }
                if (content.startsWith(BO)) {
                    message = new Message();
                    setMessage(message, content);
                }
            }
            // 处理SG行，主要是signal
            if (content.startsWith(SG)) {
                if (signals == null) {
                    signals = new LinkedList<>();
                }
                signals.add(getSignal(content));
            }
            // 处理CM行
            if (content.startsWith(CM)) {
                if (signals != null) {
                    Objects.requireNonNull(message).setSignals(signals);
                    messages.add(message);
                    signals = null;
                }
                setComments(messages, content);
            }
            // 处理BA_DEF行 （BA的定义）
            if (content.startsWith(BA_DEF)) {
                setMessageAttribute(map, content);
            }
            // 处理BA_DEF_DEF行 （BA的默认值）
            if (content.startsWith(BA_DEF_DEF)) {
                setDefaultValue(messages, content);
            }
            // 处理BA行
            if (content.startsWith(BA)) {
                setBaValues(messages, map, content);
            }
            // 处理VAL行
            if (content.startsWith(VAL)) {
                setValValues(messages, content);
            }
        }
        return messages;
    }

    @SneakyThrows
    private List<String> read(Path dbc) {
        TxtUtils txtUtils = new TxtUtils();
        log.info("dbc file path is [{}]", dbc.toAbsolutePath());
        List<String> txtContents = txtUtils.read(dbc, GBK, true);
        StringBuilder sb = new StringBuilder();
        List<String> contents = new ArrayList<>();
        boolean needAdd = true;
        // 处理数据，避免隔行的情况发生
        for (String content : txtContents) {
            log.debug("content = {}", content);
            content = content.trim();
            if (DbcUtils.judgeContent(content, BO, SG, CM, BA_DEF, BA_DEF_DEF, BA_DEF_DEF_REL, BA_DEF_REL, BA, VAL)) {
                needAdd = true;
                if (sb.length() != 0) {
                    contents.add(sb.toString());
                    sb.setLength(0);
                }
                sb.append(content);
            } else if (DbcUtils.judgeContent(content, CM_ONLY_QUOTATION)) {
                needAdd = false;
            } else {
                if (needAdd) {
                    sb.append(content).append(BLANK);
                }
            }
        }
        /*for (int i = 0; i < contents.size(); i++) {
            System.out.println(i + " : " + contents.get(i));
        }*/
        return contents;
    }

    /**
     * 根据id获取msg对象
     *
     * @param messages msg对象集合
     * @param id       msg id
     * @return msg对象
     */
    private Message getMessageById(List<Message> messages, long id) {
        for (Message message : messages) {
            if (message.getId() == id) {
                return message;
            }
        }
        throw new RuntimeException("no message id[" + id + "] found in messages");
    }

    /**
     * 根据名字获取signal
     *
     * @param signals 一个message中的signals对象
     * @param name    signal的名字
     * @return signal对象
     */
    private Signal getSignalByName(List<Signal> signals, String name) {
        for (Signal signal : signals) {
            if (name.equals(signal.getName())) {
                return signal;
            }
        }
        throw new RuntimeException("no signal name[" + name + "] found in signal");
    }

    private void setMessage(Message message, String content) {
        /*
         * 处理BO模块的，返回键值对
         * BO_ 883 GW_373: 8 Vector__XXX
         * 解析案例
         * BO_ message_id message_name ':' message_size transmitter {signal} ;
         * 以及SG_ BCM_PMSErrorFlag : 13|2@0+ (1,0) [0|3] ""  HU
         */
        String bo = DbcUtils.getContent(content, BO);
        log.debug("remove BO_ content [{}]", bo);
        String[] commaContents = bo.split(COLON);
        String part1 = commaContents[0].trim();
        String part2 = commaContents[1].trim();
        log.debug("part1 [{}] and part2 = {}", part1, part2);
        String[] first = part1.split(BLANK);
        String[] second = part2.split(BLANK);
        message.setId(Long.parseLong(first[0].trim()));
        message.setName(first[1].trim());
        message.setLength(Integer.parseInt(second[0].trim()));
        message.setSender(second[1].trim());
    }

    private void setComments(List<Message> messages, String content) {
        /*
         *  处理CM模块的，返回键值对
         *  CM_ SG_ 643 HU_SeatVertAdjMotTarPosn "Seat Vertical Adjust Motor Target Position 座椅垂直调节电机目标位置";
         *  解析案例
         *  comment = 'CM_' (char_string |
         *  'BU_' node_name char_string |
         *  'BO_' message_id char_string |
         *  'SG_' message_id signal_name char_string |
         *  'EV_' env_var_name char_string)
         *  ';' ;
         */
        if (!DbcUtils.judgeContent(content, BU, BO, EV, SG)) {
            log.error("no {}，{}，{}，{} found in content[{}]", BU, BO, EV, SG, content);
        }
        String cm = DbcUtils.getContent(content, CM_ONLY);
        // SG_ 643 HU_SeatVertAdjMotTarPosn
        String part1 = cm.substring(0, cm.indexOf(QUOTATION) - 1).trim();
        String value = cm.substring(cm.indexOf(QUOTATION) + 1, cm.length() - 1)
                .replaceAll(QUOTATION, BLANK).trim();
        log.debug("value is {}", value);
        String[] params = part1.split(BLANK);
        String type = params[0].trim();
        log.debug("type is {}", type);
        // 643 HU_SeatVertAdjMotTarPosn
        String part2 = part1.substring(part1.indexOf(type) + type.length() + 1);
        String[] values = part2.split(BLANK);
        log.debug("values is [{}]", Arrays.toString(values));
        if (type.equalsIgnoreCase(SG.trim())) {
            String msgId = values[0].trim();
            String signalName = values[1].trim();
            Message message = getMessageById(messages, Long.parseLong(msgId));
            Signal signal = getSignalByName(message.getSignals(), signalName);
            signal.setComment(value);
        }
    }

    private void setMessageAttribute(Map<String, String[]> map, String content) {
        /*
         *  解析BA_DEF_ SG_  "GenSigInactiveValue" INT 0 10000;
         */
        String baDef = DbcUtils.getContent(content, BA_DEF);
        if (!DbcUtils.judgeContent(baDef, BU, BO, EV, SG)) {
            log.debug("no {}，{}，{}，{} found in content[{}]", BU, BO, EV, SG, baDef);
            // "Manufactor" STRING ;
            String[] values = baDef.split(BLANK);
            String valueType = values[1];
            if (ENUM.equalsIgnoreCase(valueType)) {
                map.put(values[0], null);
            }
        } else {
            String[] values = baDef.split(BLANK);
            String nodeType = values[0];
            String name = values[1].replaceAll(QUOTATION, NULL);
            String valueType = values[2];
            String[] value = null;
            // 分隔符分割的长度
            int size = 3;
            if (values.length > size) {
                String valueStr = baDef.substring(baDef.indexOf(valueType) + valueType.length() + 1);
                if (valueType.equals(INT) | valueType.equals(HEX)) {
                    value = valueStr.split(BLANK);
                }
                if (valueType.equals(ENUM)) {
                    value = valueStr.split(COMMA);
                }
            }
            log.debug("node type = {} and name is {}", nodeType, name);
            log.debug("value type = {} and value is {}", valueType, Arrays.toString(value));
            map.put(name, value);
        }
    }

    private void setDefaultValue(List<Message> messages, String content) {
        /*
         *  BA_DEF_DEF_  "GatewayedSignals" "No";
         */
        String baDefDef = DbcUtils.getContent(content, BA_DEF_DEF);
        String[] contents = baDefDef.split(BLANK);
        if (contents.length == 2) {
            String name = contents[0].replaceAll(QUOTATION, NULL);
            String value = contents[1].replaceAll(QUOTATION, NULL);
            switch (name) {
                // 设置默认的周期时间信号报文发送的速度、次数、周期型号
                case GEN_MSG_CYCLE_TIME_FAST:
                    for (Message message : messages) {
                        message.setMsgCycleTimeFast(Integer.parseInt(value));
                    }
                    break;
                case GEN_MSG_NR_OF_REPETITION:
                    for (Message message : messages) {
                        message.setGenMsgNrOfRepetition(Integer.parseInt(value));
                    }
                    break;
                case GEN_MSG_CYCLE_TIME:
                    for (Message message : messages) {
                        message.setMsgCycleTime(Integer.parseInt(value));
                    }
                    break;
                case GEN_MSG_DELAY_TIME:
                    for (Message message : messages) {
                        message.setMsgDelayTime(Integer.parseInt(value));
                    }
                    break;
                //设置默认的发送类型
                case GEN_MSG_SEND_TYPE:
                    for (Message message : messages) {
                        message.setMsgSendType(value);
                    }
                    break;
                // 设置默认诊断，网络管理报文类型
                case NM_MESSAGE:
                    for (Message message : messages) {
                        message.setNmMessage(YES.equalsIgnoreCase(value));
                    }
                    break;
                case DIAG_STATE:
                    for (Message message : messages) {
                        message.setDiagState(YES.equalsIgnoreCase(value));
                    }
                    break;
                case DIAG_REQUEST:
                    for (Message message : messages) {
                        message.setDiagRequest(YES.equalsIgnoreCase(value));
                    }
                    break;
                case DIAG_RESPONSE:
                    for (Message message : messages) {
                        message.setDiagResponse(YES.equalsIgnoreCase(value));
                    }
                    break;
                //设置默认是否是标准帧
                case V_FRAME_FORMAT:
                    for (Message message : messages) {
                        message.setStandardCan(STANDARD_CAN.equalsIgnoreCase(value));
                    }
                    break;
                case STANDARD_CAN_FD:
                    for (Message message : messages) {
                        message.setStandardCan(!STANDARD_CAN.equalsIgnoreCase(value));
                    }
                    break;
                // 设置默认的信号值
                case GEN_SIG_START_VALUE:
                    for (Message message : messages) {
                        // 解决只有BO没有SG的情况
                        if (message.getSignals() != null) {
                            for (Signal signal : message.getSignals()) {
                                signal.setSigStartValue(Integer.parseInt(value));
                            }
                        }
                    }
                    break;
                default:
                    log.debug("ba default type is [{}], so nothing to do", name);
            }
        }

    }

    private void handleSg(Message message, String name, String sigName, String value) {
        Signal signal = getSignalByName(message.getSignals(), sigName);
        if (GEN_SIG_START_VALUE.equalsIgnoreCase(name)) {
            // 此处可能传入类似于1.000000的内容，所以需要用float接受之后再强转成int
            log.debug("value is ={}", value);
            if (value.contains(POINT)) {
                signal.setSigStartValue((int) (Float.parseFloat(value)));
            } else {
                signal.setSigStartValue(Integer.parseInt(value));
            }
        }
    }

    private void handleBo(Message message, String type, String value, Map<String, String[]> map) {
        log.debug("type = [{}] and value = [{}]", type, value);
        switch (type) {
            // 设置周期型号的发送频率
            case GEN_MSG_CYCLE_TIME_FAST:
                message.setMsgCycleTimeFast(Integer.parseInt(value));
                break;
            // 设周期信号发送次数
            case GEN_MSG_NR_OF_REPETITION:
                message.setGenMsgNrOfRepetition(Integer.parseInt(value));
                break;
            case GEN_MSG_DELAY_TIME:
                message.setMsgDelayTime(Integer.parseInt(value));
                break;
            // 设置msg的发送类型
            /// case GEN_SIG_SEND_TYPE:
            case GEN_MSG_SEND_TYPE:
                String sendType = map.get(type)[Integer.parseInt(value)]
                        .replaceAll(QUOTATION, NULL);
                log.debug("send type = {}", sendType);
                message.setMsgSendType(sendType);
                break;
            //设置msg的cycle time
            case GEN_MSG_CYCLE_TIME:
                message.setMsgCycleTime(Integer.parseInt(value));
                break;
            //是否是网络管理帧
            case NM_MESSAGE:
                String nmValue = map.get(type)[Integer.parseInt(value)]
                        .replaceAll(QUOTATION, NULL);
                log.debug("nm message value = {}", nmValue);
                message.setNmMessage(YES.equalsIgnoreCase(nmValue));
                break;
            //是否是诊断帧
            case DIAG_STATE:
                String diagValue = map.get(type)[Integer.parseInt(value)]
                        .replaceAll(QUOTATION, NULL);
                log.debug("diag value = {}", diagValue);
                message.setDiagState(YES.equalsIgnoreCase(diagValue));
                break;
            //是否是诊断请求帧
            case DIAG_REQUEST:
                String diagReqValue = map.get(type)[Integer.parseInt(value)]
                        .replaceAll(QUOTATION, NULL);
                log.debug("diag request value = {}", diagReqValue);
                message.setDiagRequest(YES.equalsIgnoreCase(diagReqValue));
                break;
            //是否是诊断相应帧
            case DIAG_RESPONSE:
                String diagRespValue = map.get(type)[Integer.parseInt(value)]
                        .replaceAll(QUOTATION, NULL);
                log.debug("diag response value = {}", diagRespValue);
                message.setDiagResponse(YES.equalsIgnoreCase(diagRespValue));
                break;
            // 是否是标准帧
            case V_FRAME_FORMAT:
                message.setStandardCan(STANDARD_CAN.equalsIgnoreCase(value));
                break;
            default:
                log.info("type is [{}], so nothing to do", type);
        }
    }

    private void setBaValues(List<Message> messages, Map<String, String[]> map, String content) {
        /*
         * 处理BA_ "GenMsgDelayTime" BO_ 1069 0;
         *    BA_ "GenSigStartValue" SG_ 994 ESC_ReqTargetExternal 32256;
         */
        //System.out.println(content);
        String ba = DbcUtils.getContent(content, BA);
        if (ba.contains(BO) || ba.contains(SG)) {
            log.debug("content = {}", ba);
            String[] contents = ba.split(BLANK);
            // 长度5就是SG，长度3就是BO
            if (contents.length == 4) {
                String name = contents[0].replaceAll(QUOTATION, NULL);
                String msgId = contents[2].trim();
                String value = contents[3].trim();
                Message message = getMessageById(messages, Long.parseLong(msgId));
                log.debug("msg Id is = {} and msg is = {}", msgId, message);
                handleBo(message, name, value, map);
            } else if (contents.length == 5) {
                String name = contents[0].replaceAll(QUOTATION, NULL);
                String msgId = contents[2].trim();
                String sigName = contents[3].trim();
                String value = contents[4].trim();
                Message message = getMessageById(messages, Long.parseLong(msgId));
                log.debug("msg Id is = {} and msg is = {}", msgId, message);
                handleSg(message, name, sigName, value);
            } else {
                log.debug("not standard ba");
            }
        }

    }

    private void setValValues(List<Message> messages, String content) {
        /*
         *  处理VAL模块，返回键值对
         *  VAL_ 1069 BCU_BalnFlg105_RM 1 "Balance Closed" 0 "Balance Open" ;
         */
        // 1069 BCU_BalnFlg105_RM 1 "Balance Closed" 0 "Balance Open" ;
        String val = DbcUtils.getValContent(content);
        val = val.replace(SEMICOLON, BLANK).trim();
        int blankIndex = val.indexOf(BLANK);
        String msgId = val.substring(0, blankIndex).trim();
        String others = val.substring(blankIndex + 1);
        blankIndex = others.indexOf(BLANK);
        String signalName;
        Map<String, String> map = new HashMap<>(2);
        if (blankIndex > 0) {
            // 有枚举
            signalName = others.substring(0, blankIndex).trim();
            others = others.substring(blankIndex + 1);
            while (others.length() > 0) {
                int index = others.indexOf(QUOTATION);
                String key = others.substring(0, index).trim();
                others = others.substring(index + 1);
                index = others.indexOf(QUOTATION);
                String value = others.substring(0, index).trim();
                others = others.substring(index + 1);
                map.put(key, value);
            }
        } else {
            signalName = others;
        }
        Message message = getMessageById(messages, Long.parseLong(msgId));
        Signal signal = getSignalByName(message.getSignals(), signalName);
        signal.setValues(map);

//        String val = DbcUtils.getValContent(content);
//        val = val.replace(SEMICOLON, BLANK).trim();
//        int blankIndex = val.indexOf(BLANK);
//        // String msgId = val.substring(0, blankIndex).trim();
//        String[] keys = val.split(BLANK);
//        String msgId = keys[0].trim();
//        String signalName = keys[1].trim();
//        log.debug("msg id = {} and sig name = {}", msgId, signalName);
//        Map<String, String> map = new HashMap<>(2);
//        if (val.contains(QUOTATION)) {
//            //1 "Balance Closed" 0 "Balance Open"
//            // 0 " no crash" 1 " front crash"
//            String part2 = val.substring(val.indexOf(signalName) + signalName.length() + 1,
//                    val.lastIndexOf(QUOTATION) + 1).trim() + BLANK;
//            log.trace("part2 = {}", part2);
//            part2 = part2.replaceAll((QUOTATION + BLANK), (QUOTATION + GBK));
//            log.trace("part3 = {}", part2);
//            String[] values = part2.split(GBK);
//            log.trace("values = {}", Arrays.toString(values));
//            for (String str : values) {
//                log.trace("str = {}", str);
//                // 1 "Balance Closed"
//                String key = str.substring(0, str.indexOf(QUOTATION) - 1).trim();
//                String value = str.substring(str.indexOf(QUOTATION) + 1, str.lastIndexOf(QUOTATION));
//                log.trace("key = {} and value = {}", key, value);
//                map.put(key, value);
//            }
//        }
//        Message message = getMessageById(messages, Long.parseLong(msgId));
//        Signal signal = getSignalByName(message.getSignals(), signalName);
//        signal.setValues(map);
    }

    private Signal getSignal(String content) {
        Signal signal = new Signal();
        /*
         *  处理SG模块，返回键值对
         *  SG_ HVACF_NMSleepAck : 13|1@0+ (1,0) [0|1] ""  HVACR
         *  SG_ HU_LocTiY : 6|5@0+ (1,2019) [2019|2050] "year" TBox,CGW
         *  解析案例
         *  signal = 'SG_' signal_name multiplexer_indicator ':' start_bit '|'
         *  signal_size '@' byte_order value_type '(' factor ',' offset ')'
         *  '[' minimum '|' maximum ']' unit receiver {',' receiver} ;
         *  大端还是小端 1=intel(小端模式) ，0=Motorola（大端模式）
         *  由于在DBC只有一个bit，所以只能表达Intel和MOTOROLA两种方式, 推论应该是在DBC excel描述的时候需要做转换。
         *  True表示intel， False表示Motorola
         *  大端模式表示反向，小端模式表示顺向
         */
        String sg = DbcUtils.getContent(content, SG);
        log.debug("sg = {}", sg);
        String[] colons = sg.split(COLON);
        // 此处可能存在colons.length > 2 的情况
        if (colons.length != 2) {
            String[] realColons = new String[2];
            realColons[0] = colons[0];
            realColons[1] = sg.substring(realColons[0].length() + 1);
            colons = realColons;
        }
        log.debug("colons = {}", Arrays.toString(colons));
        String name = colons[0].trim();
        log.debug("signal name = {}", name);
        signal.setName(name);
        // 6|5@0+ (1,2019) [2019|2050] "year" TBox,CGW
        String colonPart2 = colons[1].trim();
        String[] ats = colonPart2.split(AT);
        //6|5
        String at1 = ats[0];
        String[] yAxis = at1.split(Y_AXIS);
        String startBit = yAxis[0].trim();
        try {
            signal.setStartBit(Integer.parseInt(startBit));
        } catch (NumberFormatException e) {
            System.out.println(sg);
            throw new RuntimeException(e);
        }
        String signalSize = yAxis[1].trim();
        signal.setSignalSize(Integer.parseInt(signalSize));
        // 0+ (1,2019) [2019|2050] "year" TBox,CGW
        String at2 = ats[1].trim();
        String byteOrder = at2.substring(0, 1).trim();
        String valueType = at2.substring(1, 2).trim();
        log.debug("byte order [{}] and value type [{}]", byteOrder, valueType);
        signal.setByteType(ONE.equalsIgnoreCase(byteOrder));
        signal.setIsSign(PLUS.equalsIgnoreCase(valueType));
        // (1,2019) [2019|2050] "year" TBox,CGW
        String others = at2.substring(3);
        String factorOffset = others.substring(others.indexOf("(") + 1, others.indexOf(")"));
        String[] factorOffsets = factorOffset.split(COMMA);
        String factor = factorOffsets[0].trim();
        String offset = factorOffsets[1].trim();
        log.debug("factor = {} and offset = {}", factor, offset);
        signal.setFactor(Double.parseDouble(factor));
        signal.setOffset(Double.parseDouble(offset));
        String maxMin = others.substring(others.indexOf("[") + 1, others.indexOf("]"));
        String[] minmax = maxMin.split(Y_AXIS);
        String min = minmax[0].trim();
        String max = minmax[1].trim();
        log.debug("min = {} and max = {}", min, max);
        signal.setMinimum(Double.parseDouble(min));
        signal.setMaximum(Double.parseDouble(max));
        Matcher unitMatcher = PATTERN.matcher(others);
        if (unitMatcher.find()) {
            String unit = unitMatcher.group().replaceAll(QUOTATION, NULL);
            log.debug("unit = {}", unit);
            signal.setUnit(unit);
        } else {
            throw new RuntimeException("QUOTATION not found in content[" + sg + "]");
        }
        //(1,2019)[2019|2050]"year"TBox,CGW
        others = others.replaceAll(TRIM_BLANK, BLANK).replaceAll(BLANK, NULL);
        // TBox,CGW
        String receivers = others.substring(others.lastIndexOf(QUOTATION) + 1).trim();
        log.debug("receivers = {}", receivers);
        signal.setReceiver(receivers);
        return signal;
    }
}

package com.chinatsp.dbc.impl;

import com.chinatsp.dbc.api.IParse;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.entity.Param;
import com.chinatsp.dbc.entity.Signal;
import com.philosophy.base.util.ParseUtils;
import com.philosophy.base.util.StringsUtils;
import com.philosophy.excel.utils.ExcelUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020-05-31 11:57
 */
@Slf4j
@Component
public class ExcelDbcParser implements IParse {

    private final ExcelUtils excelUtils = new ExcelUtils();

    private String getValue(Row row, int index) {
        return excelUtils.getCellValue(row.getCell(index));
    }

    private Integer getNodeIndex(Row row) {
        for (Cell cell : row) {
            if (excelUtils.getCellValue(cell).toLowerCase().contains("delay")) {
                return cell.getColumnIndex();
            }
        }
        String e = "no msg delay time found in first row";
        throw new RuntimeException(e);
    }


    private List<String> getNodes(Row row) {
        int index = getNodeIndex(row);
        List<String> nodes = new LinkedList<>();
        for (int i = index; i < row.getPhysicalNumberOfCells(); i++) {
            String value = excelUtils.getCellValue(row.getCell(i)).trim();
            if (!StringUtils.isEmpty(value)) {
                nodes.add(value);
            }
        }
        log.debug("nodes list is " + Arrays.toString(ParseUtils.toArray(nodes)));
        return nodes;
    }

    /**
     * 是否同时包含字符
     *
     * @param content 字符串
     * @param strings 要包含的字符串集合
     * @return 真假
     */
    private boolean isContains(String content, String... strings) {
        for (String str : strings) {
            if (!content.contains(str.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 自动查找msg id所在的sheet
     *
     * @param workbook workbook
     * @return sheet
     */
    private Sheet getMatrixSheet(Workbook workbook) {
        int totalSheet = workbook.getNumberOfSheets();
        for (int i = 0; i < totalSheet; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            log.debug("sheet name = {}", sheet.getSheetName());
            int maxRow = sheet.getPhysicalNumberOfRows();
            log.debug("maxRow = {}", maxRow);
            if (maxRow > 0) {
                Row row = sheet.getRow(0);
                if (row != null) {
                    for (Cell cell : row) {
                        String value = excelUtils.getCellValue(cell).toLowerCase();
                        log.debug("cell value = {}", value);
                        if (isContains(value, "msg", "delay", "time")) {
                            return sheet;
                        }
                    }
                }
            }
        }
        String e = "not found msg name in Cell(0,0) in all sheet";
        throw new RuntimeException(e);
    }

    @SneakyThrows
    private Param getParam(Row row) {
        Param param = new Param();
        Field[] fields = param.getClass().getDeclaredFields();
        for (Cell cell : row) {
            int index = cell.getColumnIndex();
            String value = excelUtils.getCellValue(cell).toLowerCase();
            log.debug("value is {}", value);
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                String s = name.replaceAll("[A-Z]", " $0").substring(1);
                String[] words = s.split(BLANK);
                log.debug("words is {}", Arrays.toString(words));
                if (isContains(value, words)) {
                    field.set(param, index);
                }
            }
        }
        return param;
    }


    private String convert(String description) {
        String[] lines = description.split("\\n");
        StringBuilder sb = new StringBuilder();
        for (String str : lines) {
            sb.append(str).append(BLANK);
        }
        return sb.toString();
    }

    /**
     * 处理跨行的问题
     *
     * @param description 描述
     * @return 处理后的列表
     */
    private List<String> handleDescription(String description) {
        List<String> content = new LinkedList<>();
        String[] values = description.split("\\n");
        StringBuilder sb = new StringBuilder();
        for (String str : values) {
            sb.append(str);
            if (str.startsWith("0x")) {
                content.add(sb.toString());
                sb.setLength(0);
            }
        }
        return content;
    }

    /**
     * 处理类似于 0x1: 开启VSP命令（默认:车辆READY，非N档，车速等于0，VSP不响） 的数据
     *
     * @param valueStr 数据字符串
     * @return 返回处理后的结果
     */
    private String handle(String valueStr) {
        valueStr = valueStr.replaceAll(COLON_CHINESE, COLON);
        StringBuilder sb = new StringBuilder();
        String[] value = valueStr.split(COLON);
        for (int i = 0; i < value.length; i++) {
            sb.append(value[i]);
            if (i == 0) {
                sb.append(GBK);
            } else if (i != value.length - 1) {
                sb.append(COLON);
            }
        }
        return sb.toString();
    }

    /**
     * 处理values字符串
     *
     * @param description description字符串
     * @return 处理成键值对
     */
    private Map<String, String> handleValues(String description) {
        Map<String, String> map = new HashMap<>(12);
        if (StringsUtils.isEmpty(description)) {
            return null;
        } else {
            List<String> values = handleDescription(description);
            log.debug(Arrays.toString(ParseUtils.toArray(values)));
            for (String valueStr : values) {
                if (!StringsUtils.isEmpty(valueStr)) {
                    valueStr = handle(valueStr);
                    log.debug("valueStr {}", valueStr);
                    String[] value = valueStr.split(GBK);
                    map.put(value[0], value[1]);
                }
            }
        }
        log.debug("map = [{}]", map);
        return map;
    }

    /**
     * 处理message行的数据
     *
     * @param row     行数据
     * @param message message对象，用于添加内容
     * @param nodes   节点对象
     */
    private void handleMsg(Row row, Message message, List<String> nodes, Param param, int start, int size) {
        String name = getValue(row, param.getMsgName());
        message.setName(name);
        String type = getValue(row, param.getMsgType());
        if (NORMAL.equalsIgnoreCase(type)) {
            log.debug("normal frame");
        } else if (NM.equalsIgnoreCase(type)) {
            message.setNmMessage(true);
        } else if (DIAG.equalsIgnoreCase(type)) {
            message.setDiagState(true);
            message.setDiagRequest(name.toLowerCase().contains(REQUEST));
        }
        String id = getValue(row, param.getMsgId());
        message.setId(Long.parseLong(id.toUpperCase().replaceAll("0X", ""), 16));
        message.setMsgSendType(getValue(row, param.getMsgSendType()));
        message.setLength(Integer.parseInt(getValue(row, param.getMsgLength())));
        String cycleTime = getValue(row, param.getMsgCycleTime());
        // 没有的情况下填0
        message.setMsgCycleTime(StringsUtils.isEmpty(cycleTime) ? 0 : Integer.parseInt(cycleTime));
        // 由于有可能没有填写，所以需要一个默认值
        String msgCycleTimeFast = getValue(row, param.getMsgCycleTimeFast());
        message.setMsgCycleTimeFast(StringsUtils.isEmpty(msgCycleTimeFast) ? 0 : Integer.parseInt(msgCycleTimeFast));
        String msgNrOrReption = getValue(row, param.getMsgNrOfReption());
        message.setGenMsgNrOfRepetition(StringsUtils.isEmpty(msgNrOrReption) ? 0 : Integer.parseInt(msgNrOrReption));
        String delayTime = getValue(row, param.getMsgDelayTime());
        message.setMsgDelayTime(StringsUtils.isEmpty(delayTime) ? 0 : Integer.parseInt(delayTime));
        StringBuilder senders = new StringBuilder();
        Integer msgDelayTime = param.getMsgDelayTime();
        // 获取收发节点的数据，并以空格划分
        for (int i = start; i < size; i++) {
            String cellValue = excelUtils.getCellValue(row.getCell(i)).trim();
            if (SEND.equalsIgnoreCase(cellValue)) {
                senders.append(nodes.get(i - msgDelayTime - 1)).append(COMMA);
            }
        }
        String sender = senders.toString();
        log.debug("sender is {}", sender);
        if (sender.length() > 0) {
            message.setSender(sender.substring(0, sender.length() - 1));
        } else {
            message.setSender("");
        }
        message.setComment(BLANK);
    }

    /**
     * 根据大端小端来计算真正的startbit
     *
     * @param startBit 开始位
     * @param length   信号的长度
     * @return 真正的开始位
     */
    private int handleByteType(int startBit, int length) {
        // 开始的startBit为7， length是16， 则realStartBit为8
        // 先计算7在64bit中的位置，然后+length，再计算真实的位置
        int bitIndex = startBit / 8 * 8 + 7 - (startBit - startBit / 8 * 8);
        int offsetBitIndex = bitIndex + length - 1;
        startBit = offsetBitIndex / 8 * 8 + 7 - (offsetBitIndex - offsetBitIndex / 8 * 8);
        return startBit;
    }

    /**
     * 处理signal行的数据
     *
     * @param row     行数据
     * @param signals signals对象列表
     */
    private void handleSig(Row row, List<Signal> signals, List<String> nodes, Param param, int start, int size) {
        Signal signal = new Signal();
        signal.setName(getValue(row, param.getSignalName()));
        // 处理成一行
        signal.setComment(convert(getValue(row, param.getSignalDescription())).trim());
        String byteOrder = getValue(row, param.getByteOrder()).toLowerCase();
        // 获取start bit（表格中的)
        int startBit = Integer.parseInt(getValue(row, param.getStartBit()));
        // 获取了signal的长度
        int length = Integer.parseInt(getValue(row, param.getBitLength()));
        signal.setSignalSize(length);
        /*
         * 首先判定是Intel还是Motorola
         * 大小端的模式true intel， false motorola
         */
        if (!byteOrder.contains(MOTOROLA)) {
            signal.setByteType(true);
            signal.setStartBit(startBit);
        } else {
            signal.setByteType(false);
            if (byteOrder.contains(LSB)) {
                // 需要处理start_bit
                int realStartBit = handleByteType(startBit, length);
                signal.setStartBit(realStartBit);
            } else if (byteOrder.contains(MSB)) {
                signal.setStartBit(startBit);
            }
        }
        signal.setStartBit(startBit);
        signal.setIsFloat(UNSIGNED.equalsIgnoreCase(getValue(row, param.getDateType())));
        signal.setFactor(Double.parseDouble(getValue(row, param.getResolution())));
        signal.setOffset(Double.parseDouble(getValue(row, param.getOffset())));
        signal.setMaximum(Double.parseDouble(getValue(row, param.getSignalMaxValuePhys())));
        signal.setMinimum(Double.parseDouble(getValue(row, param.getSignalMinValuePhys())));
        String startValue = getValue(row, param.getInitialValue());
        if (StringsUtils.isEmpty(startValue)) {
            signal.setSigStartValue(0);
        } else {
            signal.setSigStartValue(Integer.parseInt(startValue.toUpperCase()
                    .replaceAll("0X", ""), 16));
        }

        signal.setUnit(getValue(row, param.getUnit()));
        // 解析SIGNAL_VALUE_DESCRIPTION行程map
        Map<String, String> valueMap = handleValues(getValue(row, param.getSignalValueDescription()));
        if (valueMap != null) {
            log.debug("valueMap = [{}]", valueMap);
            signal.setValues(valueMap);
        }
        StringBuilder receiver = new StringBuilder();
        // 获取收发节点的数据，并以空格划分
        for (int i = start; i < size; i++) {
            log.debug("current i[{}] columns", i);
            Cell cell = row.getCell(i);
            String cellValue = "";
            if (cell != null) {
                cellValue = excelUtils.getCellValue(row.getCell(i)).trim();
            }
            if (RECEIVE.equalsIgnoreCase(cellValue)) {
                receiver.append(nodes.get(i - param.getMsgDelayTime() - 1)).append(COMMA);
            }
        }
        String receive = receiver.toString();
        log.debug("receive is {}", receive);
        if (receive.length() > 0) {
            signal.setReceiver(receive.substring(0, receive.length() - 1));
        } else {
            signal.setReceiver("");
        }
        // 设置有无符号为默认
        signal.setIsSign(false);
        signals.add(signal);
    }

    @SneakyThrows
    @Override
    public List<Message> parse(Path path) {

        // 打开文件
        Workbook workbook = excelUtils.openWorkbook(path);
        int totalSheet = workbook.getNumberOfSheets();
        Sheet sheet = getMatrixSheet(workbook);
        /*
         * 根据第一排的描述确定
         */
        Row firstRow = sheet.getRow(0);
        Param param = getParam(firstRow);
        List<String> nodes = getNodes(firstRow);
        sheet.removeRow(firstRow);
        List<Message> messages = new ArrayList<>();
        List<Signal> signals = null;
        Message message = null;
        for (Row row : sheet) {
            int index = row.getRowNum();
            String msgName = null;
            String signalName = null;
            try {
                msgName = getValue(row, param.getMsgName());
                signalName = getValue(row, param.getSignalName());
                log.debug("Current line is {}, msg[{}], sig[{}]", index + 2, msgName, signalName);
            } catch (Exception e) {
                log.error("Current line is {}, but error found{}", index + 2, e.getMessage());
            }
            if (StringsUtils.isEmpty(msgName) && StringsUtils.isEmpty(signalName)) {
                log.debug("The {} line is not accept", row.getRowNum());
            } else {
                // 处理msgName
                if (!StringsUtils.isEmpty(msgName)) {
                    if (message != null) {
                        // 添加所有的signal到message，并添加message到messages中
                        message.setSignals(signals);
                        messages.add(message);
                    }
                    // 重置message和signals
                    message = new Message();
                    signals = new LinkedList<>();
                    handleMsg(row, message, nodes, param, totalSheet, nodes.size());
                } else {
                    handleSig(row, signals, nodes, param, totalSheet, nodes.size());
                }
            }
        }
        // 由于最后没有结束标识符，只能在编译完成了所有的表格再将最后一次的更新结果添加到messages中
        if (message != null) {
            message.setSignals(signals);
            messages.add(message);
        }
        System.out.println(messages);
        excelUtils.close(workbook);
        return messages;
    }
}

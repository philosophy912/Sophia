package com.chinatsp.code.service;

import com.alibaba.fastjson.JSON;
import com.chinatsp.code.BaseTestUtils;
import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.ConfigureTypeEnum;
import com.chinatsp.code.service.api.IReadService;
import com.chinatsp.code.service.api.IWriteService;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.impl.DbcParser;
import com.philosophy.base.common.Pair;
import com.philosophy.txt.util.TxtUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class WriteServiceTest {
    @Autowired
    private IReadService readerService;
    @Autowired
    private IWriteService writeService;
    @Autowired
    private DbcParser dbcParser;
    @Autowired
    private TxtUtils txtUtils;

    @Test
    void write() {
        Path path = Paths.get(BaseTestUtils.getFileFolder(), "template.xlsx");
        Pair<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>> pair = readerService.read(path);
        Path folder = Paths.get(BaseTestUtils.getCodeFolder());
        //writeService.write(pair, folder);
    }

    @SneakyThrows
    @Test
    void test1Q1() {
        Path dbc = Paths.get(BaseTestUtils.getFileFolder(),"FAW_E115_FCP_CANMatrix_V1.6.dbc");
        List<Message> messages = dbcParser.parse(dbc);
//        String s = JSON.toJSONString(messages);
//        txtUtils.write(Paths.get("D:\\Workspace\\github\\code\\file\\FAW_E115_FCP_CANMatrix_V1.6.json"), s, "utf-8", false, true);
        Path path = Paths.get(BaseTestUtils.getFileFolder(), "template1q1.xlsx");
        Pair<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>> pair = readerService.read(path);
        Path folder = Paths.get(BaseTestUtils.getCodeFolder());
        writeService.write(pair, messages, folder);
    }

    @SneakyThrows
    @Test
    void test3S1() {
        Path dbc = Paths.get(BaseTestUtils.getFileFolder(),"HiFire_B31CP_Info_HU_CAN_V2.0.dbc");
        List<Message> messages = dbcParser.parse(dbc);
//        String s = JSON.toJSONString(messages);
//        txtUtils.write(Paths.get(BaseTestUtils.getFileFolder(),"HiFire_B31CP_Info_HU_CAN_V2.0.json"), s, "utf-8", false, true);
        Path path = Paths.get(BaseTestUtils.getFileFolder(), "template3S1.xlsx");
        Pair<Map<String, List<BaseEntity>>, Map<ConfigureTypeEnum, String>> pair = readerService.read(path);
        Path folder = Paths.get(BaseTestUtils.getCodeFolder());
        writeService.write(pair, messages, folder);
    }
}
package com.chinatsp.tools.runner;

import com.alibaba.fastjson.JSON;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.impl.DbcParser;
import com.philosophy.excel.utils.ExcelUtils;
import com.philosophy.txt.util.TxtUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static com.chinatsp.dbc.api.IConstant.UTF8;

/**
 * @author lizhe
 * @date 2020/6/8 13:34
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AirConditionRunner {

    @Autowired
    private DbcParser dbcParser;
    @Autowired
    private TxtUtils txtUtils;
    @Autowired
    private ExcelUtils excelUtils;

    @SneakyThrows
    @Test
    public void test() {
        Path path = Paths.get("D:\\Temp\\test\\22222\\HiFire_GSE_CONFCAN_Matrix_CAN_V2.0_IC_MMI.dbc");
        List<Message> messages = dbcParser.parse(path);
        Path out = Paths.get("D:\\Temp\\test\\22222\\gse.py");
        String str = JSON.toJSONString(messages);
        str = "messages = " + str.replaceAll("true", "True").replaceAll("false", "False");
        txtUtils.write(out, str, "utf-8", false, false);
    }

    @Test
    @SneakyThrows
    public void test1() {
        List<String> contents = new LinkedList<>();
        String base = "C:\\Users\\philo\\Music\\Music\\Music1";
        Files.newDirectoryStream(Paths.get(base), path -> path.toString().endsWith("csv"))
                .forEach(path -> {
                    try {
                        List<String> content = txtUtils.read(path, UTF8, false);
                        contents.addAll(content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(contents.size());
        List<String[]> txtContents = new LinkedList<>();
        contents.forEach(content -> {
            txtContents.add(new String[]{content});
        });
        txtUtils.write(Paths.get(base, "test.csv"), txtContents, UTF8, false, true);
    }

}

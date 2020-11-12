package com.chinatsp.automotive.project;

import com.chinatsp.automotive.BaseTestUtils;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.entity.Signal;
import com.chinatsp.dbc.impl.DbcParser;
import com.philosophy.excel.utils.ExcelUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.chinatsp.automotive.utils.Constant.EQUAL;

/**
 * @author lizhe
 * @date 2020/10/9 9:29
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class Project {
    @Autowired
    private ExcelUtils excelUtils;
    @Autowired
    private DbcParser dbcParser;

    @SneakyThrows
    @Test
    public void test1() {
        Path dbc = Paths.get(BaseTestUtils.getFileFolder(), "HiFire_B31CP_Info_HU_CAN_V2.0.dbc");
        List<Message> messages = dbcParser.parse(dbc);
        Path excel = Paths.get(BaseTestUtils.getFileFolder(), "template3S1.xlsx");
        Workbook workbook = excelUtils.openWorkbook(excel);
        Sheet sheet = workbook.getSheet("Can信号(CanAction)");
        sheet.removeRow(sheet.getRow(0));
        for (Row row : sheet) {
            int rowNo = row.getRowNum();
            String signals = excelUtils.getCellValue(row.getCell(4));
            String sigName = signals.split(EQUAL)[0].trim();
            for (Message message : messages) {
                for (Signal signal : message.getSignals()) {
                    if (sigName.equals(signal.getName())) {
                        //System.out.println(rowNo + " = " + message.getId());
                        System.out.println(message.getId());
                    }
                }
            }
        }
        excelUtils.close(workbook);

    }
}

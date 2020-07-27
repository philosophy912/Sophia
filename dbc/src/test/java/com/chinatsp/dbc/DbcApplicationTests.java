package com.chinatsp.dbc;

import com.alibaba.fastjson.JSON;
import com.chinatsp.dbc.entity.Message;
import com.chinatsp.dbc.impl.DbcParser;
import com.chinatsp.dbc.impl.ExcelDbcParser;
import com.philosophy.base.util.FilesUtils;
import com.philosophy.txt.util.TxtUtils;
import lombok.SneakyThrows;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


class DbcApplicationTests {

    private static final TxtUtils txtUtils = new TxtUtils();
    private static final ExcelDbcParser parser = new ExcelDbcParser();
    private static final DbcParser dbcParser = new DbcParser();
    private static final String base = "D:\\Workspace\\tools\\src\\test\\resources\\excel";
    private static final String baseDbc = "D:\\Workspace\\github\\knife\\dbc\\src\\test\\resources\\dbc\\temp";

    @SneakyThrows
    public static void test1() {
        Files.newDirectoryStream(Paths.get(base)).forEach(file -> {
            System.out.println(file.toAbsolutePath());
            parser.parse(file);
        });
    }

    public static void test2() {
        Path file = Paths.get(base + File.separator + "HiFire_B31CP_Info_HU_CAN_V2.01_20190524.xls");
        parser.parse(file);
    }

    public static void test3() {
        Path file = Paths.get(base + File.separator + "HiFire_GSE_CONFCAN_Matrix_CAN_V0.92_IC_MMI.xls");
        parser.parse(file);
    }

    public static void test4() {
        try {
            Files.newDirectoryStream(Paths.get(baseDbc)).forEach(file -> {
                System.out.println(file.toAbsolutePath());
                List<Message> message = dbcParser.parse(file);
                String str = JSON.toJSONString(message);
                String value = "messages = " + str.replaceAll("false", "False")
                        .replaceAll("true", "True");
                try {
                    String name = FilesUtils.getFileNameAndExtension(file).getFirst();
                    Path pyPath = Paths.get(baseDbc + File.separator + name + ".py");
                    Path jsonPath = Paths.get(baseDbc + File.separator + name + ".json");
                    txtUtils.write(pyPath, value, "UTF-8", false, true);
                    txtUtils.write(jsonPath, str, "UTF-8", false, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // test1();
        /*test2();
        test3();*/
        test4();
    }

}

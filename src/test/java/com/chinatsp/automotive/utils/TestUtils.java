package com.chinatsp.automotive.utils;

import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.entity.testcase.TestCase;
import com.chinatsp.automotive.writer.BaseWriter;
import com.chinatsp.dbc.impl.DbcParser;

import java.nio.file.Paths;

/**
 * @author lizhe
 * @date 2020/10/29 16:19
 **/
public class TestUtils {

    public static void main(String[] args) {
//        DbcParser dbcParser = new DbcParser();
//        dbcParser.parse(Paths.get("D:\\Workspace\\github\\code\\file", "B30-006-014DB01  CAN DataBase for SC-CAN_V3.2.dbc"));
        String name = BaseWriter.class.getPackage().getName();
        System.out.println(name);
    }

}

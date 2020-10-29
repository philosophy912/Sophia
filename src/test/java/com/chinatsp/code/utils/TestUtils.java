package com.chinatsp.code.utils;

import com.chinatsp.dbc.impl.DbcParser;

import java.nio.file.Paths;

/**
 * @author lizhe
 * @date 2020/10/29 16:19
 **/
public class TestUtils {

    public static void main(String[] args) {
        DbcParser dbcParser = new DbcParser();
        dbcParser.parse(Paths.get("D:\\Workspace\\github\\code\\file", "B30-006-014DB01  CAN DataBase for SC-CAN_V3.2.dbc"));
    }

}

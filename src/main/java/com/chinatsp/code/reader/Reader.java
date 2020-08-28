package com.chinatsp.code.reader;

import org.apache.poi.sl.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/8/28 11:00
 **/
@Component
public class Reader {

    /**
     * 从excel中读取各个Sheet
     * @param path 测试用例文件
     * @return Map
     */
    private Map<String, Sheet> readExcel(Path path){
        return null;
    }
}

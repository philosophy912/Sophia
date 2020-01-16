package com.philosophy;

import com.philosophy.base.util.FilesUtils;
import com.philosophy.excel.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @author lizhe
 * @since V1.0.0 2019/10/13 11:17
 **/
@Slf4j
public class BaseTestUtils {
    // 是否需要忽略某些测试结果 True表示忽略，False表示不忽略
    public static boolean IS_NEED_IGNORE = false;


    public static String getResourceFolder() {
        return FilesUtils.getCurrentPath() + "\\src\\test\\resources";
    }

    @Test
    public void specialTest() {
        if(!IS_NEED_IGNORE){
            String excel = "D:\\Workspace\\project\\tools\\src\\main\\resources\\dbc1\\HiFire_GSE_CONFCAN_IC_MMI_CAN_V0.87.xls";
            String sheetName = "CONFCAN";
            try {
                List<String[]> contents = ExcelUtils.read(Paths.get(excel), sheetName);
                for(String[] content: contents){
                    log.debug(Arrays.toString(content));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

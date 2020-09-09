package com.chinatsp.code;

import com.philosophy.base.util.FilesUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lizhe
 * @date 2020/9/9 16:43
 **/
@Slf4j
public class BaseTestUtils {

    public static String getResourceFolder() {
        return FilesUtils.getCurrentPath() + "\\src\\test\\resources";
    }
    public static String getFileFolder() {
        return FilesUtils.getCurrentPath() + "\\file";
    }
}

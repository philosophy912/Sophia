package com.chinatsp.automotive.service.api;

import java.nio.file.Path;

/**
 * @author lizhe
 * @date 2020/9/16 10:42
 **/
public interface ITemplateService {
    /**
     * 创建Excel的模板文件
     * @param path 文件地址（仅支持excel文件)
     */
    void createTemplate(Path path);
}

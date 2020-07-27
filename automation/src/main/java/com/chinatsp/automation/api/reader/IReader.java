package com.chinatsp.automation.api.reader;

import com.chinatsp.automation.entity.base.BaseEntity;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/6/3 12:57
 **/
public interface IReader<T extends BaseEntity> {
    /**
     * 读取内容
     * @param sheet excel文件
     * @return 内容
     */
    List<T> read(Sheet sheet);
}

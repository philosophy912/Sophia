package com.chinatsp.code.convert;

import com.chinatsp.code.entity.BaseEntity;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 将Sheet转换成为Entity
 * @author lizhe
 * @date 2020/8/27 16:04
 **/
@Component
public class Convert<T extends BaseEntity> {
    /**
     * 从excel中读取到
     * @param sheet excel的sheet
     * @return List<T>
     */
    public List<T> readExcel(Sheet sheet){
        return null;
    }
}

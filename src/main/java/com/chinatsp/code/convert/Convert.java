package com.chinatsp.code.convert;

import com.chinatsp.code.entity.BaseEntity;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;


/**
 * @author lizhe
 * @date 2020/8/27 16:04
 **/
@Component
public class Convert<T extends BaseEntity> {
    /**
     * 从excel中读取到
     * @param sheet excel的sheet
     * @return T
     */
    public T readExcel(Sheet sheet){
        return null;
    }
}

package com.philosophy.excel.api;

import com.philosophy.base.api.IWriter;

/**
 * @author lizhe。
 * @since V1.0.0 2019/10/20 9:19
 **/
public interface IExcelWriter<T> extends IWriter<T> {
    /**
     * 读取某个sheet
     *
     * @param sheetName sheet的名字
     */
    void createSheet(String sheetName);
}

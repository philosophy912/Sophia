package com.philosophy.excel.api;

import com.philosophy.base.api.IReader;

/**
 * @author lizhe
 * @since V1.0.0 2019/10/20 9:21
 **/
public interface IExcelReader<T> extends IReader<T> {
    /**
     * 读取某个sheet
     *
     * @param sheetName sheet的名字
     */
    void readSheet(String sheetName);

    /**
     * 读取指定sheet的指定行列的值
     *
     * @param sheetName   sheet的名字
     * @param rowIndex    行
     * @param columnIndex 列
     * @return 该cell的值
     */
    String read(String sheetName, int rowIndex, int columnIndex);
}

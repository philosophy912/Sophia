package com.chinatsp.automotive.utils;

import com.chinatsp.automotive.checker.Checker;
import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.writer.BaseWriter;

/**
 * @author lizhe
 * @date 2020/9/1 11:08
 **/
public interface Constant {
    // 基本包名
    String PACKAGE_NAME = BaseEntity.class.getPackage().getName();
    // 检查者的包名
    String CHECKER_PACKAGE_NAME = Checker.class.getPackage().getName() + ".impl";
    // Freemarker生成类的包名
    String WRITER_PACKAGE_NAME = BaseWriter.class.getPackage().getName() + ".impl";
    // 抽象基础实体类
    String BASE_ENTITY = PACKAGE_NAME + ".BaseEntity";
    // 英文的是
    String YES = "yes";
    // 中文的是
    String CHINESE_YES = "是";
    // 逗号
    String COMMA = ",";
    // 等号
    String EQUAL = "=";
    // 横线
    String LINE = "-";
    // 分割点
    String SPLIT_POINT = "\\.";
    // 换行
    String NEXT_LINE = "\n";
    // 左括号（英文)
    String LEFT_BRACKETS = "(";
    // 右括号（英文)
    String RIGHT_BRACKETS = ")";
    // 左括号（英文)
    String SPLIT_LEFT_BRACKETS = "\\(";
    // 右括号（英文)
    String SPLIT_RIGHT_BRACKETS = "\\)";

    //template 模板名字 action
    String TEMPLATE_ACTION = "action";
    //template 模板名字 compare
    String TEMPLATE_COMPARE = "compare";
    //template 模板名字 compare
    String TEMPLATE_CONFIG = "config";
    //template 模板名字 compare
    String TEMPLATE_CONTEXT = "context";
    //template 模板名字 compare
    String TEMPLATE_TESTCASE = "testcase";


    String TOP = "top";
    String CODES = "codes";
    String DBC = "dbc";
    String TEST_CASE = "testcase";
}

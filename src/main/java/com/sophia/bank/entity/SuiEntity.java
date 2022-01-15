package com.sophia.bank.entity;

import com.sophia.bank.api.ExchangeTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
public class SuiEntity {
    // 交易类型
    private ExchangeTypeEnum exchangeType;
    // 日期
    private String date;
    // 分类
    private String category;
    // 子分类
    private String sub_category;
    // 账户1
    private String account1;
    // 账户2
    private String account2;
    // 金额
    private Double amount;
    // 成员
    private String member = "";
    // 商家
    private String seller = "";
    // 项目
    private String project = "";
    // 备注
    private String comment = "";

}

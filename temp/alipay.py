# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        alipay.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/3 - 17:47
# --------------------------------------------------------
from automotive import logger


def read_alipay(file: str) -> list:
    with open(file, "r", encoding="gbk") as f:
        return f.readlines()


def filter_data(contents: list) -> list:
    contents = list(map(lambda x: x.replace("\n", ""), contents))
    return list(filter(lambda x: len(x.split(",")) == 17, contents))


def handle_data(contents: list) -> list:
    final_data = []
    contents.pop(0)
    for content in contents:
        # excel需要的数据是交易类型	日期	分类	子分类	账户1	账户2	金额	成员	商家	项目	备注
        logger.debug(content)
        content = content.split(",")
        # 付款时间
        pay_data = content[2]
        # 付款金额
        pay_amount = content[9]
        # 交易类型
        pay_type = content[10]
        # 交易状态
        pay_status = content[16]
        final_data.append((pay_data, pay_amount, pay_type, pay_status))
    return final_data


def tt():
    contents = read_alipay("alipay_record_20200903_1745_1.csv")
    contents = filter_data(contents)
    contents = handle_data(contents)
    for content in contents:
        logger.info(content)


tt()

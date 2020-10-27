# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        alipay.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/3 - 17:47
# --------------------------------------------------------
from automotive import logger, Utils
import xlwings as xw


def read_cmd(file: str) -> list:
    with open(file, "r", encoding="utf-8") as f:
        return f.readlines()


def filter_data(contents: list) -> list:
    contents = list(map(lambda x: x.replace("\n", ""), contents))
    # 去除多余的内容
    contents = list(filter(lambda x: len(x.split(" ")) == 6, contents))
    # 只保留具体内容
    contents = list(filter(lambda x: "/" in x, contents))
    # 去掉支付宝
    contents = list(filter(lambda x: "支付宝" not in x and "年费" not in x, contents))
    return contents


def split_contents(contents: list) -> list:
    exchanges = []
    # 交易类型	日期	分类	子分类	账户1	账户2	金额	成员	商家	项目	备注
    for content in contents:
        details = content.split(" ")
        date = details[0].replace("/", "-")
        pay_data = f"2020-{date}"
        pay_amount = details[3]
        pay_detail = details[2]
        pay_type = "支出"
        category = "购物消费"
        sub_category = "电子数码"
        account = "招行信用卡P"
        if "7FRESH" in content or "四季优选" in content:
            category = "食品酒水"
            sub_category = "超市购物"
        elif "餐饮" in content:
            category = "食品酒水"
            sub_category = "外出美食"
        elif "虾仁水饺" in content:
            category = "食品酒水"
            sub_category = "中餐"
        exchanges.append((pay_type, pay_data, category, sub_category, account, "", pay_amount, "", "", "", pay_detail))
    return exchanges


def write_excel(pay: list):
    # visible设置为False的时候可能产生错误
    app = xw.App(visible=False, add_book=False)
    wb = app.books.open("template.xls", read_only=True)
    pay_sht = wb.sheets["支出"]
    pay_sht.range("A2").value = pay
    file = f"template_cmb_{Utils.get_time_as_string()}.xls"
    wb.save(file)
    wb.close()
    app.quit()


def get_content(file: str) -> list:
    contents = read_cmd(file)
    contents = filter_data(contents)
    contents = split_contents(contents)
    return contents


def run(file: str):
    contents = get_content(file)
    write_excel(contents)


run(r"C:\Users\philo\Downloads\temp\Music1\CreditCardReckoning.txt")

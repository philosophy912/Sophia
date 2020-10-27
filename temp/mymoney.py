# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        mymoney.py.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/10/27 - 14:50
# --------------------------------------------------------
import xlwings as xw
from datetime import datetime
from xlwings import Sheet
from automotive import *
from cmb import *

format_str = "%Y-%m-%d"
start_date_time = "2020-01-01"
end_date_time = "2020-10-27"
account_type = "招行信用卡P"
cmb_file = r"C:\Users\philo\Downloads\temp\Music1\CreditCardReckoning.txt"
my_money_file = r"D:\Workspace\github\code\temp\myMoney.xls"
save_file = r"D:\Workspace\github\code\temp\1.txt"


def handle_sheet(sheet: Sheet, count: int, start_date: str = None, end_date: str = None):
    """
    处理每行数据返回对象，并做filter处理
    """
    start_date = datetime.strptime(start_date.strip(), format_str) if start_date else -1
    end_date = datetime.strptime(end_date.strip(), format_str) if end_date else -1
    lines = []
    for i in range(1, count):
        index = i + 1
        logger.debug(f"{index} line")
        category = sheet.range(f"B{index}").value
        account = sheet.range(f"D{index}").value
        exchange = sheet.range(f"F{index}").value
        date = sheet.range(f"J{index}").value
        date = date.split(" ")[0]
        date_time = datetime.strptime(date, format_str)
        logger.debug(f"{index} line's account={account}, date={date_time}")
        if start_date != -1 and end_date != -1:
            # 3. 两个都传入了就是区间
            if date_time < start_date:
                break
            elif start_date <= date_time <= end_date:
                lines.append((category, account, exchange, date))
        elif start_date != -1 and end_date == -1:
            # 1. 只传入start_date表示从开始第一行到start_date所在行
            if date_time < start_date:
                break
            elif start_date <= date_time:
                lines.append((category, account, exchange, date))
        elif start_date == -1 and end_date == -1:
            # 2. 只传入end_date表示从end_date到最后一行
            if date_time <= end_date:
                lines.append((category, account, exchange, date))
    lines = list(filter(lambda x: x[1].strip() == account_type, lines))
    return lines


def get_mymoney_content(excel_file):
    app = xw.App(visible=False, add_book=False)
    wb = app.books.open(excel_file, read_only=True)
    pay_sht = wb.sheets["支出"]
    max_row = pay_sht.used_range.last_cell.row
    lines = handle_sheet(pay_sht, max_row, start_date_time, end_date_time)
    logger.debug(f"total line is {len(lines)}")
    wb.close()
    return lines


def write_to_file(contents):
    with open(save_file, "w") as f:
        for content in contents:
            category, account, exchange, date = content
            f.write(f"{category}&&{account}&&{exchange}&&{date}\n")


def get_content_from_file(txt_file) -> list:
    contents = []
    with open(txt_file, "r") as f:
        lines = f.readlines()
        for line in lines:
            line = line.replace("\n", "")
            logger.debug(f"line = {line}")
            if line != "":
                line_sep = line.split("&&")
                category = line_sep[0]
                account = line_sep[1]
                exchange = line_sep[2]
                date = line_sep[3]
                contents.append((category, account, exchange, date))
    return contents


def test():
    missing = []
    cmb_contents = get_content(cmb_file)
    # my_money_contents = get_mymoney_content(my_money_file)
    my_money_contents = get_content_from_file(save_file)
    for cmb in cmb_contents:
        cmb_date = cmb[1]
        cmb_date = datetime.strptime(cmb_date, format_str)
        cmb_price = cmb[6]
        logger.debug(f"cmb_date = {cmb_date} and cmb_price = {cmb_price} and type is {type(cmb_price)}")
        for my_money in my_money_contents:
            my_money_date = my_money[3]
            my_money_date = datetime.strptime(my_money_date, format_str)
            my_money_price = my_money[2]
            logger.debug(f"my_money_date = {my_money_date} and my_money_price = {my_money_price} "
                        f"and type is {type(my_money_price)}")
            if cmb_date == my_money_date and cmb_price.strip() == my_money_price.strip():
                break
        missing.append(cmb)
    for miss in missing:
        logger.info(miss)
    logger.info("done")


test()
# write_to_file(get_mymoney_content(my_money_file))

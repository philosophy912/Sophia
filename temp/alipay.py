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
        pay_data = content[2].strip()
        # 付款金额
        pay_amount = content[9].strip()
        # 交易类型
        pay_type = content[10].strip()
        # 交易状态
        pay_status = content[16].strip()
        # 详情
        pay_detail = f"{content[8].strip()},{content[7].strip()}"
        if pay_type != "":
            final_data.append((pay_data, pay_amount, pay_detail, pay_type, pay_status))
    return final_data


def __filter_condition(x: tuple) -> list:
    condition1 = (x[3] == "支出")
    condition2 = ("蚂蚁财富" not in x[2])
    condition3 = ("李小花" not in x[2])
    condition4 = ("医保消费" not in x[2])
    condition5 = ("熊纪涛" not in x[2])
    return condition1 and condition2 and condition3 and condition4


def separate_type(contents: list) -> tuple:
    pay = list(filter(__filter_condition, contents))
    in_come = list(filter(lambda x: x[3] == "收入", contents))
    return pay, in_come


def get_category(pay_detail: str, pay_amount: str) -> tuple:
    if "商品" in pay_detail or "亲情卡" in pay_detail:
        account = "支付宝P"
    else:
        account = "招行信用卡P"
    # 类别
    category = "食品酒水"
    sub_category = "早餐"

    if "唐家臻记" in pay_detail or "紫燕百味鸡" in pay_detail:
        sub_category = "外购凉菜"
    elif "亲情卡" in pay_detail:
        category = "人情费用"
        sub_category = "孝敬父母"
    elif "金忠食品" in pay_detail:
        sub_category = "肉类"
    elif "阿蠔海鲜焖面" in pay_detail or "卢婆婆姜鸭面" in pay_detail \
            or "顺旺基" in pay_detail or "荟福源" in pay_detail or "宜宾燃面" in pay_detail \
            or "享米时" in pay_detail or "老麻抄手" in pay_detail or "西北面点王" in pay_detail \
            or "袁记云饺" in pay_detail or "籣州牛肉面" in pay_detail or "成都鸡汤抄手" in pay_detail \
            or "大巴山猪脚饭" in pay_detail or "卤鹅饭" in pay_detail or "e特黄焖鸡瓦香鸡成都店" in pay_detail \
            or "杨铭宇黄焖鸡米饭" in pay_detail or "八二小区干海椒抄手" in pay_detail or "晓武林烤鸭" in pay_detail \
            or "乡村基" in pay_detail or "戊丰记卤肉饭" in pay_detail or "沙县小吃成都银泰城店" in pay_detail:
        sub_category = "中餐"
    elif "书亦烧仙草" in pay_detail:
        sub_category = "饮料"
    elif "相互宝" in pay_detail:
        category = "金融保险"
        sub_category = "人身保险"
    elif "成都地铁运营有限公司" in pay_detail:
        category = "行车交通"
        sub_category = "地铁"
    elif "天府通APP" in pay_detail:
        category = "行车交通"
        if pay_amount == "1.80":
            sub_category = "公交"
        else:
            sub_category = "地铁"
    elif "*登梅" in pay_detail or "雪梅" in pay_detail or "*瑞林" in pay_detail \
            or "思忠" in pay_detail or "*琴" in pay_detail or "兰兰姐" in pay_detail \
            or "*再泉" in pay_detail or "春儿" in pay_detail or "蔡德文" in pay_detail:
        sub_category = "蔬菜"
    elif "雪糕批发" in pay_detail:
        sub_category = "零食"
    elif "邓哥鱼铺" in pay_detail or "龙仕林" in pay_detail or "成都泥厨子大食堂" in pay_detail:
        sub_category = "肉类"
    elif "金翠河烧鹅餐厅" in pay_detail or "马帮冒菜" in pay_detail or "实惠啤酒鸭" in pay_detail \
            or "青年火锅店" in pay_detail:
        sub_category = "外出美食"
    elif "谢孝元" in pay_detail:
        sub_category = "面"
    elif "周小霞" in pay_detail:
        sub_category = "早餐"
    elif "无感支付" in pay_detail or "停车场" in pay_detail:
        category = "行车交通"
        sub_category = "停车"
    elif "燃气费" in pay_detail:
        category = "居家生活"
        sub_category = "燃气费"
    elif "电费" in pay_detail:
        category = "居家生活"
        sub_category = "燃气费"
    elif "星巴克" in pay_detail or "书亦燒仙草" in pay_detail or "Mii Coffee" in pay_detail:
        sub_category = "饮料"
    elif "滴滴快车" in pay_detail:
        category = "行车交通"
        sub_category = "打车"
    elif "火车票" in pay_detail:
        category = "行车交通"
        sub_category = "火车"
    elif "中国电信官方旗舰店" in pay_detail:
        category = "交流通讯"
        sub_category = "手机费"
    elif "物业管理费" in pay_detail:
        category = "居家生活"
        sub_category = "物管费"
    elif "壳牌" in pay_detail:
        category = "行车交通"
        sub_category = "加油"
    return category, sub_category, account


def handle_pay(pay: list) -> list:
    # 转换成支出支持的方式
    # 交易类型	日期	分类	子分类	账户1	账户2	金额	成员	商家	项目	备注
    contents = []
    for content in pay:
        pay_data, pay_amount, pay_detail, pay_type, pay_status = content
        category, sub_category, account = get_category(pay_detail, pay_amount)
        contents.append((pay_type, pay_data, category, sub_category, account, "", pay_amount, "", "", "", pay_detail))
    return contents


def handle_in_come(in_come: list) -> list:
    # 转换成收入支持的方式
    # 交易类型	日期	分类	子分类	账户1	账户2	金额	成员	商家	项目	备注
    contents = []
    for content in in_come:
        pay_data, pay_amount, pay_detail, pay_type, pay_status = content
        # 类别
        category = "职业收入"
        sub_category = "利息收入"
        account = "支付宝P"
        contents.append((pay_type, pay_data, category, sub_category, account, "", pay_amount, "", "", "", pay_detail))
    return contents


def write_excel(pay: list, in_come: list):
    # app = xw.App(False, False)
    wb = xw.Book("template.xls")
    pay_sht = wb.sheets["支出"]
    in_come_sht = wb.sheets["收入"]
    pay_sht.range("A2").value = pay
    in_come_sht.range("A2").value = in_come
    file = f"template_{Utils.get_time_as_string()}.xls"
    wb.save(file)
    wb.close()


def run(file: str):
    contents = read_alipay(file)
    contents = filter_data(contents)
    contents = handle_data(contents)
    pay, in_come = separate_type(contents)
    pay = handle_pay(pay)
    in_come = handle_in_come(in_come)
    write_excel(pay, in_come)


if __name__ == '__main__':
    run("alipay_record_20200904_1020_1.csv")

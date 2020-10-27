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

# 区分人
people = True


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
    condition1 = ("蚂蚁财富" not in x[2])
    condition2 = ("李小花" not in x[2]) if people else ("李哲" not in x[2])
    condition3 = ("医保消费" not in x[2])
    condition4 = ("理财买入" not in x[2])
    condition5 = ("大药房连锁" not in x[2])
    condition6 = ("基金申购" not in x[2])
    condition7 = ("基金销售" not in x[2])
    return condition1 and condition2 and condition3 and condition4 and condition5 and condition6 and condition7


def separate_type(contents: list) -> tuple:
    out_come = list(filter(lambda x: x[3] == "支出", contents))
    pay = list(filter(__filter_condition, out_come))
    in_come = list(filter(lambda x: x[3] == "收入", contents))
    return pay, in_come


def check_detail(pay_detail: str, check_list: (list, tuple)) -> bool:
    for content in check_list:
        if content in pay_detail:
            return True
    return False


def get_category(pay_detail: str, pay_amount: str) -> tuple:
    alipay = "商品", "亲情卡"
    if people:
        if check_detail(pay_detail, alipay):
            account = "支付宝P"
        else:
            account = "招行信用卡P"
    else:
        account = "花呗S"
    # 类别
    category = "食品酒水"
    sub_category = "早餐"
    outsource = "唐家臻记", "紫燕百味鸡", "敖锦记烫油鹅", "掌柜土鸡片"
    lunch = "阿蠔海鲜焖面", "卢婆婆姜鸭面", "顺旺基", "荟福源", "宜宾燃面", "享米时", "老麻抄手", "西北面点王", \
            "袁记云饺", "籣州牛肉面", "成都鸡汤抄手", "大巴山猪脚饭", "卤鹅饭", "e特黄焖鸡瓦香鸡成都店", \
            "杨铭宇黄焖鸡米饭", "八二小区干海椒抄手", "晓武林烤鸭", "乡村基", "戊丰记卤肉饭", "沙县小吃成都银泰城店", \
            "喜水饺", "兵哥豌豆面", "福记羊肉米粉", "岭南牛杂", "自小田", "搪瓷盌小面成都伏龙北巷", "蚝门圣焱", "本味简餐厅", \
            "粤饺皇", "南城香冒烤鸭卤肉饭", "贰柒拾乐山干绍面", "拾小馆", "陕西面馆", "干辣椒抄手", "豆汤饭"
    vegetables = "登梅", "雪梅", "思忠", "*琴", "兰兰姐", "*再泉", "春儿", "蔡德文", "沈德全", "小兰蔬菜店", \
                 "玲利", "邓花椒"
    meat = "金忠食品", "邓哥鱼铺", "龙仕林", "成都泥厨子大食堂", "章相山", "ZXS", "黑龙滩生态鱼铺", "谢氏冷鲜店", "良波"
    out_eat = "金翠河烧鹅餐厅", "马帮冒菜", "实惠啤酒鸭", "麦当劳", "食其家", "正反面", "青羊区东方宫牛肉拉面店", "成都港九餐饮", \
              "八二私房水饺", "鱼吖吖（武侯店）", "口味鲜面庄", "叶抄手", "雷四孃小吃", "朱记蛙三", "火舞凉山西昌原生烧烤", \
              "万州烤鱼", "肯德基", "巴山豆花饭成都", "卡萨马可", "老北京炸酱面", "禾木烤肉", "峨眉山周记烧烤", "青年火锅店", \
              "茵赫餐饮管理", "汉堡王", "热恋冰淇淋", "初壹饺子", "点都德", "跷脚牛肉", "外卖订单"
    drink = "书亦烧仙草", "星巴克", "书亦燒仙草", "Mii Coffee", "茶百道", "瑞幸咖啡", "GREYBOX COFFEE", "可口可乐", \
            "日记咖啡馆", "丸摩堂"
    super_market = "成都市北城天街店", "成都荆竹中路店", "麦德龙", "欧尚成都市高新店", "谊品生鲜", "高新店", "成都盒马", \
                   "成都中营贸易", "招商雍华府店", "万家V+南区", "银犁冷藏"
    snacks = "永辉(成都市银泰城店)", "面包新语(银泰城店)", "雪糕批发"
    pets = "鸡胸肉鲜", "猫", "伍德氏", "激光笔", "瑞爱康宠物医院", "猫砂"
    treat = "先生的酒桌"
    if check_detail(pay_detail, outsource):
        sub_category = "外购凉菜"
    elif "水果" in pay_detail:
        sub_category = "水果"
    elif check_detail(pay_detail, super_market):
        sub_category = "超市购物"
    elif check_detail(pay_detail, snacks):
        sub_category = "零食"
    elif check_detail(pay_detail, pets):
        category = "休闲娱乐"
        sub_category = "宠物"
    elif check_detail(pay_detail, treat):
        category = "人情费用"
        sub_category = "请客"
    elif "亲情卡" in pay_detail:
        category = "人情费用"
        sub_category = "孝敬父母"
    elif check_detail(pay_detail, meat):
        sub_category = "肉类"
    elif check_detail(pay_detail, lunch):
        sub_category = "中餐"
    elif check_detail(pay_detail, drink):
        sub_category = "饮料"
    elif check_detail(pay_detail, ("众安在线", "相互宝")):
        category = "金融保险"
        sub_category = "人身保险"
    elif "成都地铁运营有限公司" in pay_detail or "轨道交通" in pay_detail or "成都地铁" in pay_detail:
        category = "行车交通"
        sub_category = "地铁"
    elif "天府通APP" in pay_detail or "公共交通" in pay_detail:
        category = "行车交通"
        if pay_amount <= "1.80" or pay_amount == "2.00":
            sub_category = "公交"
        else:
            sub_category = "地铁"
    elif check_detail(pay_detail, vegetables):
        sub_category = "蔬菜"
    elif check_detail(pay_detail, out_eat):
        sub_category = "外出美食"
    elif "谢孝元" in pay_detail or "高筋鲜面" in pay_detail:
        sub_category = "面"
    elif "无感支付" in pay_detail or "停车场" in pay_detail or "*瑞林" in pay_detail:
        category = "行车交通"
        sub_category = "停车"
    elif "燃气费" in pay_detail:
        category = "居家生活"
        sub_category = "燃气费"
    elif "电费" in pay_detail:
        category = "居家生活"
        sub_category = "电费"
    elif "滴滴快车" in pay_detail:
        category = "行车交通"
        sub_category = "打车"
    elif "火车票" in pay_detail:
        category = "行车交通"
        sub_category = "火车"
    elif check_detail(pay_detail, ("中国移动", "中国电信")):
        category = "交流通讯"
        sub_category = "手机费"
    elif check_detail(pay_detail, ("重庆华宇", "物业管理费")):
        category = "居家生活"
        sub_category = "物管费"
    elif "壳牌" in pay_detail:
        category = "行车交通"
        sub_category = "加油"
    elif "宜家家居" in pay_detail:
        category = "购物消费"
        sub_category = "家居日用"
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
    app = xw.App(visible=True, add_book=False)
    wb = app.books.open("template.xls")
    if people:
        in_come_sht = wb.sheets["收入"]
        in_come_sht.range("A2").value = in_come
    pay_sht = wb.sheets["支出"]
    pay_sht.range("A2").value = pay
    file = f"template_{Utils.get_time_as_string()}.xls"
    wb.save(file)
    wb.close()
    app.quit()


def run(file: str):
    contents = read_alipay(file)
    contents = filter_data(contents)
    contents = handle_data(contents)
    pay, in_come = separate_type(contents)
    pay = handle_pay(pay)
    in_come = handle_in_come(in_come)
    write_excel(pay, in_come)


if __name__ == '__main__':
    run(r"D:\Download\Chrome\alipay_record_20201027_1314\alipay_record_20201027_1314_1.csv")

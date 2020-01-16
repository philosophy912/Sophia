# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        message
# @Purpose:     针对解析出来的Message进行一些操作
# @Author:      lizhe
# @Created:     2019/8/21 9:47
# --------------------------------------------------------
from automatedtest.common.tools.logger.logger import logger
from automatedtest.common.can.service.tools import Tools


class Message(object):

    def __init__(self, messages):
        self._messages = messages
        self._tools = Tools()

    def get_msg_name_by_id(self, message_id):
        """
            根据指定的message ID获取message的名字
            :param message_id: message ID
            :return: message name,查询不到则抛出异常
        """
        for msg in self._messages:
            if msg['id'] == message_id:
                return msg['name']
        raise ValueError(f"cannot found message({message_id}) in dbc file")

    def get_msg_obj(self, message):
        """
            根据指定的message ID或者name获取Message对象
            :param message: message ID或者name
            :return: message ID或者name对应的message对象，查询不到则抛出异常
        """
        if not isinstance(message, (str, int)):
            raise ValueError(f"Message({message}) type is not str or int, cannot get Message")
        for msg in self._messages:
            if message == msg['name'] or message == msg['id']:
                return msg
        raise ValueError(f"cannot found message({message}) Name or ID in dbc file")

    def get_signals_in_msg(self, msg_id):
        """
            获取指定的msg_id对应的所有的Signals列表
            :param msg_id: MessageID
            :return: 所有Signals的名字列表["BCM_TireSignalStatus"]
        """
        message = self.get_msg_obj(msg_id)
        signals = message["signals"]
        signal_names = []
        for signal in signals:
            signal_names.append(signal["name"])
        logger.debug("signals is " + str(signal_names))
        return signal_names

    def get_msg_by_signal(self, signal_name):
        """
            根据局signal的名字查找所在的Message
            :param signal_name: signal的名字，如IP_EngineOffTime
            :return:  message对象
        """
        # logger.debug(F"param signal_name value is {signal_name}")
        if not isinstance(signal_name, str):
            raise ValueError(f"signal_name({signal_name}) is not str")
        for message in self._messages:
            signals = message["signals"]
            for signal in signals:
                if signal["name"] == signal_name:
                    return message
        raise ValueError(f"not found signal_name({signal_name}) in dbc")

    def get_signal(self, signal_name, message=None):
        """
            根据Signal Name来获取Signal,可以传入Message（dicts/ID/NAME),如果传入则只在Message中查找
            :param signal_name: signal的名字，如"IP_EngineOffTime",
            :param message: message的名字/值，如IP_380/896
            :return:
        """
        if not isinstance(message, dict):
            message_obj = self.get_msg_by_signal(signal_name)
        else:
            # 传入的是message对象
            message_obj = message
        signals = message_obj['signals']
        for signal in signals:
            if signal['name'] == signal_name:
                return signal
        raise ValueError(f"not found Signal({signal_name}) in Message({message})")

    def get_all_signals(self, filter_):
        """
            收集所有的Signals
            :param filter_: 要过滤的sender
            :return: 所有的signal
        """
        signals = dict()
        for message_obj in self._messages:
            # 过滤HU发送的消息不再发给HU避免冲突
            if message_obj["sender"] != filter_:
                signals = message_obj['signals']
                for signal in signals:
                    signals[signal["name"]] = [message_obj["id"], signal["Minimum"], signal["Maximum"]]
        return signals

    def get_all_msgs(self, filter_):
        """
            收集所有的Messages
            :param filter_: 要过滤的sender
            :return: 所有的message
        """
        msgs = []
        for message_obj in self._messages:
            # 过滤HU发送的消息不再发给HU避免冲突
            if message_obj["sender"] != filter_:
                msgs.append(message_obj)
        return msgs

    def get_cycle_time(self, message):
        """
            获取周期时间
            :param message: 帧ID或者名字
            :return:该信号的周期时间
        """
        message_obj = self.get_msg_obj(message)
        return message_obj["GenMsgCycleTime"]

    def get_start_and_length(self, signal):
        """
            获取start和length的值
            :param signal: 信号名称
            :return: start和length的值
        """
        if isinstance(signal, dict):
            return signal['start_bit'], signal['bit_length']
        elif isinstance(signal, str):
            if -1 == signal.find("-"):
                signal_obj = self.get_signal(signal)
                return signal_obj['start_bit'], signal_obj['bit_length']
            else:
                name = signal.split("-")
                return int(name[0]), int(name[1])
        else:
            raise ValueError(f"signal({signal}) is not dict or str")

    def check_msg(self, message):
        """
            检查message是否符合规范
            :param message: message名称或者值
            :return: True|False
        """
        if isinstance(message, int):
            return self._tools.check_value(message, 0, 0x7ff)
        else:
            try:
                self.get_msg_obj(message)
                return True
            except Exception as e:
                logger.warn(e)
                return False

    def check_signal_in_dbc(self, signals):
        """
            检查Signal信号在DBC中是否能找到
            :param signals: 列表类型["IP_EngineOffTime","IP_EngineOffTime"]
            :return: True|False
        """
        for signal in signals:
            try:
                self.get_signal(signal)
            except Exception as e:
                logger.warn(e)
                return False
        return True

    def check_signal_value(self, signals, is_need_check=False):
        """
            校验每个Signals值是否符合要求（主要校验是否处于物理值的最大最小之间)
            :param signals:  {"PEPS_Easy_open":0x5, "IP_EngineOffTime":0x1}
            :param is_need_check:  是否校验 (默认不校验）
            :return: 成功返回True，否则返回False
        """
        if is_need_check:
            for signal in list(signals.keys()):
                value = signals[signal]
                try:
                    signal_obj = self.get_signal(signal)
                    min_ = signal_obj["Minimum"]
                    max_ = signal_obj["Maximum"]
                    if signal_obj["is_float"]:
                        if not (float(min_) <= float(value) <= float(max_)):
                            logger.warn(f"float value({value}) is not in [min({min_}), max({max_})]")
                            return False
                    else:
                        if not (int(float(min_)) <= int(value) <= int(float(max_))):
                            logger.warn(f"int value({value}) is not in [min({min_}), max({max_})]")
                            return False
                except Exception as e:
                    logger.warn(e)
                    return False
            return True
        else:
            return True

    def check_signal(self, signals):
        """
        检查Signal信号是否正确。
        :param signals: 信号对象，可以是CAN消息中的信号（字典类型），也可以是8位数字的信号（列表类型）
        :return:
                如果是列表则校验每位必须小于0XFF且大于0，否则返回False
                如果是字典则分为{"IP_EngineOffTime":0x2}和{"0-5":0x2}两种模式
                1. 所有信号能从dbc中找到且属于同一个message，且value处于最大值和最小值之间则返回True
                2. 如果bit-length有重叠或者value超出了最大最小值，则返回False
        """
        # 只允许传入dict或者list类型
        if not isinstance(signals, (list, dict)):
            logger.warn(f"signals({signals}) type is not list or dict")
            return False
        result = True
        # 如果是list类型，不校验8位，只校验每个byte都必须小于0XFF
        if isinstance(signals, list):
            logger.debug(f"Signals({signals}) is list")
            if 8 != len(signals):
                logger.error("signals length is not equal 8 Byte")
                return False
            for value in signals:
                result = result and self._tools.check_value(value, 0, 0xff)
            return result
        else:
            # 获取keys
            keys = list(signals.keys())
            if not self._tools.check_signal_type(keys):
                logger.warn("Signals key must be same type")
                return False
            # 名字模式
            # a. 能否从dbc文件中找到该signal，
            # b. 所有signal是否为同一个message
            # c. value是否处于每个signal的最大最小值之间
            if keys[0].find("-") == -1:
                logger.debug("name mode check")
                return self.check_signal_in_dbc(keys) and self.is_signals_in_msg(keys) and self.check_signal_value(
                    signals)
            # 数字模式
            # a. 检查value是不是超出最大值
            # b. 检查bit-length是否重叠
            else:
                message_list = []
                for x in range(64):
                    message_list.append(True)
                for key in keys:
                    value = signals[key]
                    start, length = key.split("-")
                    start = int(start)
                    length = int(length)
                    if not (self._tools.check_value(start, 0, 0x3f) and
                            self._tools.check_value(length, 0, 0x3f)):
                        logger.warn(f"start({start}) and length({length}) not in [0, 63]")
                        return False
                    else:
                        result = result and self._tools.check_value(value, 0, (2 ** length) - 1)
                    real_start = self._tools.get_position(start)
                    for i in range(real_start, (real_start - length), -1):
                        if message_list[i]:
                            message_list[i] = False
                        else:
                            logger.warn(f"start-bit area is overlapping start({start}) + length({length})")
                            return False
        return result

    def is_msg_in_dbc(self, message):
        """
            查询msg_id在DBC中是否存在
            :param message: 帧ID， 仅识别ID值，不识别名称
            :return: 成功返回True，否则返回False
        """
        try:
            self.get_msg_obj(message)
            return True
        except Exception as e:
            logger.warn(e)
            return False

    def is_signals_in_msg(self, signals, msg_id=None):
        """
            判断Signals是否属于同一个message
            :param signals: 信号列表， 如["IP_EngineOffTime","PEPS_Easy_open"]
            :param msg_id: 帧ID， 仅识别ID值，不识别名称
            :return: 信号列表中所有的信号都属于同一个message则返回True， 否则返回False
        """
        ids = []
        try:
            if msg_id is None:
                for signal in signals:
                    id_ = self.get_msg_by_signal(signal)['id']
                    ids.append(id_)
                msg_ids = set(ids)
                return len(msg_ids) == 1
            else:
                for signal in signals:
                    id_ = self.get_msg_by_signal(signal)['id']
                    if msg_id != id_:
                        return False
                return True
        except Exception as e:
            logger.warn(e)
            return False

    def calc_value(self, value, signal_name, flag):
        """
            计算物理值，如车速，温度等
            :param value: 物理值
            :param signal_name: 信号名字
            :param flag: True表示发送前设置，False表示收到前解析
            :return: 计算后的值（物理值/CAN消息值）
        """
        logger.debug(f"param value is {value} and flag is {flag} signal name is {signal_name}")
        signal_obj = self.get_signal(signal_name)
        if flag:
            calc_value = (float(value) - float(signal_obj["offset"])) / float(signal_obj["factor"])
        else:
            calc_value = (float(value) * float(signal_obj["factor"])) + float(signal_obj["offset"])
        logger.debug(f"after convert value is {calc_value}")
        return int(float(calc_value))

    def convert_signal(self, signals):
        """
        转换信号{"IP_EngineOffTime":0x2}为 {"0-5":0x2}，如果是后者则不进行转换
        :param signals: {"IP_EngineOffTime":0x2}
        :return: {"0-5":0x2}
        """
        result = dict()
        logger_result = dict()
        logger.debug(f"signals is {signals}")
        for name in list(signals.keys()):
            logger.debug(f"name is {name}")
            signal_name = self.convert_signal_name(name)
            result[signal_name] = self.calc_value(signals[name], name, True)
            logger_result[name] = result[signal_name]
        logger.info(logger_result)
        return result

    def convert_signal_name(self, signal_name):
        """
            转换信号IP_EngineOffTime为0-5如果是后者则不进行转换
            :param signal_name: signal_name(信号名）
            :return: 信号名对应的起始位-长度，如 15-7
        """
        if -1 == signal_name.find("-"):
            signal_obj = self.get_signal(signal_name)
            signal_name = str(signal_obj['start_bit']) + "-" + str(signal_obj['bit_length'])
        return signal_name

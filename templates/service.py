# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        service.py.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/16 - 16:33
# --------------------------------------------------------
from automotive import CANService, USBRelay
from .configure import *


class Service(object):

    def __init__(self):
        if dbcJson:
            self.can_service = CANService(dbcJson)

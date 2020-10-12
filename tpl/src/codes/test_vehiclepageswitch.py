# -------------------------------------------------------------------------------
# Name:        test_vehiclepageswitch.py
# Purpose:     The file is automatically generated by tools.
# Author:      CD QA Team
# Created:     2020-10-12 23:05
# -------------------------------------------------------------------------------
import allure
import pytest
from time import sleep
from src.codes.context import *


########################################################################################################################
#                                                                                                                      #
#                                                    创建Suite                                                          #
#                                                                                                                      #
########################################################################################################################
@pytest.fixture(scope="class", autouse=True)
@allure.suite("创建vehiclePageSwitch测试套件")
def suite():
    with allure.step("打开CAN盒子"):
        open_device()
        sleep(10)
    yield suite
    with allure.step("关闭CAN盒子"):
        close_device()


########################################################################################################################
#                                                                                                                      #
#                                                    创建Function                                                       #
#                                                                                                                      #
########################################################################################################################
@pytest.fixture(scope="function", autouse=True)
def function():
    with allure.step("无"):
        pass
    yield
    with allure.step("无"):
        pass


########################################################################################################################
#                                                                                                                      #
#                                                    创建测试用例                                                        #
#                                                                                                                      #
########################################################################################################################
@allure.feature("module")
@pytest.mark.usefixtures("suite")
class TestVehiclePageSwitch(object):

    @pytest.mark.usefixtures("function")
    @allure.title("充电界面")
    def test_charging_status(self):
        """
        Description:
            充电界面
        PreCondition:
            1.电源状态：OFF
            2.所有指示灯均熄灭
            3.剩余电量：0
        Steps:
            1.充电状态指示设置为True：
            VCU_ChrgStsIndcr=0x1
            VCU_ChrgConnectIndcr=0x1
        Expect Result:
            1.仪表显示充电状态界面，充电状态和充电连接指示灯激活
        """
        # 前置条件
        with allure.step("前置条件"):
            power_off()
            battery_surplus_display_0()
        # 执行步骤
        with allure.step('操作步骤'):
            vcu_chrgstsindcr_lists_open()
            vcu_chrgconnectindcr_lists_open()
            charging_status()
        # 期望结果
        with allure.step('期望结果'):
            compare(compare_charging_status())

    @pytest.mark.usefixtures("function")
    @allure.title("结束充电，回到停车界面")
    def test_charging_parking_status(self):
        """
        Description:
            结束充电，回到停车界面
        PreCondition:
            1.电源状态：OFF
            2.所有指示灯均熄灭
            3.显示正在充电：
            VCU_ChrgStsIndcr=0x1
            VCU_ChrgConnectIndcr=0x1
        Steps:
            1.结束充电：
            VCU_ChrgStsIndcr=0x0
            VCU_ChrgConnectIndcr=0x0
        Expect Result:
            1.回到停车界面
        """
        # 前置条件
        with allure.step("前置条件"):
            power_off()
            vcu_chrgstsindcr_lists_open()
            vcu_chrgconnectindcr_lists_open()
        # 执行步骤
        with allure.step('操作步骤'):
            vcu_chrgstsindcr_lists_close()
            vcu_chrgconnectindcr_lists_close()
            charging_parking_status()
        # 期望结果
        with allure.step('期望结果'):
            compare(compare_charging_parking_status())

------

# 仪表自动化代码生成工具

------

### 工具说明



本工具仅用于根据dbc文件或者excel描述的dbc文件和测试用例的测试用例文件生成自动化测试代码。

首先，需要准备项目相关的DBC文件，如: **HiFire_B31CP_Info_HU_CAN_V2.0.dbc**或者**HiFire_B31CP_Info_HU_CAN_V2.0.xls**文件

其次，需要准备测试用例的描述文件，如：**仪表自动化测试用例汇总_v1.4.xlsx**

**请注意Excel的模板文件的头必须包含英文名，否则工具无法自动解析**

Excel分为六个Sheet

- 测试用例（适用于仪表和空调屏测试)

  **测试用例包含两个部分**

  - 第一部分

    | id<br/>序号 | category<br/>类别       | preCondition<br/>前置条件 | preConditionFunctions<br/>前置条件函数名     | steps<br/>执行步骤                                | stepsFunctions<br/>执行步骤函数名                            | expect<br/>期望结果           | expectFunctions<br/>期望结果函数名 | functionName<br/>测试用例函数名  | description<br/>测试用例描述 |
    | ----------- | ----------------------- | ------------------------- | -------------------------------------------- | ------------------------------------------------- | ------------------------------------------------------------ | ----------------------------- | ---------------------------------- | -------------------------------- | ---------------------------- |
    | 1           | VehicleDriveInfodisplay | 1.电源状态：ACC           | CONDITION=Power_IGN<br/>ACTION=setActlGear_P | 1.设置续航里程最大值：VCU_VhclResidualMile  = 800 | ACTION=VhclActlPwr_120<br/>Sleep=5<br/>ACTION=VehicleSpeed_10 | 1.仪表显示剩余续驶里程为800km | Light=VhclResidualMile_800_ACC     | Vehicle_residual_mileage_ACC_800 | 剩余里程测试                 |

    说明：

    - **类别**： 类别的数量决定了测试用例生成的数量，用于区分各个模块的测试用例

    - **前置条件/函数名**: 前置条件的描述文字以及前置条件要执行的函数，必须以=号连接，左边必须为CONDITION或者ACTION来描述, 右边则为**执行动作**中相关描述的函数名。

    - **执行步骤/函数名**： *执行步骤的描述文字以及执行步骤要执行的函数*，目前支持如下情况：

      - Condition = xxx (其中xxx是**动作**表中的函数名)

      - Action = xxx (同上)

      - Sleep = xx (表示等待，代码生成会生成sleep(xxx))

      - CLEAR (表示清空CAN的stack消息，用于做CAN消息接收判断)

      - LOST （表示让CAN上所有消息停发)

      - PAUSE=xxx (表示暂停can上某个msg id的消息停发)

      - RESUME = xxx  (表示恢复Can上某个msg_id的消息发送)

      - RESUME （表示恢复CAN上所有曾经发送过的MSG id的消息发送)

        下面三个消息仅空调屏可使用\

      - CLICK = xxx (表示xxx是**屏幕操作**表中的函数名)

      - SLIDE = xxx (同上)

      - PRESS = xxx (同上)

    - **期望结果/函数名**:  期望结果的描述文字以及期望结果图片对比的相关图片对比文件名字, 必须以=号连接，左边必须为LIGHT/DARK/BLINK四种方式来描述，右边则为函数名（影响生成的json/py文件中字典的key)

    - **测试用例函数名**： 测试用例函数名，不能够重复。

    - **测试用例描述**： 对于测试用例进行的描述

  - 第二部分

    | pictureTemplateDark<br/>暗图 | pictureTemplateLight<br/>亮图 | position<br/>比较区域          | threshold<br/>对比阈值 | isArea<br/>是否区域截图<br/>（默认为否) | screenShotCount<br/>截图张数<br/>（默认1张) | holdRgb<br/>保留颜色<br/>(默认不处理) | isGray<br/>是否灰度对比<br/>(默认为否) | grayThreshold<br/>灰度二值化阈值<br/>（默认240） | displayIndex<br/>屏幕序号 |
    | ---------------------------- | ----------------------------- | ------------------------------ | ---------------------- | --------------------------------------- | ------------------------------------------- | ------------------------------------- | -------------------------------------- | ------------------------------------------------ | ------------------------- |
    | AllLightOff.bmp              | ResidualMile_800.bmp          | 1232-36-55-49<br/>633-39-58-46 | 99.01                  | 否                                      | 5                                           | 3-5-7                                 | 否                                     | 240                                              | 1                         |

    说明：

    - **暗图**： 暗图的标准文件。 暗图指灯消失的图
    - **亮图**：亮图的标准文件。亮图指灯亮的图
    - **比较区域** : 要查找的区域，可以是多个， 以-分割，分别表示x, y, width, height
    - **对比阈值**： 图像对比的精度， 即相似度.
    - **是否区域截图**：是否要进行区域截图对比，默认为否
    - **截图张数**：截图张数, 如果是BLINK对比，图片默认为5张， 如果是LIGHT和DARK对比，则图片默认为1张。
    - **保留颜色**：是否去掉干扰颜色的方式来处理，默认为-1,-1,-1， 以RGB方式来填写 
    - **是否灰度对比**：是否进行灰度对比，默认为否
    - **灰度二值化阈值**：设置灰度二值化阈值，默认为240
    - **屏幕序号**（*针对空调屏特有*): 要在那个屏幕上进行截屏

    带默认值的项目可以不填写，如果需要默认值，请填写“否”,如果填写其他则表示"是"

- 屏幕测试用例（适用仪表)

  | id<br/>序号 | category<br/>类别 | preCondition<br/>前置条件                                    | preConditionFunctions<br/>前置条件函数名     | steps<br/>执行步骤         | stepsFunctions<br/>执行步骤函数名 | expect     期望结果                | functionName<br/>测试用例函数名 | canCompares<br/>消息检查                                     |
  | ----------- | ----------------- | ------------------------------------------------------------ | -------------------------------------------- | -------------------------- | --------------------------------- | ---------------------------------- | ------------------------------- | ------------------------------------------------------------ |
  | 1           | ESC               | 1.电源状态：IGN ON<br/>2.车身稳定开关关闭：<br/>WorkingSt_ESC = 0x0 | Condition=IGN_ON<br/>ACTION=Close_ESC_Switch | 1.点击车身稳定开关请求打开 | CLEAR<br/>CLICK=click_esc         | 1.正常发送车身稳定开关打开请求信号 | ESC_Switch_open_request_IGN_ON  | 0x2E1=FCP_RearWindowHeatFunctionRe=0x2=1<br/>0x2E1=FCP_RearWindowHeatFunctionRe=0x0 |

  - 说明：

     基本和**测试用例**类似，多余的部分如下解释

    以**MessageId=SignalName=expectValue=frameCount=Exact**来区分
    
    - **CAN信号值ID**： 对那个CAN信号进行判断
    - **CAN_SIGNAL的名字**： 对该CAN信号下的那个名字的signal进行判断
    - **期望CAN消息值**:  期望这个SIGNAL的值是多少
    - **检查帧数**: 期望在测试过程中有多少个消息被检查到，如过不填则表示查看最后一帧消息 
    - **Exact**： 是否精确对比

- 动作(适用仪表和空调屏测试)

  

  | id<br/>序号 | category<br/>类别 | functionName<br/>函数名 |                 signals<br/>信号                  |   dependency<br/>依赖   | description<br/>动作描述 |
  | :---------: | :---------------: | :---------------------: | :-----------------------------------------------: | :---------------------: | ------------------------ |
  |      1      |     Condition     |        Power_OFF        |                  iBCM_PwrMod=0x0                  |                         |                          |
  |      2      |      Action       |    ESP_OFFLamp_OPEN     |                  ESP_OFFLamp=0x1                  |   Condition=Power_OFF   |                          |
  |      3      |      Action       |    iBCM_PBDSts_OPEN     | PBD_RearDoorStatus=0x2     PBD_RearDoorStsVld=0x0 | Action=ESP_OFFLamp_OPEN |                          |

  说明： 

  - **类别**： 仅支持Condition和Action，即前置条件以及动作。 
  - **函数名**:  描述这个操作的含义，不允许有重复的函数名。且函数名最好按照PEP8的标准来描述，需要符合PYTHON函数名规则。
  - **信号**：DBC中信号的描述，必须以=号进行连接，等号左边为信号名右边为信号值。
  - **依赖** : 仅对Action有效，即Action可以依赖Condition，也可以Action依赖Action。

- 屏幕操作（适用空调屏)

  | id<br/>序号 | category<br/>类别 | displayIndex<br/>屏幕序号 | functionName<br/>函数名 | position<br/>坐标点   | continueTime<br/>持续时间 | description<br/>备注 |
  | ----------- | ----------------- | ------------------------- | ----------------------- | --------------------- | ------------------------- | -------------------- |
  | 1           | CLICK             | 1                         | click_esc               | 1188-682              |                           | 点击一键启动按钮     |
  | 2           | PRESS             | 1                         | press_esc               | 1188-682              | 1                         | 左侧温度调节增加     |
  | 3           | SLIDE             | 1                         | slide_air_condition     | 1188-682<br/>1188-600 | 2                         | 右侧温度调节增加     |

  - **类别**：用于定义这个操作， 目前仅支持CLICK（点击）、PRESS（长按）、SLIDE(滑动)，其中特别注意一点是滑动，目前仅支持横向和竖向滑动，即 两点的X或者Y相同, 持续时间表示滑动时间
  - **屏幕序号**: 用于定义操作的屏幕，目前只支持1和2
  - **函数名**:  描述这个操作的含义，不允许有重复的函数名。且函数名最好按照PEP8的标准来描述，需要符合PYTHON函数名规则。
  - **坐标点**：以X-Y的方式描述，对于点击和长按来说，只能有一组X-Y，而对于SLIDE来说，只能有两组X-Y
  - **持续时间**： 除点击外，都需要有持续时间

- 前置条件（适用于所有项目, 必填)

  | id<br/>序号 | category<br/>类别 | actions<br/>函数执行前                                       | suites<br/>类执行前                                          |
  | ----------- | ----------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | 1           | BCM               | action=VehicleSpeed_0km<br/>SLEEP=1<br/>YIELD<br/>service=send_default_messages | TEXT=打开CAN盒子<br/>service=open_can<br/>condition=ign_on<br/>sleep=10<br/>service=send_messages<br/>screenshot=connect<br/>screenshot=prepare<br/>yield<br/>TEXT=关闭CAN盒子<br/>screenshot=copy_file<br/>screenshot=disconnect<br/>service=close_can |

  - **类别**： 在测试用例中定义的类别名称

  - **函数执行前**： 表示在生成的代码中某一个模块需要在每一个函数之前运行的需要执行的步骤以及在运行之后恢复的步骤

  - **类执行前**：表示在生成的代码中类执行前需要执行的步骤，需要注意的是，必须按照以下方式来编写

    **Text**必须写在第一行，用于生成 with allure.step

    operator必须在第二行有，如果实在没有可以写pass

    **yield**必须存在

    **Text**必须在yield之后

    operator必须在text之后有，如果实在没有可以写pass

###  使用说明

------

请安装JDK1.8以上版本。

修改配置文件**application.yml**后，执行命令

```powershell
java -jar tools-x.x.jar
```

### 配置说明

------

所有的配置都是通过application.yml的文件来定义的。

- 系统部分

  - **logging** （配置打印等级，仅需要debug的时候使用，默认输出为info。

    ```yml
    logging:
      level:
        # 这个部分用于调试，如果修改则需要修改成debug 
      	com.chinatsp.*: info
        # log打印等级，默认为info，可以改成debug，一般不用处理
        root: info
    ```

    

- 项目部分

  - **project**主要配置了文件夹路径以及生成类型等参数

    ```yml
project:
      # 项目名称，跟生成出来的DBC有关系
  projectName: GSE
      # DBC文件的名字， 必须放到运行路径下的file文件夹中
      dbcName: HiFire_GSE_CONFCAN_Matrix_CAN_V2.0_IC_MMI.dbc
      # 测试用例excel的名字， 必须放到运行路径下的file文件夹中
      testCaseName: 3J2仪表自动化测试用例汇总_v0.22.xlsx
      # 目前仅支持类型 cluster和AirCondition
      type: cluster
      # 屏幕尺寸的宽
      width: 1280
      # 屏幕尺寸的高
      height: 720
      # automotive最低版本号
      automotive: 2.0
      # 串口端口
      socPort: COM23
    ```
    
  

目前不支持单独生成DBC文件，需要配合测试用例使用。后续会退出单独的DBC解析工具

### 代码生成说明

------

代码生成结构如下：

```sh
-generatorxxx # 其中xxx是你的projectName
-install.bat 可以双击该文件进行代码的打包及安装工作
--source
----xxx
------can 存放cations, conditions, director, service等文件
------config 存放图片对比的文件以及dbc解析后的python文件
------tools 存放图片对比工具以及截图工具
--jsons 存放图片对比的文件以及dbc解析后的json文件
--src
--run.bat 可以双击进行软件测试工作， 如果需要针对文件夹或者文件进行测试生成报告，则需要修改"set TEST_CASE"的部分，仅需要输入src之后的相对路径
----result 
------report 存放allure测试报告的文件
------screenshot 存放截图文件
------temp 存放allure拷贝需要的文件
------template 存放模板图片文件
----test_cases 生成的测试用例文件
----test_actions 生成的测试用例文件 （仅空调屏用)
----test_compares 生成的测试用例文件 （仅空调屏用)


```


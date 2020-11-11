------

# 自动化代码生成工具

------

### 工具说明



本工具适用于：

- 高通虚拟化方案的中控+仪表测试（依赖于开发提供**Htalk**命令)
- QNX空调屏测试（依赖于开发提供的**InjectEvents**进行屏幕操作)
- 单仪表测试
- 仅HMI仪表测试用例生成（依赖于开发提供的test文件)
- 单安卓测试

#### 环境依赖

java只支持jdk_1.8

#### 工具包含：

- code-x.x.jar
- templates
  - __ init __.py
  - action.ftlh
  - compare.ftlh
  - config.ftlh
  - context.ftlh
  - testcase.ftlh



#### 使用方法：

- ```shell
  // 当前执行的文件夹下不存在file文件夹
  java -jar code-x.x.jar // 会在当前目录下生成template.xls文件
  ```

  

- ```shell
  // 当前文件夹下存在file文件夹且该文件夹中存在testcase.xls或者testcase.xlsx文件
  java -jar code-x.x.jar // 会根据testcase中描述的信息生成测试代码
  ```

- ```
  // 此时会在当前路径下寻找excel所描述的dbc文件，并生成测试代码
  java -jar code-x.x.jar yy.xlsx
  java -jar code-x.x.jar yy.xls
  ```



#### 模板文件内容填写说明

- **电源操作(BatteryAction)**

  主要是针对电源相关的操作

  | Id<br/>序号                           | 序号，主要用于定位错误                                       |
  | ------------------------------------- | ------------------------------------------------------------ |
  | Name<br/>名字/函数名/模块名           | 函数名，必须符合python的函数名规则，建议仅保留下划线和字母数字，去掉其他符号。<br/>软件支持自动将空格转换成_用于检查和代码生成，但仍然建议手动完成相关操作 |
  | Comments<br/>描述                     | 该操作的简要描述                                             |
  | BatteryType<br/>电源类型              | 下拉菜单，仅支持**IT6831**和**KONSTANTER**，请不要修改相关的文字 |
  | BatteryOperationType<br/>电源操作类型 | 下拉菜单，仅支持【设置电压/设置电流/调节电压/电压曲线】，请不要修改相关的文字 |
  | Values<br/>电源操作值                 | 若设置电压/电流值，则填写数值12.1/10<br/>若调节电压，则值设置成12-18-0.1-5，代表意思是从12V调节到18V，步长0.1V，每次调节间隔时间5秒<br/>若电压曲线，该操作值可以不用填写 |
  | RepeatTimes<br/>重复次数              | 反复执行的次数，需要手动填写次数，无默认值                   |
  | CurveFile<br/>电压曲线文件            | 电压曲线文件，需要填写该文件的绝对路径                       |

- **Can信号(CanAction)**

    主要针对CAN消息的发送操作
    
    | Id<br/>序号                 | 序号，主要用于定位错误                                       |
    | --------------------------- | ------------------------------------------------------------ |
    | Name<br/>名字/函数名/模块名 | 函数名，必须符合python的函数名规则，建议仅保留下划线和字母数字，去掉其他符号。<br/>软件支持自动将空格转换成_用于检查和代码生成，但仍然建议手动完成相关操作 |
    | Comments<br/>描述           | 该操作的简要描述                                             |
    | Signals<br/>信号名与值      | CAN消息中的Signal的名称和值的对应关系，以**=**分割，可以填写一个或者多个信号<br/>如：iBCM_FL_DoorAjarSts=0x1<br/>或者：PBD_RearDoorStatus=0x0<br/>PBD_RearDoorStsVld=0x0<br>左边部分代表信号的名称，该名称必须要在Sheet【配置(Configure)】中指定的DBC中有相关的描述信息<br>右边部分代表信号的值，该值必须满足总线值的范围，即根据信号长度计算出来的最小值和最大值。 |

- **元素操作(ElementAction)**

  主要针对Android元素的相关操作

  | Id<br/>序号                      | 序号，主要用于定位错误                                       |
  | -------------------------------- | ------------------------------------------------------------ |
  | Name<br/>名字/函数名/模块名      | 函数名，必须符合python的函数名规则，建议仅保留下划线和字母数字，去掉其他符号。<br/>软件支持自动将空格转换成_用于检查和代码生成，但仍然建议手动完成相关操作 |
  | Comments<br/>描述                | 该操作的简要描述                                             |
  | OperationActionType<br/>操作类型 | 下拉菜单， 仅支持【滑动/点击/长按/双击/上下左右滑动/上下左右滑动到底】， 请不要修改相关的文字<br>滑动：表示从某个安卓元素滑动到另外一个安卓元素，要求元素名中的元素为两行，表示两个定位的点<br>上下左右滑动/滑动到底: 表示在某个滑动列表中滑动，要求元素名中的元素为两行，第一行表示滑动列表的定位，第二行表示显示的列表（Item) |
  | Elements<br/>元素名              | 元素的名称，该名称必须要在Sheet【安卓元素(Element)】中能查询到<br> |
  | SlideTimes<br/>滑动次数/持续时间 | 若操作类型不是滑动，则无需填写该项目。<br>该表格中表示持续时间而非次数 |

- **继电器操作(RelayAction)**

  | Id<br/>序号                           | 序号，主要用于定位错误                                       |
  | ------------------------------------- | ------------------------------------------------------------ |
  | Name<br/>名字/函数名/模块名           | 函数名，必须符合python的函数名规则，建议仅保留下划线和字母数字，去掉其他符号。<br/>软件支持自动将空格转换成_用于检查和代码生成，但仍然建议手动完成相关操作 |
  | Comments<br/>描述                     | 该操作的简要描述                                             |
  | RelayOperationType<br/>继电器操作类型 | 下拉菜单，仅支持【开启/关闭/全开/全关】操作，请不要修改相关的文字<br> |
  | ChannelIndex<br/>继电器通道           | 需要开启或者关闭的通道号，生成代码的时候无法校验是否填写正确<br>但如果填写错误会导致运行时候报错<br>若选择全开或者全关操作，则无需填写通道号码 |

- **屏幕操作(ScreenOpsAction)**

  | Id<br/>序号                      | 序号，主要用于定位错误                                       |
  | -------------------------------- | ------------------------------------------------------------ |
  | Name<br/>名字/函数名/模块名      | 函数名，必须符合python的函数名规则，建议仅保留下划线和字母数字，去掉其他符号。<br/>软件支持自动将空格转换成_用于检查和代码生成，但仍然建议手动完成相关操作 |
  | Comments<br/>描述                | 该操作的简要描述                                             |
  | DeviceType<br/>屏幕类型          | 下拉菜单，仅支持【QNX/ANDROID】, 请不要修改相关的文字<br>Android只支持点击及拖动<br>QNX不支持拖动，进支持点击、长按和滑动 |
  | ScreenOperationType<br/>操作类型 | 下拉菜单, 仅支持【滑动/点击/长按/拖动】, 请不要修改相关的文字 |
  | ScreenIndex<br/>屏幕序号         | 要操作的屏幕序号                                             |
  | Points<br/>坐标点                | 要操作的坐标点，【点击/长按】支持一个坐标点，【滑动/拖动】支持两个坐标点 |
  | ContinueTimes<br/>持续时间       | 操作的持续时间，对【点击】操作无效                           |

- **截图操作(ScreenShotAction)**

    | Id<br/>序号                 | 序号，主要用于定位错误                                       |
    | --------------------------- | ------------------------------------------------------------ |
    | Name<br/>名字/函数名/模块名 | 函数名，必须符合python的函数名规则，建议仅保留下划线和字母数字，去掉其他符号。<br/>软件支持自动将空格转换成_用于检查和代码生成，但仍然建议手动完成相关操作 |
    | Comments<br/>描述           | 该操作的简要描述                                             |
    | ScreenShotType<br/>截图类型 | 下拉菜单，仅支持【空调屏/安卓屏/单HMI仪表屏/仪表屏】,请不要修改相关的文字 |
    | DisplayId<br/>屏幕序号      | 要操作的屏幕序号<br>若为智能座舱项目，对于Android屏幕来说，副屏序号从3开始，即副屏1的序号是3 |
    | Count<br/>截图张数          | 截图的数量，对于闪烁图片来说，截图数量建议超过5张，而非闪烁图来说则只需要1张 |
    | ImageName<br/>截图名称      | 截图名称，不需要带后缀名                                     |

- **安卓元素(Element)**

    | Id<br/>序号                 | 序号，主要用于定位错误                                       |
    | --------------------------- | ------------------------------------------------------------ |
    | Name<br/>名字/函数名/模块名 | 函数名，必须符合python的函数名规则，建议仅保留下划线和字母数字，去掉其他符号。<br/>软件支持自动将空格转换成_用于检查和代码生成，但仍然建议手动完成相关操作 |
    | Comments<br/>描述           | 该操作的简要描述                                             |
    | Locators<br/>元素定位符     | 元素的定位符，支持一个或者多个定位<br>编写方式为：key=value,key=value<br>如：resourceId=com.android.systemui:id/tsp_nav_button_content,text=车辆<br>可以最多写2个，用于滑动操作 |

- **公共函数(Common)**

    | Id<br/>序号                 | 序号，主要用于定位错误                                       |
    | --------------------------- | ------------------------------------------------------------ |
    | Name<br/>名字/函数名/模块名 | 函数名，必须符合python的函数名规则，建议仅保留下划线和字母数字，去掉其他符号。<br/>软件支持自动将空格转换成_用于检查和代码生成，但仍然建议手动完成相关操作 |
    | Comments<br/>描述           | 该操作的简要描述                                             |
    | ModuleName<br/>模块名       | 下拉菜单，仅支持【it6831/konstanter/can_service/android_service/relay/air_condition/soc/mcu/hypervisor/cluster_hmi】,请不要修改相关的文字 |
    | FunctionName<br/>函数名称   | 函数的名称，必须要模块名的类支持的函数，否则生成出来的代码无法执行 |
    | Params<br/>函数参数         | 函数的参数，可以有多个，必须以参数名="参数值"的方式编写<br>如：element={"resourceId":"com.chinatsp.vehicle:id/navigation_view"}<br/>locator={"resourceId": "android:id/navigation_item_text"}<br/>text="DVR" |

- **CAN信号对比(CanCompare)**

    | Id<br/>序号                 | 序号，主要用于定位错误                                       |
    | --------------------------- | ------------------------------------------------------------ |
    | Name<br/>名字/函数名/模块名 | 函数名，必须符合python的函数名规则，建议仅保留下划线和字母数字，去掉其他符号。<br/>软件支持自动将空格转换成_用于检查和代码生成，但仍然建议手动完成相关操作 |
    | Comments<br/>描述           | 该操作的简要描述                                             |
    | SignalName<br/>信号名       | CAN矩阵表中支持的信号名称                                    |
    | ExpectValue<br/>期望值      | 期望该信号的值                                               |
    | AppearCount<br/>出现次数    | 期望出现的次数，用于空调屏测试中点击只发送x次某个信号的方式。<br>若不填次数则表示该信号最后一次收到的值 |
    | Exact<br/>是否精确对比      | 若精确对比则期望值出现的次数需要和实际次数相同<br>否则实际出现的次数小于期望值出现的次数即可 |

- **Android元素对比(ElementCompare)**

  | Id<br/>序号                         | 序号，主要用于定位错误                                       |
  | ----------------------------------- | ------------------------------------------------------------ |
  | Name<br/>名字/函数名/模块名         | 函数名，必须符合python的函数名规则，建议仅保留下划线和字母数字，去掉其他符号。<br/>软件支持自动将空格转换成_用于检查和代码生成，但仍然建议手动完成相关操作 |
  | Comments<br/>描述                   | 该操作的简要描述                                             |
  | ElementCompareType<br/>元素对比类型 | 下拉菜单，仅支持【存在/不存在】，,请不要修改相关的文字       |
  | Element<br/>元素                    | 元素名称，即安卓元素(Element)表中定义的元素名称<br>若填写错误无法生成代码 |
  | Timeout<br/>超时时间                | 该元素检查的超时时间                                         |

- **图片对比(ImageCompare)**

  | Id<br/>序号                  | 序号，主要用于定位错误                                       |
  | ---------------------------- | ------------------------------------------------------------ |
  | Name<br/>名字/函数名/模块名  | 函数名，必须符合python的函数名规则，建议仅保留下划线和字母数字，去掉其他符号。<br/>软件支持自动将空格转换成_用于检查和代码生成，但仍然建议手动完成相关操作 |
  | Comments<br/>描述            | 该操作的简要描述                                             |
  | DeviceType<br/>屏幕类型      | 下拉菜单，仅支持【QNX/ANDROID】, 请不要修改相关的文字<br/>   |
  | CompareType<br/>图片对比方式 | 下拉菜单，仅支持【亮图/暗图/闪烁图】,请不要修改相关的文字<br/> |
  | ImageName<br/>截图名称       | 截图名称需要在表【截图操作(ScreenShotAction)】中能找到。     |
  | TemplateLight<br/>模板亮图   | 亮图的模板图片，必须以.JPG、.PNG、.BMP结尾                   |
  | TemplateDark<br/>模板暗图    | 暗图的模板图片，必须以.JPG、.PNG、.BMP结尾                   |
  | Positions<br/>比较区域       | 比较区域，可以是一个或者多个，以start_x-start_y-width-height区分<br>如： 147-533-155-56 |
  | Similarity<br/>相似度        | 相似度，用于图片对比                                         |
  | IsGray<br/>是否灰度对比      | 是否灰度对比                                                 |
  | Threshold<br/>灰度二值化阈值 | 灰度的二值化阈值                                             |

- **信息对比(InformationCompare)**

    | Id<br/>序号                         | 序号，主要用于定位错误                                       |
    | ----------------------------------- | ------------------------------------------------------------ |
    | Name<br/>名字/函数名/模块名         | 函数名，必须符合python的函数名规则，建议仅保留下划线和字母数字，去掉其他符号。<br/>软件支持自动将空格转换成_用于检查和代码生成，但仍然建议手动完成相关操作 |
    | Comments<br/>描述                   | 该操作的简要描述                                             |
    | Element<br/>元素                    | 元素名称，即安卓元素(Element)表中定义的元素名称<br/>若填写错误无法生成代码 |
    | ElementAttribute<br/>元素属性       | 下拉菜单，仅支持【CHECKABLE/CHECKED/CLICKABLE/ENABLED/FOCUSABLE<br>/FOCUSED/SCROLLABLE/LONG_CLICKABLE/DISPLAYED/SELECTED/TEXT】，请不要修改相关的文字<br/> |
    | Info<br/>属性信息                   | 该元素属性的信息<br>如元素的text为形式数据，则此处填写属性信息<br>若填写了该目录则保存的信息无效 |
    | SavedInformation<br/>保存的信息     | 保存的信息，用于与之前保存的信息相对比<br>即，该名称需要在表【信息保存(Information)】中能查到 |
    | InformationCompareType<br/>对比类型 | 下拉菜单，仅支持【相同/不相同】，请不要修改相关的文字        |

- **信息保存(Information)**

    | Id<br/>序号                   | 序号，主要用于定位错误                                       |
    | ----------------------------- | ------------------------------------------------------------ |
    | Name<br/>名字/函数名/模块名   | 函数名，必须符合python的函数名规则，建议仅保留下划线和字母数字，去掉其他符号。<br/>软件支持自动将空格转换成_用于检查和代码生成，但仍然建议手动完成相关操作 |
    | Comments<br/>描述             | 该操作的简要描述                                             |
    | Element<br/>元素              | 元素名称，即安卓元素(Element)表中定义的元素名称<br/>若填写错误无法生成代码 |
    | ElementAttribute<br/>元素属性 | 下拉菜单，仅支持【CHECKABLE/CHECKED/CLICKABLE/ENABLED/FOCUSABLE<br/>/FOCUSED/SCROLLABLE/LONG_CLICKABLE/DISPLAYED/SELECTED/TEXT】，请不要修改相关的文字<br/> |

- **测试用例(TestCase)**

    | Id<br/>序号                              | 序号，主要用于定位错误                                       |
    | ---------------------------------------- | ------------------------------------------------------------ |
    | Name<br/>名字/函数名/模块名              | 函数名，必须符合python的函数名规则，建议仅保留下划线和字母数字，去掉其他符号。<br/>软件支持自动将空格转换成_用于检查和代码生成，但仍然建议手动完成相关操作 |
    | Comments<br/>描述                        | 测试用例的简要描述                                           |
    | Description<br/>中文描述                 | allure中测试用例的中文描述                                   |
    | TestCaseType<br/>测试用例类型            | 下拉菜单，仅支持【全自动/半自动】,请不要修改相关的文字<br/>全自动：生成一份测试代码，包含了操作以及结果对比<br>半自动:  生成两份代码，分离了操作和结果。适用于操作后无法及时拷贝到电脑的情况<br> |
    | ModuleName<br/>模块名                    | 模块名，生成器会根据模块名生成相关的文件                     |
    | PreConditionDescription<br/>前置条件描述 | 前置条件描述                                                 |
    | PreCondition<br/>前置条件                | 操作步骤以x=y的方式来描写，比如:canaction=Power_ACC<br>也支持【pass/sleep/stack/clear/lost/open/close/yield】<br>建议前置条件中只包含sleep/clear/lost这个三种类型，其中<br>sleep=1表示休息1秒<br>clear表示清楚CAN的stack中的数据<br>lost表示停掉所有当前发送的消息<br>若要停止某个id的消息发送，请在common中编写 |
    | StepsDescription<br/>操作步骤描述        | 操作步骤描述                                                 |
    | Steps<br/>操作步骤                       | 操作步骤以x=y的方式来描写，比如:canaction=Power_ACC<br/>也支持【pass/sleep/stack/clear/lost/open/close/yield】<br/>建议操作步骤中只包含sleep/clear/lost这个三种类型，其中<br/>sleep=1表示休息1秒<br/>clear表示清楚CAN的stack中的数据<br/>lost表示停掉所有当前发送的消息<br/>若要停止某个id的消息发送，请在common中编写 |
    | ExpectDescription<br/>期望结果描述       | 期望结果描述                                                 |
    | Expect<br/>期望结果                      | 操作步骤以x=y的方式来描写，比如:canaction=Power_ACC<br/>也支持【pass/sleep/stack/clear/lost/open/close/yield】<br/>建议期望结果中只包含sleep/stack这个两种类型，其中<br/>sleep=1表示休息1秒<br/>stack表示获取当前所有的CAN消息<br>若要停止某个id的消息发送，请在common中编写 |

- **测试用例前置条件(TestCaseSetUp)**

    | Id<br/>序号                        | 序号，主要用于定位错误                                       |
    | ---------------------------------- | ------------------------------------------------------------ |
    | Name<br/>名字/函数名/模块名        | 模块名，该名字需要在表【测试用例(TestCase)】中模块名中能找到 |
    | Comments<br/>描述                  | 测试用例的简要描述                                           |
    | Functions<br/>函数执行前           | 操作步骤以x=y的方式来描写，比如:canaction=Power_ACC<br/>也支持【pass/sleep/stack/clear/lost/open/close/yield】<br/>建议前置条件中只包含sleep/clear/lost/stack这个三种类型，其中<br/>sleep=1表示休息1秒<br/>clear表示清楚CAN的stack中的数据<br/>lost表示停掉所有当前发送的消息<br/>stack表示获取当前所有的CAN消息<br/>若要停止某个id的消息发送，请在common中编写<br>该操作中必须要包含YIELD，且YIELD前后必须要包含操作行，若无操作行，则以pass标注 |
    | FunctionsBefore<br/>函数执行前描述 | 中文描述，体现在allure中                                     |
    | FunctionsAfter<br/>函数执行后描述  | 中文描述，体现在allure中                                     |
    | Suites<br/>套件执行前              | 操作步骤以x=y的方式来描写，比如:canaction=Power_ACC<br/>也支持【pass/sleep/stack/clear/lost/open/close/yield】<br/>建议前置条件中只包含sleep/clear/lost/stack这个三种类型，其中<br/>sleep=1表示休息1秒<br/>clear表示清楚CAN的stack中的数据<br/>lost表示停掉所有当前发送的消息<br/>stack表示获取当前所有的CAN消息<br/>若要停止某个id的消息发送，请在common中编写<br/>该操作中必须要包含YIELD，且YIELD前后必须要包含操作行，若无操作行，则以pass标注 |
    | SuitesBefore<br/>套件执行前描述    | 中文描述，体现在allure中                                     |
    | SuitesAfter<br/>套件执行后描述     | 中文描述，体现在allure中                                     |

- 配置(Configure)

  | Name     名字               | Comment     描述         | Content     内容                                             |
  | --------------------------- | ------------------------ | ------------------------------------------------------------ |
  | max_android_display         | 最大安卓显示屏的个数     | 最大的安卓屏幕数量，由于8155项目的特殊性，若3个安卓屏幕则需要填写为5 |
  | max_qnx_display             | 最大QNX显示屏的个数      | 最大的QNX屏幕数量，一般为1                                   |
  | voltage_min                 | 电源电压支持最小电压     | 电源支持的最小电压， 一般填写为0                             |
  | voltage_max                 | 电源电压支持最大电压     | 电源支持的最小电压， IT6831则需要填18V， konstanter则填20V   |
  | soc_serial_port             | SOC串口号                | 串口号                                                       |
  | soc_serial_baud_rate        | SOC串口波特率            | 波特率                                                       |
  | mcu_serial_port             | MCU串口号                | 串口号                                                       |
  | mcu_serial_baud_rate        | MCU串口波特率            | 波特率                                                       |
  | konstanter_serial_port      | Konstanter串口号         | 串口号                                                       |
  | konstanter_serial_baud_rate | Konstanter串口波特率     | 波特率                                                       |
  | it6831_serial_port          | IT6831串口号             | 串口号                                                       |
  | it6831_serial_baud_rate     | IT6831串口波特率         | 波特率                                                       |
  | air_condition_port          | 空调屏(QNX)串口配置      | 串口号                                                       |
  | air_condition_baud_rate     | 空调屏(QNX)串口波特率    | 波特率                                                       |
  | qnx_screen_shot_path        | QNX截图存放路径          | QNX截图板子上存放的路径                                      |
  | android_screen_shot_path    | Android截图存放路径      | Android截图板子上存放的路径                                  |
  | android_resolution_width    | Android屏幕宽            | Android屏幕宽                                                |
  | android_resolution_height   | Android屏幕高            | Android屏幕高                                                |
  | qnx_resolution_width        | QNX屏幕宽                | QNX屏幕宽                                                    |
  | qnx_resolution_height       | QNX屏幕高                | QNX屏幕高                                                    |
  | dbc_file                    | DBC文件名称              | DBC文件名字，需要放在执行包相同的文件夹下面                  |
  | dbc_json                    | DBC解析后生成的文件名称  | DBC解析后的文件，存放于src->resources->dbc中                 |
  | max_relay_channel           | 最大支持的继电器通道     | 继电器最大的通道数，用于继电器校验                           |
  | android_automation_type     | Android自动化测试方式    | 支持appium和uiautomator2                                     |
  | android_app_package         | 安卓的启动应用的package  | 安卓版本号，用于appium                                       |
  | android_app_activity        | 安卓的启动应用的activity | 安卓版本号，用于appium                                       |
  | android_version             | 安卓系统版本号           | 安卓版本号，用于appium                                       |
  | android_device_id           | 安卓设备号               | 安卓版本号，用于appium                                       |
  | test_case_type              | 测试类型                 | 支持智能座舱/仪表/中控/HMI/空调屏<br>                        |
  | username                    | 用户名                   | 空调屏的登陆用户名                                           |
  | password                    | 密码                     | 空调屏的登陆密码                                             |
  | hmi_username                | HMI用户名                | Cluster HMI的登陆用户名（telnet）                            |
  | hmi_password                | HMI密码                  | Cluster HMI的登陆密码（telnet)                               |
  | hmi_board_path              | HMI板子路径              | Cluster HMI测试程序的路径                                    |
  | hmi_test_binary             | HMI测试程序路径          | Cluster HMI的测试程序本地电脑的路径，填写后会自动将该文件夹下所有的文件传到HMI板子路径定义的路径中 |
  | ip_address                  | ip地址                   | 用于Cluster HMI的测试板子的地址                              |

  

#### 代码生成结构

- yyyymmdd_hhmmss
  - src
    - codes
      - configure.py 【相关配置，即表配置(Configure)的项目】
      - context.py 【所有的非testcase表格的内容，如common， canaction等】
    - resources 【资源文件】
      - dbc 【用于存放解析dbc文件生成的json文件】
      - templates 【模板图片】
    - result
      - report 【allure报告存放路径】
      - screenshot 【截图存放的路径，全自动化和半自动化都需要存放到该路径下】
      - temp 【存放对比过结果的图片，该图片用于allure报告】
    - testcase【测试用例存放地址】



#### 自动化测试填写技巧

- 建议先填写测试用例的描述，并翻译该描述为英文并填写到【名字/函数名/模块名】中，然后在各个Sheet中都沿用该名称。
- 若涉及到截图对比，建议先生成一份测试用例代码，并执行一次，会把所有的截图拷贝在截图中，如果截图无问题就可以把所有的截图去掉后缀名则直接变成模板文件
- 若非闪烁图对比，则可以忽视暗图填写，亮图和暗图都填写成相同的名称即可。


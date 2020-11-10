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



#### 模板文件说明

模板文件包含如下Sheet

- 电源操作(BatteryAction)

  电源操作主要针对**IT6831**以及**KONSTANTER**电源进行【设置电压/设置电流/调节电压/电压曲线】操作

  注意：电压曲线仅支持**KONSTANTER**电源

- Can信号(CanAction)

  CAN主要作用是通过设置CAN消息的Signals的值然后发送到总线上。

- 元素操作(ElementAction)

  主要针对**Android**测试中对元素的操作，如【滑动/点击/长按/双击/上下左右滑动/上下左右滑动到底】

- 继电器操作(RelayAction)

  主要针对继电器进行【开启/关闭/全开/全关】的操作

- 屏幕操作(ScreenOpsAction)

  屏幕操作主要涉及到**QNX**以及**Android**的【滑动/点击/长按/拖动】等**坐标点**操作。

- 截图操作(ScreenShotAction)

  截图操作主要进行相关的截图操作，支持【空调屏/安卓屏/单HMI仪表屏/仪表屏】

- 安卓元素(Element)

  列举出来所有的安卓元素

- 公共函数(Common)

  

- CAN信号对比(CanCompare)

- Android元素对比(ElementCompare)

- 图片对比(ImageCompare)

- 信息对比(InformationCompare)

- 信息保存(Information)

- 测试用例(TestCase)

- 测试用例前置条件(TestCaseSetUp)

- 配置(Configure)

  

#### 模板文件内容填写







bug：TestCaseSetUp中没有校验前面的内容


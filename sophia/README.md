# JAVA工具集

##### FileUtils:
- 获取程序当前运行路径
- 获取文件大小
- 获取文件扩展名
- 删除文件

##### InOutUtils

- 根据编码格式打开文件为Reader
- 根据编码格式打开文件为Writer

##### MergeUtils

- convert转换基本类型为封装类型

- merge数组

##### NumericUtils

- 随机生成 Double Long和Integer数字

- 转换Float和Double为百分比数字

- 截取Double和Float小数点
  
- 判断是否为数字或者正整数

##### ParseUtils

- 字节数组转换HEX的字符串

- HEX字符串转字节数组
- byte转换成int数组
- 集合字符串对象转字符串，可带分隔符
- 集合字符串对象转数组

##### StringsUtils（继承StringUtils)

##### ZipUtils

- 压缩文件
- 压缩文件夹
- 解压压缩文件

##### CharUtils

- 去掉windows不支持的字符（用于文件命名）
- 设置前缀数字

##### CodecUtils

- MD5/SHA方式**加密**字符串
- DES/3DES/AES**加密/解密**字符串
- RSA**加密/解密**字符串
- 生成RSA秘钥

##### CsvUtils

- 读取CSV文件的title
- 读取CSV文件的内容（不含title）
- 读取指定行列或者执行title列的内容
- 读取CSV文件的所有内容（包含title)
- 写入内容到CSV文件中

##### ExcelUtils

- 读取sheet中所有内容
- 读取指定单元格的值
- 写入内容到excel文件中

##### ImageUtils

- 缩放图片
- 图像切割
- 图像类型转换
- 彩色变黑白
- 获取图片大小
- 图片对比（支持汉明距，直方图，像素三种方法）

##### TxtUtils

- 读取txt文件的内容
- 写入内容到txt文件中





### 更新说明

#### V2.0.1

- 修改了除base外Utils方法为非静态方法

  
[![en](https://img.shields.io/badge/readme-english-orange.svg)](english.md)
![LICENSE](https://img.shields.io/badge/license-GPL-blue.svg)

# oaml
object aim markup language

> 本软件受到GPL软件协议保护，请阅读相关许可证，再使用本软件

* 什么是oaml
oaml全称对象集目标扩展语言，拥有简单的配置模式和解析模式，可以不需缩进的限制，目前为0.0.2版本测试版
* 如何使用它？
```yaml
root1: root
{
	child: 11
	{
		array: [0,0,0,0]
	}
}
root2: root2
{
	child: 11
	{
		array: [1,2,3,4]
	}
}
```
> oaml的格式约束
> 
oaml不允许多个元素在同一行，允许有同路径元素(但是最大级不支持重名，如果要重名，必须设置最大级，在子节点重名操作)，即同一级元素可以重名，每个元素都是键值对，因此，即使有下级元素存在，也可以添加值
> 括号级

Oaml通过括号分级，因此支持不缩进，oaml的分支特点
```yaml
键: 值
{
	子元素键1: 子元素值
	{
		孙元素键1: 孙元素值
		{
			...
		}
		孙元素键2: 孙元素值
	}
	子元素键2: 子元素值
	子元素键3: 子元素值
}
```
如果一个元素没有下级元素，则可以没有大括号
如果一个元素有下级元素，则必须有大括号包围
另外，大括号必须在单独一行，否则默认读取到字符串
> 数据类型

字符串，数组
数组的声明：[值1,值2,值3]
字符串声明:  值
> 使用oaml java解析oaml

* 导入部署
pom.xml导入maven：
```xml
<dependency>
  <groupId>net.noyark</groupId>
  <artifactId>oaml</artifactId>
  <version>0.0.5</version>
</dependency>
```
连接服务器
```xml
<repositories>
 <repository>
    <id>nexus</id>
    <name>Team Neux Repository</name><url> http://www.noyark.net:8081/nexus/content/groups/public/ </url>
</repository>
</repositories>
<pluginRepositories>
    <pluginRepository>
      <id>nexus</id>
      <name>Team Neux Repository</name>
      <url> http://www.noyark.net:8081/nexus/content/groups/public/ </url>
    </pluginRepository>
 </pluginRepositories>
```
* 创建oaml
```java
//获取节点容器
Document document = DocumentFactory.getDocument();

Node node = document.getEntry("root");//创建第一级
node.setValue("root");//设置值
Node node2 = node.createNode("second");//创建第二级
node2.setValue("hello,oaml");
Node node3 = node2.createNode("third");//创建第三级

XYamlWriter writer = new XYamlWriter("a2122.oml");//创建写入流
writer.write(document);//写入
writer.close();//关流
```
运行后，产生a2122.oml，内容如下
```yaml
#这个必须带上，表明字符集和版本，创建时，会自动写入
#但是解析时，必须确认它在第一行
?encoding: UTF-8 version: 1.0.0?

root: root
{
	second: hello,oaml
	{
		third: 
	}
}
```
* 解析oaml
```java
XYamlReader reader = new XYamlReader("a.2122.oml");//创建读取流
Document document2 = reader.read();//读取出Document对象
Node node = document2.getEntry("root");//读出node对象
reader.close();//关闭
```
* 简单式解析和放入
简单式解析简化oaml操作，但是不支持重复路径，即同级没有重复名字

1.默认解析 

```java
//false为是否转换为classpath流
OamlComfig oaml = new OamlConfig("select.oml",false);
oaml.put("a.b.c",1);//放入三级元素
oaml.put("a.b","1");
oaml.save();//保存文件
oaml.get("a.b.c");//获取元素
```
效果：
```yaml
a:{
  b:1{
    c:1
  }
}
```
2.反射解析
创建一个类，如下所示
```java
package net.noyark.test;

@Config("select.oml")
public class TestConfig{
  @Root("a.b")//和下面的@Note等效
  public int number = 0;
  @Node("a.b")
  public int[] is = {1,2,3};
}
```
之后只需要
```java
new ReflectSet("net",config).loadAnnotation();//net为最大包名，也可以是net.noyark
```
> 字符串数组

oaml支持字符串数组，即setValue方法可以直接
```java
setValue("[1,2,3,4]");
```
解析器将默认读为数组类型，取出时也是以数组形式取出
> 关于换行

换行直接在配置文件加入\n即可，解析器会默认以换行读取

> 对象数据类型

oaml支持对象数据类型，对象数据类型格式
(对象字段名:对象值;字段名:值)

> 数组可以换行

若想要式一个数据可以换行，可以在左右加中括号，都会默认为数组，且可以随意换行
当让，其他数据类型也可以这样做,通过getValue同样可以获得单值
> 抛锚机制

005版本后，允许抛锚，即复用值。

抛锚的语法：
&a&内容

调用锚的语法：
*a
锚后不允许添加其他字符串
> 语法总结
* 数据类型
Oaml是基于java的偏弱类型数据转换语言，除以下几种数据类型外，不会细分其他数据类型，为了保证语法简易
Oaml的数据类型有
1. 键值对
```yaml
key: value
```
java方法：getInt getDouble…8大基本类型，getValue,getString
2. 数组
```yaml
key: [value,value,value]
```
Java方法：getArray,getList,getValue
4. 对象
```yaml
key: (key: value;key: value;)
```
java方法: getObject,getObjectArray,getObjectList
6. 日期
```yaml
key: 2019-4-16 23:35
```
java方法:getDate(format)
* 特殊符号
1. \n 字符串换行
2. &n& 设置锚
3. *n 指向锚
4. {} 为节点分级
5. [] 划分数组

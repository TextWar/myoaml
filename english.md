[![en](https://img.shields.io/badge/readme-chinese-orange.svg)](README.md)
![LICENSE](https://img.shields.io/badge/license-GPL-blue.svg)
# oaml
Object aim markup language

> This software is protected by the GPL software agreement. Please read the relevant license before using this software.

* What is oaml
Oaml full name object set target extension language, with a simple configuration mode and parsing mode, can be indented without restrictions, currently version 0.0.2 beta
* How to use it?
```yaml
Root1: root
{
Child: 11
{
Array: [0,0,0,0]
}
}
Root2: root2
{
Child: 11
{
Array: [1,2,3,4]
}
}
```
> oaml format constraints
>
Oaml does not allow multiple elements on the same line, allowing the same path element (but the maximum level does not support duplicate names, if you want to rename, you must set the maximum level, the sub-node name operation), that is, the same level element can be duplicated, Each element is a key-value pair, so you can add values ​​even if there are subordinate elements
> Braces

Oaml is graded by parentheses, so support is not indented, branching features of oaml
```yaml
Key: value
{
Child element key 1: child element value
{
Sun element key 1: grand element value
{
...
}
Sun element key 2: grand element value
}
Child element key 2: child element value
Child element key 3: child element value
}
```
If an element has no subordinate elements, there can be no braces
If an element has subordinate elements, it must be surrounded by braces
In addition, the braces must be on a separate line, otherwise the default is read to the string.
> Data Type

String, array
The declaration of the array: [value 1, value 2, value 3]
String declaration: value
> parsing oaml using oaml java

* Import deployment
Import pom.xml into maven:
```xml
<dependency>
  <groupId>net.noyark</groupId>
  <artifactId>oaml</artifactId>
  <version>0.0.5</version>
</dependency>
```
connect to the server
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
* Create oaml
```java
/ / Get the node container
Document document = DocumentFactory.getDocument();

Node node = document.getEntry("root");//Create the first level
node.setValue("root");//Setting the value
Node node2 = node.createNode("second");//Create a second level
node2.setValue("hello,oaml");
Node node3 = node2.createNode("third");//Create a third level

XYamlWriter writer = new XYamlWriter("a2122.oml");//Create a write stream
Writer.write(document);//write
Writer.close();//offflow
```
After running, generate a2122.oml, the content is as follows
```yaml
#这有带, indicates the character set and version, will be automatically written when created
# However, when parsing, you must confirm that it is in the first line.
?encoding: UTF-8 version: 1.0.0?

Root: root
{
Second: hello, oaml
{
Third:
}
}
```
* Analyze oaml
```java
XYamlReader reader = new XYamlReader("a.2122.oml");//Create a read stream
Document document2 = reader.read();//Read out the Document object
Node node = document2.getEntry("root");//Read the node object
Reader.close();//close
```
* Simple parsing and putting
Simple parsing simplifies oaml operations, but does not support duplicate paths, ie, peers have no duplicate names

Default analysis

```java
//false is whether to convert to classpath stream
OamlComfig oaml = new OamlConfig("select.oml",false);
Oaml.put("a.b.c",1);//Into the third-level element
Oaml.put("a.b","1");
Oaml.save();//Save file
Oaml.get("a.b.c");//Get the element
```
effect:
```yaml
a:{
  b:1{
    c:1
  }
}
```
2. Reflection analysis
Create a class as shown below
```java
Package net.noyark.test;

@Config("select.oml")
Public class TestConfig{
  @Root("a.b")// is equivalent to @Note below
  Public int number = 0;
  @Node("a.b")
  Public int[] is = {1,2,3};
}
```
Only need after
```java
New ReflectSet("net",config).loadAnnotation();//net is the maximum package name, or it can be net.noyark
```
> string array

Oaml supports string arrays, ie the setValue method can be directly
```java
setValue("[1,2,3,4]");
```
The parser will read as an array type by default, and will also be fetched as an array when fetched.
> About line breaks

Wrap the line directly in the configuration file to join \n, the parser will default to read in a new line

> Object data type

Oaml supports object data types, object data type formats
(object field name: object value; field name: value)

> Array can be wrapped

If you want a data to be wrap, you can add brackets to the left and right, it will default to an array, and you can wrap it at will.
When you let, other data types can do the same, you can get a single value through getValue.
> Anchor mechanism

After version 005, anchoring is allowed, that is, the value is multiplexed.

Anchored syntax:
&a&content

Call the anchor syntax:
*a
Do not allow additional strings after anchor
> Grammar Summary
* type of data
Oaml is a weak type data conversion language based on java. It does not subdivide other data types except for the following data types.
Oaml data types are
Key-value pair
```yaml
Key: value
```
Java method: getInt getDouble...8 big basic type, getValue, getString
2. Array
```yaml
Key: [value,value,value]
```
Java methods: getArray, getList, getValue
4. Object
```yaml
Key: (key: value;key: value;)
```
Java method: getObject, getObjectArray, getObjectList
6. Date
```yaml
Key: 2019-4-16 23:35
```
Java method: getDate(format)
* special symbol
1. \n string wrap
2. &n& setting anchor
3. *n points to the anchor
4. {} Grade the nodes
5. [] Divide an array

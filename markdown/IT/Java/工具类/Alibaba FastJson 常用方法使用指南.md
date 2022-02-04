# 介绍

FastJson是阿里巴巴的开源JSON解析库，它可以解析JSON格式的字符串，支持将Java Bean序列化为JSON字符串，也可以从JSON字符串反序列化到JavaBean。

FastJson已经被广泛使用在各种场景，包括cache存储、RPC通讯、MQ通讯、网络协议通讯、Android客户端、Ajax服务器处理程序等等。

FastJson的API十分简洁。

```java
String text = JSON.toJSONString(obj); //序列化
VO vo = JSON.parseObject("{...}", VO.class); //反序列化
```

Maven依赖

```xml
<!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.54</version>
</dependency>
```

# JSON

com.alibaba.fastjson.JSON 这个类是FastJson API的入口，主要的功能都通过这个类提供。



## **String转JSON**

对于字符串的处理，主要是看这个字符串（jsonStr）是JSON对象格式还是JSON数组格式，然后选择对应的方法处理就行。

JSON对象字符串转为JSON对象

```
JSONObject jsonObj = JSON.parseObject(jsonStr);
```

JSON数组字符串转为JSON数组

```
JSONArray jsonArr = JSON.parseArray(jsonStr);
```



## **String转JavaBean**

```
Model model = JSON.parseObject(jsonStr, Model.class);
```



## **Object转String**

包括JSONObject、JSONArray、JavaBean、数组、List、Set、Map都可以通过这种方式转String

```
String jsonStr = JSON.toJSONString(object);
```

# JSONField

这是一个注解，用于配置在JavaBean，可以配置在getter/setter方法或者字段上，也可以直接配置在属性上。

注意：若属性是私有的，必须有set*方法。否则无法反序列化。

## **部分属性**

```
@JSONField(ordinal=1)//配置序列化的字段顺序（1.1.42版本之后才支持）

@JSONField(serialize=false) //是否参与序列化：该字段不输出  但是如果加了final，这个字段就无法被过滤

@JSONField(derialize=false) //是否参与反序列化：该字段不输出  但是如果加了final，这个字段就无法被过滤 

@JSONField(format="yyyy-MM-dd HH:mm:ss") //日期按照指定格式序列化

@JSONField(name="别名");//使用字段别名

@JSONField(serialzeFeatures={SerialzeFeatures属性});//序列化规则

@JSONField(parseFeatures={Features属性});//反序列化规则
```



### **SerializerFeature属性**

```java
public enum SerializerFeature {
    /**
     * 输出key时是否使用双引号,默认为true
     */
    QuoteFieldNames,
    /**
     * 使用单引号而不是双引号,默认为false
     */
    UseSingleQuotes,
    /**
     * 是否输出值为null的字段,默认为false
     */
    WriteMapNullValue,
    /**
     * 用枚举toString()值输出
     */
    WriteEnumUsingToString,
    /**
     * 用枚举name()输出
     */
    WriteEnumUsingName,
    /**
     * Date使用ISO8601格式输出，默认为false
     */
    UseISO8601DateFormat,
    /**
     * @since 1.1
     * List字段如果为null,输出为[],而非null
     */
    WriteNullListAsEmpty,
    /**
     * @since 1.1
     * 字符类型字段如果为null,输出为"",而非null
     */
    WriteNullStringAsEmpty,
    /**
     * @since 1.1
     * 数值字段如果为null,输出为0,而非null
     */
    WriteNullNumberAsZero,
    /**
     * @since 1.1
     * Boolean字段如果为null,输出为false,而非null
     */
    WriteNullBooleanAsFalse,
    /**
     * @since 1.1
     * 如果是true，类中的Get方法对应的Field是transient，序列化时将会被忽略。默认为true
     */
    SkipTransientField,
    /**
     * @since 1.1
     * 按字段名称排序后输出。默认为false
     */
    SortField,
    /**
     * @since 1.1.1
     * 把\t做转义输出，默认为false(不推荐，已删除)
     */
    @Deprecated
    WriteTabAsSpecial,
    /**
     * @since 1.1.2
     * 结果是否格式化,默认为false
     */
    PrettyFormat,
    /**
     * @since 1.1.2
     * 序列化时写入类型信息，默认为false。反序列化时需用到
     */
    WriteClassName,
    /**
     * @since 1.1.6
     * 消除对同一对象循环引用的问题，默认为false
     */
    DisableCircularReferenceDetect,
    /**
     * @since 1.1.9
     * 对斜杠"/"进行转义
     */
    WriteSlashAsSpecial,
    /**
     * @since 1.1.10
     * 将中文都会序列化为\uXXXX格式，字节数会多一些，但是能兼容IE 6，默认为false
     */
    BrowserCompatible,
    /**
     * @since 1.1.14
     * 全局修改日期格式,默认为false。JSON.DEFFAULT_DATE_FORMAT = “yyyy-MM-dd”;JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);
     */
    WriteDateUseDateFormat,
    /**
     * @since 1.1.15
     */
    NotWriteRootClassName,
    /**
     * @since 1.1.19
     * 一个对象的字符串属性中如果有特殊字符如双引号，将会在转成json时带有反斜杠转移符。如果不需要转义，可以使用这个属性。默认为false
     */
    DisableCheckSpecialChar,
    /**
     * @since 1.1.35
     * 将对象转为array输出
     */
    BeanToArray,
    /**
     * @since 1.1.37
     */
    WriteNonStringKeyAsString,   
    /**
     * @since 1.1.42
     */
    NotWriteDefaultValue,    
    /**
     * @since 1.2.6
     */
    BrowserSecure,    
    /**
     * @since 1.2.7
     */
    IgnoreNonFieldGetter,   
    /**
     * @since 1.2.9
     */
    WriteNonStringValueAsString,    
    /**
     * @since 1.2.11
     */
    IgnoreErrorGetter;

}
```

###  **Feature属性**

```java
public enum Feature {
    /**
     * 这个特性，决定了解析器是否将自动关闭那些不属于parser自己的输入源。 
     * 如果禁止，则调用应用不得不分别去关闭那些被用来创建parser的基础输入流InputStream和reader；
     * 如果允许，parser只要自己需要获取closed方法（当遇到输入流结束，或者parser自己调用 JsonParder#close方法），就会处理流关闭。
     * 注意：这个属性默认是true，即允许自动关闭流
     */
    AutoCloseSource,
    /**
     * 该特性决定parser将是否允许解析使用Java/C++ 样式的注释（包括'/'+'*' 和'//' 变量）。 
     * 由于JSON标准说明书上面没有提到注释是否是合法的组成，所以这是一个非标准的特性；尽管如此，这个特性还是被广泛地使用。
     * 注意：该属性默认是false，因此必须显式允许，即通过JsonParser.Feature.ALLOW_COMMENTS 配置为true。
     */
    AllowComment,
    /**
     * 这个特性决定parser是否将允许使用非双引号属性名字， （这种形式在Javascript中被允许，但是JSON标准说明书中没有）。
     * 注意：由于JSON标准上需要为属性名称使用双引号，所以这也是一个非标准特性，默认是false的。
     * 同样，需要设置JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES为true，打开该特性。
     */
    AllowUnQuotedFieldNames,
    /**
     * 该特性决定parser是否允许单引号来包住属性名称和字符串值。
     * 注意：默认下，该属性也是关闭的。需要设置JsonParser.Feature.ALLOW_SINGLE_QUOTES为true
     */
    AllowSingleQuotes,
    /**
     * 该特性决定JSON对象属性名称是否可以被String#intern 规范化表示。如果允许，则JSON所有的属性名将会 intern() ；
     * 如果不设置，则不会规范化，默认下，该属性是开放的。此外，必须设置CANONICALIZE_FIELD_NAMES为true
     * 关于intern方法作用：当调用 intern 方法时，如果池已经包含一个等于此 String 对象的字符串 （该对象由 equals(Object) 方法确定），则返回池中的字符串。
     * 否则，将此 String 对象添加到池中， 并且返回此 String 对象的引用。
     */
    InternFieldNames,
    /**
     * 这个设置为true则遇到字符串符合ISO8601格式的日期时，会直接转换成日期类。
     */
    AllowISO8601DateFormat,
    /**
     * 允许多重逗号,如果设为true,则遇到多个逗号会直接跳过。
     * {"a":1,,,"b":2}
     */
    AllowArbitraryCommas,
    /**
     * 这个设置为true则用BigDecimal类来装载数字，否则用的是double；
     */
    UseBigDecimal,  
    /**
     * @since 1.1.2 
     * 忽略不匹配
     */
    IgnoreNotMatch,
    /**
     * @since 1.1.3
     * 如果你用fastjson序列化的文本，输出的结果是按照fieldName排序输出的，parser时也能利用这个顺序进行优化读取。这种情况下，parser能够获得非常好的性能
     */
    SortFeidFastMatch,  
    /**
     * @since 1.1.3
     * 禁用ASM
     */
    DisableASM, 
    /**
     * @since 1.1.7
     * 禁用循环引用检测
     */
    DisableCircularReferenceDetect,    
    /**
     * @since 1.1.10
     * 对于没有值的字符串属性设置为空串
     */
    InitStringFieldAsEmpty,   
    /**
     * @since 1.1.35
     * 支持数组to对象
     */
    SupportArrayToBean,   
    /**
     * @since 1.2.3
     * 属性保持原来的顺序
     */
    OrderedField,  
    /**
     * @since 1.2.5
     * 禁用特殊字符检查
     */
    DisableSpecialKeyDetect,
    
    /**
     * @since 1.2.9
     * 使用对象数组
     */
    UseObjectArray;

}
```

## **测试代码**

### **目标JavaBean代码：**

```java
class User {
    
    //指定序列化字段顺序，字段名称
    @JSONField(ordinal=4,name="ID")
    private Integer id;
    
    //指定序列化字段顺序，不参加序列化
    @JSONField(ordinal=3,serialize=false)
    private String name; 
    
    //指定序列化字段顺序，不参加反序列化
    @JSONField(ordinal=2,deserialize=false)
    private Integer age;
    
    //指定序列化字段顺序，日期格式
    @JSONField(ordinal=1,format="yyyy-MM-dd")
    private Date creattime;
    
    //指定序列化规则，字段为null的时候依然参加序列化
    @JSONField(serialzeFeatures=SerializerFeature.WriteMapNullValue)
    private String phone;
  
    public Integer getId() {return id;}
    public Date getCreattime() {return creattime;}
    public void setId(Integer id) {this.id = id;}
    public void setCreattime(Date creattime) {this.creattime = creattime;}
    public String getName() {return name;}  
    public void setName(String name) {this.name = name;}  
    public Integer getAge() {return age;}  
    public void setAge(Integer age) {this.age = age;}
    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    @Override
    public String toString() {
        return "id="+id+"; name="+name+"; age="+age+"; createtime="+creattime;
    }
    
};
```

### **测试代码：**

```java
User user = new User();
user.setId(123456);
user.setName("wangbo");
user.setAge(28);
user.setCreattime(new Date());
        
String userStr = JSON.toJSONString(user);
System.out.println(userStr);
        
User user2 = JSON.parseObject(userStr, User.class);
System.out.println(user2);
```

### **执行结果：**

userStr

```
{"phone":null,"creattime":"2018-12-04","age":28,"ID":123456}
```

user2

```
id=123456; name=null; age=null; createtime=Tue Dec 04 00:00:00 CST 2018
```

可以看出：

第一步序列化的结果：按照指定字段顺序序列化的，id字段序列化为ID，name没有参加序列化，createtime按照指定格式序列化，phone为null，但是参与了序列化。

（FastJson默认的序列化规则是字段的值为null的时候，不参与序列化，serialzeFeatures=SerializerFeature.WriteMapNullValue可以在value的值为null的时候，依然会把它的值序列化出来。）

第二部反序列化结果：age没有参与反序列化。

# JSONPath

FastJson 1.2.0之后的版本支持JSONPath。这是一个很强大的功能，可以在java框架中当作对象查询语言（OQL）来使用。

## API

```
public class JSONPath {
    
    //求值，静态方法
    public static Object eval(Object rootObject, String path);

    //计算size，Map非空元素个数，对象非空元素个数，Collection的Size，数组的长度。其他无法求值返回-1
    public static int size(Object rootObject, String path);

    //是否包含，path中是否存在对象
    public static boolean contains(Object rootObject, String path);

    //是否包含，path中是否存在指定值，如果是集合或者数组，在集合中查找value是否存在
    public static boolean containsValue(Object rootObject, String path, Object value);

    //在数组或者集合中添加元素
    public static void arrayAdd(Object rootObject, String path, Object... values);

    //修改制定路径的值，如果修改成功，返回true，否则返回false
    public static boolean set(Object rootObject, String path, Object value);

}
```



## 语法

| **JSONPATH**              | **描述**                                                     |
| ------------------------- | ------------------------------------------------------------ |
| $                         | 根对象，例如$.name                                           |
| [num]                     | 数组访问，其中num是数字，可以是负数。例如$[0].leader.departments[-1].name |
| [num0,num1,num2...]       | 数组多个元素访问，其中num是数字，可以是负数，返回数组中的多个元素。例如$[0,3,-2,5] |
| [start:end]               | 数组范围访问，其中start和end是开始小表和结束下标，可以是负数，返回数组中的多个元素。例如$[0:5] |
| [start:end :step]         | 数组范围访问，其中start和end是开始小表和结束下标，可以是负数；step是步长，返回数组中的多个元素。例如$[0:5:2] |
| [?(key)]                  | 对象属性非空过滤，例如$.departs[?(name)]                     |
| [key > 123]               | 数值类型对象属性比较过滤，例如$.departs[id >= 123]，比较操作符支持=,!=,>,>=,<,<= |
| [key = '123']             | 字符串类型对象属性比较过滤，例如$.departs[name = '123']，比较操作符支持=,!=,>,>=,<,<= |
| [key like 'aa%']          | 字符串类型like过滤， 例如$.departs[name like 'sz*']，通配符只支持%  支持not like |
| [key rlike 'regexpr']     | 字符串类型正则匹配过滤， 例如departs[name like 'aa(.)*']， 正则语法为jdk的正则语法，支持not rlike |
| [key in ('v0', 'v1')]     | IN过滤, 支持字符串和数值类型  例如:  .departs[namein(′wenshao′,′Yako′)].departs[namein(′wenshao′,′Yako′)].departs[id not in (101,102)] |
| [key between 234 and 456] | BETWEEN过滤, 支持数值类型，支持not between  例如:  .departs[idbetween101and201].departs[idbetween101and201].departs[id not between 101 and 201] |
| length() 或者 size()      | 数组长度。例如$.values.size()  支持类型java.util.Map和java.util.Collection和数组 |
| .                         | 属性访问，例如$.name                                         |
| ..                        | deepScan属性访问，例如$..name                                |
| *                         | 对象的所有属性，例如$.leader.*                               |
| ['key']                   | 属性访问。例如$['name']                                      |
| ['key0','key1']           | 多个属性访问。例如$['id','name']                             |

 注意：以下两种写法的语义是相同的：

```
$.store.book[0].title
$['store']['book'][0]['title']
```



## 语法示例

| **JSONPath** | **语义**          |
| ------------ | ----------------- |
| $            | 根对象            |
| $[-1]        | 最后元素          |
| $[:-2]       | 第1个至倒数第2个  |
| $[1:]        | 第2个之后所有元素 |
| $[1,2,3]     | 集合中1,2,3个元素 |



## 代码示例



```java
package com.wangbo.fastjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

public class Test {
    
    public static void main(String[] args) {
        String jsonStr = "{\n" +
                "    \"store\": {\n" +
                "        \"bicycle\": {\n" +
                "            \"color\": \"red\",\n" +
                "            \"price\": 19.95\n" +
                "        },\n" +
                "        \"book\": [\n" +
                "            {\n" +
                "                \"author\": \"刘慈欣\",\n" +
                "                \"price\": 8.95,\n" +
                "                \"category\": \"科幻\",\n" +
                "                \"title\": \"三体\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"author\": \"itguang\",\n" +
                "                \"price\": 12.99,\n" +
                "                \"category\": \"编程语言\",\n" +
                "                \"title\": \"go语言实战\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        JSONObject jsonObject = JSON.parseObject(jsonStr);

        System.out.println(jsonObject.toString());

        //得到所有的书
        List<Book> books = (List<Book>) JSONPath.eval(jsonObject, "$.store.book");
        System.out.println("books=" + books);

        //得到所有的书名
        List<String> titles = (List<String>) JSONPath.eval(jsonObject, "$.store.book.title");
        System.out.println("titles=" + titles);

        //第一本书title
        String title = (String) JSONPath.read(jsonStr, "$.store.book[0].title");
        System.out.println("title=" + title);

        //price大于10元的book
        List<Book> list = (List<Book>) JSONPath.read(jsonStr, "$.store.book[price > 10]");
        System.out.println("price大于10元的book="+ list);

        //price大于10元的title
        List<String> list2 =(List<String>) JSONPath.read(jsonStr, "$.store.book[price > 10].title");
        System.out.println("price大于10元的title=" + list2);

        //category(类别)为科幻的book
        List<Book> list3 = (List<Book>) JSONPath.read(jsonStr,"$.store.book[category = '科幻']");
        System.out.println("category(类别)为科幻的book=" + list3);

        //bicycle的所有属性值
        Collection<String> values = (Collection<String>) JSONPath.eval(jsonObject, "$.store.bicycle.*");
        System.out.println("bicycle的所有属性值={}" + values);

        //bicycle的color和price属性值
        List<String> read =(List<String>) JSONPath.read(jsonStr, "$.store.bicycle['color','price']");
        System.out.println("bicycle的color和price属性值=" + read);

    }

}
```

## 运行结果



```
{"store":{"bicycle":{"color":"red","price":19.95},"book":[{"author":"刘慈欣","price":8.95,"category":"科幻","title":"三体"},{"author":"itguang","price":12.99,"category":"编程语言","title":"go语言实战"}]}}
books=[{"author":"刘慈欣","price":8.95,"category":"科幻","title":"三体"},{"author":"itguang","price":12.99,"category":"编程语言","title":"go语言实战"}]
titles=["三体","go语言实战"]
title=三体
price大于10元的book=[{"author":"itguang","price":12.99,"category":"编程语言","title":"go语言实战"}]
price大于10元的title=["go语言实战"]
category(类别)为科幻的book=[{"author":"刘慈欣","price":8.95,"category":"科幻","title":"三体"}]
bicycle的所有属性值={}[red, 19.95]
bicycle的color和price属性值=[red, 19.95]
```
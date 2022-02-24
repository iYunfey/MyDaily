# 1、Oracle



# 2、String

### 2.1、集合转字符串

​	注意逗号后面的空格也要处理

# 3、Lombok

### 3.1、无法转换为lombok.val||Error java

idea启动项目报错：不兼容的类型,无法转换为lombok.val||Error java：找不到符号，符号：方法getXXX()：位置：类型为com.xxx.xxxClass的变量xxx

解决办法：control+alt+S打开设置，Build,Execution,Development->Complier->Annotation Processors->勾选Enable annotation processing

# 4、Jersey:com.sun.jersey.spi.inject.Errors$ErrorMessagesException

GET请求的参数和POST的请求参数搞混了。

- @FormParam with @POST is fine.
- @QueryParam with @GET is fine.
- @FormParam with @GET = error.

# 5、tomcat

## 5.1、乱码

```java
1、解决tomcat启动时黑框中出现乱码：
（一般tomcat出现乱码都是字符集的问题）
在tomcat目录下conf中找到logging.properties文件
大概在47行处把java.util.logging.ConsoleHandler.encoding = UTF-8改为java.util.logging.ConsoleHandler.encoding = GBK 重启tomcat及可
    与编码有关的都照此设置

2、解决tomcat部署完项目后访问项目出现中文乱码问题：

2.1、在tomcat目录的bin文件下找到catalina.bat修改216行左右

set"JAVA_OPTS=%JAVA_OPTS% %JSSE_OPTS%" 修改为set"JAVA_OPTS=%JAVA_OPTS% %JSSE_OPTS%" -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8

2.2、在tomcat的server.xml配置
添加：URIEncoding=”UTF-8”
    
IDEA的idea.vmpotions与idea64.vmoptions添加
-Dfile.encoding=UTF-8
```

## 5.2、ContainerBase.addChild: start: 错误

报错信息

```
严重: ContainerBase.addChild: start: 

org.apache.catalina.LifecycleException: Failed to start component [StandardEngine[Catalina].StandardHost[localhost].StandardContext[/gomeetpc]]

 at org.apache.catalina.util.LifecycleBase.start(LifecycleBase.java:162)

 at org.apache.catalina.core.ContainerBase.addChildInternal(ContainerBase.java:1018)

 at org.apache.catalina.core.ContainerBase.addChild(ContainerBase.java:994)

 at org.apache.catalina.core.StandardHost.addChild(StandardHost.java:652)

 at org.apache.catalina.startup.HostConfig.deployDescriptor(HostConfig.java:712)

 at org.apache.catalina.startup.HostConfig$DeployDescriptor.run(HostConfig.java:2003)

 at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:471)

 at java.util.concurrent.FutureTask.run(FutureTask.java:262)

 at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145)

 at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)

 at java.lang.Thread.run(Thread.java:745)
```

问题解决如下

==================================================

换了一个tomcat的版本就可以了



# 6、IDEA

## 6.1、编译报错Error:Internal error: (java.lang.IllegalStateException)

需要重新配置：一般设置如文字大小、JDK配置、Maven配置、Git配置、插件重新安装，其余的在用到时再修改IDEA配置

# 7、SpringBoot

## 7.1、Springboot 启动报错 The bean 'xxxx', defined in class path resource XXX

```xml
需要在application.yml配置文件中，spring下增加如下配置：
main:
  allow-bean-definition-overriding: true
```

# 8、Redis

## 8.1、RedisCommandInterruptedException: Command interrupted

原因：Spring Boot 2.X默认采用的驱动lettuce有时候会有IO bug

解决办法：

在pom.xml文件中显式更换Redis驱动为jedis

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <exclusions>
       <!-- 排除因为Spring Boot Data Redis默认驱动lettuce引起的RedisCommandInterruptedException: Command interrupted-->
       <exclusion>
           <groupId>io.lettuce</groupId>
           <artifactId>lettuce-core</artifactId>
       </exclusion>
   </exclusions>
</dependency>
<!-- 声明Redis驱动为jedis-->
<dependency>
   <groupId>redis.clients</groupId>
   <artifactId>jedis</artifactId>
</dependency>
```


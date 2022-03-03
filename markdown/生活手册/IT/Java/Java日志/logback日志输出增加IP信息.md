logback日志输出增加IP信息

# 一、ClassicConverter实现类

我们创建一个类 `com.xxx.utils.IPConverterConfig` 并继承 `ch.qos.logback.classic.pattern.ClassicConverter`，内容如下：

```java
package com.xxx.utils;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 功能描述： 将ip打印到日志
 */
public class IPConverterConfig extends ClassicConverter {

    // 每次 log.info() 都会调用该 converter，所以缓存一下服务器ip
    private String cache;

    @Override
    public String convert(ILoggingEvent event) {
        if (cache != null) {
            return cache;
        }
        String result = "unknown";
        try {
            result = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        cache = result;
        return result;
    }
}
```

# 二、新增标签<conversionRule>

在 `logback.xml` 中加入，位置要在appender标签上面

```xml
<!--配置规则类的位置-->
<conversionRule conversionWord="ip" converterClass="com.xxx.utils.IPConverterConfig" />
```

# 三、修改打印格式

继续修改 `logback.xml` 中的 `<appender>` - `<encoder>` - `<pattern>` :

```xml
<appender ...(属性省略没写)...>
  <encoder>
    <pattern>%date{yyyy-MM-dd HH:mm:ss.SSS} [%ip] %level [%thread] %logger{36}%msg%n</pattern>
  </encoder>
</appender>
```

注意，打印时用 **%ip** 来表示该变量。

# 四、测试效果

## logback.xml

以打印日志到Console为例：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <!--配置规则类的位置-->
  <conversionRule conversionWord="ip" converterClass="com.xxx.utils.IPConverterConfig" />

  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>%date{yyyy-MM-dd HH:mm:ss.SSS} [%ip] %level [%thread] %logger{36} %msg%n</pattern>
    </encoder>
  </appender>

  <root level="info">
    <appender-ref ref="STDOUT"/>
  </root>
</configuration>
```

## 测试类Main

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Hello World!");
    }
}
```

# 五、完整logback

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <property name="LOG_PATH" value="/opt/app/logs" />
  <!--配置规则类的位置-->
  <conversionRule conversionWord="ip" converterClass="com.xxx.utils.IPConverterConfig" />
  <!-- 输出日志到文件，可选择按照时间与每个文件大小进行滚动 -->
  <appender name="FILE"
            class="ch.qos.logback.core.rolling.RollingFileAppender">
    <file>${LOG_PATH}/biz.log</file>
    <rollingPolicy
            class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
      <!-- rollover daily -->
      <fileNamePattern>/opt/app/logs/logback/biz-%d{yyyy-MM-dd_HH}.%i.txt</fileNamePattern>
      <!-- 每个文件最大100MB，即在单位时间（例如一个小时）内，可能会产生多个文件，各文件以1、2、3…阿拉伯数字来标示 -->
      <maxFileSize>100MB</maxFileSize>
      <!-- 保留60天（或小时，根据fileNamePattern来决定单位）的历史日志 -->
      <maxHistory>60</maxHistory>
      <!-- 所有归档文件（不包括当前文件）的总大小不超过1GB -->
      <totalSizeCap>1GB</totalSizeCap>
    </rollingPolicy>
    <encoder>
        <pattern>%date{yyyy-MM-dd HH:mm:ss.SSS} [%ip] %level [%thread] %logger{36} %msg%n</pattern>
    </encoder>
  </appender>
  <!--将上面配置的“FILE” appender包装成异步方式来提高性能，但是在高并发写日志请求的情况下默认会丢弃低级别日志，当然也可以选择配置不丢弃日志，但业务线程阻塞，注意只有logback-classic
  1.0.4+才支持AsyncAppender -->
  <appender name="ASYNC_FILE" class="ch.qos.logback.classic.AsyncAppender">
    <!-- 注意一个AsyncAppender只能引用一个appender，不能引用多个 -->
    <appender-ref ref="FILE" />
    <!-- 队列大小（整型，默认值为256，相当于最大可以同时缓存256个写日志请求到内存），值越大，高并发写日志请求的情况下越能提升程序性能，但同时也会占用更多的堆内存。建议结合压测来调整合适的队列大小 -->
    <queueSize>256</queueSize>
    <!-- 阈值（整型，默认值为队列大小queueSize的20%）。默认当队列的剩余空间小于此阈值时， 则会丢弃TRACE、DEBUG、INFO级别的日志，保留WARN、ERROR级别的日志
    。如果设置为0，表示不丢弃日志，但当队列满时，业务线程（不是写日志的异步线程）会阻塞 -->
    <discardingThreshold>0</discardingThreshold>
  </appender>

  <root level="INFO">
    <appender-ref ref="ASYNC_FILE" />
  </root>
</configuration>
```


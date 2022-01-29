# logback输出json格式日志（包括mdc）发送到kafka

## 1、pom.xml

```xml
               <!-- kafka -->
        <dependency>
            <groupId>com.github.danielwegener</groupId>
            <artifactId>logback-kafka-appender</artifactId>
            <version>0.2.0-RC1</version>
            <scope>runtime</scope>
        </dependency>
 <!-- logback-->
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-core</artifactId>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>net.logstash.logback</groupId>
            <artifactId>logstash-logback-encoder</artifactId>
            <version>5.0</version>
        </dependency>

 <!-- other-->
     <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.25</version>
        <scope>compile</scope>
    </dependency>           
            <dependency>
                <groupId>ch.qos.logback</groupId>
                <artifactId>logback-access</artifactId>
            </dependency>
```

## 2、 spring-logback.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>

<configuration debug="false" scan="true" scanPeriod="600000">
    <!--定义日志文件的存储地址 勿在 LogBack 的配置中使用相对路径 -->
    <property name="LOG_HOME" value="/var/log" />
    <contextName>${HOSTNAME}</contextName>
    <springProperty scope="context" name="appName"
        source="spring.application.name" />
    <springProperty scope="context" name="ip"
        source="spring.cloud.client.ipAddress" />

    <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符 -->
    <property name="CONSOLE_LOG_PATTERN"
        value="[%d{yyyy-MM-dd HH:mm:ss.SSS} ${ip} ${appName} %highlight(%-5level) %yellow(%X{X-B3-TraceId}),%green(%X{X-B3-SpanId}),%blue(%X{X-B3-ParentSpanId}) %yellow(%thread) %green(%logger) %msg%n" />


    <!-- <logger name="org.springframework.web" level="DEBUG" /> -->


    <!-- show parameters for hibernate sql 专为 Hibernate 定制 -->
    <!--<logger name="org.hibernate.type.descriptor.sql.BasicBinder" level="TRACE" 
        /> -->
    <!--<logger name="org.hibernate.type.descriptor.sql.BasicExtractor" level="DEBUG" 
        /> -->
    <!--<logger name="org.hibernate.engine.QueryParameters" level="DEBUG" /> -->
    <!--<logger name="org.hibernate.engine.query.HQLQueryPlan" level="DEBUG" 
        /> -->

    <!-- <logger name="org.hibernate.SQL" level="DEBUG" /> -->
    <logger name="logging.level.com.italktv.platform" level="info" />

    <!-- 控制台输出 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>${CONSOLE_LOG_PATTERN}</pattern>
            <charset>utf-8</charset>
        </encoder>
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>debug</level>
        </filter>
    </appender>

    <!-- 按照每天生成日志文件 -->
    <appender name="FILE"
        class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 正在记录的日志文件的路径及文件名 -->
        <file>${LOG_HOME}/bigdata/data-api.log</file>
        <rollingPolicy
            class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!--日志文件输出的文件名 -->
            <FileNamePattern>${LOG_HOME}/bigdata/data-api.%d{yyyy-MM-dd}.%i.log
            </FileNamePattern>
            <!--日志文件保留天数 -->
            <MaxHistory>30</MaxHistory>
            <maxFileSize>1MB</maxFileSize>
            <totalSizeCap>10MB</totalSizeCap>
        </rollingPolicy>
        <encoder
            class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
            <providers>
                <timestamp>
                    <timeZone>UTC</timeZone>
                </timestamp>
                <pattern>
                    <pattern>
                        {
                        "level": "%level",
                        "trace": "%X{X-B3-TraceId:-}",
                        "requestId": "%X{requestId}",
                        "remoteIp": "%X{remoteIp}",
                        "span": "%X{X-B3-SpanId:-}",
                        "parent":
                        "%X{X-B3-ParentSpanId:-}",
                        "thread": "%thread",
                        "class":
                        "%logger{40}",
                        "message": "%message",
                        "stack_trace":
                        "%exception{10}"
                        }
                    </pattern>
                </pattern>
            </providers>
        </encoder>
        <!--日志文件最大的大小 <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy"> 
            <MaxFileSize>10KB</MaxFileSize> </triggeringPolicy> -->
    </appender>
    
        <!-- pom dependency
    
    <dependency>
        <groupId>com.github.danielwegener</groupId>
        <artifactId>logback-kafka-appender</artifactId>
        <version>0.1.0</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>net.logstash.logback</groupId>
        <artifactId>logstash-logback-encoder</artifactId>
        <version>4.11</version>
    </dependency>
   
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.25</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-core</artifactId>
        <version>1.1.11</version>
    </dependency>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.1.11</version>
    </dependency>
    
     -->
    <!--  other appender
    <appender name="kafkaAppenderAnotherEncode"
        class="com.github.danielwegener.logback.kafka.KafkaAppender">

        <encoder
            class="com.github.danielwegener.logback.kafka.encoding.PatternLayoutKafkaMessageEncoder">
            <layout class="net.logstash.logback.layout.LogstashLayout">
                <includeMdc>true</includeMdc>
                <includeContext>true</includeContext>
                <includeCallerData>true</includeCallerData>
                <customFields>{"system":"test"}</customFields>
                <fieldNames class="net.logstash.logback.fieldnames.ShortenedFieldNames" />
            </layout>
        </encoder>

        <topic>tv_server_logstash_log</topic>
        <keyingStrategy
            class="com.github.danielwegener.logback.kafka.keying.HostNameKeyingStrategy" />
        <deliveryStrategy
            class="com.github.danielwegener.logback.kafka.delivery.AsynchronousDeliveryStrategy" />
        <producerConfig>bootstrap.servers=211.100.75.227:9092</producerConfig>
        <producerConfig>acks=0</producerConfig>
        <producerConfig>linger.ms=1000</producerConfig>
        <producerConfig>block.on.buffer.full=false</producerConfig>
        <appender-ref ref="STDOUT" />
    </appender>
-->

    <!-- https://www.cnblogs.com/maxzhang1985/p/9522507.html 
    https://logback.qos.ch/manual/layouts.html
    -->
    <appender name="kafkaAppender"
        class="com.github.danielwegener.logback.kafka.KafkaAppender">

        <encoder charset="UTF-8"
            class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">

            <providers>
                <mdc />
                <context />
                <timestamp>
                    <timeZone>UTC</timeZone>
                </timestamp>
                <pattern>
                    <pattern>
                        {

                        "level": "%level",
                        "trace": "%X{X-B3-TraceId:-}",
                        "span":
                        "%X{X-B3-SpanId:-}",
                        "parent": "%X{X-B3-ParentSpanId:-}",
                        "thread":
                        "%thread",
                        "class": "%logger{40}",
                        "message": "%message",
                        "stack_trace": "%exception{10}"
                        }
                    </pattern>
                </pattern>
            </providers>
        </encoder>

        <topic>tv_server_logstash_log</topic>
        <keyingStrategy
            class="com.github.danielwegener.logback.kafka.keying.HostNameKeyingStrategy" />
        <deliveryStrategy
            class="com.github.danielwegener.logback.kafka.delivery.AsynchronousDeliveryStrategy" />
        <producerConfig>bootstrap.servers=127.0.0.1:9092</producerConfig>
        <!-- don't wait for a broker to ack the reception of a batch. -->
        <producerConfig>acks=0</producerConfig>
        <!-- wait up to 1000ms and collect log messages before sending them as 
            a batch -->
        <producerConfig>linger.ms=1000</producerConfig>
        <!-- even if the producer buffer runs full, do not block the application 
            but start to drop messages -->
        <!--<producerConfig>max.block.ms=0</producerConfig> -->
        <producerConfig>block.on.buffer.full=false</producerConfig>
        <!-- kafka连接失败后，使用下面配置进行日志输出 -->
        <appender-ref ref="STDOUT" />
    </appender>



    <appender name="ASYNC" class="ch.qos.logback.classic.AsyncAppender">
        <appender-ref ref="kafkaAppender" />
    </appender>

    <!-- 日志输出级别 -->
    <root level="INFO">
        <!-- 生产上不输出stdout log -->
        <appender-ref ref="STDOUT" />
        <!-- <appender-ref ref="FILE" /> -->

        <!-- <appender-ref ref="kafkaAppender" /> -->
        <appender-ref ref="ASYNC" />

    </root>

</configuration>
```

## 3、添加一个mdc在logback

```java
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


@Component
public class LogInterceptor implements HandlerInterceptor {

    private final static String REQUEST_ID = "requestId";
    private static final Logger LOGGER  = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String xForwardedForHeader = httpServletRequest.getHeader("X-Forwarded-For");
        String remoteIp = httpServletRequest.getRemoteAddr();
        String uuid = UUID.randomUUID().toString();
        LOGGER.info("put requestId ({}) to logger", uuid);
        LOGGER.info("request id:{}, client ip:{}, X-Forwarded-For:{}", uuid, remoteIp, xForwardedForHeader);
        MDC.put(REQUEST_ID, uuid);
        MDC.put("remoteIp", remoteIp);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
            ModelAndView modelAndView) throws Exception {
        String uuid = MDC.get(REQUEST_ID);
        LOGGER.info("remove requestId ({}) from logger", uuid);
        MDC.remove(REQUEST_ID);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e)
            throws Exception {

    }
}
```

## 4、添加切面 intercept

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Autowired
    private LogInterceptor logInterceptor;
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);
        super.addInterceptors(registry);
    }
}
```

参考：

logback 手册：https://logback.qos.ch/manual/layouts.html
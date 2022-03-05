# 一、简介

MDC（Mapped Diagnostic Context，映射调试上下文）是 **log4j** 、**logback**及**log4j2** 提供的一种方便在多线程条件下记录日志的功能。

**MDC** 可以看成是一个**与当前线程绑定的哈希表**，可以往其中添加键值对。MDC 中包含的内容可以**被同一线程中执行的代码所访问**。

当前线程的子线程会继承其父线程中的 MDC 的内容。当需要记录日志时，只需要从 MDC 中获取所需的信息即可。MDC 的内容则由程序

在适当的时候保存进去。对于一个 Web 应用来说，通常是在请求被处理的最开始保存这些数据。

## MDC的使用方法

向MDC中设置值：MDC.put(key, value);

从MDC中取值：MDC.get(key);

将MDC中内容打印到日志中：%X{key}

# 二、实现

## 实现一：过滤器+AOP

### 新建过滤器

新建一个过滤器，实现Filter，重写init，doFilter，destroy方法，设置traceId放在doFilter中，MDC.clear()方法可以在doFilter的finally

代码块或者在destroy中调用。

```java
/**
 * @title 为logback日志增加traceId
 */
@WebFilter(urlPatterns = "/*", filterName = "logMdcFilter")
public class LogMdcFilter implements Filter {
    private static final String UNIQUE_ID = "traceId";
 
    @Override
    public void init(FilterConfig filterConfig) {
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean bInsertMDC = insertMDC();
        try {
            chain.doFilter(request, response);
        } finally {
            if(bInsertMDC) {
                MDC.remove(UNIQUE_ID);
            }
        }
    }
 
    @Override
    public void destroy() {
    }
 
    private boolean insertMDC() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().replace("-", "");
        MDC.put(UNIQUE_ID, uniqueId);
        return true;
    }
}
```

### 配置logback日志格式

```xml
将 [%X[traceId]] 添加到日志输出格式中

 <pattern>%d{HH:mm:ss} [%thread][%X{traceId}] %-5level %logger{36} - %msg%n</pattern>
```

### 异步任务补充

完成前2步之后 , 从前端发起的请求就可以输出traceId了 , 但是一些未经过前端的定时或异步任务 , 是走不了过滤器的 . 所以我们还需要添

加一个类

```java
/**
 * @title 为异步方法添加traceId
 */
@Aspect
@Component
public class LogMdcAspect {
    private static final String UNIQUE_ID = "traceId";
 
    @Pointcut("execution(public * 包路径..*.*(..))")
    public void LogHelp() {
    }
    
    @Pointcut("@annotation(org.springframework.scheduling.annotation.Async)")
    public void logPointCut() {
    }
 
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MDC.put(UNIQUE_ID, UUID.randomUUID().toString().replace("-",""));
        Object result = point.proceed();// 执行方法
        MDC.remove(UNIQUE_ID);
        return result;
    }
}
```

## 实现二： 使用JWT token过滤器的项目

Spring Boot项目经常使用Spring Security+JWT来做权限限制，在这种情况下，我们通过新建Filter过滤器来设置traceId，那么在验证

token这部分的日志就不会带上traceId，因此我们需要把代码放在jwtFilter中

```java
/**
 * token过滤器 验证token有效性
 *
 * @author china
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {undefined
    @Autowired
    private TokenService tokenService;
 
    /**
     * 日志跟踪标识
     */
    private static final String TRACE_ID = "TRACE_ID";
 
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {undefined
        MDC.put(TRACE_ID, UUID.randomUUID().toString());
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication())) {undefined
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        chain.doFilter(request, response);
    }
     
    @Override
    public void destroy() {undefined
        MDC.clear();
    }
}
```

## 实现三：拦截器

定义一个拦截器，重写preHandle方法，在方法中通过MDC设置traceId

```java
/**
 * MDC设置traceId拦截器
 */
@Component
public abstract class TraceIdInterceptor extends HandlerInterceptorAdapter {
    private static final String UNIQUE_ID = "TRACE_ID";
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put(UNIQUE_ID, UUID.randomUUID().toString());
        return true;
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        MDC.clear();
    }
}
```

**修改日志格式**

```xml
<property name="pattern">[TRACEID:%X{traceId}] %d{HH:mm:ss.SSS} %-5level %class{-1}.%M()/%L - %msg%xEx%n</property>
```

重点是**%X{traceId}**，**traceId**和MDC中的键名称一致

## MDC 存在的问题

### 异步子线程中打印日志丢失traceId

#### 解决方案一：异步方法带入上下文的traceId

异步方法会开启一个新线程，我们想要是异步方法和主线程共用同一个traceId，首先先新建一个任务适配器MdcTaskDecorator，

```java
public class MdcTaskDecorator implements TaskDecorator 
    /**
     * 使异步线程池获得主线程的上下文
     * @param runnable
     * @return
     */
    @Override
    public Runnable decorate(Runnable runnable) {undefined
        Map<String,String> map = MDC.getCopyOfContextMap();
        return () -> {undefined
            try{undefined
                MDC.setContextMap(map);
                runnable.run();
            } finally {undefined
                MDC.clear();
            }
        };
    }
}
```

然后，在线程池配置中增加executor.setTaskDecorator(new MdcTaskDecorator())的设置

```java
/**
 * 线程池配置
 **/
@EnableAsync
@Configuration
public class ThreadPoolConfig {undefined
    private int corePoolSize = 50;
    private int maxPoolSize = 200;
    private int queueCapacity = 1000;
    private int keepAliveSeconds = 300;
 
    @Bean(name = "threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {undefined
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setTaskDecorator(new MdcTaskDecorator());
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
```

最后，在业务代码上使用@Async开启异步方法即可

```java
@Async("threadPoolTaskExecutor")
void testSyncMethod();
```

#### 解决方案二：重写线程池

子线程在打印日志的过程中traceId将丢失，解决方式为重写线程池，对于直接new创建线程的情况不考略【实际应用中应该避免这种用

法】，重写线程池无非是对任务进行一次封装。

##### 线程池封装类：ThreadPoolExecutorMdcWrapper.java

说明：

- 继承**ThreadPoolExecutor**类，重新执行任务的方法
- 通过**ThreadMdcUtil**对任务进行一次包装

```java
public class ThreadPoolExecutorMdcWrapper extends ThreadPoolExecutor {
    public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                        BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                        RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()), result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }
}
```

##### 线程traceId封装工具类：ThreadMdcUtil.java

```java
public class ThreadMdcUtil {
    public static void setTraceIdIfAbsent() {
        if (MDC.get(Constants.TRACE_ID) == null) {
            MDC.put(Constants.TRACE_ID, TraceIdUtil.getTraceId());
        }
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
```

说明【**以封装Runnable为例**】：

- 判断当前线程对应MDC的Map是否存在，存在则设置
- 设置MDC中的traceId值，不存在则新生成，针对不是子线程的情况，如果是子线程，MDC中traceId不为null
- 执行run方法

代码等同于以下写法，会更直观

```java
public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return new Runnable() {
            @Override
            public void run() {
                if (context == null) {
                    MDC.clear();
                } else {
                    MDC.setContextMap(context);
                }
                setTraceIdIfAbsent();
                try {
                    runnable.run();
                } finally {
                    MDC.clear();
                }
            }
        };
    }
```

重新返回的是包装后的Runnable，在该任务执行之前【**runnable.run()**】先将主线程的Map设置到当前线程中【 即**MDC.setContextMap(context)**】，这样子线程和主线程MDC对应的Map就是一样的了。



### HTTP调用丢失traceId

在使用HTTP调用第三方服务接口时traceId将丢失，需要对HTTP调用工具进行改造，在发送时在request header中添加traceId，在下层被调用方添加拦截器获取header中的traceId添加到MDC中。

HTTP调用有多种方式，比较常见的有**HttpClient**、**OKHttp**、**RestTemplate**，所以只给出这几种HTTP调用的解决方式。

#### 1、HttpClient

实现HttpClient拦截器：

```java
public class HttpClientTraceIdInterceptor implements HttpRequestInterceptor {
    // 实现HttpRequestInterceptor接口并重写process方法
    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        String traceId = MDC.get(Constants.TRACE_ID);
        // 当前线程调用中有traceId，则将该traceId进行透传
        if (traceId != null) {
            // 添加请求体
            httpRequest.addHeader(Constants.TRACE_ID, traceId);
        }
    }
}
```



如果调用线程中含有traceId，则需要将获取到的traceId通过request中的header向下透传下去。

为**HttpClient**添加拦截器：

```java
private static CloseableHttpClient httpClient = HttpClientBuilder.create()
    		// 通过addInterceptorFirst方法为HttpClient添加拦截器。
            .addInterceptorFirst(new HttpClientTraceIdInterceptor())
            .build();
```

#### 2、OKHttp

实现OKHttp拦截器：

```java
public class OkHttpTraceIdInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String traceId = MDC.get(Constants.TRACE_ID);
        Request request = null;
        if (traceId != null) {
            //添加请求体
            request = chain.request().newBuilder().addHeader(Constants.TRACE_ID, traceId).build();
        }
        Response originResponse = chain.proceed(request);

        return originResponse;
    }
}
```

实现**Interceptor**拦截器，重写interceptor方法，实现逻辑和HttpClient差不多，如果能够获取到当前线程的traceId则向下透传。

为OkHttp添加拦截器：

```java
private static OkHttpClient client = new OkHttpClient.Builder()
			// 调用addNetworkInterceptor方法添加拦截器。
            .addNetworkInterceptor(new OkHttpTraceIdInterceptor())
            .build();
```

#### 3、RestTemplate

实现RestTemplate拦截器：

```java
public class RestTemplateTraceIdInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        String traceId = MDC.get(Constants.TRACE_ID);
        if (traceId != null) {
            httpRequest.getHeaders().add(Constants.TRACE_ID, traceId);
        }

        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
```

实现ClientHttpRequestInterceptor接口，并重写intercept方法，其余逻辑都是一样的不重复说明。

为RestTemplate添加拦截器：

```java
// 调用setInterceptors方法添加拦截器。
restTemplate.setInterceptors(Arrays.asList(new RestTemplateTraceIdInterceptor()));
```

### 第三方服务拦截器

HTTP调用第三方服务接口全流程traceId需要第三方服务配合，第三方服务需要添加拦截器拿到request header中的traceId并添加到MDC中。

```java
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果有上层调用就用上层的ID
        String traceId = request.getHeader(Constants.TRACE_ID);
        if (traceId == null) {
            traceId = TraceIdUtils.getTraceId();
        }
        
        MDC.put("traceId", traceId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        MDC.remove(Constants.TRACE_ID);
    }
}
```

说明：

- 先从request header中获取traceId
- 从request header中获取不到traceId则说明不是第三方调用，直接生成一个新的traceId
- 将生成的traceId存入MDC中

除了需要添加拦截器之外，还需要在日志格式中添加traceId的打印，如下：

```xml
 <!- 添加%X{traceId} -->
 <property name="pattern">[TRACEID:%X{traceId}] %d{HH:mm:ss.SSS} %-5level %class{-1}.%M()/%L - %msg%xEx%n</property>
```


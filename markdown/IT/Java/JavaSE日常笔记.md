# 1、String字符串

## 1.1、常见汉字排序

```java
public static void main(String[] args) throws Exception{
	Sting[] strs = {"张三(Z)","李四(L)"};
	Comparator c = Collator.getInstance(Locale.CHINA);
	Arrays.sort(strs,c);
	int i = 0;
	for(String str : strs){
		System.out.println(++i + " = " + str);
	}
}
```

## 1.2、去掉数字字符串尾部无效0

```java
 if(null != doubleStr && doubleStr.indexOf(".") > 0){  
     doubleStr = doubleStr.replaceAll("0+?$", "");//去掉多余的0  
     doubleStr = doubleStr.replaceAll("[.]$", "");//如最后一位是.则去掉  
 }
 
 也可以转换为BigDecimal，利用stripTrailingZeros()方法
 // 避免输出科学计数法
System.out.println(new BigDecimal("100.000").stripTrailingZeros().toPlainString());--100
```

## 1.3、重复拼接字符串

```java
String str = StringUtils.repeat("ab", 2);  
System.out.println(str); // 输出abab  
```

## 1.4、获取键盘输入的两种方法

```java
⽅法1：通过 Scanner
Scanner input = new Scanner(System.in);
String s = input.nextLine();
input.close();

⽅法2：通过 BufferedReader
BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
String s = input.readLine();
```





# 2、对象

## 2.1、新建对象并设置属性

```java
对象类加注解@Accessors(chain = true)

new Student().setXXX().setXXX();
```

## 2.2、对象非空字段注解

```java
1、 添加maven依赖包
	<dependency>
      <groupId>javax.validation</groupId>
      <artifactId>validation-api</artifactId>
      <version>1.1.0.Final</version>
    </dependency>

2、在校验字段上添加校验注解

class Profile{
        @NotNull(message = "字段值不能为空")
        private String name;
        
        @NotNull
        private String sex;
        
        @Max(value = 20,message = "最大长度为20")
        private String address;
        
        @NotNull
        @Size(max=10,min=5,message = "字段长度要在5-10之间")
        private String fileName;
        
        @Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$",message = "不满足邮箱正则表达式")
        private String email;
        
        @AssertTrue(message = "字段为true才能通过")
        private boolean isSave;
        
        @Future(message = "时间在当前时间之后才可以通过")
        private Date date;
 
}

3、在controller方法上添加@Validated注解
@RequestMapping("file/upload")
 public void upload(@RequestPart("files") MultipartFile files, @Validated Profile profile, Errors error) throws IOException {
       if(error.hasErrors()){
           return;
       }
       files.transferTo(new File(files.getOriginalFilename()));保存文件
}
```



另一示例

```java
import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

/**** imports ****/
public class ValidatorPojo {

	// 非空判断
	@NotNull(message = "id不能为空")
	private Long id;

	@Future(message = "需要一个将来日期") // 只能是将来的日期
	// @Past //只能去过去的日期
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 日期格式化转换
	@NotNull // 不能为空
	private Date date;

	@NotNull // 不能为空
	@DecimalMin(value = "0.1") // 最小值0.1元
	@DecimalMax(value = "10000.00") // 最大值10000元
	private Double doubleValue = null;

	@Min(value = 1, message = "最小值为1") // 最小值为1
	@Max(value = 88, message = "最大值为88") // 最大值88
	@NotNull // 不能为空
	private Integer integer;

	@Range(min = 1, max = 888, message = "范围为1至888") // 限定范围
	private Long range;

	// 邮箱验证
	@Email(message = "邮箱格式错误")
	private String email;

	@Size(min = 20, max = 30, message = "字符串长度要求20到30之间。")
	private String size;

}
```





# 3、日期

## 3.1、格式化日期

```java
// Date类型转String类型  
String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");  
System.out.println(date); // 输出 2021-05-01 01:01:01  
  
// String类型转Date类型  
Date date = DateUtils.parseDate("2021-05-01 01:01:01", "yyyy-MM-dd HH:mm:ss");  
  
// 计算一个小时后的日期  
Date date = DateUtils.addHours(new Date(), 1);  
```



# 4、JSON

## 4.1、list转JSONArray

```java
JSONArray array= JSONArray.parseArray(JSON.toJSONString(list));
```



```java
	public static void json2List(){ 
		//List -> JSON array 
		List<Bar> barList = new ArrayList<Bar>(); 
		barList.add(new Bar()); 
		barList.add(new Bar()); 
		barList.add(new Bar()); 
		String json= JSON.toJSONString(barList, true); 
		System.out.println(json); 
		//JSON array -> List 
		List<Bar> barList1 = JSON.parseArray(json,Bar.class); 
		for (Bar bar : barList1) { 
			System.out.println(bar.toString()); 
		} 
	} 

	public static void json2Map(){ 
		//Map -> JSON 
		Map<String,Bar> map = new HashMap<String, Bar>(); 
		map.put("a",new Bar()); 
		map.put("b",new Bar()); 
		map.put("c",new Bar()); 
		String json = JSON.toJSONString(map,true); 
		System.out.println(json); 
		//JSON -> Map 
		Map<String,Bar> map1 = (Map<String,Bar>)JSON.parse(json); 
		for (String key : map1.keySet()) { 
			System.out.println(key+":"+map1.get(key)); 
		} 
	} 

	public static void array2JSON(){ 
		String[] arr_String    = {"a","b","c"}; 
		String json_arr_String = JSON.toJSONString(arr_String,true); 
		System.out.println(json_arr_String); 
		JSONArray jsonArray = JSON.parseArray(json_arr_String); 
		for (Object o : jsonArray) { 
			System.out.println(o); 
		} 
		System.out.println(jsonArray); 
	} 
	public static void array2JSON2(){ 
		Bar[] arr_Bar    = {new Bar(),new Bar(),new Bar()}; 
		String json_arr_Bar = JSON.toJSONString(arr_Bar,true); 
		System.out.println(json_arr_Bar); 
		JSONArray jsonArray = JSON.parseArray(json_arr_Bar); 
		for (Object o : jsonArray) { 
			System.out.println(o); 
		} 
		System.out.println(jsonArray); 
	} 

	public static void map2JSON(){ 
		Map map = new HashMap(); 
		map.put("a","aaa"); 
		map.put("b","bbb"); 
		map.put("c","ccc"); 
		String json=JSON.toJSONString(map); 
		System.out.println(json); 
		Map map1 = JSON.parseObject(json); 
		for (Object o : map.entrySet()) { 
			Map.Entry<String,String> entry = (Map.Entry<String,String>)o; 
			System.out.println(entry.getKey()+"--->"+entry.getValue()); 
		} 
	} 
```



# 5、集合

## 5.1、子集合生成后保持父集合只读

生成子列表后，保持原列表的只读状态

```java
List<String> list = new ArrayList<>();
List<String> subList = list.subList(0, 2);
// setting list read-only
list = Collections.unmodifiableList(list);
// doSomething only-read on list
// read and write operation on subList
```



## 5.2、对象集合排序

```java
List<Student> stus = new ArrayList<Student>(){
{
    add(new Student("张三", 30, 1));	
    add(new Student("李四", 20, 2));	
    add(new Student("王五", 40, 3));	
    add(new Student("赵六", 30, 4));	
    add(new Student("陈七", 40, 5));	
    add(new Student("周八", 20, 6));	
}
};
Collections.sort(stus,new Comparator<Student>() {

    @Override
    public int compare(Student s1, Student s2) {
    	int flag;
    	// 首选按年龄升序排序
    	flag = s1.getAge()-s2.getAge();
    	if(flag==0){
            // 再按学号升序排序
            flag = s1.getNum()-s2.getNum();
   		}
    	return flag;
    }
});


stus.sort(Comparator.comparing(Student::getAge().reversed()).thenComparing(Student::getHeight);
```

二分查找先排序，效率最高

二分查找的前提：排序字段与查找条件相同

## 5.3、集合间快速转化

```java
List<String> list = new ArrayList<>(set);
Set<String> set = new HashSet<>(list);
```



## 5.4、List集合拼接成以逗号分隔的字符串

```
// 如何把list集合拼接成以逗号分隔的字符串 a,b,c  
List<String> list = Arrays.asList("a", "b", "c");  
// 第一种方法，可以用stream流  
String join = list.stream().collect(Collectors.joining(","));  
System.out.println(join); // 输出 a,b,c  
// 第二种方法，其实String也有join方法可以实现这个功能  
String join = String.join(",", list);  
System.out.println(join); // 输出 a,b,c  
```

# 6、Base64编码

  获取字节流时 res = new sun.misc.BASE64Encoder().encode(s.getBytes("GBK"));

​    字节流转换字符串时：    return new String(b,"GBK");

```java
/**
 * base64 编码、解码util
 */
public class Base64Util {
    /**
     * 将 s 进行 BASE64 编码
     */
    public static String encode(String s) {
        if (s == null)
            return null;
        String res = "";
        try {
            res = new sun.misc.BASE64Encoder().encode(s.getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 将 BASE64 编码的字符串 s 进行解码
     */
    public static String decode(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b,"GBK");
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(Base64Util.encode("哈哈"));
        System.out.println(Base64Util.decode("uf65/g=="));

    }

}


import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class Base64Util {

    final static Base64.Encoder encoder = Base64.getEncoder();
    final static Base64.Decoder decoder = Base64.getDecoder();

    /**
     * 给字符串加密
     * @param text
     * @return
     */
    public static String encode(String text) {
				// byte[] textByte = text.getBytes(StandardCharsets.UTF_8);
				// String encodedText = encoder.encodeToString(textByte);
				// return encodedText;
        return encoder.encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将加密后的字符串进行解密
     * @param encodedText
     * @return
     */
    public static String decode(String encodedText) {
        return new String(decoder.decode(encodedText), StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {

        String username = "Miracle Luna";
        String password = "p@sSW0rd";

        // 加密
        System.out.println("====  [加密后] 用户名/密码  =====");
        System.out.println(Base64Util.encode(username));
        System.out.println(Base64Util.encode(password));

        // 解密
        System.out.println("\n====  [解密后] 用户名/密码  =====");
        System.out.println(Base64Util.decode(Base64Util.encode(username)));
        System.out.println(Base64Util.decode(Base64Util.encode(password)));
    }
}
```



# 7、多线程

## 7.1、创建多线程的方式

```java
/**
 * 各参数含义
 * corePoolSize    : 线程池中常驻的线程数量。核心线程数，默认情况下核心线程会一直存活，即使处于闲置状态也不会 
 *                   受存活时间 keepAliveTime 的限制，除非将 allowCoreThreadTimeOut 设置为 true。
 * maximumPoolSize : 线程池所能容纳的最大线程数。超过这个数的线程将被阻塞。当任务队列为没有设置大小的
 *                           LinkedBlockingQueue时，这个值无效。
 * keepAliveTime   : 当线程数量多于 corePoolSize 时，空闲线程的存活时长，超过这个时间就会被回收
 * unit            : keepAliveTime 的时间单位
 * workQueue       : 存放待处理任务的队列
 * threadFactory   : 线程工厂
 * handler         : 拒绝策略，拒绝无法接收添加的任务
 */
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler) { ... ... }



public class ThreadPoolDemo {

    // 线程数
    public static final int THREAD_POOL_SIZE = 16;

    public static void main(String[] args) throws InterruptedException {
        // 使用 ThreadFactoryBuilder 创建自定义线程名称的 ThreadFactory
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("hyn-demo-pool-%d").build();
        
        // 创建线程池，其中任务队列需要结合实际情况设置合理的容量
        ThreadPoolExecutor executor = new ThreadPoolExecutor(THREAD_POOL_SIZE,
                THREAD_POOL_SIZE,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024),
                namedThreadFactory,
                new ThreadPoolExecutor.AbortPolicy());
        
        // 新建 1000 个任务，每个任务是打印当前线程名称
        for (int i = 0; i < 1000; i++) {
            executor.execute(() -> System.out.println(Thread.currentThread().getName()));
        }
        // 优雅关闭线程池
        executor.shutdown();
        executor.awaitTermination(1000L, TimeUnit.SECONDS);
        // 任务执行完毕后打印"Done"
        System.out.println("Done");
    }
}
```

7.2、有三个线程T1,T2,T3,怎么确保它们按顺序执⾏?

```java
示例一：
public class Testt {
    static Testt t = new Testt();

    class T1 extends Thread {
        
        @Override
        public void run() {
            // T3线程中要处理的东西
            System.out.println("T1线程执行");
            for (int i = 0; i < 10; i++) {
                System.out.println(this.getName() + ":" + i);
            }
        }
    }

    class T2 extends Thread {

        @Override
        public void run() {

            // T3线程中要处理的东西
            System.out.println("T2线程执行");

            for (int i = 0; i < 10; i++) {
                System.out.println(this.getName() + ":" + i);
            }
        }
    }

    class T3 extends Thread {

        @Override
        public void run() {

            // T3线程中要处理的东西
            System.out.println("T3线程执行");

            for (int i = 0; i < 10; i++) {
                System.out.println(this.getName() + ":" + i);
            }
        }
    }


    public static void main(String[] args) {
        try {
            t.new T3().start();//启动t3线程
            t.new T3().join();//阻塞主线程，执行完t3再返回
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            t.new T1().start();//启动t1线程
            t.new T1().join();//阻塞主线程，执行完t1再返回
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            t.new T2().start();//启动t2线程
            t.new T2().join();//阻塞主线程，执行完t2再返回
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

示例二：
    
public class JoinTestSync {
 
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ThreadJoinTest1 t1 = new ThreadJoinTest1("今天");
		ThreadJoinTest1 t2 = new ThreadJoinTest1("明天");
		ThreadJoinTest1 t3 = new ThreadJoinTest1("后天");
		/*
		 * 通过join方法来确保t1、t2、t3的执行顺序
		 * 
		 */
		t1.start();
		t1.join();	
		t2.start();
		t2.join();
		t3.start();
		t3.join();
	}
 
}
class ThreadJoinTest1 extends Thread{
    public ThreadJoinTest1(String name){
        super(name);
    }
    @Override
    public void run(){
        for(int i=0;i<5;i++){
            System.out.println(this.getName() + ":" + i);
        }
    }
}
```


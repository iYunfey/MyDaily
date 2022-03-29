英文官网：https://mybatis.org/mybatis-3/

中文官网：https://mybatis.net.cn/index.html

# 0、Mybatis generator

## 使用mybatis generator逆向工程生成model、mapper以及xml

### 第一步：pom文件中添加插件，用于生成代码

```xml
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.3.5</version>
    <configuration>
        <verbose>true</verbose>
        <overwrite>true</overwrite>
    </configuration>
</plugin>
```

### 第二步：generatorConfig.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <!--导入属性配置，可以不用该文件，不用的话下面的某些属性就要硬编码到代码里-->
    <properties resource="generator.properties"></properties>

    <!--指定特定数据库的jdbc驱动jar包的位置，引用generator.properties
    里的变量，下同-->
    <classPathEntry location="${jdbc.driverLocation}"/>

    <context id="default" targetRuntime="MyBatis3">

        <!-- optional，旨在创建class时，对注释进行控制 -->
        <commentGenerator>
            <property name="suppressDate" value="true"/>
            <!-- 是否去除自动生成的注释，true-是，false-否 -->
            <property name="suppressAllComments" value="true"/>
        </commentGenerator>

        <!--jdbc的数据库连接 -->
        <jdbcConnection
                driverClass="${jdbc.driverClass}"
                connectionURL="${jdbc.connectionURL}"
                userId="${jdbc.userId}"
                password="${jdbc.password}">
        </jdbcConnection>


        <!-- 非必需，类型处理器，在数据库类型和java类型之间的转换控制-->
        <javaTypeResolver>
            <!-- 把DECIMAL和NUMERIC强制转换为INTEGER，默认false -->
            <property name="forceBigDecimals" value="false"/>
        </javaTypeResolver>


        <!-- Model模型生成器,用来生成含有主键key的类，记录类 以及查询Example类
            targetPackage     指定生成的model生成所在的包名
            targetProject     指定在该项目下所在的路径
            酌情根据自己的项目结构要修改
        -->
        <javaModelGenerator targetPackage="com.XXX"
                            targetProject="src/main/java">

            <!-- 是否允许子包，即targetPackage.schemaName.tableName -->
            <property name="enableSubPackages" value="false"/>
            <!-- 是否对model添加 构造函数 -->
            <property name="constructorBased" value="true"/>
            <!-- 是否对类CHAR类型的列的数据进行trim操作 -->
            <property name="trimStrings" value="true"/>
            <!-- 建立的Model对象是否 不可改变  即生成的Model对象不会有 setter方法，只有构造方法 -->
            <property name="immutable" value="false"/>
        </javaModelGenerator>

        <!--Mapper映射文件生成所在的目录 为每一个数据库的表生成对应的SqlMap文件 
        酌情根据自己的项目结构要修改-->
        <sqlMapGenerator targetPackage="com.XXX"
                         targetProject="src/main/java">
            <property name="enableSubPackages" value="false"/>
        </sqlMapGenerator>

        <!-- 客户端代码，生成易于使用的针对Model对象和XML配置文件 的代码
                type="ANNOTATEDMAPPER",生成Java Model 和基于注解的Mapper对象
                type="MIXEDMAPPER",生成基于注解的Java Model 和相应的Mapper对象
                type="XMLMAPPER",生成SQLMap XML文件和独立的Mapper接口
                酌情根据自己的项目结构要修改
        -->
        <javaClientGenerator targetPackage="com.XXX"
                             targetProject="src/main/java" type="XMLMAPPER">
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>

<!--需要生成代码的表配置,前面四个字段比较重要，后面五个字段默认false就行了，暂时也不清楚什么意思，以后遇到在研究-->
        <table  schema="数据库名，也可以不指定，因为前面配置的时候已经指定过了" tableName="数据表名" domainObjectName="代码中的表对应的实体类名" mapperName="XXDao"
               enableCountByExample="false" enableUpdateByExample="false"
               enableDeleteByExample="false" enableSelectByExample="false"
               selectByExampleQueryId="false">
        </table>

       
    </context>
</generatorConfiguration>
```

### 第三步：指定数据库generator.properties文件

```
jdbc.driverLocation=mysql-connector-java-5.1.46.jar 包的全路径，根据自己的实际情况配置
# Oracle版：com/oracle/ojdbc7/版本号/ojdbc7-版本号.jar
jdbc.driverClass=com.mysql.jdbc.Driver # oracle.jdbc.driver.OracleDriver
jdbc.connectionURL=jdbc:mysql://IP地址:端口号/数据库名称?useSSL=false（useSSL字段在mysql5.5以后必须要加）
# Oracle版：jdbc:oracle:thin:@IP地址:端口号:数据库名称
jdbc.userId=用户名
jdbc.password=密码
```



# 1、Oracle

## 1.1、batch-insert

```sql
insert into login(id,name)
(select sequence.nextval,a.* from(
<foreach collection="list" item="item" index="index" close=")" open="(" separator="union all">
    select #{item.name}
    from dual
</foreach> 
) a)

Precautions:
When inserting multiple columns, if the inserted value has repeated names or repeated values, for example, both times are sysdate, you need to assign values directly from the code layer, and insert variable names
```

MyBatis官方更加推荐的写法

结合通用mapper sql别名最好是包名＋类名

写法一

```java
	// 获取sqlsession
	// 从spring注入原有的sqlSessionTemplate
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	// 新获取一个模式为BATCH，自动提交为false的session
	// 如果自动提交设置为true,将无法控制提交的条数，改为最后统一提交，可能导致内存溢出
	SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
	// 通过新的session获取mapper
	fooMapper =session.getMapper(FooMapper .class);
	int size = 10000;
	try{
		for (int i = 0; i < size; i++) {
			Foo foo = new Foo();
			foo.setName(String.valueOf(System.currentTimeMillis()));
			fooMapper.insert(foo);
			if (i % 1000 == 0 || i == size - 1) {
				//手动每1000个一提交，提交后无法回滚 
				session.commit();
				//清理缓存，防止溢出
				session.clearCache();
			}
		}
	} catch(Exception e){
		//没有提交的数据可以回滚
		session.rollback();
	} finally{
		session.close();
	}
```

写法二

```java
//获取sqlsession
//从spring注入原有的sqlSessionTemplate
@Autowired
private SqlSessionTemplate sqlSessionTemplate;	

public void insertBatch(Map<String, Object> paramMap, List<User> list) throws Exception {
		// 新获取一个模式为BATCH，自动提交为false的session
		// 如果自动提交设置为true,将无法控制提交的条数，改为最后统一提交，可能导致内存溢出
		SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
		try {
			if (null != list || list.size() > 0) {
				int lsize = list.size();
				for (int i = 0, n = list.size(); i < n; i++) {
					User user = list.get(i);
					user.setIndate((String) paramMap.get("indate"));
					user.setDatadate((String) paramMap.get("dataDate"));//数据归属时间
					// session.insert("com.xx.mapper.UserMapper.insert",user);
					// session.update("com.xx.mapper.UserMapper.updateByPrimaryKeySelective",_entity);
					session.insert(“包名 + 类名", user);
					if ((i > 0 && i % 1000 == 0) || i == lsize - 1) {
						// 手动每1000个一提交，提交后无法回滚
						session.commit();
						// 清理缓存，防止溢出
						session.clearCache();
					}
				}
			}
		} catch (Exception e) {
			// 没有提交的数据可以回滚
			session.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
```

示例三

```java
	try {
		int a = 2000;//每次提交2000条
		int loop = (int) Math.ceil(accountList.size() / (double) a);
		List<AccountInfo> tempList = new ArrayList<>(a);
		int start, stop;
		for (int i = 0; i < loop; i++) {
			tempList.clear();
			start = i * a;
			stop = Math.min(i * a + a - 1, accountList.size() - 1);
			System.out.println("range:" + start + " - " + stop);
			for (int j = start; j <= stop; j++) {
				tempList.add(accountList.get(j));
			}
			mapper.batchInsertAccountInfoUseSeq(tempList);

			System.out.println("已经插入" + (stop + 1) + " 条");
		}

		session.commit();
		session.clearCache();

	} catch (Exception e) {
		e.printStackTrace();
		session.rollback();
	} finally {
		if (session != null) {
			session.close();
		}
	}
	long end = System.currentTimeMillis();
	System.out.println("插入数据库执行时间 = " + (end - start1)/1000+"秒");


mapper.xml 文件中的sql 语句如下：


<insert id="batchInsertAccountInfoUseSeq" parameterType="java.util.List">
		<selectKey resultType="long" keyProperty="id" order="BEFORE"> 
            SELECT ACCOUNT_SEQ.NEXTVAL FROM dual
        </selectKey> 
		INSERT INTO ACCOUNT_INFO(ID, USERNAME,PASSWORD,GENDER, EMAIL,CREATE_DATE)
		SELECT ACCOUNT_SEQ.NEXTVAL, m.* FROM(
		<foreach collection="list" index="" item="accountInfo"
			separator="union all">
			select
			#{accountInfo.userName},
			#{accountInfo.password},
			#{accountInfo.gender},
			#{accountInfo.email},
			sysdate
			from dual
		</foreach>
		) m
	</insert>
  
————————————————————————————————————————————————
  测试100万条数据批量插入的时间为：
插入数据库执行时间 = 99秒

```

示例四

```java
 		@Override
    public boolean insertCrossEvaluation(List<CrossEvaluation> members)
            throws Exception {
        // TODO Auto-generated method stub
        int result = 1;
        SqlSession batchSqlSession = null;
        try {
            batchSqlSession = this.getSqlSessionTemplate()
                    .getSqlSessionFactory()
                    .openSession(ExecutorType.BATCH, false);// 获取批量方式的sqlsession
            int batchCount = 1000;// 每批commit的个数
            int batchLastIndex = batchCount;// 每批最后一个的下标
            for (int index = 0; index < members.size();) {
                if (batchLastIndex >= members.size()) {
                    batchLastIndex = members.size();
                    result = result * batchSqlSession.insert("MutualEvaluationMapper.insertCrossEvaluation",members.subList(index, batchLastIndex));
                    batchSqlSession.commit();
                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
                    break;// 数据插入完毕，退出循环
                } else {
                    result = result * batchSqlSession.insert("MutualEvaluationMapper.insertCrossEvaluation",members.subList(index, batchLastIndex));
                    batchSqlSession.commit();
                    System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
                    index = batchLastIndex;// 设置下一批下标
                    batchLastIndex = index + (batchCount - 1);
                }
            }
            batchSqlSession.commit();
        } 
        finally {
            batchSqlSession.close();
        }
        return Tools.getBoolean(result);
    }
```



## 1.2、batch-update

in后括号中的参数个数有限制，Oracle 9i 中个数不能超过256,Oracle 10g个数不能超过1000.

11.2、每次批量更新只能操作1000条

```java
if (list_share_date.size() < 1000) {
    shareDateMapper.updateBatchShareAndDate(list_share_date);
} else {
    int len = 1000;
    int size = list_share_date.size();
    int count = (size + len - 1) / len;
    for (int i = 0; i < count; i++) {
        List<BgdShareStatisticsDate> subListOK = list_share_date.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
        shareDateMapper.updateBatchShareAndDate(subListOK);
    }
}
```



一、全是必须非空字段

```sql
<update id="updateByList" parameterType="java.lang.List">
	update S_CUSTOMER CI
	set
	CI.STATE = 
	<foreach collection="list" item="item" index="index" open="case SALES_LEADS_ID" close=" end" separator=" ">
	when #{item.salesLeadsId,jdbcType=NUMERIC} then #{item.state,jdbcType=NUMERIC}
	</foreach>
	where CI.SALES_LEADS_ID in(
    	<foreach collection="list" item="item" index="index" open="" close="" separator=",">
	 	#{item.salesLeadsId,jdbcType=NUMERIC} 
		</foreach>
   	 )
</update>
```

二、可以有空

```sql
<update id="updateBatch" parameterType="java.util.List">
        update mydata_table
        <trim prefix="set" suffixOverrides=",">
            <trim prefix="status = case" suffix="end,">
                 <foreach collection="list" item="item" index="index">
                     <if test="item.status !=null and item.status != -1">
                         when id=#{item.id} then #{item.status}
                     </if>
                     <if test="item.status == null or item.status == -1">
                         when id=#{item.id} then mydata_table.status//原数据
                     </if>
                 </foreach>
            </trim>
            <trim prefix="modify_user = case" suffix="end,">
                 <foreach collection="list" item="item" index="index">
                     <if test="item.modifyUser !=null and item.modifyUser != ''">
                         when id=#{item.id} then #{item.modifyUser}
                     </if>
                     <if test="item.modifyUser == null or item.modifyUser == ''">
                         when id=#{item.id} then mydata_table.status//原数据
                     </if>
                 </foreach>
            </trim>
        </trim>
        where id in(
        <foreach collection="list" index="index" item="item" separator="," open="(" close=")">
            #{item.id,jdbcType=BIGINT}
        </foreach>
        )
</update>
```



## 1.3、in语句

```sql
<select id="selectByUserId" resultMap="BaseResultMap" parameterType="java.lang.String">
	select * from HealthCoupon where useType in (#{useType,jdbcType=VARCHAR})
</select>

直接报错

用list替换
<select id="selectByIdSet" resultMap="BaseResultMap">
	SELECT
		<include refid="Base_Column_List" />
	from t_user
	WHERE id IN(
        <foreach collection="list" item="id" index="index" open="(" close=")" separator=",">
          #{id}
        </foreach>
    	)
</select>
```



## 1.4、查询结果用Map返回

```sql
@MapKey("deptCode")// 取自DepartmentInfo里面的deptCode字段
Map<String,DepartmentInfo> listAllFirstDepartmentInfo();

<select id="listAllFirstDepartmentInfo" resultType="cn.tk.tmms.module.department.dto.DepartmentInfo">
	select <include refid="Base_Column_List"/>
	from DEPARTMENT_INFO
	where STATE = 0 and DEPT_LEVEL = 0
</select>	
```

## 1.5、别名

```sql
<!-- mybatis-config.xml 中 -->
<typeAlias type="com.someapp.model.User" alias="User"/>

<!-- SQL 映射 XML 中 -->
<select id="selectUsers" resultType="User">
  select id, username, hashedPassword
  from some_table
  where id = #{id}
</select>
```

# 2、MySQL

## 2.1、自增主键

```xml
<!-- 跟普通的insert没有什么不同的地方 -->  
    <insert id="insert" parameterType="com.soft.mybatis.model.Customer">  
        <!-- 跟自增主键方式相比，这里的不同之处只有两点  
                    1  insert语句需要写id字段了，并且 values里面也不能省略  
                    2 selectKey 的order属性需要写成BEFORE 因为这样才能将生成的uuid主键放入到model中，  
                    这样后面的insert的values里面的id才不会获取为空  
              跟自增主键相比就这点区别，当然了这里的获取主键id的方式为 select uuid()  
              当然也可以另写别生成函数。-->  
        <selectKey keyProperty="id" order="BEFORE" resultType="String">  
            select uuid()  
        </selectKey>  
        insert into t_customer (id,c_name,c_sex,c_ceroNo,c_ceroType,c_age)  
        values (#{id},#{name},#{sex},#{ceroNo},#{ceroType},#{age})  
    </insert> 
```

## 2.2、行行比较

SQL-92加入了行与行比较的功能，比较谓词=、<、>、和in这四个谓词的参数不再只是标量值，还可以是值列表。

动态SQL如下：

```sql
select item.name
from t_item
where (business_id,bussiness_type) in(
	<foreach collection="businessList" item="business" separator=",">
		(#{business_id},#{bussiness_type})
	</foreach>
)

具体的SQL如下
select item.name
from t_item
where (business_id,bussiness_type)
in((100,1),(200,2),(300,3));

经explain分析SQL，是会走索引的
```

# 3、常规建议

## 3.1、有意义的null

对比有含义的null值字符串或者数值，可以在代码中设置代码xml中null含义的特殊数值，

比如字符串设置为"null"，数值指定为-999

## 3.2、if标签

### 数值

#### 判空

```xml
不等于null
<if test = "num != null">

</if>
```

#### 范围查询

```xml
<if test = "num != null and num > 18">

</if>
或
<if test = "num != null and num &gt; 18">

</if>
```

##### 符号对应关系

| 推荐写法 | 实际                                                 |
| -------- | ---------------------------------------------------- |
| &gt;     | >                                                    |
| &gt;=    | >=                                                   |
| &lt;     | <(会报错  相关联的 "test" 属性值不能包含 '<' 字符）  |
| &lt;=    | <=(会报错  相关联的 "test" 属性值不能包含 '<' 字符） |



### 字符串

#### 判空

```xml
<if test = "str != null and '' != str">

</if>

或

<if test = "str != null and '' neq str">

</if>
```

#### 等值判断

```xml
<if test = "str != null and 'hello' != str">

</if>

或

<if test = "str != null and 'hello' eq str">

</if>


<!-- 但是，对于非字符串类型的参数（如Boolean），就需要写成: -->
<if test="flag != null and 'true'.toString() == flag.toString()">
    flage=#{flag, jdbcType=BOOLEAN}
</if>
```

#### 符号对应关系

|      |      |
| ---- | ---- |
| eq   | ==   |
| neq  | !=   |



#### String的对应方法

```xml
<!-- 是否以什么开头 -->
<if test="username != null and username.indexOf('ji') == 0">
</if>
<!-- 是否包含某字符 -->
<if test="username != null and username.indexOf('ji') >= 0">
</if>

<!-- 是否以什么结尾 -->
<if test="username != null and username.lastIndexOf('ji') > 0">
</if>  
```

### 集合List

#### 判空

```xml
<if test="userList != null and userList.isEmpty()">
</if> 

或

<if test="userList != null and userList.size()>0">
</if>
```



## 3.3、if-else同义替换

```xml
<choose>
    <when test="">
        //...
    </when>
    <otherwise>
        //...
    </otherwise>
</choose>
```


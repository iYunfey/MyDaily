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


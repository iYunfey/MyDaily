Oracle常见问题：https://support.oracle.com/knowledge/Oracle%20Cloud/1533057_1.html

# 1、SQL

## 1.1、基本函数

### 字符函数

#### 字符串长度

```sql
length(string)计算string所占的字符长度：返回字符串的长度，单位是字符
lengthb(string)计算string所占的字节长度：返回字符串的长度，单位是字节
如可以用length(‘string’)=lengthb(‘string’)判断字符串是否含有中文
```

#### 字符串大小写转换

```sql
upper('smith')全部变大写
lower('SMITH')全部变小写
```

#### 字符串字符位置

```sql
INSTR(string，child_string，[start]，[show_time])
string：表示源字符串。
child_string：子字符串，即要查找的字符串。
start：可选项，开始位置，默认从1开始。如果为负数，则从右向左检索。
show_time：可选项，表示子字符串第几次出现在源字符串当中，默认第1次，负数则报错。

INSTR('CORPORATE FLOOR','OR', 3, 2)中
源字符串为'CORPORATE FLOOR', 目标字符串为'OR'，起始位置为3，取第2个匹配项的位置；返回结果为 14
```

#### 字符串拼接

```sql
使用“||”进行字符串拼
SELECT '工号为'||FNumber||'的员工姓名为'||FName FROM T_Employee 
WHERE FName IS NOT NULL 

CONCAT()函数只支持两个参数，如果要进行多个字符串的拼接的话，可以使用多个CONCAT()函数嵌套使用
SELECT CONCAT(CONCAT(CONCAT('工号为',FNumber),'的员工姓名为'),FName) 
FROM T_Employee 
WHERE FName IS NOT NULL


WMSYS.WM_CONCAT()返回来自同一个分组的指定字段的非NULL值的连接起来字符串， 依赖WMSYS用户，不同oracle环境时可能用不了，返回类型为CLOB
wmsys.wm_concat(to_char(....))..应该加上to_char()可以防止乱码
WMSYS.WM_CONCAT(distinct (字段名))可以去重

示例：
1、以cid分组，把同组的sage字段值打印在一行，逗号分隔(默认)
SELECT cid, WMSYS.WM_CONCAT(sage) 
FROM student
GROUP BY cid;

2、以cid分组，把同组的sage字段的值打印在一行，竖线分隔
SELECT cid, replace(WMSYS.WM_CONCAT(sage), ',', '|') 
FROM student
GROUP BY cid;

3、以cid分组，把同组的sage字段值去重且排序后打印在一行
select cid, WMSYS.WM_CONCAT(distinct sage) 
from student group by cid;
```

#### 字符串截取

```sql
substr(string，start， [length])
string：表示源字符串，即要截取的字符串。
start：开始位置，从1开始查找。如果start是负数，则从string字符串末尾开始算起。
length：可选项，表示截取字符串长度

substr 函数结合 instr 函数截取字符串
现有需求：数据查询处理需要对code进行"拆分"
code命名规则类似：城市_所属公司_员工职位_员工姓名
BJ_BAIDU_CEO_LY
SH_BOKE_MANAGER_LWX
HRB_WM_CASHIER_OYZY
其中，城市、公司、职位、姓民字符串长度不固定。
由于字符串长度不固定，只使用substr函数无法实现需求，需配合instr函数定位到字符'_'的位置，然后使用substr函数进行截取。
获取城市简称
select
substr(source_code,1,instr(source_code,'_',1,1)-1) as city
from table;--BJ、SH、HRB

trim()删除左右两边出现的空格
ltrim()删除左边出现的字符串
rtrim()删除右边出现的字符串
select ltrim(rtrim( gao qian jing , ), ) from dual;
```

#### 字符串替换

```sql
translate(char, from, to)
select translate('abcdefga','abc','wo')  from dual;-- wodefgw
replace(char, search_string,replacement_string)
select replace('fgsgswsgs', 'sg' ,'eeerrrttt')  from dual;-- fgeeerrrtttsweeerrrttts
```

### 数字函数

#### 四舍五入

```sql
round()默认情况四舍五入取整，可以指定保留小数
计算两个时间的天数差值，保留两位小数
select round(
	TO_NUMBER(to_date('2002-12-2 23:45:57','yyyy-mm-dd hh24:mi:ss') - 
	to_date('2001-11-2 23:45:57','yyyy-mm-dd hh24:mi:ss'))
,2) 


select round(90000/1e3,2)
from dual;--90,1e6是科学缩写法，等效于1*10^6，此时round失效，不会保留小数点
```

#### 数值截取

```sql
trunc()函数可以截取数字和日期类型
select trunc(122.555) from dual t; --默认取整，122
select trunc(122.555,1) from dual t;-- 122.10
select trunc(122.555,2) from dual t;-- 122.55
select trunc(122.555,-2) from dual t;--负数表示从小数点左边开始截取2位，100
select to_char(trunc(sysdate),'yyyy-mm-dd hh24:mi:ss') from dual; --截取到日（当日的零点零分零秒）
```

#### 取余函数

```sql
mod(num1,num2)
```

#### 向上/下取整

```sql
ceil(num)
floor(num)
```

#### 求指数幂

```sql
power(n1,n2)返回 n1 的 n2 次方根
select power(2,10),power(3,3) from dual;-- 1024 27
```

### 时间函数

在日期加减时有一些规律

日期 – 数字 = 日期

日期 + 数字 = 日期

日期 – 日期 = 数字

#### 字符串转时间

```sql
to_date(str,pattern)
	where update <= to_date('2007-09-07 00:00:00','yyyy-mm-dd hh24:mi:ss')
	where update <= to_date('2007-09-07 00:00:00','yyyy-mm-dd hh24:mi:ss') and update >= to_date('2007-07-07 00:00:00','yyyy-mm-dd hh24:mi:ss')
```

#### 格式化时间

```sql
to_char(sysdate,'yyyy-mm-dd hh24:mi:ss')-- 2021-08-31 14:31:45
	可以使用 fm 去掉前导零
	to_char(sysdate,'fmyyyy-mm-dd hh24:mi:ss')-- 2021-8-31 14:31:45
```

#### 获得两个时间段中的月数

```sql
months_between(date1,date2)
select months_between(sysdate,date) from dual;-- 8
```



### 通用函数

#### 空值处理 nvl

```sql
nvl(columnName,defaultValue)
```

#### decode函数

```sql
decode(value,if1,then1,if2,then2,if3,then3,...,else)
select decode(2,1,'我是1',2,'我是2','不是1不是2') from dual;-- 我是2
select decode(3,1,'我是1',2,'我是2','不是1不是2') from dual;-- 不是1不是2
```

#### case when

```sql
(case
     when a='1' then 'xxxx'
     when a='2' then 'ssss'
 else
 	'zzzzz'
 end) as 别名
```

#### ANY

放在比较运算符后面，表示“任意”的意思。

```sql
比所有销售员工资都低
SELECT ENAME,JOB,SAL 
FROM EMP 
WHERE SAL <= ANY(SELECT SAL FROM EMP WHERE JOB='SALESMAN')
```

#### ALL

与关系操作符一起使用，表示与子查询中所有元素比较。

```sql
比所有销售员工资都高
SELECT ENAME,JOB,SAL
FROM EMP
WHERE SAL>ALL (SELECT SAL FROM EMP WHERE JOB='SALESMAN')
```

#### ROWNUM

表示行号，实际上此是一个列,但是这个列是一个伪列,此列可以在每张表中出现

```sql
select rownum, t.* from emp t
使用子查询，也正是oracle分页
select * 
from (select rownum r ,emp.* from emp) b
where b.r >5 and b.r <11
```

#### UNION ALL(并集)

返回各个查询的所有记录，包括重复记录。

#### UNION(并集)

返回各个查询的所有记录，不包括重复记录。

#### INTERSECT(交集)

返回两个查询共有的记录。

#### MINUS(差集)

返回第⼀个查询检索出的记录减去第⼆个查询检索出的记录之后剩余的记录。

```sql
--使⽤差集实现分⻚查询
select
	rownum,t.* 
from emp t 
where rownum<=10
minus
select
	rownum,t.* 
from emp t 
where rownum<=5
```



## 1.2、基本DDL、DML

### DDL

#### 表级别

##### 建表

```sql
create table 表名(
    列名 数据类型(默认值) 约束,
    列名 数据类型(默认值) 约束,
    列名 数据类型(默认值) 约束
);
```

##### 删表

```sql
drop table table_name;
truncate table t_person 删除所有数据， 其本质是删除表，重新创建表
```

#### 列级别

##### 增加列

```sql
alter table 表名 add(列名 数据类型 [default 默认值] 约束...)；
alter table table_name add(column_name column_type [default_value]..)
alter table tbl_user add age number(3) default 18 not null;
```

##### 修改列

```sql
alter table 表名 modify(原列名 新数据类型 [default 默认值] 新约束...)；
alter table tbl_user modify(password default'000000' not null);
```

##### 修改列名

```sql
alter table 表名 rename column 原列名 to 新列名；
alter table tbl_user rename column password to pwd;
```

##### 删除列

```sql
alter table 表名 drop column 列名；【注意关键字column】
alter table tbl_user drop column age;
-- 删除⼀个字段 alter table 表名称 drop column 列名
-- 删除多个字段 alter table 表名称 drop (列名 1,列名 2...)
-- 删除字段 alter table t_owners drop column remark
```

### DML

#### 插入记录

```sql
insert into table_name(column1,column2,column3...) values(value1,value2,value3,...);
```

#### 查询记录

```sql
select
	columns...
from table_name
where expressions;
```

#### 修改记录

```sql
update table_name
set column1 = value1, column2 = value2...
where expressions;
```

#### 删除记录

```sql
delete table_name
where expressions;
```



## 1.3、分析函数

function_name(<argument>,<argument>...) over(<partition_Clause><order by_Clause><windowing_Clause>);

function_name()：函数名称

argument：参数

over( )：开窗函数

partition_Clause：分区子句，数据记录集分组，group by...

order by_Clause：排序子句，数据记录集排序，order by...

windowing_Clause：开窗子句，定义分析函数在操作行的集合，三种开窗方式：rows、range、Specifying

注：使用开窗子句时一定要有排序子句！！！

### 1.3.1、count() over()  

```sql
统计分区中各组的行数，partition by 可选，order by 可选

select
	ename,esex,eage,count(*) over()
from emp; --总计数

select
	ename,esex,eage,count(*) over(order by eage)
from emp; --递加计数

select
	ename,esex,eage,count(*) over(partition by esex)
from emp; --分组计数

select
	ename,esex,eage,count(*) over(partition by esex order by eage)
from emp;--分组递加计数
```



### 1.3.2、sum() over()  

```sql
统计分区中记录的总和，partition by 可选，order by 可选

select
	ename,esex,eage,sum(salary) over()
from emp; --总累计求和

select
	ename,esex,eage,sum(salary) over(order by eage)
from emp; --递加累计求和

select
	ename,esex,eage,sum(salary) over(partition by esex)
from emp; --分组累计求和

select
	ename,esex,eage,sum(salary) over(partition by esex order by eage)
from emp; --分组递加累计求和

```

### 1.3.3、avg() over()  

```sql
统计分区中记录的平均值，partition by 可选，order by 可选

select
	ename,esex,eage,avg(salary) over()
from emp; --总平均值

select
	ename,esex,eage,avg(salary) over(order by eage)
from emp; --递加求平均值

select
	ename,esex,eage,avg(salary) over(partition by esex)
from emp; --分组求平均值

select
	ename,esex,eage,avg(salary) over(partition by esex order by eage)
from emp; --分组递加求平均值
```



### 1.3.4、min() over() 、max() over()

```sql
统计分区中记录的最小值/最大值，partition by 可选，order by 可选

select
	ename,esex,eage,salary,min(salary) over()
from emp; --求总最小值

select
	ename,esex,eage,salary,min(salary) over(order by eage)
from emp; --递加求最小值

select
	ename,esex,eage,salary,min(salary) over(partition by esex)
from emp; --分组求最小值

select
	ename,esex,eage,salary,min(salary) over(partition by esex order by eage)
from emp; --分组递加求最小值

select
	ename,esex,eage,salary,max(salary) over()
from emp; --求总最大值

select
	ename,esex,eage,salary,max(salary) over(order by eage)
from emp; --递加求最大值

select
	ename,esex,eage,salary,max(salary) over(partition by esex)
from emp; --分组求最大值

select
	ename,esex,eage,salary,max(salary) over(partition by esex order by eage)
from emp; --分组递加求最大值
```

### 1.3.5、rank() over()  

```sql
跳跃排序，partition by 可选，order by 必选

select
	ename,eage,rank() over(partition by job order by eage)
from emp;

select
	ename,eage,rank() over(order by eage)
from emp;
```

### 1.3.6、dense_rank() 

```sql
连续排序，partition by 可选，order by 必选

select
	ename,eage,dense_rank() over(partition by job order by eage)
from emp;

select
	ename,eage,dense_rank() over(order by eage)
from emp;
```

### 1.3.7、row_number() over() 

```sql
排序，无重复值，partition by 可选，order by 必选

select
	ename,eage,row_number() over(partition by job order by eage)
from emp;
select
	ename,eage,row_number() over(order by eage)
from emp;

row_number() over partition by 分组聚合

ROW_NUMBER() OVER(
    [PARTITION BY column_1, column_2,…]
    [ORDER BY column_3,column_4,…]
)

表示根据col1...分组，在分组内部根据col3...排序

SELECT 
	*, Row_Number() OVER (partition by deptid ORDER BY salary desc) rank 
FROM employee

select distinct 
    price,
    row_number() over (order by price)
from
    products
order by 
    price;
    
select *
from (
	select
  	ST.*,(row_number() over (partition by sales_leads_id order by creat_time)) px
	from S_SALES_TASK ST
)
where px = 1


select *
from a
left join (select *
           from (select b.*, (row_number()over(partition by bno order by BTell desc)) px
                 from b) e
              	where px = 1) t
    on a.aname = t.name;
```

### 1.3.8、ntile(n) over()

```sql
partition by 可选，order by 必选
n表示将分区内记录平均分成n份，多出的按照顺序依次分给前面的组

select
	ename,salary,ntile(3) over(order by salary desc)
from emp;

select
	ename,salary,ntile(3) over(partition by job order by salary desc)
from emp;
```

### 1.3.9、first_value() over()、last_value() over()

```sql
first_value() over() ：取出分区中第一条记录的字段值，partition by 可选，order by 可选
last_value() over() ：取出分区中最后一条记录的字段值，partition by 可选，order by 可选

select
	ename,first_value(salary) over()
from emp;

select
	ename,first_value(salary) over(order by salary desc)
from emp;

select 
	ename,first_value(salary) over(partition by job)
from emp;   

select
	ename,first_value(salary) over(partition by job order by salary desc)
from emp;


select
	ename,last_value(ename) over()
from emp;

select
	ename,last_value(ename) over(order by salary desc)
from emp;

select
	ename,last_value(ename) over(partition by job)
from emp;

select
	ename,last_value(ename) over(partition by job order by salary desc)
from emp;
```

### 1.3.10、first、last

```sql
first ：从DENSE_RANK返回的集合中取出排在最前面的一个值的行

last ：从DENSE_RANK返回的集合中取出排在最后面的一个值的行
select
	job,max(salary) keep(dense_rank first order by salary desc),
	max(salary) keep(dense_rank last order by salary desc)
from emp
group by job;     
```



### 1.3.11、lag() over() 、lead() over()

```sql
lag() over() ：取出前n行数据，partition by 可选，order by 必选
lead() over() ：取出后n行数据，partition by 可选，order by 必选

select
	ename,eage,
	lag(eage,1,0) over(order by salary),
	lead(eage,1,0) over(order by salary)
from emp;
 
select
	ename,eage,lag(eage,1) over(partition by esex order by salary),
	lead(eage,1) over(partition by esex order by salary)
from emp;
```

### 1.3.12、ratio_to_report(a) over(partition by b) 

```sql
求按照b分组后a的值在所属分组中总值的占比,a的值必须为数值或数值型字段

partition by 可选，order by 不可选

select
	ename,job,salary,ratio_to_report(1) over()
from emp; --给每一行赋值1，求当前行在总值的占比，总是0.1

select
	ename,job,salary,ratio_to_report(salary) over()
from emp; --当前行的值在所有数据中的占比

select
	ename,job,salary,ratio_to_report(1) over(partition by job)
from emp; --给每一行赋值1，求当前行在分组后的组内总值的占比

select
	ename,job,salary,ratio_to_report(salary) over(partition by job)
from emp; --当前行的值在分组后组内总值占比
```

### 1.3.13、percent_rank() over()  

```sql
partition by 可选，order by 必选

所在组排名序号-1除以该组所有的行数-1，排名跳跃排序
     
select
	ename,job,salary,percent_rank() over(order by salary)
from emp;

select
	ename,job,salary,percent_rank() over(partition by job order by salary)
from emp;    
```

### 1.3.14、cume_dist() over() 

```sql
partition by 可选，order by必选

所在组排名序号除以该组所有的行数，注意对于重复行，计算时取重复行中的最后一行的位置

select
	ename,job,salary,cume_dist() over(order by salary)
from emp;

select
	ename,job,salary,cume_dist() over(partition by job order by salary)
from emp;
```

### 1.3.15、precentile_cont( x ) within group(order by ...) over()    

```sql
over()中partition by可选，order by 不可选

x为输入的百分比，是0-1之间的一个小数，返回该百分比位置的数据，若没有则返回以下计算值（r）：

a=1+( x *(N-1) )  x为输入的百分比，N为分区内的记录的行数

b=ceil ( a )  向上取整

c = floor( a ) 向下取整

r=a * 百分比位置上一条数据 + b * 百分比位置下一条数据


select
	ename,job,salary,percentile_cont(0.5) within group(order by salary) over()
from emp;

select
	ename,job,salary,percentile_cont(0.5) within group(order by salary) over(partition by job) from emp;
```



### 1.3.16、precentile_disc( x ) within group(order by ...) over()  

```sql
over()中partition by可选，order by 不可选

x为输入的百分比，是0-1之间的一个小数，返回百分比位置对应位置上的数据值，若没有对应数据值，就取大于该分布值的下一个值

select
	ename,job,salary,percentile_disc(0.5) within group(order by salary) over()
from emp;
select
	ename,job,salary,percentile_disc(0.5) within group(order by salary) over(partition by job) from emp;
```

### 1.3.17、stddev() over()、stddev_samp() over()、stddev_pop() over()

```sql
stddev() over()：计算样本标准差，只有一行数据时返回0，partition by 可选，order by 可选

stddev_samp() over()：计算样本标准差，只有一行数据时返回null，partition by 可选，order by 可选

stddev_pop() over()：计算总体标准差，partition by 可选，order by 可选

select
	stddev(stu_age) over()
from student; --计算所有记录的样本标准差

select
	stddev(stu_age) over(order by stu_age)
from student; --计算递加的样本标准差

select
	stddev(stu_age) over(partition by stu_major)
from student; --计算分组的样本标准差

select
	stddev(stu_age) over(partition by stu_major order by stu_age)
from student; --计算分组递加的样本标准差
 
 
select
	stddev_samp(stu_age) over()
from student; --计算所有记录的样本标准差

select
	stddev_samp(stu_age) over(order by stu_age)
from student; --计算递加的样本标准差

select
	stddev_samp(stu_age) over(partition by stu_major)
from student; --计算分组的样本标准差

select
	stddev_samp(stu_age) over(partition by stu_major order by stu_age)
from student; --计算分组递加的样本标准差
 
 
select
	stddev_pop(stu_age) over()
from student; --计算所有记录的总体标准差

select
	stddev_pop(stu_age) over(order by stu_age)
from student; --计算递加的总体标准差

select
	stddev_pop(stu_age) over(partition by stu_major)
from student; --计算分组的总体标准差

select
	stddev_pop(stu_age) over(partition by stu_major order by stu_age)
from student;--计算分组递加的总体标准差
```

### 1.3.18、variance() over()、var_samp() over()、var_pop() over()

```sql
variance() over()：计算样本方差，只有一行数据时返回0，partition by 可选，order by 可选

var_samp() over()：计算样本方差，只有一行数据时返回null，partition by 可选，order by 可选

var_pop() over()：计算总体方差，partition by 可选，order by 可选

select
	variance(stu_age) over()
from student; --计算所有记录的样本方差

select
	variance(stu_age) over(order by stu_age)
from student; --计算递加的样本方差

select
	variance(stu_age) over(partition by stu_major)
from student; --计算分组的样本方差

select
	variance(stu_age) over(partition by stu_major order by stu_age)
from student; --计算分组递加的样本方差
 
 
select
	var_samp(stu_age) over()
from student; --计算所有记录的样本方差

select
	var_samp(stu_age) over(order by stu_age)
from student; --计算递加的样本方差

select
	var_samp(stu_age) over(partition by stu_major)
from student; --计算分组的样本方差

select
	var_samp(stu_age) over(partition by stu_major order by stu_age)
from student; --计算分组递加的样本方差
 
 
select
	var_pop(stu_age) over()
from student; --记录所有就的总体方差

select
	var_pop(stu_age) over(order by stu_age)
from student; --计算递加的总体方差

select
	var_pop(stu_age) over(partition by stu_major)
from student; --计算分组的总体方差

select
	var_pop(stu_age) over(partition by stu_major order by stu_age)
from student;--计算分组递加的样本方差

stddev()=sqrt( variance() )     sqrt()--求开方

stddev_samp()=sqrt( var_samp() )

stddec_pop=sqrt( var_pop() )
```

### 1.3.19、covar_samp over()、covar_pop over()

```sql
covar_samp over()：返回一对表达式的样本协方差，partition by 可选，order by 可选

covar_pop over()： 返回一堆表达式的总体协方差，partition by 可选，order by 可选

select
	covar_samp(stu_age,line) over()
from student; --计算所有记录的样本协方差

select
	covar_samp(stu_age,line) over(order by stu_age)
from student; --计算递加的样本协方差

select
	covar_samp(stu_age,line) over(partition by stu_major)
from student; --计算分组的样本协方差

select
	covar_samp(stu_age,line) over(partition by stu_major order by stu_age)
from student; --计算分组递加的样本协方差
 
 
select
	covar_pop(stu_age,line) over()
from student; --计算所有记录的总体协方差

select
	covar_pop(stu_age,line) over(order by stu_age)
from student; --计算递加的总体协方差

select
	covar_pop(stu_age,line) over(partition by stu_major)
from student; --计算分组的总体协方差

select
	covar_pop(stu_age,line) over(partition by stu_major order by stu_age)
from student; --计算分组递加的总体协方差
```

### 1.3.20、corr() over()

```sql
corr() over() ：返回一对表达式的相关系数，partition by 可选，order by 可选
select
	corr(stu_age,line) over()
from student; --计算所有记录的相关系数

select
	corr(stu_age,line) over(order by stu_age)
from student; --计算递加的相关系数

select
	corr(stu_age,line) over(partition by stu_major)
from student; --计算分组的相关系数

select
	corr(stu_age,line) over(partition by stu_major order by stu_age)
from student; --计算分组递加的相关系数
```

## 1.4、创建序列、获取自增主键

```sql
CREATE SEQUENCE 序列名
[INCREMENT BY n] 
[START WITH n] 
[{MAXVALUE/ MINVALUE n|NOMAXVALUE}] 
[{CYCLE|NOCYCLE}] 
[{CACHE n|NOCACHE}];

create sequence seq_user_info
increment by 1 ---序列自增值
start with 101 ---序列起始值
maxvalue 99999999 --序列最大值
nocycle
cache 10;  ---定义存放序列的内存块的大小，默认为20

nextval :取得序列的下一个内容
currval :取得序列的当前内容
select 序列名.nextval from dual;
select 序列名.currval from dual;
select 序列名.nextval from dual;

删除序列：
	drop sequence 序列名;
	这种方法谨慎使用！！！因为该序列可能正被使用！！！

修改序列（Sequence）起始值的方法
例如：若序列名称是SEQ_TEST，初始值是13，而现在要设置初始值为1013，Increment By值为:1000(1013-13)

1) 执行:ALTER SEQUENCE SEQ_TEST INCREMENT BY 1000;

2) 执行:SELECT SEQ_TEST.NEXTVAL FROM DUAL;

3) 执行:ALTER SEQUENCE SEQ_TEST INCREMENT BY 1;
```

## 1.5、计算剩余天数

```sql
to_char(table.expire_date-sysdate,'fm999.00') as remainTime

这种sql会产生三种异常，
1、expire_date、为空时，remainTime为null
2、expire_date刚过期几分钟或者十几分钟，remainTime为-.04
3、expire_date只剩几分钟刚过期，remainTime为.01
```



## 1.6、分页

```java
/**
 * @description: Oracle版分页对象
 * @author: Administrator
 * @version: 1.0.0
 * @createTime: 2021/6/11 13:22
 *
 * 对应mybatis中SQL尾部加
 * and rownumber &gt;= #{rowNumStart,jdbcType=NUMERIC}
 * and rownumber &lt;= #{rowNumEnd,jdbcType=NUMERIC}
 */
public class PageOracle<T> {

    private int pageSize = 15;
    private int pageNo = 1;
    private int rowNumStart;
    private int rowNumEnd;
    private int totalPages;
    private int totalRecords;
    private List<T> results;
    private Map<String, Object> params = new HashMap<>();//搜索条件

    public int getRowNumStart() {
        rowNumStart = (pageNo - 1) * pageNo + 1;
        return rowNumStart;
    }

    public void setRowNumStart(int rowNumStart) {
        this.rowNumStart = rowNumStart;
    }

    public int getRowNumEnd() {
        rowNumEnd = (pageNo - 1) * pageNo + pageSize;
        return rowNumEnd;
    }

    public void setRowNumEnd(int rowNumEnd) {
        this.rowNumEnd = rowNumEnd;
    }

    public int getTotalPages() {
        totalPages = totalRecords / pageSize + 1;
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }
}


⻚码pageNo=1 每⻚条数pageSize=5
 select * from
     (select rownum r, t.* from
         (select * from emp order by sal desc) t
 	where rownum <= pageNo * pageSize)
 where r > (pageNo - 1) * pageSize;
```

## 1.7、删除重复数据，只保留一个

```sql
比如，某个表要按照id和name重复，就算重复数据
delete from 表名 where rowid not in (select min(rowid) from 表名 group by id,name);
commit;
```

## 1.8、索引

```sql
新建索引
	create index 索引表 on 表名（列名1，列名2，列名3..） index 索引名 on 表名(列名)
	create index 索引表 on 表名（列名1，列名2，列名3..） index 索引表 on 表名（列名1，列名2，列名3..）
	（1）alter table 表名 add index 索引名（要添加索引字段名） 
 	（2）create index 索引名 on 表名（要添加的字段名）
	create index idx_name_age on table_name(name(20),age);
删除索引
	（1）alter table 表名 drop index 索引名 。 
	（2）drop index 索引名 on 表名。
	drop index idx_name_age;

主键索引
1、查询某个表的主键
select * from user_constraints where table_name = 'your_table_name' and constraint_type = 'P';

2、删除已有的主键
alter table your_table_name drop constraint your_primary_key_name;

3、添加联合主键
alter table your_table_name add constraint your_union_key_name primary key(column1, column2, ...columnN);
```



# 2、SQL优化

## 2.1、选择最有效率的表名顺序

```sql
 FROM 子句中包含多个表的情况下,你必须选择记录条数最少的表作为基础表
 首先,扫描第一个表(FROM 子句中最后的那个表)并对记录进行派序,然后扫描第二个表(FROM 子句中最后第二个表),最后将所有从第二个表中检索出的记录与第一个表中合适记录进行合并
 基础表(Driving Table)是指被最先访问的表(通常以全表扫描的方式被访问)
 例如:
表 TAB1 16,384 条记录
表 TAB2 1 条记录
选择 TAB2 作为基础表 (最好的方法)
select count(*) from tab1,tab2 执行时间 0.96 秒 oracle 测试标准
选择 TAB2 作为基础表 (不佳的方法)
select count(*) from tab2,tab1 执行时间 26.09 秒
如果有 3 个以上的表连接查询, 那就需要选择交叉表(intersection table)作为基础表, 
交叉表是指那个被其他表所引用的表
例如:
EMP 表描述了 LOCATION 表和 CATEGORY 表的交集.
SELECT * 
FROM LOCATION L , 
CATEGORY C,
EMP E 
WHERE E.EMP_NO BETWEEN 1000 AND 2000
AND E.CAT_NO = C.CAT_NO
AND E.LOCN = L.LOCN
将比下列 SQL 更有效率
SELECT * 
FROM EMP E ,
LOCATION L , 
CATEGORY C
WHERE E.CAT_NO = C.CAT_NO
AND E.LOCN = L.LOCN
AND E.EMP_NO BETWEEN 1000 AND 2000
```



## 2.2、WHERE 子句中的连接顺序

```sql
采用自下而上的顺序解析 WHERE 子句,根据这个原理,表之间的连接必须写在其他 WHERE条件之前, 那些可以过滤掉最大数量记录的条件必须写在 WHERE 子句的末尾。同时在链接的表中能过滤的就应该先进行过滤。
例如: 
(低效,执行时间 156.3 秒)
SELECT … 
FROM EMP E
WHERE SAL > 50000
AND JOB = ‘MANAGER’
AND 25 < (SELECT COUNT(*) FROM EMP
          WHERE MGR=E.EMPNO);
(高效,执行时间 10.6 秒)
SELECT … 
FROM EMP E
WHERE 25 < (SELECT COUNT(*) FROM EMP
WHERE MGR=E.EMPNO)
AND SAL > 50000
AND JOB = ‘MANAGER’;
```

## 2.3、**尽量多使用 COMMIT**

```sql
只要有可能,在程序中尽量多使用 COMMIT, 这样程序的性能得到提高,需求也会因为COMMIT 所释放的资源而减少:

COMMIT 所释放的资源:
a. 回滚段上用于恢复数据的信息
b. 被程序语句获得的锁
c. redo log buffer 中的空间
```

## 2.3、**计算记录条数** 

```sql
和一般的观点相反, count(*) 比 count(1)稍快 , 当然如果可以通过索引检索,对索引列的

计数仍旧是最快的. 例如 COUNT(EMPNO)
```

##  2.4、**自动选择索引**

```sql
如果表中有两个以上（包括两个）索引，其中有一个唯一性索引，而其他是非唯一性．

在这种情况下，ORACLE 将使用唯一性索引而完全忽略非唯一性索引．
举例:
SELECT ENAME
FROM EMP
WHERE EMPNO = 2326 
AND DEPTNO = 20 ;
这里，只有 EMPNO 上的索引是唯一性的，所以 EMPNO 索引将用来检索记录
```

## 2.5、**用>=替代>**

```sql
如果 DEPTNO 上有一个索引, 

高效:

SELECT *

FROM EMP

WHERE DEPTNO >=4

低效:

SELECT *

FROM EMP

WHERE DEPTNO >3

两者的区别在于, 前者 DBMS 将直接跳到第一个 DEPT 等于 4 的记录而后者将首先定位到

DEPTNO=3 的记录并且向前扫᧿到第一个 DEPT 大于 3 的记录.
```



## 2.6、**用 UNION 替换 OR (适用于索引列)**

```sql
通常情况下, 用 UNION 替换 WHERE 子句中的 OR 将会起到较好的效果. 对索引列使用 OR 将造成全表扫描 注意, 以上规则只针对多个索引列有效. 如果有 column 没有被索引, 查询效率可能会因为你没有选择 OR 而降低. 

在下面的例子中, LOC_ID 和 REGION 上都建有索引.

高效:
SELECT LOC_ID , LOC_DESC , REGION
FROM LOCATION
WHERE LOC_ID = 10
UNION
SELECT LOC_ID , LOC_DESC , REGION
FROM LOCATION
WHERE REGION = “MELBOURNE”
低效:
SELECT LOC_ID , LOC_DESC , REGION
FROM LOCATION
WHERE LOC_ID = 10 OR REGION = “MELBOURNE”
如果你坚持要用 OR, 那就需要返回记录最少的索引列写在最前面
```

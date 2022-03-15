# 一、数据库

## 1.数据库

​	英文单词DataBase     简称 ： DB

## 2.什么是数据库？

​	数据库就是用于存储和管理数据的仓库。

## 3.数据库的特点

​	1.持久化存储数据的。其实数据库就是一个文件系统

​	2.方便存储和管理数据

​	3.使用了统一的方式操作数据库 -- SQL

## 4.数据库的设计

### 1.多表之间的关系

一对一；一对多(多对一)；多对多

### 2.实现关系

| 多表关系       | 实现方式                                                     |
| -------------- | ------------------------------------------------------------ |
| 一对多(多对一) | 在多的一方建立外键，指向一的一方的主键                       |
| 多对多         | 多对多关系实现需要借助第三张中间表。中间表至少包含两个字段，这两个字段作为第三张表的外键，分别指向两张表的主键 |
| 一对一         | 一对一关系实现，可以在任意一方添加唯一外键指向另一方的主键。数据不多**,** 一对一合并为1个表；数据太多性能问题，分表 ：水平分/垂直分 |

```
多对多实现方式:

CREATE TABLE tab_favorite (
			rid INT, -- 线路id
			DATE DATETIME,
			uid INT, -- 用户id
			-- 创建复合主键
			PRIMARY KEY(rid,uid), -- 联合主键
			FOREIGN KEY (rid) REFERENCES tab_route(rid),
			FOREIGN KEY(uid) REFERENCES tab_user(uid)
		);
```

## 5.数据库设计的范式

### 1.概念

​	设计数据库时，需要遵循的一些规范。要遵循后边的范式要求，必须先遵循前边的所有范式要求

​	设计关系数据库时，遵从不同的规范要求，设计出合理的关系型数据库，这些不同的规范要求被称为不同的范式，各种范式呈递次规范，越高的范式数据库冗余越小。

​	关系型数据库

​	1⃣️数据之间有关联关系

​	2⃣️数据存储在硬盘的文件上

​	目前关系数据库有六种范式：第一范式（1NF）、第二范式（2NF）、第三范式（3NF）、巴斯-科德范式（BCNF）、第四范式(4NF）和第五范式（5NF，又称完美范式）。

### 2.分类

​	第一范式（1NF）：设计表的时候,每一列都是不可分割的原子数据项,不能再进行拆分

​	第二范式（2NF）：设计表的时候每个表至少有一个主键列

​	第三范式（3NF）：设计表的时候如果多个表之间有关联关系,通过外键体现关系,同时不要产生冗余数据

## 6.数据库的备份和还原

```
1. 命令行：
	* 语法：
		* 备份： mysqldump -u用户名 -p密码 数据库名称 > 保存的路径
		* 还原：
			1. 登录数据库
			2. 创建数据库
			3. 使用数据库
			4. 执行文件。source 文件路径
2. 图形化工具：SQLYog
```

# 二、MySQL数据库软件

	1. 安装
		* 参见《MySQL基础.pdf》
	2. 卸载
		1. 去MySQL的安装目录找到my.ini文件
			* 复制 datadir="C:/ProgramData/MySQL/MySQL Server 5.5/Data/"
		2. 卸载MySQL
		3. 删除C:/ProgramData目录下的MySQL文件夹。
		
	3. 配置
		* MySQL服务启动
			1. 手动。
			2. cmd--> services.msc 打开服务的窗口
			3. 使用管理员打开cmd
				* net start MySQL : 启动MySQL的服务
				* net stop MySQL:关闭MySQL服务
		* MySQL登录
			1. MySQL -uroot -p密码
			2. MySQL -hip -uroot -p连接目标的密码
			3. MySQL --host=ip --user=root --password=连接目标的密码
		* MySQL退出
			1. exit
			2. quit
	
		* MySQL目录结构
			1. MySQL安装目录：basedir="D:/develop/MySQL/"
				* 配置文件 my.ini
			2. MySQL数据目录：datadir="C:/ProgramData/MySQL/MySQL Server 5.5/Data/"
				* 几个概念
					* 数据库：文件夹
					* 表：文件
					表中的一行：记录
	 				表中一列：字段


# 三、SQL

	1.什么是SQL？
		Structured Query Language：结构化查询语言
		其实就是定义了操作所有关系型数据库的规则。每一种数据库操作的方式存在不一样的地方，称为“方言”。
		
	2.SQL通用语法
		1. SQL 语句可以单行或多行书写，以分号结尾。
		2. 可使用空格和缩进来增强语句的可读性。
		3. MySQL 数据库的 SQL 语句不区分大小写，关键字建议使用大写。
		4. 3 种注释
			* 单行注释: -- 注释内容 或 # 注释内容(MySQL 特有) 
			* 多行注释: /* 注释 */
		
	3. SQL分类
		1) DDL(Data Definition Language)数据定义语言
			用来定义数据库对象：数据库，表，列等。关键字：create, drop,alter 等
		2) DML(Data Manipulation Language)数据操作语言
			用来对数据库中表的数据进行增删改。关键字：insert, delete, update 等
		3) DQL(Data Query Language)数据查询语言
			用来查询数据库中表的记录(数据)。关键字：select, where 等
		4) DCL(Data Control Language)数据控制语言(了解)
			用来定义数据库的访问权限和安全级别，及创建用户。关键字：GRANT， REVOKE 等

## 1.DDL:操作数据库、表

### 1.操作数据库

	1. 操作数据库：CRUD
		1. C(Create):创建
			* 创建数据库：
				* create database 数据库名称;
			* 创建数据库，判断不存在，再创建：
				* create database if not exists 数据库名称;
			* 创建数据库，并指定字符集
				* create database 数据库名称 character set 字符集名;
	
			* 练习： 创建db4数据库，判断是否存在，并制定字符集为gbk
				* create database if not exists db4 character set gbk;
		2. R(Retrieve)：查询
			* 查询所有数据库的名称:
				* show databases;
			* 查询某个数据库的字符集:查询某个数据库的创建语句
				* show create database 数据库名称;
		3. U(Update):修改
			* 修改数据库的字符集
				* alter database 数据库名称 character set 字符集名称;
		4. D(Delete):删除
			* 删除数据库
				* drop database 数据库名称;
			* 判断数据库存在，存在再删除
				* drop database if exists 数据库名称;
		5. 使用数据库
			* 查询当前正在使用的数据库名称
				* select database();
			* 使用数据库
				* use 数据库名称;

### 2.操作表

```
2. 操作表
	1. C(Create):创建
		1. 语法：
			create table 表名(
				列名1 数据类型1,
				列名2 数据类型2,
				....
				列名n 数据类型n
			);
			* 注意：最后一列，不需要加逗号（,）
			* 数据库类型：
				1. int：整数类型
					* age int,
				2. double:小数类型
					* score double(5,2) 100.00
				3. date:日期，只包含年月日，yyyy-MM-dd
				4. datetime:日期，包含年月日时分秒	 yyyy-MM-dd HH:mm:ss
				5. timestamp:时间错类型	包含年月日时分秒	 yyyy-MM-dd HH:mm:ss	
					* 如果将来不给这个字段赋值，或赋值为null，则默认使用当前的系统时间，来自动赋值

				6. varchar：字符串
					* name varchar(20):姓名最大20个字符
					* zhangsan 8个字符  张三 2个字符
		* 创建表
			create table student(
				id int,
				name varchar(32),
				age int ,
				score double(4,1),
				birthday date,
				insert_time timestamp
			);
		* 复制表：
			* create table 表名 like 被复制的表名;	  	
	2. R(Retrieve)：查询
		* 查询某个数据库中所有的表名称
			* show tables;
		* 查询表结构
			* desc 表名;
	3. U(Update):修改
		1. 修改表名
			alter table 表名 rename to 新的表名;
		2. 修改表的字符集
			alter table 表名 character set 字符集名称;
		3. 添加一列
			alter table 表名 add 列名 数据类型;
		4. 修改列名称 类型
			alter table 表名 change 列名 新列名 新数据类型;
			alter table 表名 modify 列名 新数据类型;
		5. 删除列
			alter table 表名 drop 列名;
	4. D(Delete):删除
		* drop table 表名;
		* drop table  if exists 表名 ;
```

客户端图形化工具：SQLYog

## 2.DML：增删改表中数据

	1. 添加数据：
		* 语法：
			* insert into 表名(列名1,列名2,...列名n) values(值1,值2,...值n);
		* 注意：
			1. 列名和值要一一对应。
			2. 如果表名后，不定义列名，则默认给所有列添加值
				insert into 表名 values(值1,值2,...值n);
			3. 除了数字类型，其他类型需要使用引号(单双都可以)引起来
	2. 删除数据：
		* 语法：
			* delete from 表名 [where 条件]
		* 注意：
			1. 如果不加条件，则删除表中所有记录。
			2. 如果要删除所有记录
				1. delete from 表名; -- 不推荐使用。有多少条记录就会执行多少次删除操作
				2. TRUNCATE TABLE 表名; -- 推荐使用，效率更高 先删除表，然后再创建一张一样的表。
	3. 修改数据：
		* 语法：
			* update 表名 set 列名1 = 值1, 列名2 = 值2,... [where 条件];
	
		* 注意：
			1. 如果不加任何条件，则会将表中所有记录全部修改。



## 3.DQL：查询表中的记录

### 1.语法

	* select * from 表名;
	
	1. 语法：
		select
			字段列表
		from
			表名列表
		where
			条件列表
		group by
			分组字段
		having
			分组之后的条件
		order by
			排序
		limit
			分页限定

### 2.基础查询


	2. 基础查询
		1. 多个字段的查询
			select 字段名1，字段名2... from 表名；
			* 注意：
				* 如果查询所有字段，则可以使用*来替代字段列表。
		2. 去除重复：
			* distinct
			select distinct 列名 from 表名；# 筛选出该表中该列不同的记录
		3. 计算列
			* 一般可以使用四则运算计算一些列的值。（一般只会进行数值型的计算）
			* ifnull(表达式1,表达式2)：null参与的运算，计算结果都为null
				* 表达式1：哪个字段需要判断是否为null
				* 如果该字段为null后的替换值。
		4. 起别名：
			* as：as也可以省略

### 3.条件查询


	3. 条件查询
		1. where子句后跟条件
		2. 运算符
			* > 、< 、<= 、>= 、= 、<>
			* BETWEEN...AND  
			* IN(集合) 
			* LIKE：模糊查询
				* 占位符：
					* _:单个任意字符
					* %：多个任意字符
			* IS NULL  
			* and  或 &&
			* or  或 || 
			* not  或 !
			
				-- 查询年龄大于20岁
	
				SELECT * FROM student WHERE age > 20;
				
				SELECT * FROM student WHERE age >= 20;
				
				-- 查询年龄等于20岁
				SELECT * FROM student WHERE age = 20;
				
				-- 查询年龄不等于20岁
				SELECT * FROM student WHERE age != 20;
				SELECT * FROM student WHERE age <> 20;
				
				-- 查询年龄大于等于20 小于等于30
				
				SELECT * FROM student WHERE age >= 20 &&  age <=30;
				SELECT * FROM student WHERE age >= 20 AND  age <=30;
				SELECT * FROM student WHERE age BETWEEN 20 AND 30;
				
				-- 查询年龄22岁，18岁，25岁的信息
				SELECT * FROM student WHERE age = 22 OR age = 18 OR age = 25
				SELECT * FROM student WHERE age IN (22,18,25);
				
				-- 查询英语成绩为null
				SELECT * FROM student WHERE english = NULL; -- 不对的。null值不能使用 = （!=） 判断
				
				SELECT * FROM student WHERE english IS NULL;
				
				-- 查询英语成绩不为null
				SELECT * FROM student WHERE english  IS NOT NULL;
				
					-- 查询姓马的有哪些？ like
				SELECT * FROM student WHERE NAME LIKE '马%';
				-- 查询姓名第二个字是化的人
				
				SELECT * FROM student WHERE NAME LIKE "_化%";
				
				-- 查询姓名是3个字的人
				SELECT * FROM student WHERE NAME LIKE '___';
				
				-- 查询姓名中包含德的人
					SELECT * FROM student WHERE NAME LIKE '%德%';

### 4.排序查询

```
4. 排序查询
	* 语法：order by 子句
		* order by 排序字段1 排序方式1 ，  排序字段2 排序方式2...

	* 排序方式：
		* ASC：升序，默认的。
		* DESC：降序。

	* 注意：
		* 如果有多个排序条件，则当前边的条件值一样时，才会判断第二条件。
```

### 5.分组查询

```
5. 分组查询:
	1. 语法：group by 分组字段；
	2. 注意：
		1. 分组之后查询的字段：分组字段、聚合函数
		2. where 和 having 的区别？
			1. where 在分组之前进行限定，如果不满足条件，则不参与分组。having在分组之后进行限定，如果不满足结果，则不会被查询出来
			2. where 后不可以跟聚合函数，having可以进行聚合函数的判断。

		-- 按照性别分组。分别查询男、女同学的平均分

		SELECT sex , AVG(math) FROM student GROUP BY sex;
		
		-- 按照性别分组。分别查询男、女同学的平均分,人数
		
		SELECT sex , AVG(math),COUNT(id) FROM student GROUP BY sex;
		
		--  按照性别分组。分别查询男、女同学的平均分,人数 要求：分数低于70分的人，不参与分组
		SELECT sex , AVG(math),COUNT(id) FROM student WHERE math > 70 GROUP BY sex;
		
		--  按照性别分组。分别查询男、女同学的平均分,人数 要求：分数低于70分的人，不参与分组,分组之后。人数要大于2个人
		SELECT sex , AVG(math),COUNT(id) FROM student WHERE math > 70 GROUP BY sex HAVING COUNT(id) > 2;
		
		SELECT sex , AVG(math),COUNT(id) 人数 FROM student WHERE math > 70 GROUP BY sex HAVING 人数 > 2;
```

### 6.分页查询

```
6. 分页查询
	1. 语法：limit 开始的索引,每页查询的条数;
	2. 公式：开始的索引 = （当前的页码 - 1） * 每页显示的条数
		-- 假设每页显示3条记录 

		SELECT * FROM student LIMIT 0,3; -- 第1页
		
		SELECT * FROM student LIMIT 3,3; -- 第2页
		
		SELECT * FROM student LIMIT 6,3; -- 第3页

	3. limit 是一个MySQL"方言"
```

### 7.多表查询

```
笛卡尔积:
  如果对2个表进行查询A,B, A表的每一行都要和B表的每一行匹配

* 查询语法：
	select
		列名列表
	from
		表名列表
	where....

多表查询思路步骤：
	1.明确效果
	2.明确数据来源，来自哪些表
	3.层层递进，明确表和表之间的主外键关系，确认筛选条件
	4.明确查询信息
	
* 准备sql
	# 创建部门表
	CREATE TABLE dept(
		id INT PRIMARY KEY AUTO_INCREMENT,
		NAME VARCHAR(20)
	);
	INSERT INTO dept (NAME) VALUES ('开发部'),('市场部'),('财务部');
	# 创建员工表
	CREATE TABLE emp (
		id INT PRIMARY KEY AUTO_INCREMENT,
		NAME VARCHAR(10),
		gender CHAR(1), -- 性别
		salary DOUBLE, -- 工资
		join_date DATE, -- 入职日期
		dept_id INT,
		FOREIGN KEY (dept_id) REFERENCES dept(id) -- 外键，关联部门表(部门表的主键)
	);
	INSERT INTO emp(NAME,gender,salary,join_date,dept_id) VALUES('孙悟空','男',7200,'2013-02-24',1);
	INSERT INTO emp(NAME,gender,salary,join_date,dept_id) VALUES('猪八戒','男',3600,'2010-12-02',2);
	INSERT INTO emp(NAME,gender,salary,join_date,dept_id) VALUES('唐僧','男',9000,'2008-08-08',2);
	INSERT INTO emp(NAME,gender,salary,join_date,dept_id) VALUES('白骨精','女',5000,'2015-10-07',3);
	INSERT INTO emp(NAME,gender,salary,join_date,dept_id) VALUES('蜘蛛精','女',4500,'2011-03-14',1);
```

 笛卡尔积：

​	有两个集合A,B .取这两个集合的所有组成情况。

​	要完成多表查询，需要消除无用的数据

#### 隐式内连接查询(优于显示外连接)

```
隐式内连接：使用where条件消除无用数据

内连接是从结果表中删除与其他被连接表中没有匹配行的所有行，所以内连接可能会丢失信息。

			* 例子：
			-- 查询所有员工信息和对应的部门信息

			SELECT * FROM emp,dept WHERE emp.`dept_id` = dept.`id`;
			
			-- 查询员工表的名称，性别。部门表的名称
			SELECT emp.name,emp.gender,dept.name FROM emp,dept WHERE emp.`dept_id` = dept.`id`;
			
			SELECT 
				t1.name, -- 员工表的姓名
				t1.gender,-- 员工表的性别
				t2.name -- 部门表的名称
			FROM
				emp t1,
				dept t2
			WHERE 
				t1.`dept_id` = t2.`id`;
```

#### 显式内连接查询

```
显式内连接：
		* 语法： select 字段列表 from 表名1 [inner] join 表名2 on 条件
		* 例如：
			* SELECT * FROM emp INNER JOIN dept ON emp.`dept_id` = dept.`id`;	
			* SELECT * FROM emp JOIN dept ON emp.`dept_id` = dept.`id`;	
```

#### 左外连接

```
左外连接：
			* 语法：select 字段列表 from 表1 left [outer] join 表2 on 条件；
			* 查询的是左表所有数据以及其交集部分。
			左边表中的所有数据都要显示,右边表符合条件的显示,不符合条件null补齐

			* 例子：
				-- 查询所有员工信息，如果员工有部门，则查询部门名称，没有部门，则不显示部门名称
				SELECT 	t1.*,t2.`name` FROM emp t1 LEFT JOIN dept t2 ON t1.`dept_id` = t2.`id`;
```

#### 子查询(一般用于聚合函数)

```
子查询：
		* 概念：查询中嵌套查询，称嵌套查询为子查询。
		子查询可以作为条件，使用运算符去判断。 运算符： > >= < <= =

			-- 查询工资最高的员工信息
			-- 1 查询最高的工资是多少 9000
			SELECT MAX(salary) FROM emp;
			
			-- 2 查询员工信息，并且工资等于9000的
			SELECT * FROM emp WHERE emp.`salary` = 9000;
			
			-- 一条sql就完成这个操作。子查询
			SELECT * FROM emp WHERE emp.`salary` = (SELECT MAX(salary) FROM emp);

* 子查询不同情况
	1. 子查询的结果是单行单列的：
		* 子查询可以作为条件，使用运算符去判断。 运算符： > >= < <= =
 
		-- 查询员工工资小于平均工资的人
			SELECT * FROM emp WHERE emp.salary < (SELECT AVG(salary) FROM emp);
	2. 子查询的结果是多行单列的：
		* 子查询可以作为条件，使用运算符in来判断
		-- 查询'财务部'和'市场部'所有的员工信息
			SELECT id FROM dept WHERE NAME = '财务部' OR NAME = '市场部';
			SELECT * FROM emp WHERE dept_id = 3 OR dept_id = 2;
		-- 子查询
			SELECT * FROM emp WHERE dept_id IN (SELECT id FROM dept WHERE NAME = '财务部' OR NAME = '市场部');

	3. 子查询的结果是多行多列的：
		* 子查询可以作为一张虚拟表参与查询
		-- 查询员工入职日期是2011-11-11日之后的员工信息和部门信息
		-- 子查询
			SELECT * FROM dept t1 ,(SELECT * FROM emp WHERE emp.`join_date` > '2011-11-11') t2
			WHERE t1.id = t2.dept_id;
				
		-- 普通内连接
      SELECT * FROM emp t1,dept t2 WHERE t1.`dept_id` = t2.`id` AND t1.`join_date` >  '2011-11-11'
```

### 8.聚合函数查询

```
 聚合函数：将一列数据作为一个整体，进行纵向的计算。
	1. count：计算个数
		1. 一般选择非空的列：主键
		2. count(*)
	2. max：计算最大值
	3. min：计算最小值
	4. sum：计算和
	5. avg：计算平均值
	* 注意：聚合函数的计算，排除null值。
		解决方案：
			1. 选择不包含非空的列进行计算
			2. IFNULL函数
```

## 4.DCL:管理用户，授权

```
* DBA：数据库管理员

1. 管理用户
		1. 添加用户：
			* 语法：CREATE USER '用户名'@'主机名' IDENTIFIED BY '密码';
		2. 删除用户：
			* 语法：DROP USER '用户名'@'主机名';
		3. 修改用户密码：
			
			UPDATE USER SET PASSWORD = PASSWORD('新密码') WHERE USER = '用户名';
			UPDATE USER SET PASSWORD = PASSWORD('abc') WHERE USER = 'lisi';
			
			SET PASSWORD FOR '用户名'@'主机名' = PASSWORD('新密码');
			SET PASSWORD FOR 'root'@'localhost' = PASSWORD('123');

			* MySQL中忘记了root用户的密码？
				1. cmd -- > net stop MySQL 停止MySQL服务
					* 需要管理员运行该cmd

				2. 使用无验证方式启动MySQL服务： mysqld --skip-grant-tables
				3. 打开新的cmd窗口,直接输入MySQL命令，敲回车。就可以登录成功
				4. use MySQL;
				5. update user set password = password('你的新密码') where user = 'root';
				6. 关闭两个窗口
				7. 打开任务管理器，手动结束mysqld.exe 的进程
				8. 启动MySQL服务
				9. 使用新密码登录。
		4. 查询用户：
			-- 1. 切换到MySQL数据库
			USE myql;
			-- 2. 查询user表
			SELECT * FROM USER;
			
			* 通配符： % 表示可以在任意主机使用用户登录数据库

2. 权限管理：
		1. 查询权限：
			-- 查询权限
			SHOW GRANTS FOR '用户名'@'主机名';
			SHOW GRANTS FOR 'lisi'@'%';

		2. 授予权限：
			-- 授予权限
			grant 权限列表 on 数据库名.表名 to '用户名'@'主机名';
			-- 给张三用户授予所有权限，在任意数据库任意表上
			
			GRANT ALL ON *.* TO 'zhangsan'@'localhost';
		3. 撤销权限：
			-- 撤销权限：
			revoke 权限列表 on 数据库名.表名 from '用户名'@'主机名';
			REVOKE UPDATE ON db3.`account` FROM 'lisi'@'%';

权限管理极大提高了数据库的安全性，有效降低了数据库数据被误删的几率。
授权的关键字为:   grant 权限列表 on 数据库名.表名 to '用户名'@'主机名';
撤销权限的关键字为：revoke 权限列表 on 数据库名.表名 from '用户名'@'主机名';
```

# 四、约束

## 1.概念 

​	对表中的数据进行限定，保证数据的正确性、有效性和完整性。

​	所有的约束规则,建立表的时候确定。表结构一旦确定之后,很少更改表结构或者额外添加规则.	

##  2.分类

| 分类     |             |                                                              |
| -------- | ----------- | ------------------------------------------------------------ |
| 主键约束 | primary key | 非空且唯一，一张表只能有一个字段为主键，主键就是表中记录的唯一标识 |
| 非空约束 | not null    | 值不能为null                                                 |
| 唯一约束 | unique      | 值不能重复                                                   |
| 外键约束 | foreign key | 让表于表产生关系，从而保证数据的正确性                       |

### 主键约束primary key  

```
主键是一个表中能标识唯一行的标志（也有其他方法表示唯一行，如唯一列）。
主键主要用在查询单调数据，修改单调数据和删除单调数据上。

1. 在创建表时，添加主键约束
		create table stu(
			id int primary key,-- 给id添加主键约束
			name varchar(20)
		);

2. 删除主键
		ALTER TABLE stu DROP PRIMARY KEY;

3. 创建完表后，添加主键
		ALTER TABLE stu MODIFY id INT PRIMARY KEY;

4. 自动增长：
	1.  概念：如果某一列是数值类型的，使用 auto_increment 可以来完成值得自动增长

	2. 在创建表时，添加主键约束，并且完成主键自增长
		create table stu(
			id int primary key auto_increment,-- 给id添加主键约束
			name varchar(20)
		);
	3. 删除自动增长
		ALTER TABLE stu MODIFY id INT;
	4. 添加自动增长
		ALTER TABLE stu MODIFY id INT AUTO_INCREMENT;
```

### 外键约束：foreign key

```
1. 在创建表时，可以添加外键
	* 语法：
			create table 表名(
				....
				外键列
				constraint 外键名称 foreign key (外键列名称) references 主表名称(主表列名称)
			);

2. 删除外键
		ALTER TABLE 表名 DROP FOREIGN KEY 外键名称;

3. 创建表之后，添加外键
		ALTER TABLE 表名 ADD CONSTRAINT 外键名称 FOREIGN KEY (外键字段名称) REFERENCES 主表名称(主表列名称);

4. 级联操作
	1. 添加级联操作
		语法：ALTER TABLE 表名 ADD CONSTRAINT 外键名称 
		FOREIGN KEY (外键字段名称) REFERENCES 主表名称(主表列名称) ON UPDATE CASCADE ON DELETE CASCADE  ;
	2. 分类：
			1. 级联更新：ON UPDATE CASCADE 
			2. 级联删除：ON DELETE CASCADE 
	开发数据删除不多, 额外设置列：是否有效 N
```

### 非空约束：not null

```
在实际应用中，根据实际情况来确定是否使用非空约束。
例如：注册用户时，用户名和密码必须不为null

1. 创建表时添加约束
		CREATE TABLE stu(
			id INT,
			NAME VARCHAR(20) NOT NULL -- name为非空
		);
2. 创建表完后，添加非空约束
		ALTER TABLE stu MODIFY NAME VARCHAR(20) NOT NULL;

3. 删除name的非空约束
		ALTER TABLE stu MODIFY NAME VARCHAR(20);
```

### 唯一约束：unique

```
唯一约束在实际应用中，也要根据实际情况来进行使用。
注册账户时需要用到手机号，每个手机号只能注册一个账号，这时就要用到唯一约束。

1. 创建表时，添加唯一约束
		CREATE TABLE stu(
			id INT,
			phone_number VARCHAR(20) UNIQUE -- 添加了唯一约束
		
		);
		* 注意MySQL中，唯一约束限定的列的值可以有多个null(实测至多1个)

2. 删除唯一约束
		ALTER TABLE stu DROP INDEX phone_number;
	
3. 在创建表后，添加唯一约束
		ALTER TABLE stu MODIFY phone_number VARCHAR(20) UNIQUE;
```



# 五、事务

## 1.概念

​	如果一个包含多个步骤的业务操作，被事务管理，那么这些操作要么同时成功，要么同时失败。

​	为了完成某个功能，需要对数据库进行多次操作，这些操作要么全部成功，要么全部失败

## 2.操作

| 三大步骤 |                   |                      |
| -------- | ----------------- | -------------------- |
| 开启事务 | start transaction |                      |
| 回滚事务 | rollback          | 一旦执行出错立即回滚 |
| 提交事务 | commit            | 只有全部没出错才提交 |

## 3.事务提交的两种方式

| 提交方式 |        |                                                      |
| -------- | ------ | ---------------------------------------------------- |
| 自动提交 | mysql  | 一条DML(增删改)语句会自动提交一次事务。              |
| 手动提交 | Oracle | 自动提交默认关闭，只有手动执行提交事务commit才能提交 |

修改事务的默认提交方式：

​	查看事务的默认提交方式：SELECT @@autocommit; -- 1 代表自动提交  0 代表手动提交

​	修改默认提交方式： set @@autocommit = 0;

注意事项：一般不会修改事务默认的提交方式

## 4.事务的四大特征：ACID

| 特征        |        |                                                              |
| ----------- | ------ | ------------------------------------------------------------ |
| Atomicity   | 原子性 | 一件事务的所有操作是不可分割的最小操作单位<br/>要么同时成功(提交)，要么同时失败(回滚) |
| Consistency | 一致性 | 事务的操作不管成功还是失败，都将数据库的数据从一个一致性状态转变为另一个一致性状态，具体表现为数据总量保持不变 |
| Isolation   | 隔离性 | 多个并发事务之间要隔离(多个用户访问同一个数据库)，相互独立   |
| Durability  | 持久性 | 当事务提交或回滚后，数据库会持久化的保存数据                 |

## 5. 事务的隔离级别（了解）

###  1.概念与产生原因

​	**概念**：多个事务之间隔离的，相互独立的。但是如果多个事务操作同一批数据，则会引发一些问题，设置不同的隔离级别就可以解决这些问题。

​	**产生原因**：底层线程在切换

### 2.存在问题

| 问题             | 具体表现                                                     |
| ---------------- | ------------------------------------------------------------ |
| 脏读             | 一个事务，读取到另一个事务中没有提交的数据                   |
| 虚读(不可重复读) | 在同一个事务中，两次读取到的数据不一样                       |
| 幻读             | 一个事务操作(DML)数据表中所有记录，另一个事务添加了一条数据，则第一个事务查询不到自己的修改 |

### 3.隔离级别

| 隔离级别         |                        | 产生的问题                   |
| ---------------- | ---------------------- | ---------------------------- |
| read uncommitted | 读未提交               | 脏读、虚读(不可重复读)、幻读 |
| read committed   | 读已提交 （Oracle）    | 虚读(不可重复读)、幻读       |
| repeatable read  | 可重复读 （MySQL默认） | 幻读                         |
| serializable     | 序列化(串行化)         | 可以解决所有的问题           |

注意：隔离级别从小到大安全性越来越高，但是效率越来越低

​	数据库查询隔离级别：select @@tx_isolation;

​	数据库设置隔离级别：set global transaction isolation level  级别字符串;

### 4.隔离级别应该设置为哪个级别？

​	和具体项目有关：

​	头条：  read uncommitted

​	电商发生争抢业务： 具体情况

# 六、JDBC

## 1.概念

Java DataBase Connectivity  Java 数据库连接， Java语言操作数据库

## 2.JDBC本质

其实是官方（sun公司）定义的一套操作所有关系型数据库的规则，即接口。各个数据库厂商去实现这套接口，提供数据库驱动jar包。我们可以使用这套接口（JDBC）编程，真正执行的代码是驱动jar包中的实现类。

Connection__>socket网络流

Statement/PreparedStatement__>输出流

## 3.快速入门

步骤：

​	1.导入驱动jar包 MySQL-connector-java-5.1.37-bin.jar
​		1.复制MySQL-connector-java-5.1.37-bin.jar到项目的libs目录下
​		2.右键-->Add As Library

​	2.注册驱动

​	3.获取数据库连接对象 Connection

​	4.定义sql

​	5.获取执行sql语句的对象 Statement

​	6.执行sql，接受返回结果

​	7.处理结果

​	8.释放资源

## 4.详解各个对象

### 1.DriverManager：驱动管理对象

#### 功能1—>注册驱动

告诉程序该使用哪一个数据库驱动jar

```
写代码使用：  Class.forName("com.MySQL.jdbc.Driver");
				通过查看源码发现：在com.MySQL.jdbc.Driver类中存在静态代码块
				 static {
				        try {
				            java.sql.DriverManager.registerDriver(new Driver());
				        } catch (SQLException E) {
				            throw new RuntimeException("Can't register driver!");
				        }
					}

				注意：MySQL5之后的驱动jar包可以省略注册驱动的步骤。
```

#### 功能2—>获取数据库连接

​	方法：static Connection getConnection(String url, String user, String password) 

```
参数：
		1.url：指定连接的路径
			* 语法：jdbc:mysql://ip地址(域名):端口号/数据库名称
			* 例子：jdbc:mysql://localhost:3306/db3
			* 细节：如果连接的是本机MySQL服务器，并且MySQL服务默认端口是3306，则url可以简写为：jdbc:mysql:///数据库名称
		2.user：用户名
		3.password：密码 
```

### 2.Connection：数据库连接对象

#### 功能1—>获取执行sql 的对象

​	Statement createStatement()：不推荐用此对象

​	PreparedStatement prepareStatement(String sql)  ：防注入，一般用此对象

#### 功能2—>管理事务

```
开启事务：setAutoCommit(boolean autoCommit) ：调用该方法设置参数为false，即开启事务
		* 在执行sql之前开启事务
提交事务：commit() 
		* 当所有sql都执行完提交事务
回滚事务：rollback() 
		* 在catch中回滚事务
```

### 3.Statement：执行sql的对象

1.boolean execute(String sql) ：可以执行任意的sql 了解 

2.int executeUpdate(String sql) ：执行DML（insert、update、delete）语句、DDL(create，alter、drop)语句

​	返回值：影响的行数，可以通过这个影响的行数判断DML语句是否执行成功 返回值>0的则执行成功，反之，则失败。

3.ResultSet executeQuery(String sql)  ：执行DQL（select)语句

### 4.ResultSet：结果集对象,遍历结果集对象

```
boolean next(): 游标向下移动一行，判断当前行是否是最后一行末尾(是否有数据)，如果是，则返回false，如果不是则返回true

getXxx(参数):获取数据
		* Xxx：代表数据类型   如： int getInt() ,	String getString()
		* 参数：
				1. int：代表列的编号,从1开始   如： getString(1)
				2. String：代表列名称。 如： getDouble("balance")
		
* 注意：
		* 使用步骤：
				1. 游标向下移动一行
				2. 判断是否有数据
				3. 获取数据

			//循环判断游标是否是最后一行末尾。
	       while(rs.next()){
	          //获取数据
	          //6.2 获取数据
	          int id = rs.getInt(1);
	          String name = rs.getString("name");
	          double balance = rs.getDouble(3);
	
	          System.out.println(id + "---" + name + "---" + balance);
	      }
```

### 5.PreparedStatement：执行sql的对象

#### 1.SQL注入问题

在拼接sql时，有一些sql的特殊关键字参与字符串的拼接。会造成安全性问题

```
1. 输入用户随便，输入密码：a' or 'a' = 'a
2. 定义sql语句：select * from user where username = 'fhdsjkf' and password = 'a' or 'a' = 'a' 
居然可以登陆成功！
```

#### 2.解决sql注入问题

使用PreparedStatement对象来解决

#### 3.预编译的SQL

参数使用?作为占位符

#### 4.使用步骤

```
1. 导入驱动jar包 MySQL-connector-java-5.1.37-bin.jar
2. 注册驱动
3. 获取数据库连接对象 Connection
4. 定义sql
		* 注意：sql的参数使用？作为占位符。如：select * from user where username = ? and password = ?;
5. 获取执行sql语句的对象 PreparedStatement  Connection.prepareStatement(String sql) 
6. 给？赋值：
			* 方法： setXxx(参数1,参数2)
			* 参数1：？的位置编号 从1 开始
			* 参数2：？的值
7. 执行sql，接受返回结果，不需要传递sql语句
8. 处理结果
9. 释放资源
```

#### 5.注意

​	后期都会使用PreparedStatement来完成增删改查的所有操作，因为：

​	1.可以防止SQL注入

​	2.效率更高



# 七、数据库连接池

为什么要用数据库连接池？因为JDBC效率低下，浪费资源

## 1.概念

​	其实就是一个容器(集合)，存放数据库连接的容器。
​	当系统初始化好后，容器被创建，容器中会申请一些连接对象，当用户来访问数据库时，从容器中获取连接对象，用户访问完之后，会将连接对象归还给容器。

​	本质:提前建立好了ArrayList,存放一个个连接connection

## 2.好处

| 好处                                |
| ----------------------------------- |
| 占用资源少(占用内存少)，节约资源    |
| 语句以及逻辑处理速度快,用户访问高效 |
| 代码的复用率高                      |
| 高扩展                              |

## 3.实现

### 1.标准接口

​	DataSource   javax.sql包下的

​	方法：

​	获取连接：getConnection()

​	归还连接：Connection.close()。如果连接对象Connection是从连接池中获取的，那么调用Connection.close()方法，则不会再关闭连接了。而是归还连接

### 2.数据库厂商实现标准接口

​	C3P0：数据库连接池技术

​	Druid：数据库连接池实现技术，由阿里巴巴提供的

## 4.Druid

### 1.实现步骤

​	1⃣️导入jar包 druid-1.0.9.jar

​	2⃣️定义配置文件：

​		是properties形式的

​		可以叫任意名称，可以放在任意目录下

​	3⃣️加载配置文件。Properties

​	4⃣️获取数据库连接池对象：通过工厂来来获取  DruidDataSourceFactory

​	5⃣️获取连接：getConnection

### 2.代码实现

```
//3.加载配置文件
	Properties pro = new Properties();
	InputStream is = DruidDemo.class.getClassLoader().getResourceAsStream("druid.properties");
 	pro.load(is);
//4.获取连接池对象
	DataSource ds = DruidDataSourceFactory.createDataSource(pro);
//5.获取连接
	Connection conn = ds.getConnection();
```

### 3.定义工具类(简化书写)

1.定义一个类 JDBCUtils

2.提供静态代码块加载配置文件，初始化连接池对象

3.提供方法
	获取连接方法：通过数据库连接池获取连接
	释放资源
	获取连接池的方法

```
public class JDBCUtils {

		//1.定义成员变量 DataSource
		private static DataSource ds ;
		
		//文件的读取，只需要读取一次即可拿到这些值。使用静态代码块
		static{
			InputStream is = null;
		   try {
		   //1.加载配置文件
		       Properties pro = new Properties();
		       is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties")；    						pro.load(is);
		       is.close();
		   //2.获取DataSource
		       ds = DruidDataSourceFactory.createDataSource(pro);
		   } catch (IOException e) {
		       e.printStackTrace();
		   } catch (Exception e) {
		       e.printStackTrace();
		   }
		}
		
		/**
		 * 获取连接
		 */
		 public static Connection getConnection() throws SQLException {
		     return ds.getConnection();
		 }
		
		/**
		 * 释放资源
		 */
		public static void close(PreparedStatement pstmt,Connection conn){
		     close(null,pstmt,conn);
		}
		
		public static void close(ResultSet rs , PreparedStatement pstmt, Connection conn){
		     if(rs != null){
		     		try {
		           rs.close();
		        } catch (SQLException e) {
		           e.printStackTrace();
		        }
		     }
		     
		     if(pstmt != null){
		        try {
		           pstmt.close();
		        } catch (SQLException e) {
		           e.printStackTrace();
		        }
		     }
		
		     if(conn != null){
		        try {
		           conn.close();//归还连接
		        } catch (SQLException e) {
		           e.printStackTrace();
		        }
		     }
	}
		
		 /**
		  * 获取连接池方法
		  */
		
		public static DataSource getDataSource(){
		    return  ds;
		}
}
```

# 八、JDBCTemplate

## 1.概念

 Spring框架对JDBC的简单封装。提供了一个JDBCTemplate对象简化JDBC的开发

## 2.使用步骤

```
1. 导入jar包
2. 创建JdbcTemplate对象。依赖于数据源DataSource
		* JdbcTemplate template = new JdbcTemplate(ds);
3. 调用JdbcTemplate的方法来完成CRUD的操作
		* update(sql,参数列表):执行DML语句。增、删、改语句
		* queryForMap(sql，参数列表):查询一条结果将结果集封装为map集合，将列名作为key，将值作为value 将这条记录封装为一个map集合
			* 注意：这个方法查询的结果集长度只能是1
		* queryForList(sql，参数列表):查询结果将结果集封装为list集合
			* 注意：将每一条记录封装为一个Map集合，再将Map集合装载到List集合中
		* query(sql，new BeanPropertyRowMapper<类型>(类型.class)):查询结果，将结果封装为JavaBean对象
				* BeanPropertyRowMapper实现类,可以完成数据到JavaBean的自动封装
		* queryForObject(sql,类型.class)：查询结果，将结果封装为对象
			* 一般用于聚合函数的查询：Long.class
				String sql = "select count(id) from emp";
			  Long total = template.queryForObject(sql, Long.class);
			  System.out.println(total);
```

# 九、MySQL与Oracle区别

## 宏观角度分析

1、Oracle是大型的数据库而MySQL是中小型数据库；MySQL是开源的，Oracle是收费的，且价格昂贵。

2、Oracle支持大并发，大访问量，是OLTP的最好的工具。

3、安装占用的内存也是有差别，MySQL安装完成之后占用的内存远远小于Oracle所占用的内存，并且Oracle越用所占内存也会变多。

## 微观角度分析

### 1、对于事务的支持

MySQL对于事务默认是不支持的，只是有某些存储引擎中如：innodb可以支持；而Oracle对于事物是完全支持的。

### 2、并发性

什么是并发性？并发性是OLTP(**On-Line Transaction Processing联机事务处理过程**)数据库最重要的特性，并发性涉及到资源的获取、共享与锁定。

MySQL以表锁为主，对资源锁定的力度很大，如果一个session对一个表加锁时间过长，会让其他session无法更新此表的数据。

Oracle使用行级锁，对资源锁定的力度要小很多，只是锁定sql需要的资源，并且加锁是在数据库中的数据行上，不依赖于索引。所以Oracle对并发性的支持要好很多。

### 3、数据的持久性

Oracle保证提交的事务均可以恢复，因为Oracle把提交的sql操作线写入了在线联机日志文件中，保存到磁盘上，如果出现数据库或者主机异常重启，重启Oracle可以靠联机在线日志恢复客户提交的数据。

MySQL默认提交sql语句，但是如果更新过程中出现db或者主机重启的问题，也可能会丢失数据。

### 4、事务隔离级别

> **MySQL：**

MySQL默认的事务处理级别是'REPEATABLE-READ',也就是可重复读

1.查看当前会话隔离级别

```sql
select @@tx_isolation;
```

2.查看系统当前隔离级别

```sql
select @@global.tx_isolation;
```

3.设置当前会话隔离级别

```sql
set session transaction isolatin level repeatable read;
```

4.设置系统当前隔离级别

```sql
set global transaction isolation level repeatable read;
```

> **Oracle：**

Oracle数据库支持READ COMMITTED 和 SERIALIZABLE这两种事务隔离级别。

默认系统事务隔离级别是READ COMMITTED,也就是读已提交

```sql
--首先创建一个事务
declare
     trans_id Varchar2(100);
  begin
     trans_id := dbms_transaction.local_transaction_id( TRUE );
  end; 
--查看事务隔离级别
SELECT s.sid, s.serial#,
　　CASE BITAND(t.flag, POWER(2, 28))
　　　　WHEN 0 THEN 'READ COMMITTED'
　　　　ELSE 'SERIALIZABLE'
　　END AS isolation_level
FROM v$transaction t
JOIN v$session s ON t.addr = s.taddr AND s.sid = sys_context('USERENV', 'SID');
```

### 5、提交方式

Oracle默认不自动提交，需要手动提交。MySQL默认自动提交。

### 6、逻辑备份

MySQL逻辑备份是要锁定数据，才能保证备份的数据是一致的，影响业务正常的DML(数据操纵语言Data Manipulation Language)使用；Oracle逻辑备份时不锁定数据，且备份的数据是一致的。

### 7、sql语句的灵活性

MySQL对sql语句有很多非常实用而方便的扩展，比如limit功能(分页)，insert可以一次插入多行数据；Oracle在这方面感觉更加稳重传统一些，Oracle的分页是通过伪列和子查询完成的，插入数据只能一行行的插入数据。

### 8、数据复制

MySQL：复制服务器配置简单，但主库出问题时，丛库有可能丢失一定的数据。且需要手工切换丛库到主库。

Oracle：既有推或拉式的传统数据复制，也有dataguard的双机或多机容灾机制，主库出现问题是，可以自动切换备库到主库，但配置管理较复杂。

### 9、分区表和分区索引

MySQL的分区表还不太成熟稳定；Oracle的分区表和分区索引功能很成熟，可以提高用户访问db的体验。

### 10、售后与费用

Oracle是收费的，出问题找客服；MySQL是免费的的，开源的，出问题自己解决。

### 11、权限与安全

Oracle的权限与安全概念比较传统，中规中矩；MySQL的用户与主机有关，感觉没有什么意义，另外更容易被仿冒主机及ip有可乘之机。

### 12、性能诊断方面

Oracle有各种成熟的性能诊断调优工具，能实现很多自动分析、诊断功能。比如awr、addm、sqltrace、tkproof等 ；MySQL的诊断调优方法较少，主要有慢查询日志。

## 其他

1. Oracle是大型数据库而MySQL是中小型数据库，Oracle市场占有率达40%，MySQL只有20%左右，同时MySQL是开源的而Oracle价格非常高。
2. Oracle支持大并发，大访问量，是OLTP(On-Line Transaction Processing联机事务处理系统)最好的工具。

3. 安装所用的空间差别也是很大的，MySQL安装完后才152M而Oracle有3G左右，且使用的时候Oracle占用特别大的内存空间和其他机器性能。

> **Oracle与MySQL在其他操作上的一些区别：**

①主键 MySQL一般使用自动增长类型，在创建表时只要指定表的主键为auto increment,插入记录时，不需要再指定该记录的主键值，MySQL将自动增长；Oracle没有自动增长类型，主键一般使用的序列，插入记录时将序列号的下一个值付给该字段即可；只是ORM框架是只要是native主键生成策略即可。

②单引号的处理 MySQL里可以用双引号包起字符串，Oracle里只可以用单引号包起字符串。在插入和修改字符串前必须做单引号的替换：把所有出现的一个单引号替换成两个单引号。

③翻页的SQL语句的处理 MySQL处理翻页的SQL语句比较简单，用LIMIT 开始位置, 记录个数；Oracle处理翻页的SQL语句就比较繁琐了。每个结果集只有一个ROWNUM字段标明它的位置, 并且只能用ROWNUM<100, 不能用ROWNUM>80。

④ 长字符串的处理，长字符串的处理Oracle也有它特殊的地方。INSERT和UPDATE时最大可操作的字符串长度小于等于4000个单字节, 如果要插入更长的字符串, 请考虑字段用CLOB类型，方法借用Oracle里自带的DBMS_LOB程序包。插入修改记录前一定要做进行非空和长度判断，不能为空的字段值和超出长度字段值都应该提出警告,返回上次操作。

⑤空字符的处理 MySQL的非空字段也有空的内容，Oracle里定义了非空字段就不容许有空的内容。按MySQL的NOT NULL来定义Oracle表结构, 导数据的时候会产生错误。因此导数据时要对空字符进行判断，如果为NULL或空字符，需要把它改成一个空格的字符串。

⑥字符串的模糊比较 MySQL里用 字段名 like '%字符串%',Oracle里也可以用 字段名 like '%字符串%' ，但这种方法不能使用索引, 速度不快。

⑦Oracle实现了ANSII SQL中大部分功能，如:事务的隔离级别、传播特性等，而MySQL在这方面还是比较的弱。



## 数据类型

| 编号 | Oracle                                                       | MySQL                                                        | 注释                                                         |
| ---- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1    | NUMBER                                                       | int / DECIMAL                                                | DECIMAL就是NUMBER(10,2)这样的结构INT就是是NUMBER(10)，表示整型；  MySQL有很多类int型，tinyint mediumint bigint等，不同的int宽度不一样 |
| 2    | Varchar2（n）                                                | varchar(n)                                                   |                                                              |
| 3    | Date                                                         | DATATIME                                                     | 日期字段的处理  MySQL日期字段分DATE和TIME两种，Oracle日期字段只有DATE，包含年月日时分秒信息，用当前数据库的系统时间为 SYSDATE, 精确到秒，或者用字符串转换成日期型函数TO_DATE(‘2001-08-01','YYYY-MM-DD')年-月-日 24小时:分钟:秒的格式YYYY-MM-DD HH24:MI:SS TO_DATE()还有很多种日期格式, 可以参看Oracle DOC.日期型字段转换成字符串函数TO_CHAR(‘2001-08-01','YYYY-MM-DD HH24:MI:SS')    日期字段的数学运算公式有很大的不同。MySQL找到离当前时间7天用DATE_FIELD_NAME ＞ SUBDATE（NOW（），INTERVAL 7 DAY）Oracle找到离当前时间7天用 DATE_FIELD_NAME ＞SYSDATE - 7;    MySQL中插入当前时间的几个函数是：NOW()函数以`'YYYY-MM-DD HH:MM:SS'返回当前的日期时间，可以直接存到DATETIME字段中。CURDATE()以'YYYY-MM-DD'的格式返回今天的日期，可以直接存到DATE字段中。CURTIME()以'HH:MM:SS'的格式返回当前的时间，可以直接存到TIME字段中。例：insert into tablename (fieldname) values (now())    而Oracle中当前时间是sysdate |
| 4    | INTEGER                                                      | int / INTEGER                                                | MySQL中INTEGER等价于int                                      |
| 5    | EXCEPTION                                                    | SQLEXCEPTION                                                 | 详见<<2009001-eService-O2MG.doc>>中2.5 MySQL异常处理         |
| 6    | CONSTANT VARCHAR2(1)                                         | MySQL中没有CONSTANT关键字                                    | 从Oracle迁移到MySQL,所有CONSTANT常量只能定义成变量           |
| 7    | TYPE g_grp_cur IS REF CURSOR;                                | 光标 : MySQL中有替代方案                                     | 详见<<2009001-eService-O2MG.doc>>中2.2 光标处理              |
| 8    | TYPE unpacklist_type IS TABLE OF VARCHAR2(2000) INDEX BY BINARY_INTEGER; | 数组: MySQL中借助临时表处理  或者直接写逻辑到相应的代码中，  直接对集合中每个值进行相应的处理 | 详见<<2009001-eService-O2MG.doc>>中2.4 数组处理              |
| 9    | 自动增长的序列                                               | 自动增长的数据类型                                           | MySQL有自动增长的数据类型，插入记录时不用操作此字段，会自动获得数据值。Oracle没有自动增长的数据类型，需要建立一个自动增长的序列号，插入记录时要把序列号的下一个值赋于此字段。 |
| 10   | NULL                                                         | NULL                                                         | 空字符的处理  MySQL的非空字段也有空的内容，Oracle里定义了非空字段就不容许有空的内容。按MySQL的NOT NULL来定义Oracle表结构, 导数据的时候会产生错误。因此导数据时要对空字符进行判断，如果为NULL或空字符，需要把它改成一个空格的字符串。 |

## 基本语法

| 编号 | 类别                     | Oracle                                                       | MySQL                                                        | 注释                                                         |
| ---- | ------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1    | 变量的声明方式不同       | li_index NUMBER := 0                                         | DECLARE li_index INTEGER DEFAULT 0                           | 1. MySQL 使用DECLARE定义局部变量.  定义变量语法为: DECLARE var_name[,...] type [DEFAULT value] 要给变量提供一个默认值，需要包含一个DEFAULT子句。值可以被指定为一个表达式，不需要为一个常数。如果没有DEFAULT子句，初始值为NULL。 |
| 2    | 变量的赋值方式不同       | lv_inputstr := iv_inputstr                                   | SET lv_inputstr = iv_inputstr                                | 1. Oracle变量赋值使用:=  MySQL 使用赋值使用set关键字. 将一个值赋给一个变量时使用"=". |
| 3    | 跳出（退出）语句不同     | EXIT;                                                        | LEAVE procedure name;                                        | Oracle: 如果exit语句在循环中就退出当前循环.如果exit语句不再循环中,就退出当前过程或方法. |
|      |                          | while 条件 loop  exit;  end loop;                            | label_name:while 条件 do  leave label_name;  end while label_name; | MySQL: 如果leave语句后面跟的是存储过程名,则退出当前存储过程. 如果leave语句后面跟的是lable名. 则退出当前lable. |
| 4    | 定义游标                 | TYPE g_grp_cur IS REF CURSOR;                                | DECLARE cursor_name CURSOR FOR SELECT_statement;             | Oracle可以先定义游标,然后给游标赋值.  MySQL定义游标时就需要给游标赋值. MySQL定义游标出自 MySQL 5.1 参考手册20.2.11.1.声明光标. |
| 5    | 定义数组                 | TYPE unpacklist_type IS TABLE OF VARCHAR2(2000) INDEX BY BINARY_INTEGER; | 可以使用临时表代替Oracle数组, 也可以循环拆分字符来替代Oracle数组. | 目前可以使用临时表来代替Oracle数组.  详见<<2009002-OTMPPS-Difficult Questions-0001.doc>>中2.4 MySQL数组处理部分 |
| 6    | 注释方式不同             | "-- message" 或 "/** …. */" 或"/* …. */"                     | "-- message" 或 "/* …. */" 或 "#"                            | MySQL注释来自 MySQL 5.1参考手册 9.5. 注释语法, 建议同Oracle一样, 单行用--, 多行/* */ |
| 7    | 自带日期时间函数格式不同 | Oracle时间格式：yyyy-MM-dd hh:mi:ss                          | MySQL时间格式：%Y-%m-%d %H:%i:%s                             | 1. MySQL日期字段分DATE和TIME两种.  Oracle日期字段只有DATE，包含年月日时分秒信息.  2. MySQL中取当前系统时间为now()函数,精确到秒.  Oracle中取当前数据库的系统时间为SYSDATE, 精确到秒. |
| 8    | 日期加减                 | 当前时间加N天: sysdate+N  当前时间减N天: sysdate-N           | 日期相加: date_add(now(), INTERVAL 180 DAY)  日期相减: date_sub('1998-01-01 00:00:00', interval '1 1:1:1' day_second) |                                                              |
| 9    | 字符串连接符不同         | result := v_int1\|\|v_int2;                                  | set result =concat(v_int1,v_int2);                           | 1. Oracle使用\|\|连接字符串,也可以使用concat函数. 但Oracle的concat函数只能连接两个字符串.  MySQL使用concat方法连接字符串. MySQL的concat函数可以连接一个或者多个字符串,如  MySQL> select concat('10'); 结果为: 10.  MySQL> select concat('11','22','33','aa'); 结果为: 112233aa <br /> 2. "\|\|"在MySQL是与运算 |
| 10   | in()                     | in内最多1000个数据，多于1000用in(1000) or in(1001)           | in内的数据数量没有限制，可对sql的长度有限制，默认4M          |                                                              |
| 11   | 分页函数                 | rownum                                                       | limit 起始位置，每页记录数                                   |                                                              |
| 12   | 定义游标不同             | CURSOR l_bk_cur IS  SELECT B.BK_HDR_INT_KEY, B.BK_NUM  FROM ES_SR_DTL_VRB A, ES_BK_HDR B  WHERE A.BK_HDR_INT_KEY = B.BK_HDR_INT_KEY  AND b.BK_STATUS != ES_BK_PKG.g_status_can  AND A.SR_HDR_INT_KEY = ii_sr_hdr_int_key; | DECLARE l_bk_cur CURSOR  FOR SELECT B.BK_HDR_INT_KEY, B.BK_NUM  FROM ES_SR_DTL_VRB A, ES_BK_HDR B  WHERE A.BK_HDR_INT_KEY = B.BK_HDR_INT_KEY  AND b.BK_STATUS != ES_BK_PKG.g_status_can  AND A.SR_HDR_INT_KEY = ii_sr_hdr_int_key; | 详见<<2009002-OTMPPS-Difficult Questions-0001.doc>>中2.2 MySQL游标处理部分 |
| 13   | 事务回滚                 | ROLLBACK;                                                    | ROLLBACK;                                                    | Oracle和MySQL中使用方法相同                                  |
| 14   | GOTO语句                 | GOTO check_date;                                             | GOTO check_date;                                             | Oracle和MySQL中使用方法相同                                  |

## 函数

| 编号 | 类别         | Oracle                                                  | MySQL                                                        | 注释                                                         |
| ---- | ------------ | ------------------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1    | 数字函数     | round(1.23456,4)                                        | round(1.23456,4)                                             | 一样：<br/> Oracle：select round(1.23456,4) value from dual<br/> MySQL：select round(1.23456,4) value |
| 2    |              | abs(-1)                                                 | abs(-1)                                                      | 功能: 将当前数据取绝对值<br/> 用法: Oracle和MySQL用法一样<br/> MySQL: select abs(-1) value<br/> Oracle: select abs(-1) value from dual |
| 3    |              | ceil(-1.001))                                           | ceiling(-1.001)                                              | 功能: 返回不小于 X 的最小整数<br/> 用法:<br/> MySQL: select ceiling(-1.001) value<br/> Oracle: select ceil(-1.001) value from dual |
| 4    |              | floor(-1.001)                                           | floor(-1.001)                                                | 功能: 返回不大于 X 的最大整数值<br/> 用法:<br/> MySQL: select floor(-1.001) value<br/> Oracle: select floor(-1.001) value from dual |
| 5    |              | Max(expr)/Min(expr)                                     | Max(expr)/Min(expr)                                          | 功能:返回 expr 的最小或最大值。MIN() 和 MAX() 可以接受一个字符串参数；在这<br/> 种情况下，它们将返回最小或最大的字符串传下。<br/> 用法:<br/> Oracle: select max(user_int_key) from sd_usr;<br/> MySQL: select max(user_int_key) from sd_usr; |
| 6    | 字符串函数   | ascii(str)                                              | ascii(str)                                                   | 功能:返回字符串 str 最左边的那个字符的 ASCII 码值。如果 str 是一个空字符串，<br/> 那么返回值为 0。如果 str 是一个 NULL，返回值也是 NULL.<br/> 用法:<br/> MySQL:select ascii('a') value<br/> Oracle:select ascii('a') value from dual |
| 7    |              | CHAR(N,...)                                             | CHAR(N,...)                                                  | 功能:CHAR() 以整数类型解释参数，返回这个整数所代表的 ASCII 码值给出的字符<br/> 组成的字符串。NULL 值将被忽略.<br/> 用法:<br/> MySQL:select char(97) value<br/> Oracle:select chr(97) value from dual |
| 8    |              | REPLACE(str,from_str,to_str)                            | REPLACE(str,from_str,to_str)                                 | 功能: 在字符串 str 中所有出现的字符串 from_str 均被 to_str 替换，然后返回这个字符串.<br/> 用法:<br/> MySQL: SELECT REPLACE('abcdef', 'bcd', 'ijklmn') value<br/> Oracle: SELECT Replace('abcdef', 'bcd', 'ijklmn') value from dual |
| 9    |              | INSTR('sdsq','s',2)                                     | INSTR('sdsq','s')                                            | 参数个数不同<br/> Oracle: select INSTR('sdsq','s',2) value from dual（要求从位置2开始）<br/> MySQL: select INSTR('sdsq','s') value（从默认的位置1开始） |
| 10   |              | SUBSTR('abcd',2,2)                                      | substring('abcd',2,2)                                        | 函数名称不同：<br/> Oracle: select substr('abcd',2,2) value from dual<br/> MySQL: select substring('abcd',2,2) value |
| 11   |              | instr(‘abcdefg','ab')                                   | locate(‘ab','abcdefg')                                       | 函数名称不同：<br/> instr -> locate（注意：locate的子串和总串的位置要互换）<br/> Oracle: SELECT instr('abcdefg', 'ab') VALUE FROM DUAL<br/> MySQL: SELECT locate('ab', 'abcdefg') VALUE |
| 12   |              | length（str）                                           | char_length()                                                | 函数名称不同：<br/> ORACEL: SELECT length('AAAASDF') VALUE FROM DUAL<br/> MySQL: SELECT char_length('AAAASDF') VALUE |
| 13   |              | REPLACE('abcdef', 'bcd', 'ijklmn')                      | REPLACE('abcdef', 'bcd', 'ijklmn')                           | 一样：<br/> Oracle: SELECT REPLACE('abcdef', 'bcd', 'ijklmn') value from dual<br/> MySQL: SELECT REPLACE('abcdef', 'bcd', 'ijklmn') value |
| 14   |              | LPAD('abcd',14, '0')                                    | LPAD('abcd',14, '0')                                         | 一样：  Oracle: select LPAD('abcd',14, '0') value from dual<br/> MySQL: select LPAD('abcd',14, '0') value from dual |
| 15   |              | UPPER(iv_user_id)                                       | UPPER(iv_user_id)                                            | 一样：<br/> Oracle: select UPPER(user_id) from sd_usr;<br/> MySQL: select UPPER(user_id) from sd_usr; |
| 16   |              | LOWER(iv_user_id)                                       | LOWER(iv_user_id)                                            | 一样：<br/> Oracle: select LOWER(user_id) from sd_usr;<br/> MySQL: select LOWER(user_id) from sd_usr; |
| 17   | 控制流函数   | nvl(u.email_address, 10)                                | IFNULL(u.email_address, 10)<br/> 或<br/> ISNULL(u.email_address) | 函数名称不同（根据不同的作用进行选择）：<br/> Oracle: select u.email_address, nvl(u.email_address, 10) value from sd_usr u (如果u.email_address=NULl,就在DB中用10替换其值)<br/> MySQL: select u.email_address, IFNULL(u.email_address, 10) value from sd_usr u(如果u.email_address=NULl,显示结果中是10，而不是在DB中用10替换其值)<br/> select u.email_address, ISNULL(u.email_address) value from sd_usr u（如果u.email_address是NULL, 就显示1<true>,否则就显示0<false>） |
| 18   |              | DECODE(iv_sr_status,g_sr_status_com, ld_sys_date, NULL) | 无，请用IF或CASE语句代替.<br/> IF语句格式:(expr1,expr2,expr3) | 说明:<br/> \1. decode(条件,值1,翻译值1,值2,翻译值2,...值n,翻译值n,缺省值)<br/> 该函数的含义如下：<br/> IF 条件=值1 THEN<br/> 　　　RETURN(翻译值1)<br/> ELSIF 条件=值2 THEN<br/> 　　　RETURN(翻译值2)<br/> 　　　......<br/> ELSIF 条件=值n THEN<br/> 　　　RETURN(翻译值n)<br/> ELSE<br/> 　　　RETURN(缺省值)<br/> END IF<br/> <br/> \2. MySQL If语法说明<br/> 功能: 如果 expr1 是TRUE (expr1 <> 0 and expr1 <> NULL)，则IF()的返回值为expr2;<br/> 否则返回值则为 expr3。IF() 的返回值为数字值或字符串值，具体情况视其所在<br/> 语境而定。<br/> 用法:<br/> MySQL: SELECT IF(1>2,2,3); |
| 19   | 类型转换函数 | TO_CHAR(SQLCODE)                                        | date_format/ time_format                                     | 函数名称不同  SQL> select to_char(sysdate,'yyyy-mm-dd') from dual; <br />SQL> select to_char(sysdate,'hh34-mi-ss') from dual;<br/> MySQL> select date_format(now(),'%Y-%m-%d');<br/> MySQL> select time_format(now(),'%H-%i-%S'); |
| 20   |              | to_date(str,format)                                     | STR_TO_DATE(str,format)                                      | 函数名称不同：<br/> Oracle:SELECT to_date('2009-3-6','yyyy-mm-dd') VAULE FROM DUAL<br/> MySQL: SELECT STR_TO_DATE('2004-03-01', '%Y-%m-%d') VAULE |
| 21   |              | trunc(-1.002)                                           | cast(-1.002 as SIGNED)                                       | 函数名称不同：<br/> TRUNC函数为指定元素而截去的日期值。<br/> Oracle： select trunc(-1.002) value from dual<br/> MySQL：select cast(-1.002 as SIGNED) value<br/> MySQL：<br/> 字符集转换 : CONVERT(xxx USING gb2312)<br/> 类型转换和SQL Server一样,就是类型参数有点点不同 : CAST(xxx AS 类型) , CONVERT(xxx,类型)，类型必须用下列的类型：<br/> <br/> 可用的类型　<br/> 二进制,同带binary前缀的效果 : BINARY<br/> 字符型,可带参数 : CHAR()<br/> 日期 : DATE<br/> 时间: TIME<br/> 日期时间型 : DATETIME<br/> 浮点数 : DECIMAL<br/> 整数 : SIGNED<br/> 无符号整数 : UNSIGNED |
| 22   |              | TO_NUMBER(str)                                          | CAST("123" AS SIGNED INTEGER)                                | 函数名称不同<br/> Oracle:SELECT TO_NUMBER('123') AS VALUE FROM DUAL;<br/> MySQL: SELECT CAST("123" AS SIGNED INTEGER) as value;<br/> SIGNED INTEGER:带符号的整形 |
| 23   | 时间函数     | SYSDATE                                                 | now() / SYSDATE()                                            | 写法不同：<br/> Oracle:select SYSDATE value from dual<br/> MySQL:select now() value<br/> select sysdate() value |
| 24   |              | Next_day(sysdate,7)                                     | 自定义一个函数:F_COMMON_NEXT_DAY(date,int)                   | 函数名称不同：<br/> Oracle: SELECT Next_day(sysdate,7) value FROM DUAL<br/> MySQL: SELECT F_COMMON_NEXT_DAY(SYSDATE(), 3) value from DUAL;<br/> (3:指星期的索引值)返回的指定的紧接着下一个星期的日期 |
| 25   |              | ADD_MONTHS(sysdate, 2)                                  | DATE_ADD(sysdate(), interval 2 month)                        | 函数名称不同:<br/> Oracle: SELECT ADD_MONTHS(sysdate, 2) as value from DUAL;<br/> MySQL: SELECT DATE_ADD(sysdate(), interval 2 month) as value from DUAL; |
| 26   |              | 2个日期相减(D1-D2)                                      | DATEDIFF(date1,date2)                                        | 功能: 返回两个日期之间的天数。<br/> 用法:<br/> MySQL: SELECT DATEDIFF('2008-12-30','2008-12-29') AS DiffDate<br/> Oracle: 直接用两个日期相减（比如d1-d2=12.3） |
| 27   | SQL函数      | SQLCODE                                                 | MySQL中没有对应的函数，但JAVA中SQLException。getErrorCode()函数可以获取错误号 | Oracle内置函数SQLCODE和SQLERRM是特别用在OTHERS处理器中，分别用来返回Oracle的错误代码和错误消息。<br/> MySQL: 可以从JAVA中得到错误代码，错误状态和错误消息 |
| 28   |              | SQLERRM                                                 | MySQL中没有对应的函数，但JAVA中SQLException。getMessage()函数可以获取错误消息 | Oracle内置函数SQLCODE和SQLERRM是特别用在OTHERS处理器中，分别用来返回Oracle的错误代码和错误消息。<br/> MySQL: 可以从JAVA中得到错误代码，错误状态和错误消息 |
| 29   |              | SEQ_BK_DTL_OPT_INT_KEY.NEXTVAL                          | 自动增长列                                                   | 在MySQL中是自动增长列. 如下方法获取最新ID:<br/> START TRANSACTION;<br/> INSERT INTO user(username,password)<br/> VALUES (username,MD5(password));<br/> SELECT LAST_INSERT_ID() INTO id;<br/> COMMIT; |
| 30   |              | SUM(enable_flag)                                        | SUM(enable_flag)                                             | 一样：<br/> ORCALE： SELECT SUM(enable_flag) FROM SD_USR;<br/> MySQL： SELECT SUM(enable_flag) FROM SD_USR; |
| 31   |              | DBMS_OUTPUT.PUT_LINE(SQLCODE)                           | 在MySQL中无相应的方法，其作用是在控制台中打印，用于测试，对迁移无影响。 | dbms_output.put_line每行只能显示255个字符，超过了就会报错    |

## 循环语句

| 编号 | 类别           | Oracle                                                       | MySQL                                                        | 注释                                                         |
| ---- | -------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1    | IF语句使用不同 | IF iv_weekly_day = 'MON'THEN  ii_weekly_day := 'MON';  ELSIF iv_weekly_day = 'TUE' THEN  ii_weekly_day := 'TUE';  END IF; | IF iv_weekly_day = 'MON'THEN  set ii_weekly_day = 'MON';  ELSEIF iv_weekly_day = 'TUE' THEN  set ii_weekly_day = 'TUE';  END IF; | 1. MySQL和Oracle除了关键字有一个字差别外(ELSEIF/ELSIF),if语句使用起来完全相同.  2. MySQL if语句语法: 摘自 MySQL 5.1 参考手册 20.2.12.1. IF语句  IF search_condition THEN statement_list  [ELSEIF search_condition THEN statement_list] ...  [ELSE statement_list]  END IF  IF实现了一个基本的条件构造。如果search_condition求值为真，相应的SQL语句列表被执行。如果没有search_condition匹配，在ELSE子句里的语句列表被执行。statement_list可以包括一个或多个语句。 |
| 2    | FOR语句不同    | FOR li_cnt IN 0..(ii_role_cnt-1) LOOP  SELECT COUNT(*) INTO li_role_ik_cnt FROM SD_ROLE  WHERE ROLE_CD = lo_aas_role_upl(li_cnt);  IF li_role_ik_cnt = 0 THEN  RETURN 'N';  END IF;  li_role_ik_cnt := -3;  END LOOP; | loopLable:LOOP  IF i > (ii_role_cnt-1) THEN  LEAVE looplable;  ELSE  SELECT COUNT(*) INTO li_role_ik_cnt FROM SD_ROLE  WHERE ROLE_CD = 'ADMIN_SUPER'; /*lo_aas_role_upl(li_cnt);*/  IF li_role_ik_cnt = 0 THEN  RETURN 'N';  END IF;  SET li_role_ik_cnt = -3;  SET i = i+1;  END IF;  END LOOP loopLable; | 1. Oracle使用For语句实现循环.  MySQL使用Loop语句实现循环.  2. Oracle 使用For…loop关键字.  MySQL使用loopLable:LOOP实现循环. |
| 3    | while语句不同  | WHILE lv_inputstr IS NOT NULL LOOP  ...  END LOOP;           | WHILE lv_inputstr IS NOT NULL DO  ...  END WHILE;            | 1. Oracle 中使用while语句关键字为: while 表达式 loop… end loop;  MySQL 中使用while语句关键字为: while 表达式 do… end while; |

## 存储过程&Function

| 编号 | 类别                               | Oracle                                                       | MySQL                                                        | 注释                                                         |
| ---- | ---------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1    | 创建存储过程语句不同               | create or replace procedure P_ADD_FAC(  id_fac_cd IN ES_FAC_UNIT.FAC_CD%TYPE) is | DROP PROCEDURE IF EXISTS `SD_USER_P_ADD_USR`;  create procedure P_ADD_FAC(  id_fac_cd varchar(100)) | 1.在创建存储过程时如果存在同名的存储过程,会删除老的存储过程.  Oracle使用create or replace.  MySQL使用先删除老的存储过程,然后再创建新的存储过程.  2. Oracle 存储过程可以定义在package中,也可以定义在Procedures中. 如果定义在包中,一个包中可以包含多个存储过程和方法.如果定义在Procedures中,存储过程中不可以定义多个存储过程.  MySQL 存储过程中不可以定义多个存储过程.  3. Oracle中字符串类型可以使用varchar2.  MySQL 需要使用varchar  4. Oracle中参数varchar长度不是必须的,  MySQL中参数varchar长度是必须的, 比如varchar(100) |
| 2    | 创建函数语句不同                   | CREATE OR REPLACEFUNCTION F_ROLE_FACS_GRP(  ii_role_int_key IN SD_ROLE.ROLE_INT_KEY%TYPE  ) RETURN VARCHAR2 | DROP FUNCTION IF EXISTS `SD_ROLE_F_ROLE_FACS_GRP`;  CREATE FUNCTION `SD_ROLE_F_ROLE_FACS_GRP`(  ii_role_int_key INTEGER(10)  ) RETURNS varchar(1000) | 1.在创建函数时如果存在同名的函数,会删除老的函数.  Oracle使用create or replace.  MySQL使用先删除老的函数,然后再创建新的函数.  2. Oracle 函数可以定义在package中,也可以定义在Functions中. 如果定义在包中,一个包中可以包含多个存储过程和函数.如果定义在Functions中,每个函数只能定义一个函数.  MySQL Functions不可以定义多个函数.  3. Oracle返回值用return.  MySQL返回值用returns. |
| 3    | 传入参数写法不同                   | procedure P_ADD_FAC(  id_fac_cd IN ES_FAC_UNIT.FAC_CD%TYPE)  | create procedure P_ADD_FAC(  (in) id_fac_cd varchar(100))    | 1. Oracle存储过程参数可以定义为表的字段类型.  MySQL存储过程不支持这种定义方法.需要定义变量的实际类型和长度.  2. Oracle 参数类型in/out/inout写在参数名后面.  MySQL 参数类型in/out/inout写在参数名前面.  3. Oracle 参数类型in/out/inout 都必须写.  MySQL 参数类型如果是in,则可以省略. 如果是out或inout则不能省略.  注意: MySQL中指定参数为IN, OUT, 或INOUT 只对PROCEDURE是合法的。（FUNCTION参数总是被认为是IN参数） RETURNS字句只能对FUNCTION做指定，对函数而言这是强制的。它用来指定函数的返回类型，而且函数体必须包含一个RETURN value语句。 |
|      |                                    | function func_name(  gw_id in(out) varchar2 )                | create function func_name(  gw_id varchar（100))             |                                                              |
| 4    | 包的声明方式                       | create or replace package/package body package name          | 拆分成多个存储过程或函数                                     | Oracle可以创建包,包中可以包含多个存储过程和方法.  MySQL没有没有包这个概念,可以分别创建存储过程和方法. 每个存储过程或方法都需要放在一个文件中.  例1: 方法命名  Oracle 中SD_FACILITY_PKG.F_SEARCH_FAC  to MySQL SD_FACILITY_F_SEARCH_FAC  例2: 过程命名  Oracle 中SD_FACILITY_PKG.P_ADD_FAC  to MySQL SD_FACILITY_P_ADD_FAC |
| 5    | 存储过程返回语句不一样             | return;                                                      | LEAVE proc; (proc 代表最外层的begin end)                     | Oracle存储过程和方法都可以使用return退出当前过程和方法.  MySQL存储过程中只能使用leave退出当前存储过程.不可以使用return.  MySQL方法可以使用return退出当前方法. |
| 6    | 存储过程异常处理不一样             | EXCEPTION  WHEN OTHERS THEN  ROLLBACK ;  ov_rtn_msg := c_sp_name\|\|'('\|\| li_debug_pos \|\|'):'\|\|  TO_CHAR(SQLCODE)\|\|': '\|\|SUBSTR(SQLERRM,1,100); | DECLARE EXIT HANDLER FOR SQLEXCEPTION  BEGIN  ROLLBACK ;  set ov_rtn_msg = concat(c_sp_name,'(', li_debug_pos ,'):',  TO_CHAR(SQLCODE),': ',SUBSTR(SQLERRM,1,100));  END; | Oracle : 内部异常不需要定义,在存储过程或函数末尾写上EXCEPTION后,后面的部分即为异常处理的部分. Oracle可以定义自定义异常,自定义异常需要使用raise关键字抛出异常后,才可以在EXCEPTION中捕获.    MySQL: MySQL内部异常也需要先定义,在定义的同时也需要实现异常的功能.  目前MySQL不支持自定义异常. |
| 7    | 过程和函数的声明变量的位置不同     | 声明变量在begin…end体之前                                    | 声明变量在begin...end体内，begin之后其他任何内容之前         |                                                              |
| 8    | NO_DATA_FOUND异常处理              | EXCEPTION  WHEN NO_DATA_FOUND THEN  oi_rtn_cd := 1;  ov_rtn_msg := SD_COMMON.P_GET_MSG('DP-CBM-01100a-016',  li_sub_rtn_cd,  lv_sub_rtn_msg  ); | 使用FOUND_ROWS()代替NO_DATA_FOUND. 详见注释.                 | Oracle中:  NO_DATA_FOUND是游标的一个属性.  当select没有查到数据就会出现 no data found 的异常，程序不会向下执行.    MySQL:  没有NO_DATA_FOUND这个属性.但可是使用FOUND_ROWS()方法得到select语句查询出来的数据.如果FOUND_ROWS()得到的值为0,就进入异常处理逻辑. |
| 9    | 在存储过程中调用存储过程方式的不同 | Procedure_Name(参数);                                        | Call Procedure_Name(参数);                                   | MySQL存储过程调用存储过程，需要使用Call pro_name(参数).  Oracle调用存储过程直接写存储过程名就可以了. |
| 10   | 抛异常的方式不同                   | RAISE Exception_Name;                                        | 见备注                                                       | 详见<<2009002-OTMPPS-Difficult Questions-0001.doc>>中2.5 MySQL异常处 |



## 函数区别

|             | MySQL                                               | Oracle                                             |
| ----------- | --------------------------------------------------- | -------------------------------------------------- |
| concat(A,B) | 支持三个字符串拼接                                  | 只支持两个字符串拼接，想要多个用“\|\|”进行拼接     |
| in()        | in内的数据数量没有限制，可对sql的长度有限制，默认4M | in内最多1000个数据，多于1000用in(1000) or in(1001) |
| 时间格式    | yyyy-mm-dd hh24:mi:ss                               | %Y-%m-%d %H:%i:%s                                  |
| 分页函数    | limit 起始位置，每页记录数                          | rownum                                             |
| 当前时间    | now()                                               | sysdate                                            |
|             |                                                     |                                                    |
|             |                                                     |                                                    |
|             |                                                     |                                                    |
|             |                                                     |                                                    |


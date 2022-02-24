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
		1. 去mysql的安装目录找到my.ini文件
			* 复制 datadir="C:/ProgramData/MySQL/MySQL Server 5.5/Data/"
		2. 卸载MySQL
		3. 删除C:/ProgramData目录下的MySQL文件夹。
		
	3. 配置
		* MySQL服务启动
			1. 手动。
			2. cmd--> services.msc 打开服务的窗口
			3. 使用管理员打开cmd
				* net start mysql : 启动mysql的服务
				* net stop mysql:关闭mysql服务
		* MySQL登录
			1. mysql -uroot -p密码
			2. mysql -hip -uroot -p连接目标的密码
			3. mysql --host=ip --user=root --password=连接目标的密码
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
			* 单行注释: -- 注释内容 或 # 注释内容(mysql 特有) 
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

			* mysql中忘记了root用户的密码？
				1. cmd -- > net stop mysql 停止mysql服务
					* 需要管理员运行该cmd

				2. 使用无验证方式启动mysql服务： mysqld --skip-grant-tables
				3. 打开新的cmd窗口,直接输入mysql命令，敲回车。就可以登录成功
				4. use mysql;
				5. update user set password = password('你的新密码') where user = 'root';
				6. 关闭两个窗口
				7. 打开任务管理器，手动结束mysqld.exe 的进程
				8. 启动mysql服务
				9. 使用新密码登录。
		4. 查询用户：
			-- 1. 切换到mysql数据库
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
		* 注意mysql中，唯一约束限定的列的值可以有多个null(实测至多1个)

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

​	1.导入驱动jar包 mysql-connector-java-5.1.37-bin.jar
​		1.复制mysql-connector-java-5.1.37-bin.jar到项目的libs目录下
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
写代码使用：  Class.forName("com.mysql.jdbc.Driver");
				通过查看源码发现：在com.mysql.jdbc.Driver类中存在静态代码块
				 static {
				        try {
				            java.sql.DriverManager.registerDriver(new Driver());
				        } catch (SQLException E) {
				            throw new RuntimeException("Can't register driver!");
				        }
					}

				注意：mysql5之后的驱动jar包可以省略注册驱动的步骤。
```

#### 功能2—>获取数据库连接

​	方法：static Connection getConnection(String url, String user, String password) 

```
参数：
		1.url：指定连接的路径
			* 语法：jdbc:mysql://ip地址(域名):端口号/数据库名称
			* 例子：jdbc:mysql://localhost:3306/db3
			* 细节：如果连接的是本机mysql服务器，并且mysql服务默认端口是3306，则url可以简写为：jdbc:mysql:///数据库名称
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
1. 导入驱动jar包 mysql-connector-java-5.1.37-bin.jar
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


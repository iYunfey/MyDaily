# 1、查询性能优化

## 使用 Explain 进行分析

Explain 用来分析 SELECT 查询语句，开发人员可以通过分析 Explain 结果来优化查询语句。

比较重要的字段有：

### id执行顺序

执行计划包含的信息 id 有一组数字组成。表示一个查询中各个子查询的执行顺序;

```
id相同执行顺序由上至下。
id不同，id值越大优先级越高，越先被执行。 
id为null时表示一个结果集，不需要使用它查询，常出现在包含union等查询语句中。
```



### select_type查询类型

| id   | select_type  | description                          |
| ---- | ------------ | ------------------------------------ |
| 1    | SIMPLE       | 不包含任何子查询或union等查询        |
| 2    | PRIMARY      | 包含子查询最外层查询就显示为 PRIMARY |
| 3    | SUBQUERY     | 在select或 where字句中包含的查询     |
| 4    | DERIVED      | from字句中包含的查询                 |
| 5    | UNION        | 出现在union后的查询语句中            |
| 6    | UNION RESULT | 从UNION中获取结果集                  |

### type访问类型

```
ALL 扫描全表数据
index 遍历索引
range 索引范围查找
index_subquery 在子查询中使用 ref unique_subquery 在子查询中使用 
eq_ref ref_or_null 对Null进行索引的优化的 
ref fulltext 使用全文索引
ref 使用非唯一索引查找数据
eq_ref 在join查询中使用PRIMARY KEYorUNIQUE NOT NULL索引关联。
```

### possible_keys

可能使用的索引，注意不一定会使用。查询涉及到的字段上若存在索引，则该索引将被 列出来。当该列为 NULL时就要考虑当前的SQL是否需要优化了

### key

显示MySQL在查询中实际使用的索引，若没有使用索引，显示为NULL。

TIPS:查询中若使用了覆盖索引(覆盖索引:索引的数据覆盖了需要查询的所有数据)，则该索引仅出现在key列表中

### key_length

索引长度

### ref

列与索引的比较，表示上述表的连接匹配条件，即哪些列或常量被用于查找索引列上的值

### rows

返回估算的结果集数目，并不是一个准确的值

### extra

执行情况的描述和说明

```
1. Using index 使用覆盖索引
2. Using where 使用了用where子句来过滤结果集
3. Using filesort 使用文件排序，使用非索引列进行排序时出现，非常消耗性能，尽量优化。 4. Using temporary 使用了临时表 sql优化的目标可以参考阿里开发手册
```



## 慢查询日志

用于记录执行时间超过某个临界值的SQL日志，用于快速定位慢查询，为我们的优化做参考。

开启慢查询日志

配置项:slow_query_log

可以使用show variables like ‘slov_query_log’查看是否开启，如果状态值为OFF，可以使用set GLOBAL slow_query_log = on来开启，它会在datadir下产生一个xxx-slow.log的文件。

设置临界时间
 配置项:long_query_time
 查看:show VARIABLES like 'long_query_time'，单位秒
 设置:set long_query_time=0.5 

实操时应该从长时间设置到短的时间，即将最慢的SQL优化掉 

查看日志，一旦SQL超过了我们设置的临界时间就会被记录到xxx-slow.log中



## 优化数据访问

### 1. 减少请求的数据量

- 只返回必要的列：最好不要使用 SELECT * 语句。
- 只返回必要的行：使用 LIMIT 语句来限制返回的数据。
- 缓存重复查询的数据：使用缓存可以避免在数据库中进行查询，特别在要查询的数据经常被重复查询时，缓存带来的查询性能提升将会是非常明显的。

### 2. 减少服务器端扫描的行数

最有效的方式是使用索引来覆盖查询。

## 重构查询方式

### 1. 切分大查询

一个大查询如果一次性执行的话，可能一次锁住很多数据、占满整个事务日志、耗尽系统资源、阻塞很多小的但重要的查询。

```sql
DELETE FROM messages WHERE create < DATE_SUB(NOW(), INTERVAL 3 MONTH);
```

```sql
rows_affected = 0
do {
    rows_affected = do_query(
    "DELETE FROM messages WHERE create  < DATE_SUB(NOW(), INTERVAL 3 MONTH) LIMIT 10000")
} while rows_affected > 0
```

### 2. 分解大连接查询

将一个大连接查询分解成对每一个表进行一次单表查询，然后在应用程序中进行关联，这样做的好处有：

- 让缓存更高效。对于连接查询，如果其中一个表发生变化，那么整个查询缓存就无法使用。而分解后的多个查询，即使其中一个表发生变化，对其它表的查询缓存依然可以使用。
- 分解成多个单表查询，这些单表查询的缓存结果更可能被其它查询使用到，从而减少冗余记录的查询。
- 减少锁竞争；
- 在应用层进行连接，可以更容易对数据库进行拆分，从而更容易做到高性能和可伸缩。
- 查询本身效率也可能会有所提升。例如下面的例子中，使用 IN() 代替连接查询，可以让 MySQL 按照 ID 顺序进行查询，这可能比随机的连接要更高效。

```sql
SELECT * FROM tag
JOIN tag_post ON tag_post.tag_id=tag.id
JOIN post ON tag_post.post_id=post.id
WHERE tag.tag='mysql';
```

```sql
SELECT * FROM tag WHERE tag='mysql';
SELECT * FROM tag_post WHERE tag_id=1234;
SELECT * FROM post WHERE post.id IN (123,456,567,9098,8904);
```

#2、推荐做法

## 2.1、where条件查询

尽量避免的操作：or、表达式、函数运算、**!=或<>操作符**

替代方法：

| 错误示例                                                | 替代方法                                                     |
| ------------------------------------------------------- | ------------------------------------------------------------ |
| select * from user where userid=1 or age =18            | //使用union all<br/>select * from user where userid=1 <br/>union all <br/>select * from user where age = 18 |
|                                                         | //或者分开两条sql写：<br/>select * from user where userid=1<br/>select * from user where age = 18 |
| select userId，name from user where userId like '%123'; | select userId，name from user where userId like '123%';      |
| select * from user where age-1 =10；                    | select * from user where age =11；                           |
| select age,name  from user where age <>18;              | //可以考虑分开两条sql写 <br />select age,name  from user where age <18;<br /> select age,name  from user where age >18; |

**对查询进行优化，应考虑在 where 及 order by 涉及的列上建立索引，尽量避免全表扫描**

```
反例：
select * from user where address ='深圳' order by age ;

正例：
添加索引
alter table user add index idx_address_age (address,age)
```



## 2.2、join连接

**Inner join 、left join、right join，优先使用Inner join，如果是left join，左边表结果尽量小**

- nner join 内连接，在两张表进行连接查询时，只保留两张表中完全匹配的结果集
- left join 在两张表进行连接查询时，会返回左表所有的行，即使在右表中没有匹配的记录。
- right join 在两张表进行连接查询时，会返回右表所有的行，即使在左表中没有匹配的记录。

都满足SQL需求的前提下，推荐优先使用Inner join（内连接），如果要使用left join，左边表数据结果尽量小，如果有条件的尽量放到左边处理。

```sql
反例:
select * from tab1 t1 left join tab2 t2  on t1.size = t2.size where t1.id>2;

正例：
select * from (select * from tab1 where id >2) t1 left join tab2 t2 on t1.size = t2.size;
```



## 2.2、limit分页查询

### 2.2.1、limit 1

如果知道查询结果只有一条或者只要最大/最小一条记录，建议用limit 1

理由：
加上limit 1后,只要找到了对应的一条记录,就不会继续向下扫描了,效率将会大大提高。 当然，**如果name是唯一索引的话，是不必要加上limit 1了**，因为limit的存在主要就是为了防止全表扫描，从而提高性能,如果一个语句本身可以预知不用全表扫描，有没有limit ，性能的差别并不大。

```sql
反例：
select id，name from employee where name='jay'
正例
select id，name from employee where name='jay' limit 1;
```

### 2.2.2、大于100页时的分页查询

```sql
select
	t1.*
from tb1 as t1,
	(select id from tb1 where [condition] limit offset,rows) as t2
where t1.id = t2.id;	
```

### 2.2.3、优化limit分页

我们日常做分页需求时，一般会用 limit 实现，但是当偏移量特别大的时候，查询效率就变得低下。

理由：
当偏移量最大的时候，查询效率就会越低，因为Mysql并非是跳过偏移量直接去取后面的数据，而是先把偏移量+要取的条数，然后再把前面偏移量这一段的数据抛弃掉再返回的。 如果使用优化方案一，返回上次最大查询记录（偏移量），这样可以跳过偏移量，效率提升不少。 方案二使用order by+索引，也是可以提高查询效率的。 方案三的话，建议跟业务讨论，有没有必要查这么后的分页啦。因为绝大多数用户都不会往后翻太多页。

```sql
反例：
select id，name，age from employee limit 10000，10

正例：
//方案一 ：返回上次查询的最大记录(偏移量)
select id，name from employee where id>10000 limit 10.

//方案二：order by + 索引
select id，name from employee order by id  limit 10000，10

//方案三：在业务允许的情况下限制页数：
```


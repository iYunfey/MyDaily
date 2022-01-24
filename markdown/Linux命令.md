

# 零、终端快捷键

| 命令          | 释义                                                         |
| ------------- | ------------------------------------------------------------ |
| Ctrl + a/Home | 切换到命令行开头                                             |
| Ctrl + e/End  | 切换到命令行末尾                                             |
| Ctrl + l      | 清除屏幕内容，效果等同于 clear                               |
| Ctrl + u      | 清除剪切光标之前的内容                                       |
| Ctrl + k      | 剪切清除光标之后的内容                                       |
| Ctrl + y      | 粘贴刚才所删除的字符                                         |
| Ctrl + r      | 在历史命令中查找 (这个非常好用，输入关键字就调出以前的命令了) |
| Ctrl + c      | 终止正在进行的命令                                           |
| ctrl + o      | 重复执行命令                                                 |
| Ctrl + d      | 退出 shell，logout                                           |
| Ctrl + z      | 转入后台运行,但在当前用户退出后就会终止                      |
| Ctrl + t      | 颠倒光标所在处及其之前的字符位置，并将光标移动到下一个字符   |
| Alt + t       | 交换当前与以前单词的位置                                     |
| Alt + d       | 剪切光标之后的词                                             |
| Ctrl+w        | 剪切光标所在处之前的一个词(以空格、标点等为分隔符)           |
| Ctrl+(x u)    | 按住 Ctrl 的同时再先后按 x 和 u，撤销刚才的操作              |
| Ctrl+s        | 锁住终端                                                     |
| Ctrl+q        | 解锁终端                                                     |
| !!            | 重复执行最后一条命令                                         |
| history       | 显示你所有执行过的编号+历史命令。这个可以配合!编辑来执行某某命令 |
| !$            | 显示系统最近的一条参数。比如我先用 cat /etc/sysconfig/network-scripts/ifconfig-eth0，然后我想用 vim 编辑。 一般的做法是先用↑ 显示最后一条命令，然后用 Home 移动到命令最前，删除 cat，然后再输入 vim 命 令。其实完全可以用 vim !$来代替。 |



ls = list
cd = change directory
cp = copy
rm = remove
mv = move
pwd = print work directory
ps = process status
df = disk free
du = disk usage
mkdir = make directory
rmdir = remove directory
su = switch user
chown = change owner
chmod = change mode



-a = all
-l = list
-f = force
-h = -human-readable
-n = number
-u = user
-z = zip

# 一、日志管理命令

## 1.1	cat

### 1.1.1	用途

​		从第一行到最后一行连续打印在控制台，连接⽂件或标准输⼊并打印。这个命令常⽤来显⽰⽂件内容，或者将⼏个⽂件连接起来显⽰，或者从标准输⼊读取内容并显⽰，它常与重定向符号配合使⽤。



### 1.1.2	命令格式

​		cat [选项] [文件]...



### 1.1.3	命令功能

​		1.一次性显示整个文件：cat filename
​		2.创建一个新文件：cat > filename，只能创建新文件，不能编辑已有文件
​		3.将几个文件合并为一个文件：cat file1 file2 file3 > file4



### 1.1.4	命令实例

#### 		1.把 log2012.log 的⽂件内容加上⾏号后输⼊ log2013.log 这个⽂件⾥

​		命令：cat -n log2012.log log2013.log

```
[root@localhost test]# cat log2012.log 
2012-01
2012-02
======
[root@localhost test]# cat log2013.log 
2013-01
2013-02
2013-0
======
[root@localhost test]# cat -n log2012.log log2013.log 
1 2012-01
2 2012-02
3
4
5 ======
6 2013-01
7 2013-02
8
9
10 2013-03
11 ======
[root@localhost test]#
```



#### 		2.把 log2012.log 和 log2013.log 的⽂件内容加上⾏号（空⽩⾏不加）之后将内容附加到 log.log ⾥。 

​        命令：cat -b log2012.log log2013.log log.log

```
[root@localhost test]# cat -b log2012.log log2013.log log.log
1 2012-01
2 2012-02
3 ======
4 2013-01
5 2013-02
6 2013-03
7 ======
[root@localhost test]#
```



#### 		3.把 log2012.log 的⽂件内容加上⾏号后输⼊ log.log 这个⽂件⾥

​		命令：cat -n log2012.log > log.log

```
[root@localhost test]# cat log.log 
[root@localhost test]# cat -n log2012.log > log.log
[root@localhost test]# cat -n log.log 
1 2012-01
2 2012-02
3
4
5 ======
[root@localhost test]#
```



### 1.1.5	tac

​		tac 是将 cat 反写过来，所以他的功能就跟 cat 相反， cat 是由第⼀⾏到最后⼀⾏连续显⽰在萤幕上，⽽ tac 则是由最后⼀⾏到第⼀⾏反向在萤幕上显⽰出来！





## 1.2	more

### 1.2.1	概述

​		more命令，功能类似 cat ，cat命令是整个⽂件的内容从上到下显⽰在屏幕上。 more会以⼀页⼀页的显⽰⽅便使⽤者逐页阅读，⽽最基本的指令就是按空⽩键（space）就往下⼀页显⽰，按 b 键就会往回（back）⼀页显⽰，⽽且还有搜寻字串的功能 。more命令从前向后读取⽂件，因此在启动时就加载整个⽂件。 

### 1.2.2	命令格式

​		more [-dlfpcsu ] [-num ] [+/ pattern] [+ linenum] [file ... ]

### 1.2.3	命令功能

​		more命令和cat的功能⼀样都是查看⽂件⾥的内容，但有所不同的是more可以按页来查看⽂件的内容，还⽀持直接跳转⾏等功能。

### 1.2.4	命令参数

| 参数      | 作用                                                         |
| --------- | ------------------------------------------------------------ |
| +n        | 从笫n⾏开始显⽰                                              |
| -n        | 定义屏幕⼤⼩为n⾏                                            |
| +/pattern | 在每个档案显⽰前搜寻该字串（pattern），然后从该字串前两⾏之后开始显⽰ |
| -c        | 从顶部清屏，然后显⽰                                         |
| -d        | 提⽰“Press space to continue，’q’ to quit（按空格键继续，按q键退出）”，禁⽤响铃功能 |
| -l        | 忽略Ctrl+l（换页）字符                                       |
| -p        | 通过清除窗⼜⽽不是滚屏来对⽂件进⾏换页，与-c选项相似         |
| -s        | 把连续的多个空⾏显⽰为⼀⾏                                   |
| -u        | 把⽂件内容中的下划线去掉                                     |



### 1.2.5	常⽤操作命令

| 操作   | 作用                         |
| ------ | ---------------------------- |
| Enter  | 向下n⾏，需要定义。默认为1⾏ |
| Ctrl+F | 向下滚动⼀屏                 |
| 空格键 | 向下滚动⼀屏                 |
| Ctrl+B | 返回上⼀屏                   |
| =      | 输出当前⾏的⾏号             |
| ：f    | 输出⽂件名和当前⾏的⾏号     |
| V      | 调⽤vi编辑器                 |
| !命令  | 调⽤Shell，并执⾏命令        |
| q      | 退出more                     |



### 1.2.6	命令实例

#### 		实例1：显⽰⽂件中从第3⾏起的内容

​		命令：more +3 log2012.log

```
[root@localhost test]# cat log2012.log 
2012-01
2012-02
2012-03
2012-04-day1
2012-04-day2
2012-04-day3
======
[root@localhost test]# more +3 log2012.log 
2012-03
2012-04-day1
2012-04-day2
2012-04-day3
======
[root@localhost test]#
```

​		

#### 		实例2：从⽂件中查找第⼀个出现"day3"字符串的⾏，并从该处前两⾏开始显⽰输出

​		命令：more +/day3 log2012.log

```
[root@localhost test]# more +/day3 log2012.log 
...skipping
2012-04-day1
2012-04-day2
2012-04-day3
2012-05
2012-05-day1
======
[root@localhost test]#
```



#### 		实例3：设定每屏显⽰⾏数

​		命令：more -5 log2012.log

```
[root@localhost test]# more -5 log2012.log 
2012-01
2012-02
2012-03
2012-04-day1
2012-04-day2
```



#### 		实例4：列⼀个⽬录下的⽂件，由于内容太多，我们应该学会⽤**more**来分页显⽰。这得和管道 **|** 结合起来

​		命令：ls -l | more -5

```
[root@localhost test]# ls -l | more -5
总计 36
-rw-r--r-- 1 root root 308 11-01 16:49 log2012.log
-rw-r--r-- 1 root root 33 10-28 16:54 log2013.log
-rw-r--r-- 1 root root 127 10-28 16:51 log2014.log
lrwxrwxrwx 1 root root 7 10-28 15:18 log_link.log -> log.log
-rw-r--r-- 1 root root 25 10-28 17:02 log.log
-rw-r--r-- 1 root root 37 10-28 17:07 log.txt
drwxr-xr-x 6 root root 4096 10-27 01:58 scf
drwxrwxrwx 2 root root 4096 10-28 14:47 test3
drwxrwxrwx 2 root root 4096 10-28 14:47 test4

每页显⽰5个⽂件信息，按 Ctrl+F 或者 空格键 将会显⽰下5条⽂件信息
```



## 1.3	less

### 1.3.1	概述

​		less ⼯具也是对⽂件或其它输出进⾏分页显⽰的⼯具，应该说是linux正统查看⽂件内容的⼯具，功能极其强⼤。less 的⽤法⽐起 more 更加的有弹性。在 more 的时候，我们并没有办法向前⾯翻，只能往后⾯看，但若使⽤了 less 时，就可以使⽤ [pageup] [pagedown] 等按键的功能来往前往后翻看⽂件，更容易⽤来查看⼀个⽂件的内容！除此之外，在 less ⾥头可以拥有更多的搜索功能，不⽌可以向下搜，也可以向上搜。

​		less命令可以显示⾏号，即less -N log.txt

### 1.3.2	命令格式

​		less [参数] ⽂件



### 1.3.3	命令功能



​		less 与 more 类似，但使⽤ less 可以随意浏览⽂件，⽽ more 仅能向前移动，却不能向后移动，⽽且 less 在查看之前不会加载整个⽂件。



### 1.3.4	命令参数

| 参数列表        | 作用                                                 |
| --------------- | ---------------------------------------------------- |
| -b <缓冲区⼤⼩> | 设置缓冲区的⼤⼩                                     |
| -e              | 当⽂件显⽰结束后，⾃动离开                           |
| -f              | 强迫打开特殊⽂件，例如外围设备代号、⽬录和⼆进制⽂件 |
| -g              | 只标志最后搜索的关键词                               |
| -i              | 忽略搜索时的⼤⼩写                                   |
| -m              | 显⽰类似more命令的百分⽐                             |
| -N              | 显⽰每⾏的⾏号                                       |
| -o <⽂件名>     | 将less 输出的内容在指定⽂件中保存起来                |
| -Q              | 不使⽤警告⾳                                         |
| -s              | 显⽰连续空⾏为⼀⾏                                   |
| -S              | ⾏过长时间将超出部分舍弃                             |
| -x <数字>       | 将“tab”键显⽰为规定的数字空格                        |
| /字符串：       | 向下搜索“字符串”的功能                               |
| ?字符串：       | 向上搜索“字符串”的功能                               |
| n：             | 重复前⼀个搜索（与 / 或 ? 有关）                     |
| N：             | 反向重复前⼀个搜索（与 / 或 ? 有关）                 |
| b               | 向后翻⼀页                                           |
| d               | 向后翻半页                                           |
| h               | 显⽰帮助界⾯                                         |
| Q               | 退出less 命令                                        |
| u               | 向前滚动半页                                         |
| y               | 向前滚动⼀⾏                                         |
| 空格键          | 滚动⼀⾏                                             |
| 回车键          | 滚动⼀页                                             |
| pagedown按键    | 向下翻动⼀页                                         |
| pageup按键      | 向上翻动⼀页                                         |



### 1.3.5	命令实例

#### 		实例1：查看⽂件

​		命令：less log2013.log



#### 		实例2：**ps**查看进程信息并通过**less**分页显⽰

​		命令： ps -ef | less



#### 		实例3:	浏览多个⽂件

​		命令：less log2013.log log2014.log

​		输⼊ ：n后，切换到 log2014.log

​		输⼊ ：p 后，切换到log2013.log



### 1.3.6	附加备注

#### 1.3.6.1	全屏导航

| 组合键   | 作用         |
| -------- | ------------ |
| ctrl + F | 向前移动⼀屏 |
| ctrl + B | 向后移动⼀屏 |
| ctrl + D | 向前移动半屏 |
| ctrl + U | 向后移动半屏 |



#### 1.3.6.2	单⾏导航

| 输入指令 | 作用         |
| -------- | ------------ |
| j        | 向前移动⼀⾏ |
| k        | 向后移动⼀⾏ |



#### 1.3.6.3	其它导航

| 输入指令 | 作用           |
| -------- | -------------- |
| G        | 移动到最后⼀⾏ |
| g        | 移动到第⼀⾏   |
| q / ZZ   | 退出 less 命令 |



#### 1.3.6.4	其它命令

| 输入指令 | 作用                               |
| -------- | ---------------------------------- |
| v        | 使⽤配置的编辑器编辑当前⽂件       |
| h        | 显⽰ less 的帮助⽂档               |
| &pattern | 仅显⽰匹配模式的⾏，⽽不是整个⽂件 |



#### 1.3.6.5	标记导航

当使⽤ less 查看⼤⽂件时，可以在任何⼀个位置作标记，可以通过命令导航到标有特定标记的⽂本位置：

ma - 使⽤ a 标记⽂本的当前位置

'a - 导航到标记 a 处





## 1.4	tail

### 1.4.1	概述

​		tail 命令从指定点开始将⽂件写到标准输出。使⽤tail命令的-f选项可以⽅便的查阅正在改变的⽇志⽂件，tail -f filename会把filename⾥最尾部的内容显⽰在屏幕上,并且不但刷新,使你看到最新的⽂件内容。



### 1.4.2	命令格式

​		tail [必要参数] [可选参数] [⽂件]

### 1.4.3	命令功能

​		⽤于显⽰指定⽂件末尾内容，不指定⽂件时，作为输⼊信息进⾏处理。常⽤查看⽇志⽂件。 

### 1.4.4	命令参数

| 参数列表                        | 作用                         |
| ------------------------------- | ---------------------------- |
| -f                              | 循环读取                     |
| -q                              | 不显⽰处理信息               |
| -v                              | 显⽰详细的处理信息           |
| -c<数⽬>                        | 显⽰的字节数                 |
| -n<⾏数>                        | 显⽰⾏数                     |
| --pid=PID 与-f合⽤              | 表⽰在进程ID,PID死掉之后结束 |
| -q, --quiet, --silent           | 从不输出给出⽂件名的⾸部     |
| -s, --sleep-interval=S 与-f合⽤ | 表⽰在每次反复的间隔休眠S秒  |



### 1.4.5	命令实例

#### 		实例1：显⽰⽂件末尾内容

​		命令：tail -n 5 log2014.log

```
[root@localhost test]# tail -n 5 log2014.log 
2014-09
2014-10
2014-11
2014-12
==============================
[root@localhost test]#
```



#### 		实例2：动态循环查看⽂件内容

​		命令：tail -f test.log

```
[root@localhost ~]# ping 192.168.120.204 > test.log &[1] 11891
[root@localhost ~]# tail -f test.log 
PING 192.168.120.204 (192.168.120.204) 56(84) bytes of data.
64 bytes from 192.168.120.204: icmp_seq=1 ttl=64 time=0.038 ms
64 bytes from 192.168.120.204: icmp_seq=2 ttl=64 time=0.036 ms
64 bytes from 192.168.120.204: icmp_seq=3 ttl=64 time=0.033 ms
64 bytes from 192.168.120.204: icmp_seq=4 ttl=64 time=0.027 ms
64 bytes from 192.168.120.204: icmp_seq=5 ttl=64 time=0.032 ms
64 bytes from 192.168.120.204: icmp_seq=6 ttl=64 time=0.026 ms
64 bytes from 192.168.120.204: icmp_seq=7 ttl=64 time=0.030 ms
64 bytes from 192.168.120.204: icmp_seq=8 ttl=64 time=0.029 ms
64 bytes from 192.168.120.204: icmp_seq=9 ttl=64 time=0.044 ms
64 bytes from 192.168.120.204: icmp_seq=10 ttl=64 time=0.033 ms
64 bytes from 192.168.120.204: icmp_seq=11 ttl=64 time=0.027 ms
[root@localhost ~]#

ping 192.168.120.204 > test.log & //在后台ping远程主机。并输出⽂件到test.log；这种做法也使⽤于⼀个以上的档案监视。⽤Ctrl＋c来终⽌。
```

​		

#### 		实例3：从第5⾏开始显⽰⽂件

​		命令：tail -n +5 log2014.log

```
[root@localhost test]# cat log2014.log 
2014-01
2014-02
2014-03
2014-04
2014-05
2014-06
2014-07
2014-08
2014-09
2014-10
2014-11
2014-12
==============================
[root@localhost test]# tail -n +5 log2014.log
2014-05
2014-06
2014-07
2014-08
2014-09
2014-10
2014-11
2014-12
==============================
```



## 1.5	head

### 1.5.1	概述

​		head 与 tail 就像它的名字⼀样的浅显易懂，它是⽤来显⽰开头或结尾某个数量的⽂字区块，head ⽤来显⽰档案的开头⾄标准输出中，⽽ tail 想当然就是看档案的结尾。

 

### 1.5.2	命令格式

​		head [参数]... [⽂件]... 



### 1.5.3	命令功能

​		head ⽤来显⽰档案的开头⾄标准输出中，默认head命令打印其相应⽂件的开头10⾏。



### 1.5.4	命令参数

| 参数列表 | 作用       |
| -------- | ---------- |
| -q       | 隐藏文件名 |
| -v       | 显示文件名 |
| -c<字节> | 显示字节数 |
| -n<行数> | 显示的行数 |



### 1.5.5	命令实例

#### 		实例1：显⽰⽂件的前n⾏

​		命令：head -n 5 log2014.log

```
[root@localhost test]# cat log2014.log 
2014-01
2014-02
2014-03
2014-04
2014-05
2014-06
2014-07
2014-08
2014-09
2014-10
2014-11
2014-12
==============================
[root@localhost test]# head -n 5 log2014.log 
2014-01
2014-02
2014-03
2014-04
2014-05[root@localhost test]#
```

#### 		实例2：显⽰⽂件前n个字节

​		命令：head -c 20 log2014.log

```
[root@localhost test]# head -c 20 log2014.log
2014-01
2014-02
2014
[root@localhost test]#
```



#### 		实例3：⽂件的除了最后n个字节以外的内容

​		命令：head -c -32 log2014.log

```
[root@localhost test]# head -c -32 log2014.log
2014-01
2014-02
2014-03
2014-04
2014-05
2014-06
2014-07
2014-08
2014-09
2014-10
2014-11
2014-12[root@localhost test]#
```



#### 		实例4：输出⽂件除了最后n⾏的全部内容

​		命令：head -n -6 log2014.log

```
[root@localhost test]# head -n -6 log2014.log
2014-01
2014-02
2014-03
2014-04
2014-05
2014-06
2014-07[root@localhost test]#
```



## 1.6	grep

### 1.6.1	概述

​		Linux系统中grep命令是⼀种强⼤的⽂本搜索⼯具，它能使⽤正则表达式搜索⽂本，并把匹 配的⾏打印出来。grep全称是Global Regular Expression Print，表⽰全局正则表达式版本，它的使⽤权限是所有⽤户。

​		grep的⼯作⽅式是这样的，它在⼀个或多个⽂件中搜索字符串模板。如果模板包括空格，则必须被引⽤，模板后的所有字符串被看作⽂件名。搜索的结果被送到标准输出，不影响原⽂件内容。

​		grep可⽤于shell脚本，因为grep通过返回⼀个状态值来说明搜索的状态，如果模板搜索成功，则返回0，如果搜索不成功，则返回1，如果搜索的⽂件不存在，则返回2。我们利⽤这些返回值就可进⾏⼀些⾃动化的⽂本处理工作。



### 1.6.2	命令格式

​		grep [option] pattern file



### 1.6.3	命令功能

​		⽤于过滤/搜索的特定字符。可使⽤正则表达式能多种命令配合使⽤，使⽤上⼗分灵活。 



### 1.6.4	命令参数

| 参数列表     | 全称                            | 作用                                                         |
| ------------ | ------------------------------- | ------------------------------------------------------------ |
| -a           | text #                          | 不要忽略⼆进制的数据                                         |
| -A<显⽰⾏数> | after-context=<显⽰⾏数>        | 除了显⽰符合范本样式的那⼀列之外，并显⽰该⾏之后的内容       |
| -b           | byte-offset                     | 在显⽰符合样式的那⼀⾏之前，标⽰出该⾏第⼀个字符的编号       |
| -B<显⽰⾏数> | before-context=<显⽰⾏数>       | 除了显⽰符合样式的那⼀⾏之外，并显⽰该⾏之前的内容           |
| -c           | count                           | 计算符合样式的列数                                           |
| -C<显⽰⾏数> | context=<显⽰⾏数>或-<显⽰⾏数> | 除了显⽰符合样式的那⼀⾏之外，并显⽰该⾏之前后的内容         |
| -d <动作>    | directories=<动作>              | 当指定要查找的是⽬录⽽⾮⽂件时，必须使⽤这项参数，否则grep指令将回报信息并停⽌动作 |
| -e<范本样式> | regexp=<范本样式>               | 指定字符串做为查找⽂件内容的样式                             |
| -E           | extended-regexp                 | 将样式为延伸的普通表⽰法来使⽤                               |
| -f<规则⽂件> | fifile=<规则⽂件>               | 指定规则⽂件，其内容含有⼀个或多个规则样式，让grep查找符合规则条件的⽂件内容，格式为每⾏⼀个规则样式 |
| -F           | fifixed-regexp                  | 将样式视为固定字符串的列表                                   |
| -G           | basic-regexp                    | 将样式视为普通的表⽰法来使⽤                                 |
| -h           | no-fifilename                   | 显⽰符合样式的那⼀⾏之前，不标⽰该⾏所属的⽂件名称           |
| -H           | with-fifilename                 | 在显⽰符合样式的那⼀⾏之前，表⽰该⾏所属的⽂件名称           |
| -i           | ignore-case                     | 忽略字符⼤⼩写的差别                                         |
| -l           | fifile-with-matches             | 列出⽂件内容符合指定的样式的⽂件名称                         |
| -L           | fifiles-without-match           | 列出⽂件内容不符合指定的样式的⽂件名称                       |
| -n           | line-number                     | 在显⽰符合样式的那⼀⾏之前，标⽰出该⾏的列数编号             |
| -q           | quiet或--silent                 | 不显⽰任何信息                                               |
| -r           | recursive                       | 此参数的效果和指定“-d recurse”参数相同                       |
| -s           | no-messages                     | 不显⽰错误信息                                               |
| -v           | revert-match                    | 显⽰不包含匹配⽂本的所有⾏                                   |
| -V           | version                         | 显⽰版本信息                                                 |
| -w           | word-regexp                     | 只显⽰全字符合的列                                           |
| -x           | line-regexp                     | 只显⽰全列符合的列                                           |
| -y           |                                 | 此参数的效果和指定“-i”参数相同                               |



### 1.6.5	规则表达式

grep的规则表达式:

```
^ 			#锚定⾏的开始 如：'^grep'匹配所有以grep开头的⾏。 
$ 			#锚定⾏的结束 如：'grep$'匹配所有以grep结尾的⾏。 
. 			#匹配⼀个⾮换⾏符的字符 如：'gr.p'匹配gr后接⼀个任意字符，然后是p。 
* 			#匹配零个或多个先前字符 如：'*grep'匹配所有⼀个或多个空格后紧跟grep的⾏。 
.* 			#⼀起⽤代表任意字符。 
[] 			#匹配⼀个指定范围内的字符，如'[Gg]rep'匹配Grep和grep。 
[^] 		#匹配⼀个不在指定范围内的字符，如：'[^A-FH-Z]rep'匹配不包含A-R和T-Z的⼀个字母开头，紧跟rep的⾏。 
\(..\) 	#标记匹配字符，如'\(love\)'，love被标记为1。 
\< 			#锚定单词的开始，如:'\<grep'匹配包含以grep开头的单词的⾏。 
\> 			#锚定单词的结束，如'grep\>'匹配包含以grep结尾的单词的⾏。 
x\{m\}  #重复字符x，m次，如：'0\{5\}'匹配包含5个o的⾏。 
x\{m,\} 	#重复字符x,⾄少m次，如：'o\{5,\}'匹配⾄少有5个o的⾏。 
x\{m,n\}  #重复字符x，⾄少m次，不多于n次，如：'o\{5,10\}'匹配5--10个o的⾏。 
\w 			#匹配⽂字和数字字符，也就是[A-Za-z0-9]，如：'G\w*p'匹配以G后跟零个或多个⽂字或数字字符，然后是p。 
\W 				#\w的反置形式，匹配⼀个或多个⾮单词字符，如点号句号等。 
\b 				#单词锁定符，如: '\bgrep\b'只匹配grep。 


POSIX字符:
为了在不同国家的字符编码中保持⼀⾄，POSIX(The Portable Operating System Interface)增加了特殊的字符类，如[:alnum:]是[AZa-z0-9]的另⼀个写法。要把它们放到[]号内才能成为正则表达式，如[A- Za-z0-9]或[[:alnum:]]。在linux下的grep除fgrep外，都⽀持POSIX的字符类。
[:alnum:] #⽂字数字字符 
[:alpha:] #⽂字字符 
[:digit:] #数字字符 
[:graph:] #⾮空字符（⾮空格、控制字符） 
[:lower:] #⼩写字符 
[:cntrl:] #控制字符 
[:print:] #⾮空字符（包括空格） 
[:punct:] #标点符号 
[:space:] #所有空⽩字符（新⾏，空格，制表符） 
[:upper:] #⼤写字符 
[:xdigit:] #⼗六进制数字（0-9，a-f，A-F）
```





### 1.6.6	命令实例

#### 		实例1：查找指定进程

​		命令：ps -ef|grep svn

```
[root@localhost ~]# ps -ef|grep svn
root 4943 1 0 Dec05 ? 00:00:00 svnserve -d -r /opt/svndata/grape/
root 16867 16838 0 19:53 pts/0 00:00:00 grep svn
[root@localhost ~]#

第⼀条记录是查找出的进程；第⼆条结果是grep进程本⾝，并⾮真正要找的进程
```



#### 		实例2：查找指定进程个数

​		命令：ps -ef|grep svn -c

​					ps -ef|grep -c svn

```
[root@localhost ~]# ps -ef|grep svn -c
2
[root@localhost ~]# ps -ef|grep -c svn 
2
[root@localhost ~]#
```



#### 		实例3：从⽂件中读取关键词进⾏搜索

​		命令：cat test.txt | grep -f test2.txt

```
[root@localhost test]# cat test.txt 
hnlinux
peida.cnblogs.com
ubuntu
ubuntu linux
redhat
Redhat
linuxmint
[root@localhost test]# cat test2.txt 
linux
Redhat
[root@localhost test]# cat test.txt | grep -f test2.txt
hnlinux
ubuntu linux
Redhat
linuxmint
[root@localhost test]#

输出test.txt⽂件中含有从test2.txt⽂件中读取出的关键词的内容⾏
```





#### 		实例3：从⽂件中读取关键词进⾏搜索 且显⽰⾏号

​		命令：cat test.txt | grep -nf test2.txt

```
[root@localhost test]# cat test.txt 
hnlinux
peida.cnblogs.com
ubuntu
ubuntu linux
redhat
Redhat
linuxmint
[root@localhost test]# cat test2.txt 
linux
Redhat
[root@localhost test]# cat test.txt | grep -nf test2.txt
1:hnlinux
4:ubuntu linux
6:Redhat
7:linuxmint
[root@localhost test]#

输出test.txt⽂件中含有从test2.txt⽂件中读取出的关键词的内容⾏，并显⽰每⼀⾏的⾏号
```



#### 		实例5：从⽂件中查找关键词

​		命令：grep 'linux' test.txt

```
[root@localhost test]# grep 'linux' test.txt 
hnlinux
ubuntu linux
linuxmint
[root@localhost test]# grep -n 'linux' test.txt 
1:hnlinux
4:ubuntu linux
7:linuxmint
[root@localhost test]#
```

​		

#### 		实例6：从多个⽂件中查找关键词

​		命令：grep 'linux' test.txt test2.txt

```
[root@localhost test]# grep -n 'linux' test.txt test2.txt 
test.txt:1:hnlinux
test.txt:4:ubuntu linux
test.txt:7:linuxmint
test2.txt:1:linux
[root@localhost test]# grep 'linux' test.txt test2.txt 
test.txt:hnlinux
test.txt:ubuntu linux
test.txt:linuxmint
test2.txt:linux
[root@localhost test]#

多⽂件时，输出查询到的信息内容⾏时，会把⽂件的命名在⾏最前⾯输出并且加上":"作为标⽰符
```



#### 		实例7：grep不显⽰本⾝进程

​		命令：ps aux|grep \[s]sh

​					ps aux | grep ssh | grep -v "grep"

```
[root@localhost test]# ps aux|grep ssh
root 2720 0.0 0.0 62656 1212 ? Ss Nov02 0:00 /usr/sbin/sshd
root 16834 0.0 0.0 88088 3288 ? Ss 19:53 0:00 sshd: root@pts/0 
root 16901 0.0 0.0 61180 764 pts/0 S+ 20:31 0:00 grep ssh
[root@localhost test]# ps aux|grep \[s]sh
root 2720 0.0 0.0 62656 1212 ? Ss Nov02 0:00 /usr/sbin/sshd
root 16834 0.0 0.0 88088 3288 ? Ss 19:53 0:00 sshd: root@pts/0 
[root@localhost test]# ps aux | grep ssh | grep -v "grep"
root 2720 0.0 0.0 62656 1212 ? Ss Nov02 0:00 /usr/sbin/sshd
root 16834 0.0 0.0 88088 3288 ? Ss 19:53 0:00 sshd: root@pts/0
```



#### 		实例8：找出已u开头的⾏内容

​		命令：cat test.txt |grep ^u

```
[root@localhost test]# cat test.txt |grep ^u
ubuntu
ubuntu linux
[root@localhost test]#
```



#### 		实例9：输出⾮u开头的⾏内容

​		命令：cat test.txt |grep ^u

```
[root@localhost test]# cat test.txt |grep ^[^u]
hnlinux
peida.cnblogs.com
redhat
Redhat
linuxmint
[root@localhost test]#
```



#### 		实例10：输出以hat结尾的⾏内容

​		命令：cat test.txt |grep hat$

```
[root@localhost test]# cat test.txt |grep hat$
redhat
Redhat
[root@localhost test]#
```



#### 		实例11：输出正确IP地址

​		命令：grep "[0-9]\{1,3\}\.[0-9]\{1,3\}\.[0-9]\{1,3\}\.[0-9]\{1,3\}"

```
[root@localhost test]# ifconfig eth0|grep "[0-9]\{1,3\}\.[0-9]\{1,3\}\.[0-9]\{1,3\}\.[0-9]\{1,3\}"
 inet addr:192.168.120.204 Bcast:192.168.120.255 Mask:255.255.255.0
[root@localhost test]# ifconfig eth0|grep -E "([0-9]{1,3}\.){3}[0-9]"
 inet addr:192.168.120.204 Bcast:192.168.120.255 Mask:255.255.255.0
[root@localhost test]#
```





#### 		实例12：显⽰包含ed或者at字符的内容⾏

​		命令：cat test.txt |grep -E "ed|at"

```
[root@localhost test]# cat test.txt |grep -E "peida|com"
peida.cnblogs.com
[root@localhost test]# cat test.txt |grep -E "ed|at"
redhat
Redhat
[root@localhost test]#
```





#### 		实例13：显⽰当前⽬录下⾯以.txt 结尾的⽂件中的所有包含每个字符串⾄少有7个连续⼩写字符的字符串的⾏

​		命令：grep '[a-z]\{7\}' *.txt

```
[root@localhost test]# grep '[a-z]\{7\}' *.txt
test.txt:hnlinux
test.txt:peida.cnblogs.com
test.txt:linuxmint
[root@localhost test]#
```





#### 		实例14:	输出匹配grep条件的数据⾏的前后各10⾏：（可能满⾜grep条件的有好多，所以可能输出好⼏个21⾏为⼀组的数据）

​		命令：grep -n -C10 'R0619' caps-biz.txt



#### 		实例15:	输出最后⼀次匹配grep条件的数据⾏的前后各10⾏：(借助tail命令取最后⼏⾏)

​		命令：grep -n -C10 'R0619' caps-biz.txt | tail -n 21 

​		命令：grep -n -C10 'R0619' caps-biz.txt | tail -n 21 | less（借助less命令的/pattern可以⾼亮搜索词）





## 1.7	综合使用

tail -n 10 test.log 查询⽇志尾部最后10⾏的⽇志;

tail -n +10 test.log 查询10⾏之后的所有⽇志;

head -n 10 test.log 查询⽇志⽂件中的头10⾏⽇志;

head -n -10 test.log 查询⽇志⽂件除了最后10⾏的其他所有⽇志; 



### 1)按⾏号查看---过滤出关键字附近的⽇志

通常我们⽤grep拿到的⽇志很少,我们需要查看关键信息附

近的⽇志.

⾸先: cat -n log.txt |grep "jiang" ([root@vm1 ~]# grep -n"jiang" log.txt也可以)

注意：less命令的显示⾏号是⼤写的N，即less -N log.txt

得到关键⽇志的⾏号，⽐如这⾥找到包含“jiang”的记录很多⾏，假设我们要的结果是第18⾏附近的⽇志。

此时如果我想查看这个关键字前5⾏和后5⾏的⽇志（当然也可以使⽤上⾯的grep命令加-C参数完成）:

cat -n log.txt |tail -n +13|head -n 10

tail -n +13表示查询13⾏之后的⽇志

head -n 10 则表示在前⾯的查询结果⾥再查前10条记录

如果想直接看最新的⽇志，那么⾏号最⼤的就是。可以根据⽇志记录时间等条件筛选⼀下，或者使⽤less命令，不

断地翻⻚(end定位到最后)

如果满⾜grep条件的⽇志很多，那么可以结合tail命令只显示最后⼏⾏。



如果我们查找的⽇志很多,打印在屏幕上不⽅便查看,有两个⽅法: 

(1)使⽤more和less命令,如: cat -n test.log |grep "jiang"|more 这样就分⻚打印了,通过空格键翻⻚，回⻋键下⼀

⾏。

(2)使⽤ >xxx.txt将其保存到⽂件中,到时可以拿下这个⽂件分析.如:

cat -n test.log |grep "jiang" >xxx.txt



### 2)根据⽇期来查看

⼀般在⽇志系统中都会记录打印⽇志的时间，通常我们⾮常需要查找指定时间端的⽇志：

特别说明:该命令中的两个⽇期值必须是⽇志⽂件中包含的值,否则该命令⽆效.

sed -n '/2017-06-04 14W06W27/,/2017-06-04 14W06W34/p' test.log

sed -n '/2017/06/04 14W06W27/,/2017/06/04 14W06W34/p'test.log

上述命令⽆效，需要转义：

sed -n '/2017\/06\/04 14W06W27/,/2017\/06\/0414W06W34/p' test.log



关于⽇期打印，可以先 grep '2014-12-17 16W17W20' test.log来确定⽇志中是否有该时间点，以确保上⼀步可以拿到⽇志。这个根据时间段查询⽇志是⾮常有⽤的命令。

特别说明:该命令中的两个⽇期值必须是⽇志⽂件中包含的值，否则该命令⽆效

### 1.7.3、根据关键词查看

#### 在前面关键词的基础上继续筛选

```
cat tkbroker_insure_2021-12-28.log | grep '经代通核保接口返回结果明文' -C 20 | grep '王鑫' -C 20

grep '经代通核保接口返回结果明文' -n -C 20 tkbroker_insure_2021-12-28.log | tail -n 20
```



# 二、CPU相关、进程

## 1、 查看cpu硬件配置

```
less /proc/cpuinfo 

uname -a                查看内核/操作系统/CPU信息
head -n 1 /etc/issue    查看操作系统版本
less /proc/cpuinfo      查看CPU信息
hostname                查看计算机名
```

## 2、 top  命令

实时显示各种系统资源使用情况及进程状态

```
详细命令参数：
h: 显示帮助
c：显示详细的命令参数
M：按照占用内存大小（%MEM 列）对进程排序；
P：按照 CPU 使用率( %CPU 列）对进程排序；
u：显示指定用户的进程。默认显示所有进程；
T：根据累计运行时间排序
```

某一个进程下的线程资源使用情况：

```
top -p {pid} -H  
```

查看系统load、cpu资源的其它命令

```
 mpstat 1 （汇总的）
 mpstat -P ALL 1  （汇总的+每个cpu的）
 w
 uptime
 top -H 和 ps -efL/ -Tel  显示 线程
```

## 3、统计一个进程下的线程数

```
cat /proc/${pid}/status

返回：
...省略
Threads:	74
....省略

其它命令：
top -bH -d 3 -p {pid}
```

pstree （以树状图的方式展现进程之间的派生关系）
[Linux命令大全](http://man.linuxde.net/pstree)

```
pstree -p
pstree -p {pid} | wc -l
```

pstack （显示每个进程的栈跟踪），也可以查看一个进程下的线程总数

```
pstack {pid} 
// 输出第一行
pstack {pid} | head -1
```

## 4、查看所有进程

```
ps -ef
ps -ef|grep java
```

5、对于Java应用从操作系统层面观察，就只有进程和线程两个指标，任何东西在操作系统层面都是以文件的形式存储的，进程也不例外。Linux上部署一个Tomcat程序产生一个进程，这个进程所有的东西都在这个目录下

ll /proc/{pid}/

```
## 可以查看所有的socket连接
ll /proc/{pid}/fd | grep socket   
```

## 5、ulimit -a （显示当前的各种用户进程限制）

[linux修改max user processes limits](http://blog.csdn.net/bbaiggey/article/details/51004817)    

```
ulimit -a

core file size (blocks, -c) 100
data seg size (kbytes, -d) unlimited
file size (blocks, -f) unlimited
pending signals (-i) 15237
max locked memory (kbytes, -l) 64
max memory size (kbytes, -m) unlimited
open files (-n) 1024
pipe size (512 bytes, -p) 8
POSIX message queues (bytes, -q) 819200
stack size (kbytes, -s) 8192
cpu time (seconds, -t) unlimited
max user processes (-u) 15237
virtual memory (kbytes, -v) unlimited
file locks (-x) unlimited
```

```
输出的每一行由资源名字、（单位，ulimit命令的参数）、软限制组成。详细解释：
参数 描述
core file size core文件的最大值为100 blocks，
data seg size 进程的数据段可以任意大
file size 文件可以任意大
pending signals 最多有15237个待处理的信号
max locked memory 一个任务锁住的物理内存的最大值为64kB
max memory size 一个任务的常驻物理内存的最大值
open files 一个任务最多可以同时打开1024的文件
pipe size 管道的最大空间为4096字节
POSIX message queues POSIX的消息队列的最大值为819200字节
stack size 进程的栈的最大值为8192字节
cpu time 进程使用的CPU时间
max user processes 当前用户同时打开的进程(包括线程)的最大个数为15237
virtual memory 没有限制进程的最大地址空间
file locks 所能锁住的文件的最大个数没有限制
```

# 三、内存相关 

## 1、vmstat

Virtual Memory Statistics，统计进程、内存、io、cpu等的活动信息。对于多CPU系统，vmstat打印的是所有CPU的平均输出

```
procs
r: 运行队列中进程数量
b: 等待IO的进程数量
memory
swpd: 使用虚拟内存大小
free: 可用内存大小
buff: 用作缓冲的内存大小
cache: 用作缓存的内存大小
swap
si: 每秒从交换区写到内存的大小
so: 每秒从内存写入交换区的大小
io
bi: 每秒读取的块数（现在的Linux版本块的大小为1024bytes）
bo: 每秒写入的块数
system
in: 每秒中断数，包括时钟中断
cs: 每秒上下文切换数
cpu（以百分比表示）
us: 用户进程执行时间(user time)
sy: 系统进程执行时间(system time)
id: 空闲时间(包括IO等待时间)
wa: 等待IO时间
```

**注意：排查问题时，要特别关注r的值，如果长时间超过cpu核数2倍，说明系统的负载很重，cpu已经无法及时处理堆积任务。**

## 2、sar -r

## 3、cat /proc/meminfo 

## 4、free -m

# 四、IO及网络

## 1、 tsar --traffic：显示网络带宽

## 2、 netstat 

一般用于检验本机各端口的网络连接情况。netstat是在内核中访问网络及相关信息的程序，它能提供TCP连接，TCP和UDP监听，进程内存管理的相关报告。

**命令参数：**

```
-a或–all 显示所有连线中的Socket。
-A<网络类型>或–<网络类型> 列出该网络类型连线中的相关地址。
-c或–continuous 持续列出网络状态。
-C或–cache 显示路由器配置的快取信息。
-e或–extend 显示网络其他相关信息。
-F或–fib 显示FIB。
-g或–groups 显示多重广播功能群组组员名单。
-h或–help 在线帮助。
-i或–interfaces 显示网络界面信息表单。
-l或–listening 显示监控中的服务器的Socket。
-M或–masquerade 显示伪装的网络连线。
-n或–numeric 直接使用IP地址，而不通过域名服务器。
-N或–netlink或–symbolic 显示网络硬件外围设备的符号连接名称。
-o或–timers 显示计时器。
-p或–programs 显示正在使用Socket的程序识别码和程序名称。
-r或–route 显示Routing Table。
-s或–statistice 显示网络工作信息统计表。
-t或–tcp 显示TCP传输协议的连线状况。
-u或–udp 显示UDP传输协议的连线状况。
-v或–verbose 显示指令执行过程。
-V或–version 显示版本信息。
-w或–raw 显示RAW传输协议的连线状况。
-x或–unix 此参数的效果和指定”-A unix”参数相同。
–ip或–inet 此参数的效果和指定”-A inet”参数相同。
```

输出结果：

```
一个是Active Internet connections，称为有源TCP连接，其中"Recv-Q"和"Send-Q"指的是接收队列和发送队列。这些数字一般都应该是0。如果不是则表示软件包正在队列中堆积。这种情况只能在非常少的情况见到。

另一个是Active UNIX domain sockets，称为有源Unix域套接口(和网络套接字一样，但是只能用于本机通信，性能可以提高一倍)。

Proto显示连接使用的协议,RefCnt表示连接到本套接口上的进程号,Types显示套接口的类型,State显示套接口当前的状态,Path表示连接到套接口的其它进程使用的路径名。

状态说明：
LISTEN：侦听来自远方的TCP端口的连接请求
SYN-SENT：再发送连接请求后等待匹配的连接请求（如果有大量这样的状态包，检查是否中招了）
SYN-RECEIVED：再收到和发送一个连接请求后等待对方对连接请求的确认（如有大量此状态，估计被flood攻击了）
ESTABLISHED：代表一个打开的连接
FIN-WAIT-1：等待远程TCP连接中断请求，或先前的连接中断请求的确认
FIN-WAIT-2：从远程TCP等待连接中断请求
CLOSE-WAIT：等待从本地用户发来的连接中断请求
CLOSING：等待远程TCP对连接中断的确认
LAST-ACK：等待原来的发向远程TCP的连接中断请求的确认（不是什么好东西，此项出现，检查是否被攻击）
TIME-WAIT：等待足够的时间以确保远程TCP接收到连接中断请求的确认
CLOSED：没有任何连接状态
```

找出运行在指定端口的进程

```
netstat -anpt | grep ':20130'
netstat -nat | grep "172.16.49.161:20130"
```

其它使用场景

```
netstat -nat |awk '{print $6}'|sort|uniq -c    不同网络状态结果统计
netstat -anop | grep 6379     应用连接Redis情况
netstat -pt             输出中显示 PID 和进程名称
netstat -s              查看网络统计信息
netstat -nu             显示当前UDP连接状况
netstat -nt             显示当前TCP连接状况
netstat -i              显示网卡列表
```

## 3、 iostat

iostat是I/O statistics（输入/输出统计）的缩写，主要的功能是对系统的磁盘I/O操作进行监视。它的输出主要显示磁盘读写操作的统计信息，同时也会给出CPU使用情况。同vmstat一样，iostat也不能对某个进程进行深入分析，仅对系统的整体情况进行分析。

**命令参数：**

```
-c 显示CPU使用情况
-d 显示磁盘使用情况
-k 以 KB 为单位显示
-m 以 M 为单位显示
-N 显示磁盘阵列(LVM) 信息
-n 显示NFS 使用情况
-p[磁盘] 显示磁盘和分区的情况
-t 显示终端和CPU的信息
-x 显示详细信息
-V 显示版本信息
```

输出结果：

```
cpu属性值说明：

%user：CPU处在用户模式下的时间百分比。
%nice：CPU处在带NICE值的用户模式下的时间百分比。
%system：CPU处在系统模式下的时间百分比。
%iowait：CPU等待输入输出完成时间的百分比。
%steal：管理程序维护另一个虚拟处理器时，虚拟CPU的无意识等待时间百分比。
%idle：CPU空闲时间百分比。

备注：如果%iowait的值过高，表示硬盘存在I/O瓶颈，%idle值高，表示CPU较空闲，如果%idle值高但系统响应慢时，有可能是CPU等待分配内存，此时应加大内存容量。%idle值如果持续低于10，那么系统的CPU处理能力相对较低，表明系统中最需要解决的资源是CPU。


disk属性值说明：（iostat -x）

rrqm/s:  每秒进行 merge 的读操作数目。即 rmerge/s
wrqm/s:  每秒进行 merge 的写操作数目。即 wmerge/s
r/s:  每秒完成的读 I/O 设备次数。即 rio/s
w/s:  每秒完成的写 I/O 设备次数。即 wio/s
rsec/s:  每秒读扇区数。即 rsect/s
wsec/s:  每秒写扇区数。即 wsect/s
rkB/s:  每秒读K字节数。是 rsect/s 的一半，因为每扇区大小为512字节。
wkB/s:  每秒写K字节数。是 wsect/s 的一半。
avgrq-sz:  平均每次设备I/O操作的数据大小 (扇区)。
avgqu-sz:  平均I/O队列长度。
await:  平均每次设备I/O操作的等待时间 (毫秒)。
svctm: 平均每次设备I/O操作的服务时间 (毫秒)。
%util:  一秒中有百分之多少的时间用于 I/O 操作，即被io消耗的cpu百分比

备注：如果 %util 接近 100%，说明产生的I/O请求太多，I/O系统已经满负荷，该磁盘可能存在瓶颈。如果 svctm 比较接近 await，说明 I/O 几乎没有等待时间；如果 await 远大于 svctm，说明I/O 队列太长，io响应太慢，则需要进行必要优化。如果avgqu-sz比较大，也表示有当量io在等待。
```

定时显示所有信息（每隔 2秒刷新显示，且显示3次）

```
iostat 2 3
```

以kB为单位显示所有信息

```
iostat -k 3
```

## 4、sar -b：磁盘状态历史记录

# 五、文件

## 5.1、 lsof (一切皆文件)

[命令详情](http://man.linuxde.net/lsof)

查看你进程开打的文件，打开文件的进程，进程打开的端口(TCP、UDP)

```
// 查看sys.log文件被哪个进程打开
lsof sys.log 
// 查看端口被哪个进程占用
lsof  -i：端口号      
// 查看各个进程打开的文件数量
lsof -n |awk '{print $2} " " $3'|sort|uniq -c |sort -nr|more
```

## 5.2、 df

```
df -hl
磁盘的使用情况
```

## 5.3、 du

```
du -hl
当前目录下的最叶子目录的大小
du -sch * 
当前目录下的各目录的大小
```

## 5.4、 find

### 5.4.1、以名称为条件

涉及参数-name，-iname

```
find . -name sys.log  
当前目录下查找名称为sys.log的文件

find / -name sys.log  
在根目录下查找名称为sys.log的文件

find . -name "sy*log"
查找文件支持通配符

find . -iname 'SY*LOG'
当前目录下查找名称为SY*LOG的文件，忽略大小写
```

### 5.4.2、以权限为条件

-perm参数

```
find ./ -perm 777
./test
./sort.txt
```

#### 5.4.3、以文件类型为条件

涉及参数-type，例如要查找当前目录下的符号链接文件：

```
find ./ -type l
./test
ls -al test
lrwxrwxrwx 1 hyb hyb 8 11月 24 10:10 test -> home.zip
```

主要类型有：

```
f 普通文件
d 目录
b 块设备文件
c 字符设备文件
l 符号链接
s 套接字
p 管道文件
```

#### 5.4.4、以文件大小为条件

涉及参数-size，例如：

```
find ./ -size 1k  #查找当前目录下小于1k的文件
./test
./sort4.txt
./sort2.txt
./sort3.txt
./test.sh
./sort.txt

find -size +1M  #查找当前目录下大于1M的文件
./test.zip

find . -size +20M
查找当前目录下大小超过20M的文件  

find . -size +20M | xargs ls -lh
查找当前目录下大小超过20M的文件，并计算文件大小

find -type f -printf '%s %p\n' |sort -nr | head  
查找占用空间最大的10个文件

```

常用单位有：

- k 千字节
- M 兆字节
- G 吉字节
- c 字节
- b 块，一般为512字节
- w 字大小，两个字节

#### 5.4.5、以归属为条件

涉及参数-user，-nouser，-group，-nogroup等，例如：

```
find ./ -user root  #查找当前目录下root用户的文件
find ./ -nouser   #查找当前目录下root用户的被删除的文件
```

-group，-nogroup类似的用法，只不过条件是用户组。

#### 5.4.6、以时间为条件

涉及参数-mtime，-atime，-ctime，-newer，-anewer，-cnewer，-amin，-cmin等，例如：

```
find ./ -mtime 3 #查找3天前更改过的文件
find ./ -mtime -3 #查找3天内更改过的文件
find ./ -mtime 0 #查找今天更改过的文件
find ./ -newer sort.txt #查找比sort.txt修改时间更新的文件
find ./ -anewer sort.txt #查找比sort.txt访问时间更新的文件
find ./ -amin  5 #查找5分钟之前访问过的文件
```

注：

- atime 最后访问时间
- mtime 最后修改时间
- ctime 最后修改时间，这里包括属性和权限

## 5.5、 tail

从指定点开始将文件标准输出

```
显示文件最后5行内容
tail -n 5 test.log

实时显示文件内容
tail -f test.log

ping baidu.com > 1.txt &
后台以守护进程的方式，将ping命令的返回结果写入 1.txt
```

## 5.6、which

which命令会在PATH变量指定的路径中，搜索某个系统命令的位置。例如：

```
which -a which  #查看命令which所在位置，-a参数表示找出所有
/usr/bin/which
/bin/which
```

PATH变量有哪些内容呢？我们来看一下(不同电脑可能不同)：

```
echo $PATH
/home/hyb/bin:/home/hyb/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/games:/snap/bin:/usr/lib/jvm/java-8-oracle/bin:/usr/lib/jvm/java-8-oracle/db/bin:/usr/lib/jvm/java-8-oracle/jre/bin

```

PATH环境变量存放着一些路径信息，例如/usr/bin，当你在shell终端敲入一个命令，但是在PATH中包含的路径下没有时并且也不是内置命令时，就会提示：command not found。

当你已经安装了一个命令，但是使用时却提示找不到该命令，可以查看该环境变量，是否有你安装命令的路径。

## 5.7、whereis

whereis命令用于搜索程序的二进制文件，源代码文件或帮助文档。例如：

```
whereis ls  #如果上述三者有，则三者都会显示。
ls: /bin/ls /usr/share/man/man1/ls.1.gz

whereis -m ls #只查看ls的帮助手册
ls: /usr/share/man/man1/ls.1.gz

whereis -b ls #只查找ls的二进制文件
ls: /bin/ls

whereis stdio.h #查找stdio.h头文件，和帮助手册
stdio: /usr/include/stdio.h /usr/share/man/man3/stdio.3.gz
```

同样地，它不能查找到内置命令。

## 5.8、type

type用于查看命令类型，一般有以下类型：

```
alias：别名
keyword：关键字
builtin：内置命令
file：外部命令
```

而常见参数如下：

```
-t 输出类型名，如file
-p 如果是外部命令，则显示其所在路径
-a 对于外部命令，它会显示命令路径，命令类型等信息
```

我们来看几个例子：

```
type ls   #ls是一个别名
ls is aliased to `ls --color=auto'

type cd    #cd是一个内置命令
cd is a shell builtin

type find
find is /usr/bin/find

type  function  #function是一个shell关键字
function is a shell keyword

type -a which  #显示所有路径
which is /usr/bin/which
which is /bin/which
```

## 5.9、locate

locate用于快速查找任何文件。它从一个系统数据库进行文件查找，而不需要遍历磁盘，因此速度极快。通常该系统数据库每天更新一次（可以查看系统的/etc/cron.daily/mlocate，不同系统可能不一样）

常见选项如下：

```
-e  仅查找存在的文件
-q  安静模式，不会显示任何错误讯息
-n  至多显示 n个输出
-r  使用正规运算式
-i  查找忽略大小写
-c  打印匹配结果数量
```

假设当前目录早已存在以下文件:

```
locate.txt  locate.log  LOCATE.zip
```

我们来看一些实例。

#### 快速查找文件

```
locate locate.txt  #查找locate.txt
/home/hyb/workspaces/shell/locate/locate.txt
```

#### 查找存在的文件

```
locate locate.txt  #查找之前删除locate.txt
#虽然文件不存在，但是仍然被查找出来
/home/hyb/workspaces/shell/locate/locate.txt 

locate -e locate.txt  #-e参数可以查找只存在的文件
(由于该文件不存在，因此也不会被查找出来)
```

#### 查找计算文件的数量

```
locate -c locate.log #只计算查找到的数量
 1
```

#### 忽略大小写查找

```
locate -i locate.zip
/home/hyb/workspaces/shell/locate/LOCATE.zip
```

#### 使用正则表达式

普通的查找是模糊匹配的，因此只要目标名称中包含要搜索的名称，都会被搜索出来，但是我们可以利用正则表达式，来精确查找。

```
locate -r /locate.log$  #查找以/locate.log结尾的文件
```

locate查找存在的一个问题是，如果最近有文件被删除，它仍然能找出来，最近有文件增加，它却找不到。也就是说，它的查找并不具备实时性。当然我们可以手动执行updatedb命令来更新数据库（可能需要root权限）



# 六、用户

```
w                       查看活动用户
id <用户名>              查看指定用户信息
last                    查看用户登录日志
cut -d: -f1 /etc/passwd    查看系统所有用户
cut -d: -f1 /etc/group     查看系统所有组
crontab -l              查看当前用户的计划任务
```

# 七、其它

## 1、查看所有安装的软件包

```
rpm -qa                 
```

## 2、查看环境变量

```
env                     
```

## 3、Mac 删除git文件夹，删除svn文件夹

```
cd到该文件夹

//删除文件夹下的所有 .svn 文件
find . -name ".svn" | xargs rm -Rf

//删除文件夹下的所有 .git 文件
find . -name ".git" | xargs rm -Rf
```

## 4、post请求

```
curl -X POST "http://10.153.3.15:8081/jdt-server/jdtCreateLink?companyNum=1&policyNum=09A50521&tag=app" -H "accept: */*"
```


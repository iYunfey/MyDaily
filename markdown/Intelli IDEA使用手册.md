# 一、下载安装破解

下载地址：https://www.jetbrains.com/zh-cn/idea/

# 二、配置参数

### 项目默认存储位置

File->Settings->Appearance & Behavior->System Settings->project opening->default directory设置默认路径

### 关闭自动更新

File->Settings->Appearance & Behavior->System Settings->Updates下取消Automatically check updates for勾选

### 隐藏.idea文件夹和.iml文件

IntelliJ IDEA项⽬目会⾃自动⽣生成⼀一个.idea⽂文件夹和.iml⽂文讲，看着实在是碍眼，所以对以上⽂文件进⾏行行隐藏处理理 在File->Settings->Editor->File Types下的”Ignore files and folders”⼀一栏添加 *.idea;*.iml;

### 文件编码设置UTF-8

File->Settings->Editor->File Encodings

Global Encoding:UTF-8

Project Encoding:UTF-8

Default encoding for properties files:UTF-8 勾选上Transparent native-to-ascii conversion

### 自动导包

File->Settings->Editor- >General->Auto Import下进⾏配置，Insert imports on paste 选择All，其余四个选项全部勾选

### 自动编译

File->Settings->Build，Execution，Development—>complier—>build project automatically打勾

### 设置主题

File->Settings->Appearance&Behavior——>appearance——>theme——>选择主题

### 代码提示忽略大小写

editor——>general——>code completion——>取消match case

### 换行符使用 Unix 格式

editor->code style->General->Line separator->Unix and macOS(\n)

### 自动换行

File->Settings->Editor–>General–>Soft wraps—>soft wraps files后面输入框内添加;*.java

把文件类型清空，然后输入*.*就能匹配全部文件格式自动换行

### 软换行

代码行号，右键—>Soft wrap

### maven设置

File->Settings->Build,Execution,Deployment->Build Tools->Maven下对Maven进⾏配置，

1. user settings file:指定Maven的settings.xml位置
2. local repository: 指定Maven的本地仓库位置，是读取settings.xml⾃动配置的
3. maven home directory:指定本地Maven的安装⽬录所在，这⾥可以选择你的Maven安装⽬录

### 设置鼠标悬浮提示

File–>settings–>Editor–>General–>勾选Show quick documentation...

### 快捷键代码提示

keymap——>Main menu——>code——>completion——>Basic——>先Remove再Add，alt+/

### 快捷键关闭单个Tab

keymap——>Main menu——>Window——>Editor tabs——>close——>先Remove再Add，control+w

### 注释斜体改为正体

File–>settings–>Editor–>color scheme——>Language defaults——>comments——>Doc comment——>text以及line comment取消勾选italic

### 单行显示多个Tabs

File–>settings–>Editor–>General -->Editor Tabs–>去掉show tabs in single row的√

### 改变Tabs位置到左侧

点击任意一个Tab，右键configuration editor tabs——>tab placement属性由Top改为Left

File–>settings–>Editor–>General -->Editor Tabs——>tab placement属性由Top改为Left

### Tabs排序

点击任意一个Tab，右键configuration editor tabs——>Tab order——>sort tabs alphabetically

File–>settings–>Editor–>General -->Editor Tabs——>Tab order——>sort tabs alphabetically

### terminal

File–>settings–>terminal->将cmd.exe换为git/bin/bash.exe

### 将git的push命令显示在工具栏

File–>settings–>Appearance&Behavior->Manu and Toolbars->navigation Bar Toolbar->NavBarVCS group->VCSNavToolbarActions->add action -> version control systems ->Git->Repository->找到push

### 生成序列化ID

File -> Settings -> Editor -> Inspections -> 搜索 Serialization issues ，找到 Serializable class without 'serialVersionUID' ->打上勾，Apply->OK，把鼠标放在在类名前会自动提示添加序列化id

### 显示内存使用情况

File-->settings-->apperance-->window options-->show memory indicator

### springboot项目显示run dashboard

找到.idea下面的workspace.xml文件  2.在下面的代码中加入一段配置代码  源代码位置

```
<component name="RunDashboard">
 <option name="ruleStates">
     <list>
       <RuleState>
           <option name="name" value="ConfigurationTypeDashboardGroupingRule" />
       </RuleState>
       <RuleState>
          <option name="name" value="StatusDashboardGroupingRule" />
       </RuleState>
     </list>
  </option>
  <option name="contentProportion" value="0.22874807" />
  <option name="configurationTypes">
     <set>
          <option value="SpringBootApplicationConfigurationType" />
     </set>
  </option>
 </component>
```

### 代码搜索设置

```
全局搜索control+shift+F之后在Match case  Words 之后的箭头勾选隐藏的Regex进行正则表达式搜索
```

### 前进后退添加到工具栏

File——>Appearance & Behavior——>Menu and Toolbars——>Navigation Bar Toolbar——>Toolbar Run Actions——>Add Action——>Main menu——>Navigate——>ctrl选中Back和Forward——>点击ok



# 三、快捷键对比

不特殊说明Mac版，一般可用command替代Ctrl，option代替Alt

| 描述                                                         | Windows                 | Mac Os        |
| ------------------------------------------------------------ | ----------------------- | ------------- |
| 进入光标所在的方法/变量的接口/定义处 👓== Ctrl + 鼠标左击     | Ctrl + B                |               |
| 跳转到类型声明处 👓                                           | Ctrl + Shift + B        |               |
| 在某个调用的方法名上使用会跳转到具体的实现处，可以跳过接口   | Ctrl + Alt + B          |               |
| 快速查看光标所在的方法/类的定义                              | Ctrl + Shift + I        |               |
| 查找光标所在的方法 / 变量 / 类被调用的地方                   | Alt + F7                |               |
| 显示使用的地方。寻找被该类或是变量被调用的地方，用弹出框的方式找出来 | Ctrl + Alt + F7         |               |
| 弹出框查询所有使用的地方                                     | Ctrl + Shift + Alt + F7 |               |
| 跳转到下一个 高亮错误 / 警告位置 👓                           | F2                      |               |
| 跳转到上一个高亮错误 / 警告位置                              | Shift + F2              |               |
| 方法参数提示显示 👓                                           | Ctrl + P                | Command   + P |
| 光标所在的变量/类名/方法名等上面（也可以在提示补充的时候按），显示文档内容 | Ctrl + Q                | %             |
| 前往当前光标所在的方法的父类的方法/接口定义 👓                | Ctrl + U                |               |
| 显示当前类的层次结构                                         | Ctrl + H                |               |
| 调用层次                                                     | Ctrl + Alt + H          |               |
| 弹出当前文件结构层，可以在弹出的层上直接输入，进行筛选       | Ctrl + F12              | Command + F12 |
| 显示当前文件结构                                             | Alt + 7                 |               |
| 定位/显示到当前文件的 Navigation Bar                         | Alt + Home              |               |
| 高亮显示所有该选中文本，按 Esc 高亮消失 👓                    | Ctrl + Shift + F7       |               |

## 代码运行

| 描述                                                         | Windows            | Mac Os                |
| ------------------------------------------------------------ | ------------------ | --------------------- |
| 等效于点击工具栏的 Run 按钮 ​                                 | Shift + F10        |                       |
| Run 当前代码                                                 | Ctrl + Shift + F10 | Control + Shift + R/D |
| 弹出 Run 的可选择菜单                                        | Alt+ Shift + F10   | Control + Shift + R   |
| 编译选中的文件/包/Module                                     | Ctrl + Shift + F9  | Command + Shift + F9  |
| Debug 模式下,如当前行断点是一个方法,则进入当前方法,如果该方法体还有方法,则不会进入该内嵌方法 | F7                 | F7                    |
| Debug 模式下,如当前行断点是一个方法,则不进入当前方法         | F8                 | F8                    |
| Debug 模式下,恢复程序运行,但是如果该断点下面代码还有断点则停在下一个断点上 | F9                 | F9                    |
| 在 Debug 模式下，设置光标当前行为断点，如果当前已经是断点则去掉断点 | Ctrl + F8          | Option + F8           |
| 等效于点击工具栏的 Debug 按钮                                | Shift + F9         | Command + D           |
| 在 Debug 模式下，跳出，表现出来的效果跟 F9 一样              | Shift + F8         | Shift + F8            |
| 弹出所有的 Debug 列表                                        | Ctrl + Shift + F8  | Command + Shift + F8  |
| 在 Debug 模式下，智能步入。断点所在行上有多个方法调用，会弹出进入哪个方法 | Shift + F7         | Shift + F7            |
| 弹出 Debug 的可选择菜单                                      | Alt+ Shift + F9    |                       |
| Debug 模式下,进入当前方法体内                                | Alt+ Shift + F7    |                       |

## 文本编辑

| 描述                                                         | Windows                         | Mac Os              |
| ------------------------------------------------------------ | ------------------------------- | ------------------- |
| 在当前文件进行文本替换 👓                                     | Ctrl + R                        | Command + R         |
| 根据输入内容替换对应内容，范围为整个项目或 指定目录内文件 👓  | Ctrl + Shift + R                | Command + Shift + R |
| 撤销 👓                                                       | Ctrl + Z                        | Command + Z         |
| 删除光标所在行或 删除选中的行 👓                              | Ctrl + Y                        |                     |
| 复制光标所在行 / 复制选择内容                                | Ctrl + C                        |                     |
| 剪切光标所在行 / 剪切选择内容                                | Ctrl + X                        | Command + X         |
| 复制光标所在行 / 复制选择内容，并把复制内容插入光标位置下面 👓 | Ctrl + D                        | Command + D         |
| 取消撤销 👓                                                   | Ctrl + Shift + Z                | Command + Shift + Z |
| 删除光标后面的单词或是中文句 👓                               | Ctrl + Delete                   |                     |
| 删除光标前面的单词或是中文句 👓                               | Ctrl + BackSpace                | Option + BackSpace  |
| 光标所在行上空出一行，光标定位到新行 👓                       | Ctrl + Alt + Enter              |                     |
| 复制当前文件磁盘路径到剪贴板 👓                               | Ctrl + Shift + C                |                     |
| 删除变量、方法、类、文件等                                   | Alt + Delete                    |                     |
| 批量修改名称                                                 | Shift + F6                      |                     |
| 同时编辑多个内容                                             | Ctrl + Alt + Shift              |                     |
| 纵向选择多行代码                                             | Alt + 鼠标左键并拖动            |                     |
| 查看复制历史                                                 | Ctrl+shift+V  Ctrl+Shift+Insert |                     |

## 代码选择

| 描述                                                         | Windows            | Mac Os               |
| ------------------------------------------------------------ | ------------------ | -------------------- |
| 行首行尾                                                     | Home / End         |                      |
| 在当前文件跳转到指定行处                                     | Ctrl + G           |                      |
| 光标跳转到当前单词 / 中文句的左侧开头位置 👓                  | Ctrl + ←           | Option + ←           |
| 光标跳转到当前单词 / 中文句的右侧开头位置 👓                  | Ctrl + →           | Option + →           |
| 展开/折叠代码                                                | Ctrl + +/-         | Option + +/-         |
| 展开/折叠所有代码 👓                                          | Ctrl + Shift + +/- | Option + Shift + +/- |
| 跳到文件头                                                   | Ctrl + Home        | Command + ↑          |
| 跳到文件尾                                                   | Ctrl + c           | Command + ↓          |
| 选中光标到当前行头位置                                       | Shift + Home       | Command + ←          |
| 选中光标到当前行尾位置                                       | Shift + End        | Command + →          |
| 开始新一行。光标所在行下空出一行，光标定位到新行位置 👓       | Shift + Enter      | Shift + Enter        |
| 退回到上一个操作的地方 👓                                     | Ctrl + Alt + ←     | Option + Command + ← |
| 前往到下一个操作的地方 👓                                     | Ctrl + Alt + →     | Option + Command + → |
| 移动光标到当前所在代码的花括号开始位置                       | Ctrl + [           |                      |
| 移动光标到当前所在代码的花括号结束位置                       | Ctrl + ]           |                      |
| 选中从光标所在位置到它的顶部中括号位置 👓                     | Ctrl + Shift + [   |                      |
| 选中从光标所在位置到它的底部中括号位置 👓                     | Ctrl + Shift + ]   |                      |
| 移动光标到当前所在代码的开始或结束位置                       | Ctrl + Shift + M   |                      |
| 切换当前已打开的窗口中的子视图 👓                             | Alt+ ←             |                      |
| 按切换当前已打开的窗口中的子视图 👓                           | Alt+ →             |                      |
| 当前光标跳转到当前文件的前一个方法名位置 👓                   | Alt + ↑            |                      |
| 当前光标跳转到当前文件的后一个方法名位置 👓                   | Alt + ↓            |                      |
| 选中跨屏幕代码，左键点击开始位置，找到结尾位置shift+左键点击结尾位置即可 | shift+鼠标左键     |                      |

## 内容查找

| 描述                                                         | Windows                | Mac Os               |
| ------------------------------------------------------------ | ---------------------- | -------------------- |
| 弹出 SearchEverywhere 弹出层                                 | double Shift           | Double Shift         |
| 在当前文件进行文本查找 👓                                     | Ctrl + F               | Command + F          |
| 根据输入的 类名 查找类文件 👓                                 | Ctrl + N               | Command + O          |
| 根据输入内容查找整个项目或 指定目录内文件 👓                  | Ctrl + Shift + F       | Command + Option + F |
| 通过文件名定位/打开文件/目录,打开目录需要在输入的内容后面多加一个/ 👓 | Ctrl + Shift + N       | Command + Shift + O  |
| 查找动作/设置                                                | Ctrl + Shift + A       | Command + Shift + A  |
| 前往指定的变量/方法                                          | Ctrl + Shift + Alt + N | Command + Option + O |
| 在查找模式下，定位到下一个匹配处                             | F3                     | Command + G          |
| 在查找模式下，查找匹配上一个                                 | Shift + F3             | Command + Shift + G  |
| 调转到所选中的词的下一个引用位置 👓                           | Ctrl + F3              |                      |

## 代码辅助

| 描述                                                         | Windows                | Mac Os                                     |
| ------------------------------------------------------------ | ---------------------- | ------------------------------------------ |
| 插入自定义动态代码模板 👓                                     | Ctrl + J               | Command + J                                |
| 基础代码补全 👓                                               | Ctrl + Space (Alt + /) | Control + Space                            |
| 智能代码提示                                                 | Ctrl + Shift + Space   | Control + Command + Space                  |
| 自动结束代码，行末自动添加分号 👓                             | Ctrl + Shift + Enter   | Command + Shift + Enter                    |
| 代码自动生成，如生成对象的 set / get 方法，构造函数，toString() 等 👓 | Alt + Insert           | Command  + N, Control + Enter, Control + N |
| 根据光标所在问题，提供快速修复选择，光标放在的位置不同提示的结果也不同 👓 | Alt + Enter            | Option + Enter                             |
| 弹出模板选择窗口，将选定的代码加入动态模板中                 | Ctrl + Alt + J         | Command + Option + J                       |
| 选择可重写的方法                                             | Ctrl + O               | Command + O                                |
| 选择可继承的方法                                             | Ctrl + I               | Command + I                                |
| 对当前类生成单元测试类，如果已经存在的单元测试类则可以进行选择 👓 | Ctrl + Shift + T       | Command + Shift + T                        |

## 代码优化

| 描述                                                         | Windows               | Mac Os               |
| ------------------------------------------------------------ | --------------------- | -------------------- |
| 注释光标所在行代码，会根据当前不同文件类型使用不同的注释符号 👓 | Ctrl + /              | Command + /          |
| 代码块注释 👓                                                 | Ctrl + Shift + /      | Command + Shift + /  |
| 缩进 👓                                                       | Tab                   | Tab                  |
| 取消缩进 👓                                                   | Shift + Tab           | Shift + Tab          |
| 格式化代码，可以对当前文件和整个包目录使用 👓                 | Ctrl + Alt + L        | Control + Option + L |
| 优化导入的类，可以对当前文件和整个包目录使用 👓               | Ctrl + Alt + O        | Control + Option + O |
| 光标所在行或 选中部分进行自动代码缩进，有点类似格式化        | Ctrl + Alt + I        | Control + Option + I |
| 对选中的代码弹出环绕选项弹出层 👓                             | Ctrl + Alt + T        | Command + Option + T |
| 对选中的代码进行大/小写轮流转换 👓                            | Ctrl + Shift + U      | Command + Option + U |
| 自动将下一行合并到当前行末尾 👓                               | Ctrl + Shift + J      | Control + Shift + J  |
| 快速移除环绕代码 👓                                           | Ctrl + Shift + Delete |                      |
| 重构-快速提取参数到方法                                      | Ctrl + Alt + P        | Command + Shift + P  |
| 重构-快速提取常量                                            | Ctrl + Alt + C        | Command + Shift + C  |
| 重构-快速提取成员变量                                        | Ctrl + Alt + F        | Command + Shift + F  |
| 重构-快速提取变量                                            | Ctrl + Alt + V        | Command + Shift + V  |
| 重构-快速提取方法                                            | Ctrl + Alt + M        | Command + Shift + M  |
| 对文件 / 文件夹 重命名                                       | Shift + F6            | Shift + F6           |
| 移动光标所在行向上移动 👓                                     | Alt + Shift + ↑       |                      |
| 移动光标所在行向下移动 👓                                     | Alt + Shift + ↓       |                      |
| 光标放在方法名上，将方法移动到上一个方法前面，调整方法排序 👓 | Ctrl + Shift + ↑      |                      |
| 光标放在方法名上，将方法移动到下一个方法前面，调整方法排序 👓 | Ctrl + Shift + ↓      |                      |

## 版本控制

| 描述                             | Windows         | Mac Os             |
| -------------------------------- | --------------- | ------------------ |
| 显示版本控制常用操作菜单弹出层 👓 | Alt + ~         | Command + V        |
| 版本控制提交项目 git commit      | Ctrl + K        | Command + K        |
| 版本控制更新项目 git pull        | Ctrl + T        | Command + T        |
| 版本控制显示最近修改项目         | Alt + Shift + C | Option + Shift + C |

## IDE 相对路径

| 描述                                                         | Windows                | Mac Os                |
| ------------------------------------------------------------ | ---------------------- | --------------------- |
| 弹出该文件路径 👓                                             | Ctrl + Alt + F12       |                       |
| 在打开的文件标题上，弹出该文件路径 👓                         | Ctrl + 鼠标左击        |                       |
| 显示当前文件选择目标弹出层，弹出层中有很多目标可以进行选择 👓 | Alt + F1               |                       |
| 打开 IntelliJ IDEA 系统设置 👓                                | Ctrl + Alt + S         | Command + ,           |
| 打开当前项目设置 👓                                           | Ctrl + Shift + Alt + S | Command + ;           |
| 编辑器最大化 👓                                               | Ctrl + Shift + F12     | Command + Shift + F12 |
| 隐藏当前或 最后一个激活的工具窗口                            | Shift + Esc            | Shift + Esc           |
| 从工具窗口进入代码文件窗口 👓                                 | Esc                    | Esc                   |
| 编辑窗口切换，如果在切换的过程又加按上 delete，则是关闭对应选中的窗口 | Ctrl + Tab             | Control + Tab         |
| 显示最近打开的文件记录列表 👓                                 | Ctrl + E               |                       |
| 显示修改历史                                                 | Ctrl + Shift + E       |                       |



# 四、插件

Lombok，Key promoter，Json Parser，Free Mybatis plugin，Translation，CodeGlance

# 五、启动项目

## 启动方式

1_右键run启动

2_菜单栏启动

3_Run窗口运行 

## 启动参数

菜单栏——>edit configuration 

用法一：启动参数-D可以覆盖application.properties的配置，例：-Dserver.port.8081

用法二：配合maven，用于多环境切换 ，例：-Dprofile=test/dev

# 六、调试项目

## 基本断点调试

蓝色背景 的行就是当前程序停顿的行

step over（F8），执行到当前方法的下一行，步过

step into（F7），进入当前行调用的方法体，步过

step out（shift+F8），执行完当前的方法，步过

force step into（alt+shift+F7），可以进入第三方jar包或者jdk自带方法内部

Alt+Shift+F8，强制步过

Alt+F9，运行至光标处
Ctrl+Alt+F9，强制运行至光标处

鼠标悬停在变量上，变量下方灰色阴影点击展开即可查看变量属性

debug窗口step out右边drop frame可以回溯到上个方法内



## 断点管理

断点右键可以设置其属性 ，去掉enabled，保留断点位置又可以跳过断点

view breakpoints 可以管理所有断点及其属性

mute breakpoints取消所有断点

### 条件断点（问号）

循环条件断点：当循环到第几次的时候停止，在相应断点属性condition内填写Java代码条件i == 2，这样断点右下角有个问号，表明是条件断点

期望条件断点：当某个属性值等于期望值时停止

### 异常断点（闪电）

在view breakpoints界面，点+号新增Java Exception Breakpoints，写一个异常。闪电标识

### 添加变量监控

debugger界面，variables添加眼镜按钮，输入Java代码变量



# 七、代码模板

## 修改方法注释模版

File->Settings->Editor->Live Templates下添加⾃定义Template Group，并在⾃定义Template Group下添加⾃定义Template

第一个

```
**
 * @description :
$params$
 * @return : $return$
 * @author : $user$
 * @date : $date$ $time$
 */
 
 Params形参自动获取

groovyScript("def result=''; def params=\"${_1}\".replaceAll('[\\\\[|\\\\]|\\\\s]', '').split(',').toList(); for(i = 0; i < params.size(); i++) {result+=' * @param ： ' + params[i]  + ((i < params.size() - 1) ? '\\n' : '')}; return result", methodParameters())

return	methodReturnType()

author	user()

date	date()

time	time()
```

第二个

```
**
 * @description: 
 *
 * $VAR1$ $params$
 * @return $returns$
 * @author $user$
 * @date $date$ $time$
 */
 
 VAR1		groovyScript("   def result='';    def params=\"${_1}\".replaceAll('[\\\\[|\\\\]|\\\\s]', '').split(',').toList();     for(i = 0; i < params.size(); i++) {         if(i!=0)result+= ' * ';         result+='@param ' + params[i] + ((i < (params.size() - 1)) ? '\\n' + '\\t' : '');     };     return result", methodParameters())
 
 params
 returns	methodReturnType()
 user		user()
 date		date()
 time		time()
```

## postfix

先打变量或者表达式，再打.快捷键，例：user.null、i==1.if、user.sout、list.size().fori

# 八、Git

## 切换git远程提交位置

2020.1版本，VCS-Git-Remotes，然后修改远程仓库URL

## 快速查看提交记录

代码行数右键，点击Annotate，，可以看到提交时间以及提交人，直接双击可以看到当时提交的所有记录

# 九、Tomcat

## 9.1、tomcat启动控制台

deployment会把下面的log区域遮住

点击debug模式下控制台右上角Layout，取消勾选deployment
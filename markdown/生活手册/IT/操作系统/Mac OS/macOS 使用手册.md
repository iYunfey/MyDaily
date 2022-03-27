官方使用手册：https://support.apple.com/zh-cn/guide/mac-help/welcome/mac

## 系统设置 > 触控板

### 光标与点按

✓ 查询与数据检测器

​	三指轻点

✓ 辅助点按

​	双指点或轻点

✓轻点来点按

​	单指轻点

✓用力点按和触感反馈

### 滚动缩放

✓ 默认全选

### 更多手势

✓ 默认全选

✓在页面间轻扫

​	双指左右滚动

✓在全屏幕显示的应用间轻扫

​	四指左右轻扫

✓通知中心

​	双指从右边缘向左轻扫

✓调度中心

​	四指向上轻扫

✓应用Expose

​	四指向下轻扫

✓启动台

​	捏拢拇指和其它三指

✓显示桌面

​	张开拇指和其它三指

## 三指拖动

打开偏好设置—>辅助功能—>左侧找到鼠标与触控板—>触控板属性—>启用拖移—>选中三指拖移

## Finder

- Finder > 显示

显示标签页栏
显示路径栏
显示状态栏
自定工具栏 > 去除所有按钮，仅剩搜索栏 

- Finder > 偏好设置

通用
开启新 Finder 窗口时打开：HOME「用户名」目录
边栏
添加 HOME「用户名」目录 和 创建代码文件目录
将 共享的(shared) 和 标记(tags) 目录去掉 

## 菜单栏

去掉蓝牙等无需经常使用的图标
将电池显示设置为百分比



## 改变Launchpad内图标排列的行数和列数

启动终端

改变行数量

defaults write com.apple.dock springboard-rows -int X

改变列数量

defaults write com.apple.dock springboard-columns -int X

使改变生效

killall Dock

将X换成数字

如果想恢复原样

defaults write com.apple.dock springboard-rows Default

defaults write com.apple.dock springboard-columns Default

killall Dock

提示：千万不要改太小了，一行最少放5个，否则改回来你得拖好多图标到某页上

13寸笔记本上，最佳是一页5行，每行9个（rows=5，columns=9）

15.4自定义了一页7行，每行11个（rows=5，columns=9）

## 显示隐藏文件

在 macOS Sierra，我们可以直接使用快捷键⌘⇧.(Command + Shift + .) 来快速（在 Finder 中）显示和隐藏隐藏文件

## 虚拟内存

如果你的内存有4GB以上，大部分情况下都已经够用了，于是就可以禁用虚拟内存，以获得更快的速度：

查看虚拟内存使用情况sysctl vm.swapusage

**前提：先禁用SIP**

重启终端

csrutil disabled

禁用虚拟内存

sudo launchctl unload -w /System/Library/LaunchDaemons/com.apple.dynamic_pager.plist

禁用以后，磁盘上还保留着这些交换文件，它们已经没用了，也可以删掉：

sudo rm /private/var/vm/swapfile

**记得启用SIP**

csrutil enable

如果要重新启用虚拟内存的话，可以执行这条命令：

sudo launchctl load -w /System/Library/LaunchDaemons/com.apple.dynamic_pager.plist
# git提交时写message的规范

### commit message(提交说明)

```
git commit -m "写一行提交说明"
 
# 跳出文本编辑器，写多行
git commit
 
# add && commit 注意：add只针对修改文件不包括新增加的文件
git commit -am ""
```

### commit message格式

```
<type>(<scope>): <subject>
// 空一行
<body>
// 空一行
<footer>
 
# 其中，Header 是必需的，Body 和 Footer 可以省略。
# 不管是哪一个部分，任何一行都不得超过72个字符（或100个字符）。这是为了避免自动换行影响美观。
```

### Header说明

```
# type
feat：新功能（feature）
fix：修补bug
docs：文档（documentation）
style： 格式（不影响代码运行的变动）
refactor：重构（即不是新增功能，也不是修改bug的代码变动）
test：增加测试
chore：构建过程或辅助工具的变动
 
# scope
scope用于说明 commit 影响的范围，比如数据层、控制层、视图层等等，视项目不同而不同。
 
# subject
subject是 commit 目的的简短描述，不超过50个字符。
- 以动词开头，使用第一人称现在时，比如change，而不是changed或changes
- 第一个字母小写
- 结尾不加句号（.）
```

### Body说明

```
Body 部分是对本次 commit 的详细描述，可以分成多行。
（1）使用第一人称现在时，比如使用change而不是changed或changes。
（2）应该说明代码变动的动机，以及与以前行为的对比。
```


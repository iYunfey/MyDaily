# 查看

## git branch -a

```
查看所有的分支，包括本地和远程
```

## git branch

```
查看所有本地分支，带*为当前分支
```

## git status 

```
查看文件的修改状态 
```

## git log

```
查看当前分支的提交记录

统计代码提交行数

git log --author="dan"  --since='2022-01-17 00:00:00' --until='2022-01-17 23:59:59'    --pretty=tformat: --numstat |awk '{ add += $1; subs += $2; loc += $1 - $2 } END { printf "added lines: %s, removed lines: %s, total lines: %s\n", add, subs, loc }'
```

## git log -p 

```
查看代码改动点（所有）
```

## git branch -vv

```
查看到本地分支跟踪的远程分支
```



# 创建

## git branch 分支名

```
在本地库创建新的分支
```

## git push -u origin 分支名

```
提交本地创建的分支到远程服务器
第一次创建新应用，最后提交到master
git push -u origin master
```

## git checkout -b develop remotes/origin/develop

```
切换远程开发分支在本地创建影像
```

# 删除

## git branch -d 分支名

```
删除本地分支（删之前需要切换非当前分支）
```

# 切换

## git checkout 分支名

```
在分支之间切换
```

# 提交推送

## git add 文件路径

```
git add src/main/java/com/onlyone/csw/controllers/Test.java 

标记需要提交的文件，支持*通配符
```

## git commit -m “备注”  

```
将本地修改保存到本地仓库中，并添加备注
```

## git push

```
将本地仓库修改推送到服务器上的仓库中
```

# 同步

## git pull 

```
同步服务器最新内容到本地
```

# 合并

## git merge 分支a

```
将分支a内容合到当前分支上，最后要执行git commit 和 git push
```

# 暂存

## git stash save

```
git stash save "暂存注释说明"
```

## git stash list

```
查看当前stash中的内容
```

## git stash apply

```
git stash apply "暂存注释说明"
```

## git stash pop

```
将当前stash中的内容弹出，并应用到当前分支对应的工作目录上。
注：该命令将堆栈中最近保存的内容删除（栈是先进后出）
```

## git stash drop

```
git stash drop  "暂存注释说明"
```

## git stash clear

```
清除堆栈中的所有 内容
```

## git stash show

```
查看堆栈中最新保存的stash和当前目录的差异。
```

# 比较

## git diff  topic  master

```
直接将两个分支上最新的提交做diff	
```

## git diff

```
查看当前未提交的文件的改动点
```



# 版本回退

## 工作区

```
git checkout -- <文件名>
```

## add到暂存区

```
git reset HEAD
```

## 已提交到本地仓库

```
git reset --hard <版本号>

<版本号>写法：
	上一版本：^
		git reset --hard^
	上两个版本：^^，一个^代表一个版本
		git reset --hard^^
	前100个版本：~100
		git reset --hard~100
	指定版本：提交记录日志的hash码，比如695ce1fe
		git reset --hard 695ce1fe
		
git reset --hard HEAD~3  

会将最新的3次提交全部重置，只在本地生效 

git push -f origin 分支名 

强制提交到远程服务器，此时回退了3个版本，git服务器的提交log也会清掉。		
		
		
git reset <commit-id>  #默认就是-mixed参数。
修改代码 或者  git checkout 撤销修改
git push -f 强制提交
ps：查看git log，会发现原来已经提交过的log也会被删除

```

## 远程仓库

```
上面那个操作push到远程仓库即可


git reset --hard HEAD~3  
会将最新的3次提交全部重置，只在本地生效 

git push -f origin 分支名 
强制提交到远程服务器，此时回退了3个版本，git服务器的提交log也会清掉。
```


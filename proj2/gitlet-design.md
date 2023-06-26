# Gitlet Design Document

**Name**:

## Classes and Data Structures

### Main

用于gitlet的入口，主要写入抽象的命令方法，不进行方法的实现，利用Repository当中的静态方法对gitlet仓库进行操作

#### Fields

无字段，主要利用命令行参数作为命令选择依据


### StagingArea
暂存区对象，用于控制暂存区的各项数据和操作
#### Fields
文件指针ADDAREA指向存储blobs的TreeMap对象的文件
BLOBS_MAX指向存储blobs当前的最新值的文件
ADDSTATUS指向存储暂存区文件列表的文件
MODSTATUS指向存储被修改文件列表的文件
blobs 存有文件名与blob的hashcode的映射关系
link_add 暂存区的文件名列表

操作有
init_Staging()  初始化暂存区
add(File filename,String hashcode)  将名为filename的文件以及其对应的hashcode映射到blobs中
save()  保存暂存区的信息到status中
calchash(File name)  计算name文件的hash码
static getBlobs()  返回blobs的静态方法，用于ComTreeControler类添加Commit
static clearStatus()  清空提交后的Status


### ComTreeControler
Commit树的控制对象，用于控制和操作Commit树的添加，删除，更新修改等操作
#### Fields
相关文件的指针
commit_name 保存Commit名与Commit的hash码的映射关系
commit_tree 保存Commit的hash码与Commit对象的映射关系
commit_link 保存当前分支的commit对象链
head  分支头
master 主分支

操作有
inital_comTree()  初始化Commit Tree，将初始节点加入到Commit Tree中
add(Commit com) 将Commit添加到commit_tree中，映射commit_name与hash码的关系
commit_log() 将Commit 的提交历史打印出来
caclHash()  计算节点的hash码


###Commit
保存每一个Commit 的提交信息，包括提交message，提交时间，父节点Commit，父节点Commit的hash码，当前提交的blobs映射

数据：
message 提交信息
timeStamp 提交时间
parent Commit父节点
parent_hash 父节点Hash码
hashcode  当前节点的hash码
blobs 当前Commit的文件映射信息


操作
getTimeStamp() 获取提交时间
messgae() 获取提交信息
getParent() 获取父节点
printInfo(String hashname) 打印该提交节点信息
timeSet()  设置提交时间为当前时间
setBlobs(TreeMap<String,String> s) 设置当前节点的blobs
calcHash() 计算当前节点的hashcode
getHash() 获得当前节点的hashcode 
 



## Algorithms
Main 无算法
Commit 
对于每个Commit的hash码生成，应当先建立一个字节数组缓冲区bo，
建立一个对象输出流oo，将对象输出流的输出位置指向bo
然后将oo里的数据强行flush进入bo，将bo转换为Object传给sha1函数生成每一个Commit独有的hash码

## Persistence


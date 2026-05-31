
### 模块说明：
modules:
- jvm-app 对应桌面客户端
- android-app 对应安卓客户端
- server 对应服务器
- shared 对应不同客户端的ui、逻辑实现，默认src下为默认共享
- rs_lib rust逻辑，暂不参与模块耦合
- common 共享数据结构、定义接口给服务器和客户端
  

### 代码组织：
screen
view
viewmodel
usecase
repository（rpc可省略）
service（服务请求）

route
service（rpc） / repository（非rpc）
dao


### Todo:
- amper怎么进行测试[已解决]
- Diglol Id 在数据库存储、对应实体的类型最优方案(尤其是data class)[已解决]
- kRpc 通信改造
- ktor 静态资源映射
- ktor 的 kRpc 依赖注入[已解决] 
- rs_lib 怎么更好的嵌入开发流程
- Paging 怎么使用
- 页面适应性改造
- kn 原生模块开发
- koin 注解使用
- mmkv 使用
- DataStore 使用


### 启动命令
#### 启动 jvm-app
```shell
 .\amper.bat run --compose-hot-reload-mode --module jvm-app
```
```shell
 .\amper.bat run --module jvm-app 
```


#### 启动 server
```shell
 .\amper.bat run --module server
```

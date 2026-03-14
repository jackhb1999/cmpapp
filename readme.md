
## 启动 jvm-app
```shell
 .\amper.bat run --compose-hot-reload-mode --module jvm-app
```
```shell
 .\amper.bat run --module jvm-app 
```


## 启动 server
```shell
 .\amper.bat run --module server
```

模块说明：
modules:
- jvm-app 对应桌面客户端
- android-app 对应安卓客户端
- server 对应服务器
- shared 对应不同客户端的ui、逻辑实现，默认src下为默认共享
- rslib rust逻辑，暂不参与
- common 共享数据结构给服务器和客户端






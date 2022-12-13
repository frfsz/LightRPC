# 1. 一个轻量级RPC调度框架
 ACE RPC是在JAVA RMI组件上构建的，方便使用，且对业务无入侵，性能优于restful，非常适合于中小型项目，省掉三方组件维护成本，如dubbo等之类的框架。
# 2. 使用方式
克隆源码到本地，使用命令 `mvn install` 进行编译，编译完成后在项目中引入即可。
## 2.1 服务端
### 2.1.1 引入依赖
```
<dependency>
  <groupId>com.ace.rpc</groupId>
  <artifactId>ace-rpc-server-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```
### 2.1.2 YAML配置

```
ace:
  rpc:
    enabled: true
    base-package: com.ace.fastapi #这是接口类所在的路径，这个包路径一定要作为一个独立的jar包，客户端也需要引入同一个jar包。
    registry:
      server-port: 9000 #这是RPC请求监听端口
```

## 2.2 客户端
### 2.2.1 引入依赖

```
<dependency>
  <groupId>com.ace.rpc</groupId>
  <artifactId>ace-rpc-client-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```
### 2.2.2 YAML配置

```
ace:
  rpc:
    enabled: true
    base-package: com.ace.fastapi #这是接口类所在的路径，这个包路径一定要作为一个独立的jar包，跟服务端引入一样。
    registry:
      clients:
      - host: loaclhost #RPC服务端主机IP
        port: 9000 #RPC服务端主机端口
        name:
          - admin  #子包名 全路径为com.ace.fastapi.admin
      - host: 192.168.1.154 #RPC服务端主机IP
        port: 9000 #RPC服务端主机端口
        name:
          - test  #子包名 全路径为com.ace.fastapi.test
```
## 2.3 示例
《完善中》
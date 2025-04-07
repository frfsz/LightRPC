# 1. 一个超级轻量的RPC调度框架
Light RPC是在JAVA RMI组件上构建的，方便使用，且对业务无入侵，性能优于restful，非常适合于中小型项目，省掉三方组件维护成本，如dubbo等之类的框架。
# 2. 使用示例
克隆源码到本地，使用命令 `mvn install` 进行编译，编译完成后在项目中引入即可。
## 2.1 服务端配置
### 2.1.1 引入依赖
```xml
<dependency>
  <groupId>io.github.frfsz</groupId>
  <artifactId>light-rpc-server-starter</artifactId>
  <version>2.0.2</version>
</dependency>
```
### 2.1.2 YAML配置

```yaml
light:
  rpc:
    enabled: true
    base-package: com.test #这是接口类所在的路径，这个包路径一定要作为一个独立的jar包，客户端也需要引入同一个jar包。
    registry:
      server-port: 9000 #这是RPC请求监听端口
```

## 2.2 客户端配置
### 2.2.1 引入依赖

```xml
<dependency>
  <groupId>io.github.frfsz</groupId>
  <artifactId>light-rpc-client-starter</artifactId>
  <version>2.0.2</version>
</dependency>
```
### 2.2.2 YAML配置

```yaml
light:
  rpc:
    enabled: true
    base-package: com.test #这是接口类所在的路径，这个包路径一定要作为一个独立的jar包，跟服务端引入一样。
    registry:
      clients:
      - host: 192.168.1.2 #RPC服务端主机IP
        port: 9000 #RPC服务端主机端口
        name:
          - admin  #子包名 全路径为com.test.admin
      - host: 192.168.1.1 #RPC服务端主机IP
        port: 9000 #RPC服务端主机端口
        name:
          - test  #子包名 全路径为com.test.test
```
## 2.3 代码示例
以下示例代码基于spring boot框架。
### 2.3.1 公共项目(Maven/Gradle)
```java
package com.test.admin;

@RpcService
public interface ServerService{

    public void test(String name);
}
```
### 2.3.2 服务端项目(Maven/Gradle)
```java

package com.test.service.impl;
import com.test.admin.ServerService;

public class ServerServiceImpl implements ServerService{

    @Override
    public void test(String name){
        System.out.println("test:"+name);
    };
}
```

### 2.3.3 客户端项目(Maven/Gradle)

```java

package com.test.client;

import org.springframework.stereotype.Service;
import io.github.frfsz.light.rpc.core.RpcClient;
import com.test.admin.ServerService;

@Service
public class TestClient {
    
    @RpcClient
    private ServerService serverService;
    
    public void test(){
        serverService.test("test");
    }
}
```
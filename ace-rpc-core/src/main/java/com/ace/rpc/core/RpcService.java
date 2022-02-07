/**
 * 文件名：RemoteService.java 版权：Copyright © Suzhou Keda Technology Co.Ltd. All Rights Res erved.
 */
package com.ace.rpc.core;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.DependsOn;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
@DependsOn("remoteClientFactory")
/**
 * @description 远程接口调用,只能加载interface接口类上,加此注解的类，所有传输对象必须实现序列化接口，否则会在传输时报错
 * @author fengsheng
 * @since 2020年1月6日
 * @date 2020年1月6日
 */
public @interface RpcService {

}

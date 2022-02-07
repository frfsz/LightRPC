/**
 * 文件名：AutowiredRemote.java 版权：Copyright © Suzhou Keda Technology Co.Ltd. All Rights Res erved.
 */
package com.ace.rpc.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@DependsOn("remoteClientFactory")
@Service
/**
 * @description 声明注入的所在类为远程调度端，该注解继承@Service特性
 * @author fengsheng
 * @since 2020年1月17日
 * @date 2020年1月17日
 */
public @interface RpcClient {

    @AliasFor(annotation = Service.class)
    String value() default "";

}

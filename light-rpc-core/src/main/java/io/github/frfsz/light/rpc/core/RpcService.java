/**
 * 文件名：RemoteService.java 版权：Copyright © Suzhou Keda Technology Co.Ltd. All Rights Res erved.
 */
package io.github.frfsz.light.rpc.core;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
/**
 * @description 远程接口调用,只能加载interface接口类上,加此注解的类，所有传输对象必须实现序列化接口，否则会在传输时报错
 * @author fengsheng
 * @since 2020年1月6日
 * @date 2020年1月6日
 */
public @interface RpcService {

}

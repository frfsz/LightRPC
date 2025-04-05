/**
 * 文件名：AutowiredRemote.java 版权：Copyright © Suzhou Keda Technology Co.Ltd. All Rights Res erved.
 */
package io.github.frfsz.light.rpc.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Lazy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Lazy
/**
 * @description 声明注入的所在类为远程调度端，该注解继承@Service特性
 * @author fengsheng
 * @since 2020年1月17日
 * @date 2020年1月17日
 */
public @interface RpcClient {
}

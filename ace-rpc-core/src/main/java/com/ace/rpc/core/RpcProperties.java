/**
 * 文件名：RpcProperties.java 版权：Copyright © Suzhou Keda Technology Co.Ltd. All Rights Res erved.
 */
package com.ace.rpc.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description RPC配置文件读取
 * @author fengsheng
 * @since 2020年4月24日
 * @date 2020年4月24日
 */
@ConfigurationProperties(prefix = "ace.rpc")
public class RpcProperties {

    private boolean enabled = false;

    private String basePackage = "classpath";

    private RpcRegistryProperties registry;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public RpcRegistryProperties getRegistry() {
        return registry;
    }

    public void setRegistry(RpcRegistryProperties registry) {
        this.registry = registry;
    }

}

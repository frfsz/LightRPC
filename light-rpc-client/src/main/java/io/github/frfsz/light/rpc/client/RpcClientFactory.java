/**
 * 文件名：JccRmiClientConfig.java 版权：Copyright © Suzhou Keda Technology Co.Ltd. All Rights Res erved.
 */
package io.github.frfsz.light.rpc.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import io.github.frfsz.light.rpc.core.RpcClientProperties;
import io.github.frfsz.light.rpc.core.RpcProperties;
import io.github.frfsz.light.rpc.core.RpcService;

/**
 * @description 初始化时自动初始化所有@RemoteService注解interface类
 * @author fengsheng
 * @since 2020年1月7日
 * @date 2020年1月7日
 */
public class RpcClientFactory {

    private static final Logger log = LoggerFactory.getLogger(RpcClientFactory.class);

    private static Reflections reflections;

    @Resource
    private RpcProperties rpcProperties;

    @Resource
    public void initRemoteClient(ApplicationContext context) {
        reflections = new Reflections(rpcProperties.getBasePackage());
        log.info("Init rpc Client");
        Map<String, RpcClientProperties> clientMap = new HashMap<>();
        rpcProperties.getRegistry().getClients().stream().forEach(client -> {
            for (int i = 0; i < client.getName().length; i++) {
                clientMap.put(client.getName()[i], client);
            }
        });
        Set<Class<?>> remoteServices = reflections.getTypesAnnotatedWith(RpcService.class, true);
        for (Class<?> remoteService : remoteServices) {
            if (remoteService.isInterface()) {
                String serviceName = remoteService.getName();
                serviceName = serviceName.substring(serviceName.lastIndexOf(".") + 1);
                String pre = serviceName.substring(0, 1).toLowerCase();
                serviceName = pre + serviceName.substring(1);
                try {
                    AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
                    String moudle = remoteService.getName();
                    String arr[] = moudle.split("[.]");
                    String subMoudle = arr[arr.length - 2];
                    RpcClientProperties rpcClient = clientMap.get(subMoudle);
                    if (rpcClient == null) {
                        log.warn("Failed to initialize remoteClient,The " + remoteService.getName()
                                + " RPC client does not configure corresponding server address");
                        continue;
                    }
                    moudle = moudle.replace(".", "/");
                    String serviceUrl = "rmi://" + rpcClient.getHost() + ":" + rpcClient.getPort() + "/" + moudle;
                    BeanDefinitionBuilder builder = BeanDefinitionBuilder
                            .genericBeanDefinition(RmiProxyFactoryBean.class);
                    builder.addPropertyValue("serviceUrl", serviceUrl);
                    builder.addPropertyValue("cacheStub", false);
                    builder.addPropertyValue("lookupStubOnStartup", false);
                    builder.addPropertyValue("serviceInterface", remoteService);
                    DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
                    defaultListableBeanFactory.registerBeanDefinition(serviceName, builder.getBeanDefinition());
                    log.info("The RemoteClient-{} configuration is successful.", serviceName);
                } catch (Exception e) {
                    log.error("Init RemoteClient-{} failed", serviceName, e);
                }
            } else {
                throw new RuntimeException(
                        "Failed to initialize remoteClient,The " + remoteService.getName() + " is not Interface");
            }
        }
    }
}

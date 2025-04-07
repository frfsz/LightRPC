/**
 * 文件名：JccAccepterFactory.java 版权：Copyright © Suzhou Keda Technology Co.Ltd. All Rights Res erved.
 */
package io.github.frfsz.light.rpc.server;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.remoting.rmi.RmiServiceExporter;
import org.springframework.scheduling.annotation.Scheduled;

import io.github.frfsz.light.rpc.core.RpcProperties;
import io.github.frfsz.light.rpc.core.RpcService;

/**
 * @description 初始化时自动初始化所有@RemoteService注解Bean
 * @author fengsheng
 * @since 2020年1月7日
 * @date 2020年1月7日
 */
public class RpcServerFactory {

    private static final Logger log = LoggerFactory.getLogger(RpcServerFactory.class);
    private static Reflections reflections;
    private static Map<String, RmiServiceExporter> regNames = new HashMap<>();

    @Value("${light.rpc.registry.server-host:localhost}")
    private String rmiRegistryHost;

    @Autowired
    private RpcProperties rpcProperties;

    @Autowired
    public void initRemoteServer(ApplicationContext context) throws RemoteException {
        reflections = new Reflections(rpcProperties.getBasePackage());
        int rmiRegistryPort = rpcProperties.getRegistry().getServerPort();
        log.info("Init rpc server");
        Registry registry = LocateRegistry.getRegistry(rmiRegistryHost, rmiRegistryPort);
        try {
            registry.list();
        } catch (ConnectException cn) {
            log.info("There is no RMI proxy service. Create a new proxy service.");
            LocateRegistry.createRegistry(rmiRegistryPort);
        }
        System.setProperty("java.rmi.server.hostname", rmiRegistryHost);
        Set<Class<?>> remoteServices = reflections.getTypesAnnotatedWith(RpcService.class, true);
        remoteServices.stream().forEach(service -> {
            try {
                Set<?> serviceSet = reflections.getSubTypesOf(service);
                if (serviceSet != null && !serviceSet.isEmpty()) {
                    for (Object classObject : serviceSet) {
                        AutowireCapableBeanFactory serviceBeanFactory = context.getAutowireCapableBeanFactory();
                        Class<?> clazz = (Class<?>) classObject;
                        Object serviceImpl = serviceBeanFactory.createBean(clazz);
                        AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
                        RmiServiceExporter exporter = new RmiServiceExporter();
                        exporter.setRegistry(registry);
                        exporter.setServiceInterface(service);
                        exporter.setService(serviceImpl);
                        String moudle = service.getName();
                        RpcService annRemote = service.getAnnotation(RpcService.class);
                        if (null == annRemote) {
                            return;
                        }
                        moudle = moudle.replace(".", "/");
                        exporter.setServiceName(moudle);
                        exporter.afterPropertiesSet();
                        beanFactory.autowireBean(exporter);
                        regNames.put(moudle, exporter);
                        log.info("The RemoteServer-{} configuration is successful.", service.getName());
                        break;
                    }
                } else {
                    log.warn("Init RemoteServer-{} failed,no corresponding implementation class found.",
                        service.getName());
                }
            } catch (Exception e) {
                log.error("Init RemoteServer-{} failed", service.getName(), e);
            }
        });
    }

    /**
     * @description 定时轮询注册表，如发现注册表已不存在，重新创建注册表，重新绑定注册表；如发现注册表中无自身的注册信息则重新注册。
     * @author fengsheng
     * @since 2020年4月22日
     * @date 2020年4月22日
     * @throws RemoteException
     */
    @Scheduled(fixedDelay = 5 * 1000, initialDelay = 30 * 1000)
    public void keepAliveRegistry() throws RemoteException {
        int rmiRegistryPort = rpcProperties.getRegistry().getServerPort();
        Registry registry = null;
        List<String> namesList = null;
        try {
            registry = LocateRegistry.getRegistry(rmiRegistryHost, rmiRegistryPort);
            String bindRegNames[] = registry.list();
            namesList = Arrays.asList(bindRegNames);
        } catch (ConnectException e) {
            log.info("There is no RMI proxy service. Create a new proxy service.");
            registry = LocateRegistry.createRegistry(rmiRegistryPort);
            String bindRegNames[] = registry.list();
            namesList = Arrays.asList(bindRegNames);
        } catch (RemoteException e) {
            log.error("Create a new remote proxy service failed.");
        }
        if (registry != null && namesList != null) {
            for (String reg : regNames.keySet()) {
                if (!namesList.contains(reg)) {
                    RmiServiceExporter service = regNames.get(reg);
                    service.prepare();
                }
            }
        }
    }
}

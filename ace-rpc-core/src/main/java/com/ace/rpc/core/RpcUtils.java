/**
 *com.ace.rpc.core.RpcUtils.java @2021 frank 版权所有
 */
package com.ace.rpc.core;

import org.springframework.remoting.rmi.RmiProxyFactoryBean;

/**
 * @author frank
 * @date 2021年10月10日
 */
public class RpcUtils {

	private RpcUtils() {
	}

	@SuppressWarnings("unchecked")
	public static <T> T getRpcService(String host, int port, Class<T> remoteService) {
		String moudle = remoteService.getName();
		moudle = moudle.replace(".", "/");
		String serviceUrl = String.format("rmi://%s:%s/%s", host, port, moudle);
		RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
		rmiProxyFactoryBean.setServiceInterface(remoteService);
		rmiProxyFactoryBean.setServiceUrl(serviceUrl);
		rmiProxyFactoryBean.setCacheStub(false);
		rmiProxyFactoryBean.afterPropertiesSet();
		return (T) rmiProxyFactoryBean.getObject();
	}
}

package com.ace.rpc.server;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ace.rpc.core.RpcProperties;

@Configuration
@EnableScheduling
@EnableConfigurationProperties(value = RpcProperties.class)
@ConditionalOnProperty(prefix = "ace.rpc", name = "enabled", havingValue = "true")
public class RpcServerConfigration {

	@Bean
	public RpcServerFactory server() {
		return new RpcServerFactory();
	}
}
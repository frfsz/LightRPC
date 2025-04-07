package io.github.frfsz.light.rpc.server;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.github.frfsz.light.rpc.core.RpcProperties;

@Configuration
@EnableScheduling
@EnableConfigurationProperties(value = RpcProperties.class)
@ConditionalOnProperty(prefix = "light.rpc", name = "enabled", havingValue = "true")
@AutoConfigureOrder(Integer.MIN_VALUE + 1)
public class RpcServerConfigration {

	@Bean
	public RpcServerFactory server() {
		return new RpcServerFactory();
	}
}
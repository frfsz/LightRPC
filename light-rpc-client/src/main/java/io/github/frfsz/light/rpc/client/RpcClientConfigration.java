package io.github.frfsz.light.rpc.client;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import io.github.frfsz.light.rpc.core.RpcProperties;

@EnableScheduling
@EnableConfigurationProperties(value = RpcProperties.class)
@ConditionalOnProperty(prefix = "light.rpc", name = "enabled", havingValue = "true")
@AutoConfigureOrder(Integer.MIN_VALUE)
@AutoConfigureBefore(value = { Service.class, Controller.class })
public class RpcClientConfigration {

	@Bean
	public RpcClientFactory client() {
		return new RpcClientFactory();
	}

}
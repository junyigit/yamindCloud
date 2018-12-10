package com.yamind.cloud;

import com.yamind.cloud.common.support.config.MyLocaleResolver;
import com.yamind.cloud.modules.netty.NettyServer;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.net.InetSocketAddress;
import java.util.Locale;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class CloudApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Value("${n.port}")
	private int port;

	@Value("${n.url}")
	private String url;


	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private NettyServer socketServer;
	@Autowired
	RedisTemplate redisTemplate;

	public static void main(String[] args) {
		SpringApplication.run(CloudApplication.class, args);

	}

	@Override//为了打包springboot项目
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}

	@Override
	public void run(String... strings) {

		InetSocketAddress address = new InetSocketAddress(url, port);

		String clientIP = address.getAddress().getHostAddress();

		logger.debug("IP address:"+ clientIP +"url" +url);

		ChannelFuture future = socketServer.run(address);

		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				socketServer.destroy();
			}
		});

		future.channel().closeFuture().syncUninterruptibly();
	}



}

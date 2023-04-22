package com.eduardo.lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.eduardo.*"})
public class LojaVirtualApplication implements AsyncConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(LojaVirtualApplication.class, args);
	}

	@Override
	@Bean
	public Executor getAsyncExecutor() {

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		executor.setCorePoolSize(10);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Assyncrono Thread");
		executor.initialize();

		return executor;
	}

}

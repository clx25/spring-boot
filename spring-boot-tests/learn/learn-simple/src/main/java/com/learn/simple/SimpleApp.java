package com.learn.simple;

import com.learn.simple.config.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import javax.annotation.Resource;

//载入处理ConfigurationProperties的组件
@EnableConfigurationProperties
@SpringBootApplication
public class SimpleApp {
	@Resource
	ConfigProperties configProperties;
	public static void main(String[] args) {
		ConfigurableApplicationContext c = SpringApplication.run(SimpleApp.class, args);
//		Object bean = c.getBean(AutoConfigurationPackages.class.getName());
		SimpleApp simpleApp = c.getBean("simpleApp", SimpleApp.class);
		System.out.println(simpleApp.configProperties);
	}
}

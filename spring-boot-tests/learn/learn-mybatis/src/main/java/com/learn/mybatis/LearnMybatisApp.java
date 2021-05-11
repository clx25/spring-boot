package com.learn.mybatis;

import com.learn.mybatis.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LearnMybatisApp {
	public static void main(String[] args) {
		ConfigurableApplicationContext c = SpringApplication.run(LearnMybatisApp.class, args);
		UserController u = c.getBean("userController", UserController.class);
		u.listUser();
	}
}

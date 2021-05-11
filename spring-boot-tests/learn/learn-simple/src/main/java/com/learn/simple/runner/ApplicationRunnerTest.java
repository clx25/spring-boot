package com.learn.simple.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunnerTest implements ApplicationRunner {
	@Override
	public void run(ApplicationArguments args) throws Exception {
//		throw new Exception("777");
	}
}

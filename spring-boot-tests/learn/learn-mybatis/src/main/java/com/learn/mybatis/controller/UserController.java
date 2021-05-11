package com.learn.mybatis.controller;

import com.learn.mybatis.mapper.UserMapper;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

	@Resource
	private UserMapper userMapper;

	public void listUser(){
		System.out.println(userMapper.listUser(1,2));
	}

}

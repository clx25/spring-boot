package com.learn.simple.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

	@GetMapping
	public String get(){
		return "getrtwhdfgsdr34";
	}
}
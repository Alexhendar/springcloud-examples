package com.example.feignconsumer.web;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.feignconsumer.vo.UserVO;

@RestController
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@GetMapping("/")
	public UserVO home(@RequestParam String name) {
		logger.debug("create user with name: {}",name);
		return new UserVO(RandomUtils.nextLong(),name);
	}
}

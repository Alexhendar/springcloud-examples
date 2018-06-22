package cn.pdmi.platform.pupuser.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.pdmi.platform.pupuser.vo.User;
@RequestMapping("/feign")
@RestController
public class FeignController {

	@RequestMapping(value="/hello",method=RequestMethod.GET)
	public String hello(@RequestParam String name) {
		return "Hello " + name;
	}
	@PostMapping("/user")
	public User addUser(@RequestBody User u) {
		return u;
	}
}

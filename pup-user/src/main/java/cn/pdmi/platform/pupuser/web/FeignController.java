package cn.pdmi.platform.pupuser.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.pdmi.platform.pupuser.service.UserService;
import cn.pdmi.platform.pupuser.vo.User;
import cn.pdmi.platform.pupuser.vo.UserVO;
import net.sf.json.JSONObject;
@RequestMapping("/feign")
@RestController
public class FeignController {
	private static final Logger logger = LoggerFactory.getLogger(FeignController.class);
	@Autowired
	private UserService  feignService;

	@RequestMapping(value="/hello",method=RequestMethod.GET)
	public String hello(@RequestParam String name) {
		logger.info("pup-user : /feign/hello with {}",name);
		return "Hello " + name;
	}
	@PostMapping("/user")
	public User addUser(@RequestBody User u) {
		logger.info("pup-user : /feign/user with {}",JSONObject.fromObject(u));
		return u;
	}
	@RequestMapping("/demo")
	public String demo(@RequestParam String name) {
		UserVO userVO = feignService.findUserByName(name);
		logger.info("feign demo with id : {},and name: {}",userVO.getId(),userVO.getName());
		return userVO.getName();
	}
}

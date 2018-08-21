package cn.pdmi.platform.pupuser.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.pdmi.platform.pupuser.vo.UserVO;

@FeignClient(name="feign-consumer",fallback=UserServiceCallback.class)
public interface UserService {
	@RequestMapping(value="/",method=RequestMethod.GET)
	public UserVO findUserByName(@RequestParam("name") String name);
}

package cn.pdmi.platform.pupuser.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.pdmi.platform.pupuser.vo.UserVO;

@FeignClient(name="feign-consumer",fallback=FeignServiceCallback.class)
public interface FeignService {
	@RequestMapping(value="/",method=RequestMethod.GET)
	public UserVO demo(@RequestParam("name") String name);
}

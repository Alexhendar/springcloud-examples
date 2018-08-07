package cn.pdmi.platform.pupuser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.pdmi.platform.pupuser.vo.UserVO;
@Component
public class FeignServiceCallback implements FeignService {

	private static final Logger logger = LoggerFactory.getLogger(FeignServiceCallback.class);
	
	@Override
	public UserVO demo(String name) {
		logger.info("fall back with feign demo : {}",name);
		
		UserVO user = new UserVO(1L, name);
		return user;
	}

}

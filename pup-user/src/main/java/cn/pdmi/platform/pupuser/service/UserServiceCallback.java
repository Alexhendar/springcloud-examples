package cn.pdmi.platform.pupuser.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.pdmi.platform.pupuser.vo.UserVO;
@Component
public class UserServiceCallback implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceCallback.class);
	
	@Override
	public UserVO findUserByName(String name) {
		logger.info("fall back with feign : {}",name);
		
		UserVO user = new UserVO(1L, name);
		return user;
	}

}

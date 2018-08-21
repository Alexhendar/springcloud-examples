package cn.pdmi.platform.pupuser.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinasofti.oauth2.client.CSIClient;
import com.chinasofti.oauth2.client.model.CSIClientCertification;
import com.chinasofti.oauth2.client.util.Constant;
import com.chinasofti.oauth2.exception.OAuth2Exception;

import cn.pdmi.platform.pupuser.config.PupConfig;
import cn.pdmi.platform.pupuser.vo.JWTToken;
import net.sf.json.JSONObject;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
	
	@Autowired
	private PupConfig pupConfig;
	@Autowired
	private RsaSigner signer;

	/**
	 * 根据code从pup获取accesstoken接口，生成的accesstoken和userid等信息，计算返回JWT
	 * @param code
	 * @return
	 */
	@GetMapping("/accesstoken")
	public JWTToken accesstoken(@RequestParam String code,HttpServletRequest request,HttpServletResponse response) {
		String userinfo = "";
		if(StringUtils.isEmpty(code)){
			// 返回提示，client重新引导用户登录
			logger.info("code 为空,url: {} {}",request.getRequestURI(),request.getQueryString());
			//TODO 设置统一的状态，包装token
		}
		CSIClient client=new CSIClient(pupConfig,request,response);
		try {
			userinfo = client.bindUserInfo(code);
		} catch (OAuth2Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
		CSIClientCertification cSICC=(CSIClientCertification)request.getSession().getAttribute(Constant.SESSIONAUTHENTICATIONKEY);
		String accesstoken = cSICC.getAccessToken();
		JSONObject userObject = JSONObject.fromObject(userinfo);
		userObject.put("accesstoken", accesstoken);
		// 设置1天有效时间
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		userObject.put("exp", sdf.format(cal.getTime()));
		userObject.put("iat", sdf.format(new Date()));
		
	/*	userObject.put("iss", "");	// jwt签发者
		userObject.put("sub", "");	// jwt所面向的用户
		userObject.put("aud", "");	// 接收jwt的一方
		userObject.put("nbf", "");	// 接收jwt的一方
		userObject.put("jti", "");	// jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
*/		
		
		Jwt jwt = JwtHelper.encode(userObject.toString(), signer);
		
		JWTToken token = new JWTToken();
		token.setToken(jwt.getEncoded());
		return token;
	}
	/**
	 * 退出当前系统的应用状态，单点登录的状态由app client处理
	 * @return 当前系统退出登录处理结果
	 */
	@PutMapping("/logout")
	public String logout() {
		return "logout";
	}
	@GetMapping("/pup")
	public String pup(@RequestParam String code,HttpServletRequest request,HttpServletResponse response) {
		String userinfo = "";
		if(StringUtils.isEmpty(code)){
			// 返回提示，client重新引导用户登录
			logger.info("code 为空,url: {} {}",request.getRequestURI(),request.getQueryString());
			return "";
		}
		CSIClient client=new CSIClient(pupConfig,request,response);
		try {
			userinfo = client.bindUserInfo(code);
		} catch (OAuth2Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
		CSIClientCertification cSICC=(CSIClientCertification)request.getSession().getAttribute(Constant.SESSIONAUTHENTICATIONKEY);
		String accesstoken = cSICC.getAccessToken();
		return userinfo + accesstoken;
	}
	@GetMapping("/pupsecret")
	public String pupConfig() {
		return pupConfig.getClientSecret();
	}
	
}

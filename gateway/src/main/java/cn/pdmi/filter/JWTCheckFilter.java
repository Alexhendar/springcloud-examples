package cn.pdmi.filter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import net.sf.json.JSONObject;
import reactor.core.publisher.Mono;

@Service
public class JWTCheckFilter {

	private static final Logger logger = LoggerFactory.getLogger(JWTCheckFilter.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");

	@Autowired
	private RsaVerifier verifier;
	
	@Bean
	public WebFilter jwtCheckFilter() {
		return (ServerWebExchange ctx, WebFilterChain chain) -> {
			boolean hasAuthorized = false;
			ServerHttpRequest request = ctx.getRequest();
			ServerHttpResponse response = ctx.getResponse();
			if (request.getURI().toString().contains("/user/")) {
				hasAuthorized = true;
			}
			logger.info("intercept request for jwtcheck with url: " + request.getURI());
			if (!hasAuthorized) {
				HttpHeaders headers = request.getHeaders();
				if (headers.containsKey("Authorization")) {
					List<String> authList = headers.get("Authorization");
					for (String bearerToke : authList) {
						if (verifyToken(bearerToke)) {
							// token有效，终止循环
							hasAuthorized = true;
							break;
						}
					}
				}
			}
			if (!hasAuthorized) {
				logger.info("unauthorized!");
				response.setStatusCode(HttpStatus.UNAUTHORIZED);
				return Mono.empty();
			}
			return chain.filter(ctx);
		};
	}

	boolean verifyToken(String bearerToke) {
		boolean verifyResult = false;
		String token = bearerToke.substring("Bearer ".length());
		try {
			Jwt jwt = JwtHelper.decodeAndVerify(token, verifier);
			System.out.println(jwt.getClaims());
			JSONObject json = JSONObject.fromObject(jwt.getClaims());
			String exp = (String) json.get("exp");
			System.out.println(exp);
			Date date = sdf.parse(exp);
			if (date.after(new Date())) {
				// JWT 验证有效
				verifyResult = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return verifyResult;
	}
}

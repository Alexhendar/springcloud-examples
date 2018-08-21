package cn.pdmi.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
public class FilterConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(FilterConfiguration.class);

	// 这里为支持的请求头，如果有自定义的header字段请自己添加（不知道为什么不能使用*）
	private static final String ALLOWED_HEADERS = "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN,token,username,client";
	private static final String ALLOWED_METHODS = "*";
	private static final String ALLOWED_ORIGIN = "*";
	private static final String ALLOWED_Expose = "*";
	private static final String MAX_AGE = "18000L";

	@Bean
	public WebFilter corsFilter() {
		return (ServerWebExchange ctx, WebFilterChain chain) -> {
			ServerHttpRequest request = ctx.getRequest();
			logger.info("intercept request with uri : " + request.getURI());
			if (CorsUtils.isCorsRequest(request)) {
				ServerHttpResponse response = ctx.getResponse();
				HttpHeaders headers = response.getHeaders();
				headers.add("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
				headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
				headers.add("Access-Control-Max-Age", MAX_AGE);
				// https://www.jianshu.com/p/f33e7f94dc06 火狐浏览器兼容性
				headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
				headers.add("Access-Control-Expose-Headers", ALLOWED_Expose);
				headers.add("Access-Control-Allow-Credentials", "true");
				if (request.getMethod() == HttpMethod.OPTIONS) {
					response.setStatusCode(HttpStatus.OK);
					return Mono.empty();
				}
			}
			return chain.filter(ctx);
		};
	}

	
}

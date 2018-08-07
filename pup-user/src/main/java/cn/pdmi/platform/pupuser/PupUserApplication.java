package cn.pdmi.platform.pupuser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.jwt.crypto.sign.RsaSigner;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
public class PupUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(PupUserApplication.class, args);
	}

	@Bean
	public RsaSigner jwtSigner() {
		String privateKey = StringUtils.EMPTY;
		try {
			InputStream ips = PupUserApplication.class.getResourceAsStream("/id_rsa");
			// File keyFile = ResourceUtils.getFile("classpath:id_rsa");
			InputStreamReader reader = new InputStreamReader(ips);
			StringBuffer buffer = new StringBuffer();
			int tmp;
			while ((tmp = reader.read()) != -1) {
				buffer.append((char) tmp);
			}
			privateKey = buffer.toString();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		RsaSigner signer = new RsaSigner(privateKey);
		return signer;
	}
	
	 //用来拦截处理HystrixCommand注解
    @Bean
    public HystrixCommandAspect hystrixAspect() {
        return new HystrixCommandAspect();
    }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean getServlet() {
		HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet,"/actuator/hystrix.stream");
		registrationBean.setLoadOnStartup(1);
		registrationBean.setName("HystrixMetricsStreamServlet");
		return registrationBean;
	}
}

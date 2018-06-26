package cn.pdmi;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RsaVerifier rsaVerifier() {
		String pubKey = StringUtils.EMPTY;
		try {
			InputStream ips = GatewayApplication.class.getResourceAsStream("/id_rsa.pub");
			// File keyFile = ResourceUtils.getFile("classpath:id_rsa");
			InputStreamReader reader = new InputStreamReader(ips);
			StringBuffer buffer = new StringBuffer();
			int tmp;
			while ((tmp = reader.read()) != -1) {
				buffer.append((char) tmp);
			}
			pubKey = buffer.toString();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		RsaVerifier verifier = new RsaVerifier(pubKey);
		return verifier;
	}
}

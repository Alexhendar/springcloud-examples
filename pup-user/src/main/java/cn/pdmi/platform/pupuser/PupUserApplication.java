package cn.pdmi.platform.pupuser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.jwt.crypto.sign.RsaSigner;

@SpringBootApplication
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
}

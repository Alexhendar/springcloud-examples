package cn.pdmi.platform.pupuser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import net.sf.json.JSONObject;


public class JsonParserTest {


	@Test
	public void test() {
		String jsonString = "{\"openId\":\"NTFhODBkMzZkODliNDM4ZmE3MWFjNWI3N2FmMmIxMjFAYzliYTg0MWRiMjI3NDEwZGFlYmRiOGE3N2QyMmY5M2Q=\",\"userId\":\"51a80d36d89b438fa71ac5b77af2b121\",\"username\":\"张俊永\",\"account\":\"zhangjunyong\"}";
		JSONObject json = JSONObject.fromObject(jsonString);
		
		System.out.println(json.get("openId"));
		System.out.println(json.get("userId"));
		System.out.println(json.get("username"));
		System.out.println(json.get("account"));
		System.out.println(json.toString());
	}

	@Test
	public void testPrivateKey() {
		String privateKey = StringUtils.EMPTY;
		try {
			InputStream ips = JsonParserTest.class.getResourceAsStream("/id_rsa");
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
		
		System.out.println(privateKey);
	}
}

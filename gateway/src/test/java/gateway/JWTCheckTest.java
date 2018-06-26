package gateway;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import net.sf.json.JSONObject;

public class JWTCheckTest {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS");
	@Test
	public void testDateFormat() {
		Date date = new Date();
		System.out.println(sdf.format(date));
	}

	@Test
	public void test() {
		String bearer = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcGVuSWQiOiJOVEZoT0RCa016WmtPRGxpTkRNNFptRTNNV0ZqTldJM04yRm1NbUl4TWpGQVl6bGlZVGcwTVdSaU1qSTNOREV3WkdGbFltUmlPR0UzTjJReU1tWTVNMlE9IiwidXNlcklkIjoiNTFhODBkMzZkODliNDM4ZmE3MWFjNWI3N2FmMmIxMjEiLCJ1c2VybmFtZSI6IuW8oOS_iuawuCIsImFjY291bnQiOiJ6aGFuZ2p1bnlvbmciLCJhY2Nlc3N0b2tlbiI6ImtCZTJCaFN2cnhnUWw5emJQWnllSFBMRjNCSTJCOWNyMWh1bzJCdWJ2TnhkdGNXdHp3bldaZENXaHI0WVJxQlp5dm1QanZNenVleU9BT01qT3pGZFI2Y01DSGNYL1N4MkJKVXBVVC9kbnVYQjB3UGxJcWVUY0dndHZqamF0NDJCUTZ0WGVLZWRibjZnZlk5NFZsdTJCUWdKMjFvUnd6MlZkZXdhUklSRGN3S3BDUlFJUT0iLCJleHAiOiIyMDE4LTA2LTI3IDEwOjU0OjQwOjk1OCIsImlhdCI6IjIwMTgtMDYtMjYgMTA6NTQ6NDA6OTU4In0.DqMT4RW4tiEfqt1HpSW_FOLL55AKVKrm06ICeb3hRnfOQKpoEwU-qv93tzkU5XcXMZs_18_wo8eIvuFA4kgwyMKFAHsi9fD6rsCKL97kwM53SVM3KSnz7s1lukm-cK3vUNPSxqO80MPxO4VISxXabFq8lVU3ywLdOLo7BWXmGWau9U2Dn3fCG7MKUidR5_3R6gjVvGJm2azSHlRWqSvHxm2I6tbkClylJAvr4_jyeGVvH9uTt5EWRxnHYjVcwJWjvzlZHK_spJbG1tBJYZ05EYbAFdgFhQWYX1SaJ7GRhHol86M54tp3_cHvBFsmbeVCreMMk-cZK2XvFusEYGKesQ";
//		System.out.println(bearer.substring("Bearer ".length()));
		System.out.println(bearer.replace("Bearer ", ""));
		String token = bearer.substring("Bearer ".length());
		
		String pubKey = StringUtils.EMPTY;
		try {
			InputStream ips = JWTCheckTest.class.getResourceAsStream("/id_rsa.pub");
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
		try {
			Jwt jwt = JwtHelper.decodeAndVerify(token, verifier);
			System.out.println(jwt.getClaims());
			
			JSONObject json = JSONObject.fromObject(jwt.getClaims());
			String exp = (String) json.get("exp");
			System.out.println(exp);
			Date date = sdf.parse(exp);
			if(!date.after(new Date())) {
				System.out.print("日期无效");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}

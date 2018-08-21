package cn.pdmi.platform.pupuser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.chinasofti.oauth2.client.model.OAuth2Config;

/**
 * 从Spring Cloud Config加载配置信息
 * 
 * @author Alexhendar
 *
 */
@Component
public class PupConfig extends OAuth2Config {
	@Value("${client_id}")
	private String clientId;
	@Value("${client_secret}")
	private String clientSecret;
	@Value("${redirect_url}")
	private String redirectURL;
	@Value("${authorize_url}")
	private String authorizeURL;
	@Value("${access_token_url}")
	private String accessTokenURL;
	@Value("${api_url}")
	private String apiURL;
	@Value("${user_info_url}")
	private String userinfoURL;
	@Value("${logout_url}")
	private String logoutURL;
	@Value("${login.logoutRedirectURL}")
	private String isToRedirectUrl;

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public String getRedirectURL() {
		return redirectURL;
	}

	public String getAuthorizeURL() {
		return authorizeURL;
	}

	public String getAccessTokenURL() {
		return accessTokenURL;
	}

	public String getApiURL() {
		return apiURL;
	}

	public String getUserinfoURL() {
		return userinfoURL;
	}

	public String getLogoutURL() {
		return logoutURL;
	}

	public String getIsToRedirectUrl() {
		return isToRedirectUrl;
	}

	public String getProperty(String key) {
		switch (key) {
		case "client_id":
			return this.clientId;
		case "client_secret":
			return this.clientSecret;
		case "redirect_url":
			return this.redirectURL;
		case "authorize_url":
			return this.clientId;
		case "access_token_url":
			return this.accessTokenURL;
		case "api_url":
			return this.apiURL;
		case "user_info_url":
			return this.userinfoURL;
		case "logout_url":
			return this.logoutURL;
		case "login.logoutRedirectURL":
			return this.isToRedirectUrl;
		}
		return "";
	}

}

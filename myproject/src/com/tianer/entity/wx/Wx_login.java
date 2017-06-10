package com.tianer.entity.wx;

/**
 * 微信登录信息
 * @author Administrator
 *
 */
public class Wx_login {
	private String login_id;
 	private String wx_openid;   
	private String access_token;
	public String getWx_openid() {
		return wx_openid;
	}
	public void setWx_openid(String wx_openid) {
		this.wx_openid = wx_openid;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	@Override
	public String toString() {
		return "Wx_login [wx_openid=" + wx_openid + ", access_token="
				+ access_token + "]";
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}  
	 
	
	
}

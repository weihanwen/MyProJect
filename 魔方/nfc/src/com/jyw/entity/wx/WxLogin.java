package com.jyw.entity.wx;

 

/**
 * 微信登录用户
 */
public class WxLogin {
	private String WXMEMBER_ID;		//用户id
	private String SHOWLOOK_ID;		//用户id
	private String NAME;	//用户名
	private String IMAGE_URL; 	//密码
	private String SEX;		//姓名
	private String OPEN_ID;		//权限
 	public String getWXMEMBER_ID() {
		return WXMEMBER_ID;
	}
	public void setWXMEMBER_ID(String wXMEMBER_ID) {
		WXMEMBER_ID = wXMEMBER_ID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getIMAGE_URL() {
		return IMAGE_URL;
	}
	public void setIMAGE_URL(String iMAGE_URL) {
		IMAGE_URL = iMAGE_URL;
	}
	public String getSEX() {
		return SEX;
	}
	public void setSEX(String sEX) {
		SEX = sEX;
	}
	public String getOPEN_ID() {
		return OPEN_ID;
	}
	public void setOPEN_ID(String oPEN_ID) {
		OPEN_ID = oPEN_ID;
	}
	public String getSHOWLOOK_ID() {
		return SHOWLOOK_ID;
	}
	public void setSHOWLOOK_ID(String sHOWLOOK_ID) {
		SHOWLOOK_ID = sHOWLOOK_ID;
	}
	  
	
}

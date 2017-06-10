package com.tianer.util.rsa;

import com.tianer.util.StringUtil;


public class RSAConstants {
 
	public static final String KEY_ALGORITHM = "RSA";   
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    

	public static String macid="admin";  //运营商账号/编号
		
    public static final String PUBLIC_KEY = "RSAXsCpPublicKey";   
    public static final String PRIVATE_KEY = "RSAXsCpPrivateKey"; 
    
	public static String md5macid="hzxs"; //运营商账号/编号 
	public static String md5key="Nmd38mAhN1t4GPSHItbIkwf88HVbqEDN"; 
    
	//公钥文件
	public static final String PUBLIC_KEY_FILE = StringUtil.getUrl()+"rsa/pubkey.pem";
	
	//私钥文件
	public static final String PRIVATE_KEY_FILE = StringUtil.getUrl()+"rsa/privkey_pkcs8.pem";
	
}

package com.tianer.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tianer.util.Const;
import com.tianer.util.XmlUtil;
import com.tianer.util.rsa.RSACoder;
import com.tianer.util.rsa.RSACoderTest;
/**
 * 
* 类名称：WebAppContextListener.java
* 类描述： 
* 作者单位： 
* 联系方式：
* @version 1.0
 */
public class WebAppContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
	}

	public void contextInitialized(ServletContextEvent event) {
		Const.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		//System.out.println("========获取Spring WebApplicationContext");
		Const.APPVALIDATION = XmlUtil.initAppValidation();
		try {
			//初始化的key
			RSACoderTest.keyMap = RSACoder.initKey();
			RSACoderTest.privateKey = RSACoder.getPrivateKey(RSACoderTest.keyMap);  
	        //System.err.println("私钥： \n\r" + privateKey);   
			RSACoderTest.publicKey = RSACoder.getPublicKey(RSACoderTest.keyMap);   
	        //System.err.println("公钥: \n\r" + publicKey); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}

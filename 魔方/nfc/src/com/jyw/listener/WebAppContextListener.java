package com.jyw.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jyw.util.Const;
import com.jyw.util.XmlUtil;
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
	}

}

package com.tianer.util;
 
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.tianer.entity.system.AppValidation;
/**
 * 项目名称：
 * @author:tianer
 * 
*/
public class Const {
	
	public static final String SESSION_WXLOGIN = "wx_session";	//微信用户对象
	public static final String SESSION_WXLOGIN_ID = "session_wxlogin_id";	//微信用户ID
	public static final String PSIYAO   = "ping/your_rsa_private_key.pem";		//P++私钥
	public static final String shiyou_pay = "shiyou";	 
 	public static final String liuliang_pay = "liuliang";	 
 	public static final double minpay_shiyou_money = 100;
	public static final String SESSION_ORDER = "session_orderid";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//=================
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	
	public static final String SESSION_menuList = "menuList";			//当前菜单
	public static final String SESSION_allmenuList = "allmenuList";		//全部菜单
	
	public static final String SESSION_QX = "QX";
	public static final String SESSION_userpds = "userpds";			
	
	public static final String SESSION_USERROL = "USERROL";				//用户对象
	public static final String SESSION_USERNAME = "USERNAME";			//用户名
	
	public static final String JYIP = "T";//禁用Ip
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	public static final String LOGIN = "/login_toLogin.do";				//登录地址
	
	public static final String SYSNAME = "admin00/head/SYSNAME.txt";	//系统名称路径
	public static final String PAGE	   = "admin00/head/PAGE.txt";		//分页条数配置路径
	public static final String EMAIL   = "admin00/head/EMAIL.txt";		//邮箱服务器配置路径
	public static final String SMS1   = "admin00/head/SMS1.txt";		//短信账户配置路径1
	public static final String SMS2   = "admin00/head/SMS2.txt";		//短信账户配置路径2
	public static final String ALGORITHM   = "admin00/head/SMS2.txt";	//公式
	
 	
	public static final String FILEPATH = "uploadify/uploads/";			//文件上传路径
	public static final String AVATARFILEPATH = "uploadify/uploads/avatar/";	//头像文件上传路径
	public static final String AREAFILEPATH = "uploadify/uploads/area/";	//头像文件上传路径
	
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(websocket)|(shiyou)|(payorder)|(payback)|(goods)|(licai)).*";	//不对匹配该值的访问路径拦截（正则）
	public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化
	
	/**
	 * APP Constants
	 */
	//app注册接口_请求协议参数)
	public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[]{"countries","uname","passwd","title","full_name","company_name","countries_code","area_code","telephone","mobile"};
	public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[]{"国籍","邮箱帐号","密码","称谓","名称","公司名称","国家编号","区号","电话","手机号"};
	
	//app登录接口_请求协议中的参数
	public static final String[] APP_LOGIN_PARAM_ARRAY = new String[]{"uname","passwd"};
	public static final String[] APP_LOGIN_VALUE_ARRAY = new String[]{"邮箱账号","密码"};
	
	//app登录状态接口_请求协议中的参数
	public static final String[] APP_LOGINSTATUS_PARAM_ARRAY = new String[]{"app_id","status"};
	public static final String[] APP_LOGINSTATUS_VALUE_ARRAY = new String[]{"app登录用户ID","登录状态"};	
	
	//忘记密码,查找用户账户是否存在接口_请求协议中的参数
	public static final String[] APP_FORGOTPASSWORD_PARAM_ARRAY = new String[]{"uname"};
	public static final String[] APP_FORGOTPASSWORD_VALUE_ARRAY = new String[]{"邮箱账号"};
	
	//APP参数访问控制
	public static Map<String,HashMap<String, AppValidation>> APPVALIDATION = new HashMap<String,HashMap<String, AppValidation>>();
	

	

	
}

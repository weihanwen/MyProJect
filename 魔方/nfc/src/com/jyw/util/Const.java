package com.jyw.util;
 
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.jyw.entity.system.AppValidation;
/**
 * 项目名称：
 * @author:tianer
 * 
*/
public class Const {
	
	//模板
		/*
		 * cast(cast((ifnull(a.sale_money,'0')) as decimal(10,2)) as char)  sale_money
		 * date_format(a.createtime ,'%Y-%m-%d %H:%i:%s') as createtime,
		 * ServiceHelper.getAppPcdService().saveLog(order_id, message,"10");
		 * onkeyup="value=value.replace(/[^\d\.]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" 
		 * (cast(round( 6378.138 * 2 * asin( sqrt( pow( sin(( abs(b.latitude  * pi() / 180 - #{latitude} * pi() / 180) ) / 2 ), 2 ) + cos(b.latitude  * pi() / 180) * cos( #{latitude} * pi() / 180) * pow( sin(( abs(b.longitude * pi() / 180 - #{longitude}  * pi() / 180) ) / 2 ), 2 ))) ,3) as  decimal(10,1))) as char 
		 * 手机验证码用shiro-seesion，图形验证码用http-session存储
		 * 
		 * to_days(now()) &gt; to_days(endtime) 
		 * 
		 */
	
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	
	public static final String SESSION_menuList = "menuList";			//当前菜单
	public static final String SESSION_allmenuList = "allmenuList";		//全部菜单
	
	public static final String SESSION_QX = "QX";
	public static final String SESSION_userpds = "userpds";			
	
	public static final String SESSION_USERROL = "USERROL";				//用户对象
	public static final String SESSION_USERNAME = "USERNAME";			//用户名
	
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	public static final String LOGIN = "/login_toLogin.do";				//登录地址
	
	public static final String SYSNAME = "admin00/head/SYSNAME.txt";	//系统名称路径
	public static final String PAGE	   = "admin00/head/PAGE.txt";		//分页条数配置路径
	public static final String EMAIL   = "admin00/head/EMAIL.txt";		//邮箱服务器配置路径
	public static final String SMS1   = "admin00/head/SMS1.txt";		//短信账户配置路径1
	public static final String SMS2   = "admin00/head/SMS2.txt";		//短信账户配置路径2
	public static final String SMS3   = "admin00/head/sendtime.txt";		//下单之后送货时间
	public static final String SMS4   = "admin00/head/arrvertime.txt";		//送货之后预计到达时间
	public static final String ALGORITHM   = "admin00/head/SMS2.txt";	//公式
	
	public static final String FILEPATH = "uploadify/uploads/";			//文件上传路径
	public static final String AVATARFILEPATH = "uploadify/uploads/avatar/";	//头像文件上传路径
	public static final String AREAFILEPATH = "uploadify/uploads/area/";	//头像文件上传路径
	
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(websocket)|(img)|(wx)).*";	//不对匹配该值的访问路径拦截（正则）
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
	

	
	//开始设置参数
	public static  String sendtime=Tools.readTxtFile(Const.SYSNAME);//下单之后多久可以送货
	public static  String arrivetime=Tools.readTxtFile(Const.SYSNAME);//送货多久可以到达
	
	
	//微信登录人的key
	public static final String WXLOGIN = "wxlogin";
	public static final String WxLOGIN_URL = "wxlogin/htmlWxLogin.do";	 //微信登录地址wxlogin/toLoginWx.do
	//存储暂时的一些参数
	public static String access_token="";
	public static String jsapi_ticket="";
 
	
}

package com.tianer.controller.base;


import java.text.DecimalFormat;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.entity.Page;
import com.tianer.util.DateUtil;
import com.tianer.util.Logger;
import com.tianer.util.PageData;
import com.tianer.util.UuidUtil;

public class BaseController {
	
	public Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;
	
	protected Lock lock = new ReentrantLock();//锁， lock.lock();， lock.unlock();
	
	public static DecimalFormat    df3   = new DecimalFormat("######0.000"); 
	public static DecimalFormat    df2   = new DecimalFormat("######0.00"); 
	public static DecimalFormat    df1   = new DecimalFormat("######0.0"); 
	public static DecimalFormat    df0   = new DecimalFormat("######0"); 

	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();	
		return request;
	}

	/**
	 * 得到32位的uuid
	 * @return
	 */
	public static String get32UUID(){
		return UuidUtil.get32UUID();
	}
	
	/**
	 * 得到32位的uuid
	 * @return
	 */
	public static   String UUID(){
		return UuidUtil.get32UUID();
	}
	
	
	/**
	 * 得到八位的id
	 */
	public static String get8UID(){
			String str = "";
 			for(int i = 0; i < 8; i++){
				str += (int)(Math.random()*10);
			}
		return str;
	}
	
	
	/**
	 * 得到四位的id
	 */
	public String get4UID(){
			String str = "";
 			for(int i = 0; i < 4; i++){
				str += (int)(Math.random()*10);
			}
		return str;
	}
	
	/**
	 * 充值单号
	 */
	public static String getCZUID(){
			String str = "";
			DateUtil date = new DateUtil();
			String da = date.getDayshms();
			int num = (int)((Math.random()*9+1)*1000);
			//组合为编号
			str = "CZ"+da+""+num;
		return str;
	}
	
	
	/**
	 * 红包id
	 */
	public static String getREDUID(){
			String str = "";
			DateUtil date = new DateUtil();
			String da = date.getDayshms();
			int num = (int)((Math.random()*9+1)*1000);
			//组合为编号
			str = "RED"+da+""+num;
		return str;
	}
	
	
	/**
	 * 支付单号
	 */
	public  static String getXFUID(){
			String str = "";
			DateUtil date = new DateUtil();
			String da = date.getDayshms();
			int num = (int)((Math.random()*9+1)*1000);
			//组合为编号
			str = "XF"+da+""+num;
		return str;
	}
	
	/**
	 * 提现单号单号
	 */
	public static String getTXUID(){
			String str = "";
			DateUtil date = new DateUtil();
			String da = date.getDayshms();
			int num = (int)((Math.random()*9+1)*1000);
			//组合为编号
			str = "TX"+da+""+num;
		return str;
	}
	
	
	/**
	 * 得到十位的id
	 */
	public static String get10UID(){
			String str = "";
 			for(int i = 0; i < 10; i++){
				str += (int)(Math.random()*10);
			}
		return str;
	}
	
	
	/**
	 * 得到随机九位字母
	 */
	public String get9ZM(){
		String chars = "abcdefghijklmnopqrstuvwxyz";
 		String str = "";
 		for(int i = 0; i < 9; i++){
			str += chars.charAt((int)(Math.random() * 26));
		}
		return str;
	}
	
	/**
	 * 得到随机4位字母或数字
	 */
	public String get4ZMSZ(){
		String chars = "abcdefghijklmnopqrstuvwxyz12345678901234567890";
 		String str = "";
 		for(int i = 0; i < 4; i++){
			str += chars.charAt((int)(Math.random() * 46));
		}
		return str;
	}
	
	/**
	 * 得到时间类型id
	 */
	public static String getTimeID(){
			String str = DateUtil.getDayshms()+  (int)((Math.random()*9+1)*1000);
		return str;
	}
	
	
	/**
	 * 得到反馈类型id
	 */
	public static String getFanKuiID(){
			String str ="FK"+ DateUtil.getDayshms()+  (int)((Math.random()*9+1)*1000);
		return str;
	}
	
	/**
	 * 得到6位的id
	 */
	public static String get6UID(){
			String str = "";
			str += (int)(Math.random()*9+1);
			for(int i = 0; i < 5; i++){
				str += (int)(Math.random()*10);
			}
		return str;
	}
	
	 
	
	
	//主函数
	public static void main(String[] msg){
		BaseController b=new BaseController();
//		String str=b.get6UID();
//		System.out.println((int)(Math.random()*4));
//		String tihuo_id="1234567890";
//		String id=tihuo_id.substring(0, 2)+"  "+tihuo_id.substring(2, 6)+"  "+tihuo_id.substring(6, 10);
		System.out.println(b.getTimeID());
	}
	
	/**
	 * 得到分页列表的信息 
	 */
	public Page getPage(){
		
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}
	
	
}

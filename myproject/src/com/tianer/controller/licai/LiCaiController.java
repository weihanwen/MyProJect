package com.tianer.controller.licai;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.wx.Wx_login;
import com.tianer.service.licai.LiCaiService;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.SmsUtil;
import com.tianer.util.StringUtil;
import com.tianer.util.bankpay.MingShengUtil;
import com.tianer.util.bankpay.SignEncryptDncryptSignChk;
import com.tianer.util.wx.WxUtil;
import com.tianer.util.wx.WxpubOAuth;
  
/** 
 * 油卡控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/licai")
public class LiCaiController extends BaseController {
	
	@Resource(name="liCaiService")
	private LiCaiService liCaiService;
 	
	  
	/**
	 * 微信登录授权页面
	 * licai/toLoginWx.do 
	 * 
	 * 客户经理电话 manager_phone
	 * 内容 text_infor
 	 */
	@RequestMapping(value="/toLoginWx")
	public void toLoginWx(HttpServletRequest request,HttpServletResponse response){
		String code = "";
		try {
			String manager_phone=request.getParameter("manager_phone");
			String text_infor=request.getParameter("text_infor");
			code = WxpubOAuth.createOauthUrlForCode(WxUtil.APP_ID, WxUtil.HOST+"/licai/htmlWxLogin.do?text_infor="+text_infor+"&manager_phone="+manager_phone, true);
  			response.sendRedirect(code); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * licai/htmlWxLogin.do?code=
	 *  授权完直接登录
	 *  1：未注册过的先得获取手机验证码
	 *  2：已注册直接前往首页
	 *  
	 *  返回值：用户信息  
	 *  session存储 wxlogin_id   shiro存储 HtmlUser
	 *  
	 */
	@RequestMapping(value="/htmlWxLogin")
	public ModelAndView HtmlWxLogin(HttpServletRequest request)throws Exception{
		ModelAndView mv = this.getModelAndView();
  		PageData pd = new PageData();
   		try {
   				pd=this.getPageData();
   				String code=pd.getString("code");
     			pd = WxpubOAuth.getOpenId(pd,WxUtil.APP_ID, WxUtil.APP_SECRET, code);
    			if(pd.getString("wxopen_id") == null || pd.getString("wxopen_id").equals("")){
    				mv.setViewName("redirect:goErrorJsp.do");
   			 		return mv;
   				} 
     			//根据openid判断是否存在用户
//    			String manager_phone=pd.getString("manager_phone");
//    			String text_infor=pd.getString("text_infor");
    			pd.put("wx_user_openid", pd.getString("wxopen_id"));
  				if(liCaiService.findById(pd) != null){
  					pd.put("appointment_phone", liCaiService.findById(pd).getString("phone"));
  					//新增预约信息
  					liCaiService.saveLiCaiInfor(pd);
  					//发送短信
//  				SmsUtil.sendZcCode(phone, code);
  					mv.setViewName("licai/yuyueok");
  				}else{
  				//获取用户的一些信息
  	    			pd=WxpubOAuth.getUserInforForNotGuanZhu(pd,pd.getString("wxopen_id"),pd.getString("access_token"));//获取未关注的用户信息
  					mv.addObject("pd", pd);
  					mv.setViewName("licai/getcode");
  				}
      		} catch (Exception e) {
   				e.printStackTrace();
 		}
  		return mv;
	}
	
	

	 /**
	  * 获取验证码
	  * licai/getCode.do
	  * 
	  * phone 
	  */
	@RequestMapping(value="/getCode")
	@ResponseBody
	public Object getCode(String phone) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
 		String code=StringUtil.getSixRandomNum();
		try {
//			SmsUtil.sendZcCode(phone, code);
			//将验证码放到session里
 		    SecurityUtils.getSubject().getSession().setAttribute("ZHUCE_CODE" , code);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
		}
		return map;
	 }
	
	
	/**
	 *  licai/saveOK.do 
	 *  获取验证码之后直接登录
	 *  
	 *  messagecode   验证码
 	 *  text_infor	  信息
	 *  manager_phone	经理电话
 	 *  wx_user_openid 微信openid
	 *  image_url	头像
	 *  name	姓名
	 *  phone  客户电话
 	 *  
	 */
	@RequestMapping(value="/saveOK")
	@ResponseBody
	public Object saveOK()throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
  		PageData pd = new PageData();
  		String result="1";
  		String message="ok";
   		try {
   			pd=this.getPageData();
   			String messagecode=pd.getString("messagecode");
   			String sessioncode=(String) SecurityUtils.getSubject().getSession().getAttribute("ZHUCE_CODE");
			if(!messagecode.equals(sessioncode)){
				result="0";
				message="验证码错误";
			}
   			liCaiService.saveUser(pd); 
    		//新增预约信息
   			pd.put("appointment_phone", pd.getString("phone"));
			liCaiService.saveLiCaiInfor(pd);
			//发送短信给客户经理
//			SmsUtil.sendZcCode(phone, code);
       	} catch (Exception e) {
   				e.printStackTrace();
 		}
   		map.put("result", result);
   		map.put("message", message);
  		return map;
	}
	

 
	//licai/goyuyueok.do
	@RequestMapping(value="/goyuyueok")
	public ModelAndView goyuyueok(HttpServletRequest request)throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("licai/yuyueok"); 
   		return mv;
	}
	//licai/gogetcode.do
	@RequestMapping(value="/gogetcode")
	public ModelAndView gogetcode(HttpServletRequest request)throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("licai/getcode"); 
   		return mv;
	}
	
	
 
 
}

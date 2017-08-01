package com.jyw.controller.wx;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
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

import com.jyw.controller.base.BaseController;
import com.jyw.entity.wx.WxLogin;
import com.jyw.service.business.Carousel_figureService;
import com.jyw.service.business.CategoryService;
import com.jyw.service.business.Daily_menuService;
import com.jyw.service.wx.WxmemberService;
import com.jyw.util.Const;
import com.jyw.util.DateUtil;
import com.jyw.util.PageData;
import com.jyw.util.wxpay.WxUtil;
import com.jyw.util.wxpay.WxpubOAuth;
 
 
/** 
 * 
* 类名称：WxloginController   
* 类描述：  登录页面
* 创建人：魏汉文  
* 创建时间：2016年5月26日 下午3:46:49
 */
@Controller("wxloginController")
@RequestMapping(value="/wxlogin")
public class WxloginController extends BaseController {
	

	@Resource(name="wxmemberService")
	private WxmemberService wxmemberService;
	@Resource(name="carousel_figureService")
	private Carousel_figureService carousel_figureService;//轮播图
 	@Resource(name="categoryService")
	private CategoryService categoryService;//类别集合
 	@Resource(name="daily_menuService")
	private Daily_menuService daily_menuService;
	
	/**
	 * 微信登录授权页面
	 * wxlogin/toLoginWx.do 
 	 */
	@RequestMapping(value="/toLoginWx")
	public void toLoginWx(HttpServletRequest request,HttpServletResponse response){
		String code = "";
		try {
			code = WxpubOAuth.createOauthUrlForCode(WxUtil.APP_ID, WxUtil.HOST+"/wxlogin/htmlWxLogin.do", true);
  			response.sendRedirect(code); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * wxlogin/htmlWxLogin.do?code=
	 *  授权完直接登录
	 *  1：未注册过的先得获取手机验证码
	 *  2：已注册直接前往首页
	 *  
	 *  返回值：用户信息  
	 *  session存储 Wxlogin   shiro存储 HtmlUser
	 *  
	 */
	@RequestMapping(value="/htmlWxLogin")
	public ModelAndView HtmlWxLogin(HttpServletRequest request)throws Exception{
		ModelAndView mv = this.getModelAndView();
		//shiro管理的session
 		Subject currentUser = SecurityUtils.getSubject();  
 		Session session = currentUser.getSession();
  		PageData pd = new PageData();
    	try {
   				pd=this.getPageData();
   				String code=pd.getString("code");
      			pd = WxpubOAuth.getOpenId(pd,WxUtil.APP_ID, WxUtil.APP_SECRET, code);
    			if(pd.getString("open_id") == null || pd.getString("open_id").equals("")){
    				mv.setViewName("redirect:toLoginWx.do");
   			 		return mv;
   				} 
//   			String wxopen_id ="owD2DwsxdygwHXxNV75kjGT7Wvlw";
     			WxLogin login=new WxLogin();
     			PageData mpd=wxmemberService.findById(pd);
     			if( mpd == null){
    				//获取用户的一些信息
        			pd=WxpubOAuth.getUserInforForNotGuanZhu(pd,pd.getString("open_id"),pd.getString("access_token"));//获取未关注的用户信息
        			String wxmember_id=BaseController.getTimeID();
        			String showlook_id=BaseController.get8UID();
        			pd.put("wxmember_id", wxmember_id);
        			pd.put("showlook_id", showlook_id);
        			wxmemberService.save(pd);
        			login.setIMAGE_URL(pd.getString("image_url"));
        			login.setNAME(pd.getString("name"));
        			login.setOPEN_ID(pd.getString("open_id"));
        			login.setSEX(pd.getString("sex"));
        			login.setSHOWLOOK_ID(showlook_id);
        			login.setWXMEMBER_ID(wxmember_id);
     			}else{
     				login.setIMAGE_URL(mpd.getString("image_url"));
        			login.setNAME(mpd.getString("name"));
        			login.setOPEN_ID(mpd.getString("open_id"));
        			login.setSEX(mpd.getString("sex"));
        			login.setSHOWLOOK_ID(mpd.getString("showlook_id"));
        			login.setWXMEMBER_ID(mpd.getString("wxmember_id"));
    			}
     			session.setAttribute(Const.WXLOGIN, login);
     			//前往首页的代码
     			//1.获取轮播图
     			List<PageData> lunboList=carousel_figureService.listAllOk(pd);
     			mv.addObject("lunboList", lunboList);
     			//2.获取分类类别
     			List<PageData> leibieList=categoryService.listAll(pd);
     			mv.addObject("leibieList", leibieList);
     			//3默认获取当日便当类别的所有
     			pd.put("day", DateUtil.getDay());
     			List<PageData> biandanList=daily_menuService.listAllNowDay(pd);
     			mv.addObject("biandanList", biandanList);
      	} catch (Exception e) {
   			e.printStackTrace();
 		}
   		mv.setViewName("wx/wxindex");
 		return mv;
	}
	
 

	/**
	 * 生成微信公众号signature
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/getSignatureAjax")
	@ResponseBody
	public Object getSignatureAjax( ){
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "1";
		String message="获取成功";
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			String noncestr=pd.getString("noncestr");
			String timestamp=pd.getString("timestamp");
			String url=pd.getString("now_url");
			String jsapi_ticket=Const.jsapi_ticket;
 			if(jsapi_ticket.equals("")){
				jsapi_ticket=WxpubOAuth.setJiChuAccess_token().getString("jsapi_ticket");
			}
 			map.put("jsapi_ticket", jsapi_ticket);
			String signature=WxpubOAuth.getSignatureAjax(noncestr, timestamp, jsapi_ticket, url);
			map.put("data", signature);
 		} catch(Exception e){
			result="0";
			message="系统异常";
			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message",message);
		return map;
	}

	
	
	
	
	
    
	
	
	
 
	
	
	
}

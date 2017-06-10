package com.tianer.controller.shiyou;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tianer.controller.base.BaseController;
import com.tianer.entity.wx.Wx_login;
import com.tianer.service.shiyou.ShiYouService;
import com.tianer.util.Const;
import com.tianer.util.PageData;
import com.tianer.util.bankpay.SignEncryptDncryptSignChk;
import com.tianer.util.wx.WxUtil;
import com.tianer.util.wx.WxpubOAuth;
  
/** 
 * 油卡控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/shiyou")
public class ShiYouController extends BaseController {
	
	@Resource(name="shiYouService")
	private ShiYouService shiYouService;
 	
	
	/**
	 * 访问登录页
	 * @return
	 * shiyou/getOpenid.do
	 */
	@RequestMapping(value="/getOpenid")
	public ModelAndView getOpenid()throws Exception{
		ModelAndView mv = this.getModelAndView();
		Subject currentUser = SecurityUtils.getSubject();  
	    Session session = currentUser.getSession();
	    Wx_login wlogin=new Wx_login();
	    String wx_openid ="";
	    String access_token="";
 		PageData pd = new PageData();
 		try {
				//获取用户的openid
				pd = this.getPageData();
				try {
					String code = pd.getString("code");
					if(code != null){
						wx_openid = WxpubOAuth.getOpenId(WxUtil.APP_ID, WxUtil.APP_SECRET, code);
						access_token=WxpubOAuth.getAccess_token(WxUtil.APP_ID, WxUtil.APP_SECRET, code);
					}
 				} catch (Exception e) {
					// TODO: handle exception
					wx_openid="";
					access_token="";
				}
 				//通过openid获取相关信息
 				System.out.println(wx_openid);
 				PageData e=new PageData();
				e.put("wx_openid", wx_openid);
				e.put("access_token", access_token);
				//根据openid判断是否存在用户
				String oiluser_id=BaseController.get32UUID();
 				if(shiYouService.findUserById(e) != null){
					shiYouService.editUser(e);
					oiluser_id=shiYouService.findUserById(e).getString("oiluser_id");
 				}else{
 					e.put("oiluser_id", oiluser_id);
 					shiYouService.saveUser(e);
 				}
 				wlogin.setAccess_token(access_token);
 				wlogin.setWx_openid(wx_openid);
 				session.setAttribute(Const.SESSION_WXLOGIN, wlogin);
 				session.setAttribute(Const.SESSION_WXLOGIN_ID, wlogin);
  				mv.setViewName("shiyou/shiyou_zhu");
  				//设置订单的session
  				if(session.getAttribute(Const.SESSION_ORDER ) == null ){
  					String session_orderid=BaseController.getTimeID();
  					session.setAttribute(Const.SESSION_ORDER, session_orderid);
  				} 
    		} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
				logger.error(e.toString(), e);
 		}
 		return mv;
	}
	
	/**
	 * 首页
	 * localhost/myproject/shiyou/toIndex.do
	 */
	@RequestMapping(value="/toIndex")
	public void toLogin(HttpServletRequest request,HttpServletResponse response){
		String code = "";
		try {
			code = WxpubOAuth.createOauthUrlForCode(WxUtil.APP_ID, WxUtil.HOST, true);
			System.out.println(code);
 			response.sendRedirect(code); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
 
}

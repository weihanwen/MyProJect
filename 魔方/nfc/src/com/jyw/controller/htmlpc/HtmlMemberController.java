package com.jyw.controller.htmlpc;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jyw.controller.base.BaseController;
import com.jyw.util.PageData;
import com.jyw.util.wxpay.WXPayConstants;
import com.jyw.util.wxpay.WXPayPath;
import com.jyw.util.wxpay.WXPayUtil;
import com.jyw.util.wxpay.WxUtil;
import com.jyw.util.wxpay.WxpubOAuth;
 
 
/** 
 * 
* 类名称：HtmlMemberController   
* 类描述：  h5的页面
* 创建人：魏汉文  
* 创建时间：2016年5月26日 下午3:46:49
 */
@Controller("htmlMemberController")
@RequestMapping(value="/html_member")
public class HtmlMemberController extends BaseController {
	
	
	
	/**
	 * 微信登录授权页面
	 * html_member/toLoginWx.do 
 	 */
	@RequestMapping(value="/toLoginWx")
	public void toLoginWx(HttpServletRequest request,HttpServletResponse response){
		String code = "";
		try {
			code = WxpubOAuth.createOauthUrlForCode(WxUtil.APP_ID, WxUtil.HOST+"/html_member/htmlWxLogin.do", true);
  			response.sendRedirect(code); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * html_member/htmlWxLogin.do?code=
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
 		String member_id="";
   		try {
   				pd=this.getPageData();
   				String code=pd.getString("code");
     			pd = WxpubOAuth.getOpenId(pd,WxUtil.APP_ID, WxUtil.APP_SECRET, code);
    			if(pd.getString("wxopen_id") == null || pd.getString("wxopen_id").equals("")){
    				mv.setViewName("redirect:goErrorJsp.do");
   			 		return mv;
   				} 
//   			String wxopen_id ="owD2DwsxdygwHXxNV75kjGT7Wvlw";
   				//获取用户的一些信息
    			pd=WxpubOAuth.getUserInforForNotGuanZhu(pd,pd.getString("wxopen_id"),pd.getString("access_token"));//获取未关注的用户信息
  				pd=WxpubOAuth.getWxInformation(pd,pd.getString("wxopen_id"));//对于已经关注所获取的信息
   				 
  				
    	} catch (Exception e) {
   			e.printStackTrace();
 		}
   		mv.setViewName("htmlmember/wxLogin");
 		return mv;
	}
	
	
	
	
	
	
	
	
	
	
	
   
	// 开始转微信支付的一些接口操作======================================================================
	
	/**
	 * 公众号微信支付
	 * html_member/wxpayorder.do?total_fee=0.01&attach=1&body=购买商品
	 * 
	 * total_fee  金额
	 * attach     支付类型  1-优惠买单支付，2-购买提货券商品,3-优选商品,4-充值商品
	 * body       商品说明
	 * out_trade_no   订单ID
	 */
	public static Map<String, String> WxPayOrder(String _total_fee,String attach,String out_trade_no) throws Exception{
		Map<String, String> returnmap=new HashMap<String, String>();
   		try {
  			PageData pd=new PageData();
   			BigDecimal total_fee = new BigDecimal(Double.parseDouble(_total_fee)*100);
  	    	//开始调用微信支付接口
  			WXPayPath dodo = new WXPayPath("3");
 	    	Map<String, String> reqData=new HashMap<String, String>();
 	    	reqData.put("body", "九鱼网-充值余额");
 	    	reqData.put("attach",attach);
 	    	reqData.put("out_trade_no", out_trade_no);
	    	reqData.put("fee_type", "CNY");
	    	reqData.put("total_fee", String.valueOf(total_fee.intValue()));
	    	reqData.put("spbill_create_ip", dodo.getSpbill_create_ip());
	    	reqData.put("notify_url", "https://www.jiuyuvip.com/back_chat/notify.do");
	     	//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
	    	//MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
	    	reqData.put("trade_type", "JSAPI");
	    	reqData.put("openid", "");
	        reqData.put("sign_type", WXPayConstants.MD5);
  	    	Map<String, String> map2=dodo.unifiedOrder(reqData);
 	    	//开始处理结果
  	        if(map2.get("return_code").toString().equals("SUCCESS") && map2.get("result_code").toString().equals("SUCCESS")  ){
  	    	  returnmap.put("appId", map2.get("appid").toString());
 	    	  returnmap.put("timeStamp", String.valueOf(WXPayUtil.getCurrentTimestamp()));
 	    	  returnmap.put("nonceStr", WXPayUtil.generateNonceStr());
 	    	  returnmap.put("package","prepay_id="+ map2.get("prepay_id").toString());
 	    	  returnmap.put("signType", WXPayConstants.MD5);
   	    	 //二次签名
   	    	  String sign=dodo.AddSign(returnmap);
   	    	  returnmap=WXPayUtil.xmlToMap(sign);
     	      returnmap.put("result_code", map2.get("result_code").toString());
  	       } 
  	       returnmap.put("payment_type", attach);
  	       returnmap.put("out_trade_no", out_trade_no);
  	       returnmap.put("return_code", map2.get("return_code").toString());
	       returnmap.put("return_msg", map2.get("return_msg").toString());
   		} catch (Exception e) {
			// TODO: handle exception
 			e.printStackTrace();
		}
		return returnmap;
	}
	
	
	

   	/**
   	 * 微信支付的订单交易支付接口
   	* 方法名称:：payorder 
   	* 方法描述：订单支付接口
   	* html_member/payorder.do
   	* 
   	 */
	@RequestMapping(value="/payorder")
	@ResponseBody
	public  Object PayOrder(HttpServletRequest request) throws Exception{
 		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
	  	String result="1";
		String message="支付成功";
		PageData pd=new PageData();
		try{
			pd = this.getPageData();
			
		}catch(Exception e){
			result="0";
			message="系统异常";
 			logger.error(e.toString(), e);
		}
		map.put("result", result);
		map.put("message", message);
		map.put("data", data);
		return map;

	}

	
	
	
 
	
	
	
}

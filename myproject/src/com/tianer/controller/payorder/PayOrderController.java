package com.tianer.controller.payorder;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianer.controller.base.BaseController;
import com.tianer.service.shiyou.ShiYouService;
import com.tianer.util.Const;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;
import com.tianer.util.bankpay.MingShengUtil;
import com.tianer.util.bankpay.SignEncryptDncryptSignChk;

/** 
 * 类名称：PayOrderController
 * 创建人：订单支付控制层
 *  
 */
@Controller
@RequestMapping(value="/payorder")
public class PayOrderController extends BaseController {
	
	@Resource(name="shiYouService")
	private ShiYouService shiYouService;
	
	
	 /**
	  * 获取微信支付的url.民生银行
	  * @param request
	  * @return
	  * @throws Exception
	  * payorder/getWxPayUrl.do
	  */
	@RequestMapping(value="/getWxPayUrl")
	@ResponseBody
	public Object getWxPayUrl(HttpServletRequest request) throws Exception{
		System.out.println("==============================微信充值石油订单交易支付接口");
		Map<String, Object> map = new HashMap<String, Object>();
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();	
		String result="1";
		String message="支付成功";
		String url=SignEncryptDncryptSignChk.chewxpayurl;
		PageData pd=new PageData();
		try {
			pd=this.getPageData();
 			String xtsession=(String) session.getAttribute(Const.SESSION_ORDER);
			String session_orderid =pd.getString("session_orderid");
			if(!xtsession.equals(session_orderid)){
				map.put("result", "0");
				map.put("message", "操作时间过长，请刷新重新操作");
				map.put("data", "");
		    	return map;
			}
			Object oiluser_id= session.getAttribute(Const.SESSION_WXLOGIN_ID);
			if(oiluser_id == null){
				map.put("result", "0");
				map.put("message", "请用微信端打开");
				map.put("data", "");
		    	return map;
			}
			pd.put("oiluser_id", String.valueOf(oiluser_id));
			String amount=pd.getString("money");//金额单位为：元
			if(amount == null || amount.equals("") || Double.parseDouble(amount) < Const.minpay_shiyou_money ){
				map.put("result", "0");
				map.put("message", "石油最少充值"+ Const.minpay_shiyou_money);
				map.put("data", "");
		    	return map;
			}
 			String phone =pd.getString("phone");
 			String oilcard_number =pd.getString("oilcard_number");
			if(oilcard_number == null || oilcard_number.equals("")){
				map.put("result", "0");
				map.put("message", "油卡不能为空");
				map.put("data", "");
		    	return map;
			}
			//新增卡号
			if(shiYouService.findCardById(pd) == null){
				shiYouService.saveCard(pd);
			}
 			String arsid=pd.getString("arsid");
//  			String remark=pd.getString("remark");//备注
  			String remark="";//备注
 			String orderInfo="油卡类型"+arsid+"卡号:"+oilcard_number+",支付金额："+amount;//商品信息
 			String merchantSeq=SignEncryptDncryptSignChk.platformId+Const.shiyou_pay+BaseController.getTimeID();//订单号
			BigDecimal _amount = new BigDecimal(amount);
			int usermoney = _amount.multiply(new BigDecimal("100")).intValue();
			String notifyUrl=SignEncryptDncryptSignChk.nowip+"/myproject/payback/wxpayBackWayMinSheng.do";
			String redirectUrl=SignEncryptDncryptSignChk.nowip+"/myproject/shiyou/toIndex.do";
			//生成加密后的数据
			String encryptContext=MingShengUtil.WxGoPayUrl(usermoney, merchantSeq, notifyUrl, redirectUrl, orderInfo, remark);
  			//生成一个订单
			pd.put("oilorder_id", merchantSeq);
 			pd.put("oilcard_number", oilcard_number);
 			pd.put("channel", "wx");
 			pd.put("jiami_message", encryptContext);
			shiYouService.saveOrder(pd);
 			//返回地址
			url=url+encryptContext;
			map.put("data", url);
			//移除session
   			session.removeAttribute(Const.SESSION_ORDER);
 			//=====
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.toString(), e);
			result="0";
			message="系统错误";
		}
		map.put("result", result);
		map.put("message", message);
		return map;
	 }
//	
//	/**
//   	 * 充值石油订单交易支付接口p+++
//   	 */
//	@RequestMapping(value="/shiyouPay")
//	@ResponseBody
//	public Object shiyouPay(HttpServletRequest request) throws Exception{
//		System.out.println("==============================充值石油订单交易支付接口");
// 		Map<String, Object> map = new HashMap<String, Object>();
// 		Subject currentUser = SecurityUtils.getSubject();  
//		Session session = currentUser.getSession();	
//  		String result="1";
//		String message="支付成功";
//  		PageData pd=new PageData();
//		try{
// 			pd = this.getPageData();
// 			String xtsession=(String) session.getAttribute(Const.SESSION_ORDER);
//			String session_orderid =pd.getString("session_orderid");
//			if(!xtsession.equals(session_orderid)){
//				map.put("result", "0");
//				map.put("message", "操作时间过长，请刷新重新操作");
//				map.put("data", "");
//		    	return map;
//			}
//			String arsid =pd.getString("arsid");
//			String money =pd.getString("money");
//			if(money == null || money.equals("")){
//				money="0";
//			}
//			if(Double.parseDouble(money) < Const.minpay_shiyou_money ){
//				map.put("result", "0");
//				map.put("message", "石油最少充值"+ Const.minpay_shiyou_money);
//				map.put("data", "");
//		    	return map;
//			}
//			String phone =pd.getString("phone");
//			String channel =pd.getString("channel");
//			String oilcard_id =pd.getString("oilcard_id");
//			if(oilcard_id == null || oilcard_id.equals("")){
//				map.put("result", "0");
//				map.put("message", "油卡不能为空");
//				map.put("data", "");
//		    	return map;
//			}
//			String oilorder_id =BaseController.getTimeID();
//			pd.put("oilcard_id", oilcard_id);
//			shiYouService.saveOrder(pd);
//			//2.获取charge对象
//			System.out.println("油卡充值获取charge对象");
//			Charge charge = ChargeExample.getPay(RSAConstants.md5macid+oilorder_id, Double.parseDouble(money)*100,getIp(request),channel,Const.shiyou_pay,"油卡消费");
//			if(charge == null ){
//				map.put("result", "0");
//				map.put("message", "支付失败，charge出错");
//				map.put("data", "");
//		    	return map;
//			}
// 			map.put("data", charge);			 
//   			System.out.println("生成订单结束");
//  		}catch(Exception e){
//			result="0";
//			message="系统异常";
//			map.put("data", e.toString());
//			logger.error(e.toString(), e);
//		}
//		map.put("result", result);
//		map.put("message", message);
//    	return map;
//	}
	
	
	/*
	 * 获取IP
	 */
	public static String getIp(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}
 		 //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		 if(ipAddress!=null && ipAddress.length()>15){
		 //"***.***.***.***".length() = 15
		 if(ipAddress.indexOf(",")>0){
		 ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
		 }
		 }
		return ipAddress;
	}
	
	
	
	 
}

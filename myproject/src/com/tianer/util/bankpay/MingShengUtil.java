package com.tianer.util.bankpay;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.sun.istack.internal.logging.Logger;
import com.tianer.controller.base.BaseController;
import com.tianer.util.DateUtil;
import com.tianer.util.PageData;

/**
 * 民生银行支付措施
 * @author Administrator
 *
 */
public class MingShengUtil extends BaseController {

	
	/**
	 * 生成加密后的数据
	 * @param usermoney
	 * @param merchantSeq
	 * @param notifyUrl
	 * @param redirectUrl
	 * @param orderInfo
	 * @param remark
	 * @return
	 */
	public static String WxGoPayUrl(int usermoney,String merchantSeq,String notifyUrl,String redirectUrl,String orderInfo,String remark){
  		String encryptContext="";
		try {
			/*
			 * 格式
			 * "{\"amount\":\"2\"," + "\"defaultTradeType\":\"API_WXQRCODE\","
			+ "\"isConfirm\":\"0\"," + "\"isShowSuccess\":\"0\"," + "\"merchantName\":\"乐收银测试\","
			+ "\"merchantNum\":\"M01002016070000000789\"," + "\"merchantSeq\":\"10086201607052312\","
			+ "\"notifyUrl\":\"http://111.205.207.103/merchantdemo/noticeServlet\"," + "\"orderInfo\":\"\","
			+ "\"platformId\":\"cust0001\"," + "\"printFlag\":\"0\"," + "\"remark\":\"\","
			+ "\"selectTradeType\":\"API_WXQRCODE\"," + "\"transDate\":\"20160627\","
			+ "\"transTime\":\"201606270900000\"}";
			 */
			Map<String, String> jsonpd=new LinkedHashMap<String,String>();
 			jsonpd.put("amount", String.valueOf(usermoney));//金额，以分为单位
			jsonpd.put("defaultTradeType", SignEncryptDncryptSignChk.defaultTradeType);//类型代码：H5_WXJSAPI
			jsonpd.put("isConfirm", "0");
			jsonpd.put("isShowSuccess", "0");
			jsonpd.put("merchantName", SignEncryptDncryptSignChk.merchantName);// 商户名称，用于在民生页面回显
			jsonpd.put("merchantNum", SignEncryptDncryptSignChk.merchantNo);//该订单对应的民生统一商户号
			jsonpd.put("merchantSeq", merchantSeq);// 商户流水(须保证唯一),建议是商户平台号+8位日期+商户自定的订单号
  			jsonpd.put("notifyUrl", notifyUrl);//商户实现的接收异步通知的url地址
 			jsonpd.put("orderInfo", orderInfo);//商户订单内容，用于在民生页面反显
  			jsonpd.put("platformId", SignEncryptDncryptSignChk.platformId);//民生银行为接入平台分配的平台编号,与平台证书一一对应
 			jsonpd.put("printFlag", "0");
  			jsonpd.put("redirectUrl", redirectUrl);//用于跳回商户首页
			jsonpd.put("remark", remark);//备注
  			jsonpd.put("selectTradeType", SignEncryptDncryptSignChk.selectTradeType);//类型代码：H5_WXJSAPI
  			jsonpd.put("subAppId", "");
  			jsonpd.put("subOpenId", "");
  			jsonpd.put("transDate", DateUtil.getDays());//yyyyMMdd
  			jsonpd.put("transTime", DateUtil.getDayshms());//yyyyMMddHHmmssSSS
 			JSONObject jsonObject = JSONObject.fromObject(jsonpd);
			System.out.println("json数据："+jsonObject.toString());
			String context=jsonObject.toString();
 			String sign = SignEncryptDncryptSignChk.getSign(context);
 			System.out.println("签名："+sign);
 
			String signContext = SignEncryptDncryptSignChk.sign(sign, context);
 			System.out.println("加密前："+signContext);
 
			encryptContext = SignEncryptDncryptSignChk.encrypt(signContext);
 			System.out.println("加密后："+encryptContext);
 			
 			String dncryptContext =SignEncryptDncryptSignChk.dncrypt(encryptContext);
  			System.out.println("解密后："+dncryptContext);
 
			System.out.println("验签："+SignEncryptDncryptSignChk.signCheck(dncryptContext));
		} catch (Exception e) {
			// TODO: handle exception
			new BaseController().logger.error(e.toString(), e);
		}
		return encryptContext;
		
	}
	
}

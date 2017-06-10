package com.tianer.util;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

 

/**
 * 通过短信接口发送短信
 * 阿里大鱼短信文档
 * 魏汉文
 */
public class SmsUtil {
	
	private static String url="http://gw.api.taobao.com/router/rest";
	private static String appkey="23743735";
	private static String secret="f22fecab63722ba8fa3556578ae56dae";
	private static String time="60";
	private static String product="个人应用";
	private static String jiuyudizhi="www.baidu.com";
	private static String xt_phone="15260282340";
	
	/**
	 * 
	 * "接收向上电子${orderid}充值结果的通知，但系统不存在该订单或并非抛单给向上,可能由于对方不支持该产品，请核查"
	 */
	public static void BugSendMsg(String orderid){
		//短信模板的内容
		String json="{\"orderid\":\""+orderid+"\"}";
		try {
			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setSmsType("normal");
			req.setSmsFreeSignName("八小时融");
			req.setRecNum(xt_phone);
			req.setSmsTemplateCode("SMS_60795346");
			req.setSmsParamString(json);
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			System.out.println(rsp.getBody());
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**
	 * 
	 * "恭喜你，充值${money}成功到账，请注意查收！
	 */
	public static void SuccessSendMsg(String money,String phone){ 
		//短信模板的内容
		String json="{\"money\":\""+money+"\"}";
		try {
			TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setSmsType("normal");
			req.setSmsFreeSignName("八小时融");
			req.setRecNum(phone);
			req.setSmsTemplateCode("SMS_60735250");
			req.setSmsParamString(json);
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			System.out.println(rsp.getBody());
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
  	 
	
	// =================================================================================================
	public static void main(String [] args) {
		
  	}
	
	
}


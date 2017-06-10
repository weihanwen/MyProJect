package com.tianer.controller.payback;
 

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

 
import com.tianer.controller.base.BaseController;
import com.tianer.service.business.w.WService;
import com.tianer.service.shiyou.ShiYouService;
import com.tianer.util.Const;
import com.tianer.util.MD5;
import com.tianer.util.PageData;
import com.tianer.util.SmsUtil;
import com.tianer.util.bankpay.SignEncryptDncryptSignChk;
import com.tianer.util.rsa.RSACoderTest;
import com.tianer.util.rsa.RSAConstants;
 
  
/** 
 * 支付回调接口
 */
@Controller
@RequestMapping(value="/payback")
public class PayBackController extends BaseController {
	
	@Resource(name="shiYouService")
	private ShiYouService shiYouService;
	
	@Resource(name="wService")
	private WService wService;
	
	/**
	 * 第三方支付或者充值回调
	* 方法名称:：payBackWay 
	* 方法描述：
	* 创建人：魏汉文=======民生银行
	* 创建时间：2016年7月5日 下午6:30:51
	* payback/wxpayBackWayMinSheng.do
	 */
	@RequestMapping(value = "wxpayBackWayMinSheng")
	@ResponseBody
		public String wxpayBackWayMinSheng(HttpServletRequest request, HttpServletResponse response) throws Exception {
			 System.out.println("进来回调了=======================>>>>>>>");
			 PageData ssspd=new PageData();
 				 try{
 					ssspd=this.getPageData();
		 		    // 获得 http body 内容
	 		        BufferedReader reader = request.getReader();
			        StringBuffer buffer = new StringBuffer();
			        String string;
		 	        while ((string = reader.readLine()) != null) {
			            buffer.append(string);
			        }
			        reader.close();
 			        JSONObject s=new JSONObject(buffer.toString());
 			        String content=s.optString("content"); 
 			        String dncryptContext = SignEncryptDncryptSignChk.dncrypt(content);
 					System.out.println("--------------------------------------");
 					System.out.println("解密后："+dncryptContext);
  					String signChkResult = SignEncryptDncryptSignChk.signCheck(dncryptContext);
 					System.out.println("--------------------------------------");
 					System.out.println("验证签名结果："+signChkResult);
  					JSONObject lastjson=new JSONObject(dncryptContext);
					JSONObject jv=lastjson.optJSONObject("body");
					String orderno=jv.optString("order_no");//支付订单号
					String channel=jv.optString("channel");//支付方式
					String trade=jv.optString("serialNo");//流水单号/序列号
			 		Double price=jv.optDouble("amount");//支付金额（消费/充值金额）
			 		wService.saveLog(orderno, dncryptContext,"minsheng");//log
 		  	        // 解析异步通知数据
 		 	        if (signChkResult.equals("true")) { //支付成功
							System.out.println("进入支付成功=============>>>>>>>>>>>>>>>>>");
 						 	PageData pd=new PageData();
						 	if(orderno.contains(Const.shiyou_pay)){ //body:1-充值油卡支付的糊掉
						 			String oilorderid=orderno.substring(RSAConstants.md5macid.length());
 						 			int money=(int)(price/100);
							 		pd.put("oilorder_id", oilorderid);
						 			PageData orderpd=shiYouService.findById(pd);
						 			if(orderpd != null && orderpd.getString("order_status").equals("0")){
						 				String phone=orderpd.getString("phone");
						 				String oilcard_number=orderpd.getString("oilcard_number");
						 				String arsid=orderpd.getString("arsid");
						 				RSACoderTest tt=new RSACoderTest();
						 				String timestamp=tt.getTimestampStr();
						 				pd.put("timestamp", timestamp);
									    pd.put("transaction_no", trade);
									    pd.put("channel", channel);
									    pd.put("order_status", "2");
									    shiYouService.editOrder(pd);
									    //进行油卡的充值操作
									    try {
										    	//订单号需叫上所提供的的前缀Const.TOP_UP_ORDER_PREFIX
								 			String sign=tt.getSign(String.valueOf(money), orderno, phone, oilcard_number, timestamp, arsid);
							 				System.out.println("签名："+sign);
							 				//充值油卡
							 				String requestUrl = "http://shop.test.bolext.cn:81/shop/buyunit/orderpayforjyk.do?encryptType=MD5&deno="+String.valueOf(money)+"&macid="+RSAConstants.md5macid+"&orderid="+orderno+"&phone="+oilcard_number+"&arsid="+arsid+"&sign="+URLEncoder.encode(sign,"UTF-8")+"&time=" + timestamp;
							 	       		System.out.println("请求地址：" + requestUrl); 
							 	 			String xml=tt.httpGet(requestUrl);
							 	 			System.out.println("回调回来的数据："+xml);
							 	  			Map<String, Object> map = tt.getMapFromXML(xml);
									         String id=(String) map.get("id");
								            String orderid=(String)map.get("orderid");
								            String deno=(String) map.get("deno");
								            String successdeno=(String) map.get("successdeno");
								            String resultcode=(String) map.get("errcode");
								            String resultinfor=(String) map.get("errinfo");
								            PageData oilpd=new PageData();
								            String return_orderid= orderid.substring(RSAConstants.md5macid.length());
								            oilpd.put("oilorder_id", return_orderid);
										    if(resultcode.equals("AccountClosed")){//
										    	oilpd.put("order_status", "99");
	 									    }else if(resultcode.equals("AccountBalance")){
		 									    oilpd.put("order_status", "99");
		 									    }else if(resultcode.equals("ProductClosed")){ 
		 									    	oilpd.put("order_status", "99");
	 									    }else if(resultcode.equals("ProductError")){
	 									    	oilpd.put("order_status", "99");
	 									    }else{
	 									    	oilpd.put("order_status", "2");
	 									    }
										    if(oilpd.getString("order_status").equals("99")){
//										    	//进行退款
//	 									    	Charge ch = Charge.retrieve(orderno);//orderno 是已付款的订单号
//	 									        Map<String, Object> refundMap = new HashMap<String, Object>();
//	 									        refundMap.put("amount", price);
//	 									        refundMap.put("description", resultinfor);
//	 									        refundMap.put("refund_type", "1");
//	 									        refundMap.put("cons_id", orderno);
//	 									        Refund refund = ch.getRefunds().create(refundMap);
//	 									        String refund_status=refund.getStatus();
//	 									        if(refund_status.equals("pending")){
//	 									        	oilpd.put("order_status", "97");
//	 									        }else if(refund_status.equals("succeeded")){
//	 									        	oilpd.put("order_status", "98");
//	 									        }else if(refund_status.equals("failed")){
//	 									        	oilpd.put("order_status", "99");//退款失败
//	 									        }
//	 									        wService.saveLog(oilorderid, refund.toString(),"3");//log
										    	oilpd.put("order_status", "1");//支付失败
										    }
		 									oilpd.put("remarks", successdeno+"/"+resultinfor);
	  							    	    shiYouService.editOrder(oilpd);
	  							    	    oilpd=null;
										} catch (Exception e) {
											// TODO: handle exception
											logger.error(e.toString(), e);
										}
							 		}
 						 		} else if(orderno.contains(Const.liuliang_pay)){//充值流量支付的回调
					 			
 						 		}
					 	 		response.setStatus(200);
		 	 	    }else {
				    	return "FALSE";
				    }
		   }catch(Exception e){
			   System.out.println("出错===============》》"+e.toString());
			   logger.error("出错===============》》"+e.toString(), e);
		   }
		   return "SUCCESS";
		}
	
	
	
	
//	
//		/**
//		 * 第三方支付或者充值回调
//		* 方法名称:：payBackWay 
//		* 方法描述：
//		* 创建人：魏汉文 p++++
//		* 创建时间：2016年7月5日 下午6:30:51
//		 */
//		@RequestMapping(value = "wxpayBackWayPing")
//		@ResponseBody
// 		public void wxpayBackWayPing(HttpServletRequest request, HttpServletResponse response) throws Exception {
//				 System.out.println("Ping++进来回调了=======================>>>>>>>");
//				 request.setCharacterEncoding("UTF8");
//  				 try{
// 		 		        // 获得 http body 内容
//		 		        BufferedReader reader = request.getReader();
//				        StringBuffer buffer = new StringBuffer();
//				        String string;
//			 	        while ((string = reader.readLine()) != null) {
//				            buffer.append(string);
//				        }
//				        reader.close();
//			  	        // 解析异步通知数据
//		 		        Event event = Webhooks.eventParse(buffer.toString());
//			 	        if (event.getType() != null && !event.getType().equals("" ) && "charge.succeeded".equals(event.getType())) { //支付成功
//								System.out.println("进入支付成功=============>>>>>>>>>>>>>>>>>"+event.getData().getObject());
//								JSONObject s=new JSONObject(buffer.toString());
//		 						JSONObject obj=s.optJSONObject("data");
//								JSONObject jv=obj.optJSONObject("object");
//								String body=jv.optString("body"); 
// 								String orderno=jv.optString("order_no");//支付订单号
//								String channel=jv.optString("channel");//支付方式
//								String trade=jv.optString("transaction_no");//流水单号
//						 		Double price=jv.optDouble("amount");//支付金额（消费/充值金额）
// 						 		PageData pd=new PageData();
// 						 		if(body.equals(Const.shiyou_pay)){ //body:1-充值油卡支付的糊掉
// 						 			String oilorderid=orderno.substring(RSAConstants.md5macid.length());
// 						 			wService.saveLog(oilorderid, jv.toString(),"11");//log
//						 			int money=(int)(price/100);
// 						 			pd.put("oilorder_id", oilorderid);
//						 			PageData orderpd=shiYouService.findById(pd);
//						 			if(orderpd != null && orderpd.getString("order_status").equals("0")){
//						 				String phone=orderpd.getString("phone");
//						 				String oilcard_number=orderpd.getString("oilcard_number");
//						 				String arsid=orderpd.getString("arsid");
//						 				RSACoderTest tt=new RSACoderTest();
//						 				String timestamp=tt.getTimestampStr();
//						 				pd.put("timestamp", timestamp);
//									    pd.put("transaction_no", trade);
//									    pd.put("channel", channel);
//									    pd.put("order_status", "2");
//									    shiYouService.editOrder(pd);
//									    //进行油卡的充值操作
//									    try {
// 									    	//订单号需叫上所提供的的前缀Const.TOP_UP_ORDER_PREFIX
//  							 				String sign=tt.getSign(String.valueOf(money), orderno, phone, oilcard_number, timestamp, arsid);
//							 				System.out.println("签名："+sign);
//							 				//充值油卡
//							 				String requestUrl = "http://shop.test.bolext.cn:81/shop/buyunit/orderpayforjyk.do?encryptType=MD5&deno="+String.valueOf(money)+"&macid="+RSAConstants.md5macid+"&orderid="+orderno+"&phone="+oilcard_number+"&arsid="+arsid+"&sign="+URLEncoder.encode(sign,"UTF-8")+"&time=" + timestamp;
//							 	       		System.out.println("请求地址：" + requestUrl); 
//							 	 			String xml=tt.httpGet(requestUrl);
//							 	 			System.out.println("回调回来的数据："+xml);
//							 	  			Map<String, Object> map = tt.getMapFromXML(xml);
// 								            String id=(String) map.get("id");
//								            String orderid=(String)map.get("orderid");
//								            String deno=(String) map.get("deno");
//								            String successdeno=(String) map.get("successdeno");
//								            String resultcode=(String) map.get("errcode");
//								            String resultinfor=(String) map.get("errinfo");
//								            PageData oilpd=new PageData();
//								            String return_orderid= orderid.substring(RSAConstants.md5macid.length());
//								            oilpd.put("oilorder_id", return_orderid);
//										    if(resultcode.equals("AccountClosed")){//
//										    	oilpd.put("order_status", "99");
//	 									    }else if(resultcode.equals("AccountBalance")){
//		 									    oilpd.put("order_status", "99");
// 	 									    }else if(resultcode.equals("ProductClosed")){ 
// 	 									    	oilpd.put("order_status", "99");
//	 									    }else if(resultcode.equals("ProductError")){
//	 									    	oilpd.put("order_status", "99");
//	 									    }else{
//	 									    	oilpd.put("order_status", "2");
//	 									    }
//										    if(oilpd.getString("order_status").equals("99")){
//										    	//进行退款
//	 									    	Charge ch = Charge.retrieve(orderno);//orderno 是已付款的订单号
//	 									        Map<String, Object> refundMap = new HashMap<String, Object>();
//	 									        refundMap.put("amount", price);
//	 									        refundMap.put("description", resultinfor);
//	 									        refundMap.put("refund_type", "1");
//	 									        refundMap.put("cons_id", orderno);
//	 									        Refund refund = ch.getRefunds().create(refundMap);
//	 									        String refund_status=refund.getStatus();
//	 									        if(refund_status.equals("pending")){
//	 									        	oilpd.put("order_status", "97");
//	 									        }else if(refund_status.equals("succeeded")){
//	 									        	oilpd.put("order_status", "98");
//	 									        }else if(refund_status.equals("failed")){
//	 									        	oilpd.put("order_status", "99");//退款失败
//	 									        }
//	 									        wService.saveLog(oilorderid, refund.toString(),"3");//log
//										    }
// 	 									    oilpd.put("remarks", successdeno+"/"+resultinfor);
//	  							    	    shiYouService.editOrder(oilpd);
//	  							    	    oilpd=null;
//										} catch (Exception e) {
//											// TODO: handle exception
//											logger.error(e.toString(), e);
//										}
// 						 			}
//						 			//移除session
//						 			Subject currentUser = SecurityUtils.getSubject();  
//						 		    Session session = currentUser.getSession();
//						 			session.removeAttribute(Const.SESSION_ORDER);
//						 			//=====
// 						 		} else if(body.equals(Const.liuliang_pay)){//充值流量支付的回调
//						 			
//						 		}
//   					 	 	 	response.setStatus(200);
// 		 	 	        }else if ("refund.succeeded".equals(event.getType())) {
// 		 	 	        		response.setStatus(200);
//					    } else {
//					         	response.setStatus(500);
//					    }
//			   }catch(Exception e){
//				   System.out.println("出错===============》》"+e.toString());
//				   logger.error("出错===============》》"+e.toString(), e);
// 			   }
//			System.out.println("Ping++结束回调了=======================>>>>>>>");
// 		}
//		
//		
// 
//	
//	
	
	
	/**
	 * 油卡充值接收代充商充值结果回调通知
	 * 
	 * @param orderItem
	 * @return
	 */
	@RequestMapping(value = "oilReceiveOrderNotify")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public String receiveOrderNotify(Map requestParams) throws IOException {
		StringBuffer logstr = new StringBuffer();
		try {
			 
 			String deno   	   = ((String[]) requestParams.get("deno"))[0];        // 面值
			String errcode 	   = ((String[]) requestParams.get("errcode"))[0];     // 返回码
			String errinfo 	   = ((String[]) requestParams.get("errinfo"))[0];     // 返回说明
			String id 		   = ((String[]) requestParams.get("id"))[0];          // id
			String orderid 	   = ((String[]) requestParams.get("orderid"))[0];     // 订单号
			String sign    	   = ((String[]) requestParams.get("sign"))[0];        // 签名
			String successdeno = ((String[]) requestParams.get("successdeno"))[0]; // 成功金额
			errinfo = URLDecoder.decode(errinfo,"UTF-8");
			//签名字段进行URL解码
			sign = URLDecoder.decode(sign,"UTF-8");
			String logMsg = "接收向上电子空充通知的充值结果 " + "订单号：" + orderid + "，充值状态："+ errcode + "，签名 ：" + sign;
			logstr.append(logMsg);
			logger.info(logMsg);
 	        //自己使用md5验签
			StringBuffer sbReturn = new StringBuffer();
			// 返回的数据进行组合
			sbReturn.append("deno").append(deno)
				    .append("errcode").append(errcode)
				    .append("errinfo").append(errinfo)
				    .append("id").append(id)
				    .append("orderid").append(orderid)
				    .append("successdeno").append(successdeno).append(RSAConstants.md5key);
 			logger.info("返回结果："+sbReturn.toString());
			//再生成MD5签名
			String own_sign=MD5.md5(sbReturn.toString());
			System.out.println("油卡回调接口签名："+own_sign);
			String oilorderid=orderid.substring(RSAConstants.md5macid.length());//数据库订单号
  		   //对比签名
 	        if (!own_sign.equals(sign)) {
	        	logger.info("rsa订单验签失败" + oilorderid );
				logstr = new StringBuffer("rsa订单验签失败" + oilorderid );
				wService.saveLog(oilorderid, logstr.toString(),"shiyou");
				return "false";
			}
 			PageData pd=new PageData();
			pd.put("oilorder_id", oilorderid);
			pd=shiYouService.findOrderById(pd);
			if(pd == null ){
				logger.info("在系统中找不到向通上知的订单" + oilorderid);
				logstr.append("，在系统中找不到向上通知的订单");
				SmsUtil.BugSendMsg(oilorderid);
				wService.saveLog(orderid, logstr.toString(),"shiyou");
				return "false";
			}
			String order_status=pd.getString("order_status");
			if(order_status.equals("1")){
				return "true";
			}
			String phone=pd.getString("phone");
			String oilcard_number=pd.getString("oilcard_number");
			String timestamp=pd.getString("timestamp");
			String arsid=pd.getString("arsid");
			if ("OrderSuccess".equals(errcode)) { // 处理成功（充值成功/部分成功）
				//充值金额
				if(Double.parseDouble(deno) == Double.parseDouble(successdeno)){
					//充值成功
					logger.info(     "接收向上电子" + oilorderid + "充值的通知，充值成功!");
					logstr.append("接收向上电子" + oilorderid + "充值的通知，充值成功!");
					wService.saveLog(oilorderid, logstr.toString(),"shiyou");
 				}else {
					//部分成功
 					int succprice = (int)(Double.valueOf(successdeno)*100);
					logger.info("接收向上电子" + oilorderid + "充值的通知，充值部分成功!"+succprice);
					logstr.append("接收向上电子" + oilorderid + "充值的通知，充值部分成功!"+succprice);
 					wService.saveLog(oilorderid, logstr.toString(),"shiyou");
				}
			} else if ("OrderFail".equals(errcode)) { // 充值失败的订单

				
			} else {
				logger.error("接收到向上电子返回状态：" + errcode);
			}
 		} catch (Exception e) {
			// TODO: handle exception
 			logger.error(e.toString(), e);
		}
		return "true";
	}
	
	
	//退款成功，接收 Webhooks 通知
//	@RequestMapping("/getRefundWebhooks")
//	@ResponseBody
//	public void getRefundWebhooks(HttpServletRequest request, HttpServletResponse response) throws Exception{
//		logBefore(logger, "退款成功，接收 Webhooks 通知");
//		request.setCharacterEncoding("UTF8");
//        //获取头部所有信息
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            System.out.println(key+" "+value);
//        }
//        // 获得 http body 内容
//        BufferedReader reader = request.getReader();
//        StringBuffer buffer = new StringBuffer();
//        String string;
//        while ((string = reader.readLine()) != null) {
//            buffer.append(string);
//        }
//        reader.close();
//        // 解析异步通知数据
//        Event event = Webhooks.eventParse(buffer.toString());
//        if ("charge.succeeded".equals(event.getType())) {
//        	response.setStatus(200);
//        } else if ("refund.succeeded".equals(event.getType())) {
//         	Refund refund = (Refund) event.getData().getObject();
////         	String charge_id = refund.getCharge();	//拿到退款的charge_id
// //        	//Charge charge = Charge.retrieve(charge_id);//查询charge对象
////    		//String cons_id = charge.getOrderNo();
//  			int _total_fee = refund.getAmount();
//			Map<String, String> metadata = refund.getMetadata();
// 			String refund_type = metadata.get("refund_type");
//			String cons_id = metadata.get("cons_id");//订单ID
//     		BigDecimal total_fee = new BigDecimal(_total_fee);
//    		total_fee = total_fee.divide(new BigDecimal(100));//退款金额
//         	PageData pd = new PageData();
//         	//1-充值石油退款，2-充值流量退款
//			if(Const.shiyou_return.equals(refund_type)){ 
//				String oilorder_id=cons_id.substring(RSAConstants.md5macid.length());
//				wService.saveLog(oilorder_id, refund.toString(),"13");//log
// 				pd.put("oilorder_id", oilorder_id);
//				PageData oilpd=shiYouService.findOrderById(pd);
//				if(oilpd != null){
//					pd.put("order_status", "98");
//					shiYouService.editOrder(pd);
// 				} 
// 			}else if(Const.liuliang_return.equals(refund_type)){
//
//			}
//			response.setStatus(200);
//        } else {
//            response.setStatus(500);
//        }
// 	}
 
}

package com.jyw.listener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jyw.util.PageData;
import com.jyw.util.ServiceHelper;
 

/**
 * 
* 类名称：PassTimerTest   
* 类描述：过期定时器   
* 创建人：魏汉文  
* 创建时间：2016年6月17日 下午1:39:44
 */
@Component
public class TimeClear {
	
	        private  static boolean flag=true;
 
			 /**
			  * 每天凌晨清空
			  * 魏汉文20160617
			  */
 			@Scheduled(cron = "1 0 0 * * *")
			 public void QingKong() {
 					System.err.println("每天凌晨清空********开始");
 				    try{
 				    	deleteOrder();
 				    	deleteOrderTime();
    				}catch(Exception e){
				    	System.out.println("每天凌晨清空"+e.toString());
				    }
				    System.out.println("每天凌晨清空*******结束");
			 }
  			
 			
 			
 			//删除未完成的订单
 			public void deleteOrder(){
 				try {
 					PageData pd=new PageData();
  			    	List<PageData> orderList=ServiceHelper.getWxOrderService().getStatusIszero(pd);
 			    	for (PageData e : orderList) {
 				    		ServiceHelper.getWxOrderService().deleteOrderLunch(e);
 					}
 			    	ServiceHelper.getWxOrderService().deleteOrder(pd);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
 			}
 			
 			//删除订单跑腿费定时
 			public void deleteOrderTime(){
 				try {
 					ServiceHelper.getWxOrderService().deleteOrderTime(null);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
 			}
 			
 			
 			 
 			 
 			public static void main(String[] args) {
 				 
			}
 			
  }
 
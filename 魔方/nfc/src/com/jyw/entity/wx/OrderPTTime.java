package com.jyw.entity.wx;

import java.util.TimerTask;

import com.jyw.util.PageData;
 
 
 
/**
 * 
* 类名称：第一笔订单的支付时间，倒计时十分钟
* 创建人：魏汉文  
* 创建时间：2016年4月7日 上午9:50:10
 */
public class OrderPTTime extends TimerTask{
 
 	private String order_id;//订单ID
     

	public   OrderPTTime(){
		
	}

	public   OrderPTTime(String order_id ) {
 		this.order_id = order_id;
 	}

	
	@Override
	public void run() {
			/*
			 * 在定时器到时间的时候 
 			 */
  		 	PageData pd=new PageData();
 		 	try { 
 		 		//获取所有订单
 		 		
 		 		//获取1单的配送金额
 		 		
 		 		//根据总单数获取总配送金额
 		 		
 		 		//计算得可优惠的金额，发放账户
 		 		
 		 		
 		 		
 		 	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    
	public static void main(String[] age){
 	}

}

package com.jyw.entity.wx;

import java.util.TimerTask;

import com.jyw.util.PageData;
 
 
 
/**
 * 
* 类名称：去支付的订单到期时间
* 创建人：魏汉文  
* 创建时间：2016年4月7日 上午9:50:10
 */
public class OrderShop extends TimerTask{
 
 	private String order_id;//订单ID
     

	public   OrderShop(){
		
	}

	public   OrderShop(String order_id ) {
 		this.order_id = order_id;
 	}

	
	@Override
	public void run() {
			/*
			 * 在定时器到时间的时候 
 			 */
  		 	PageData pd=new PageData();
 		 	try { 
 		 		//获取当前订单
 		 		
 		 		//判断状态是否已经支付成功
 		 		
 		 		//未支付成功，获取购买商品，库存
 		 		
 		 		//未支付成功更新购买商品的库存
 		 		
 		 		
 		 		
 		 		
 		 		
 		 	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
    
	public static void main(String[] age){
 	}

}

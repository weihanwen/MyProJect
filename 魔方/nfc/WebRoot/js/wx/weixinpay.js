		//公众号发起微信支付
	    function onBridgeReady(payment_type_,appId_,timeStamp_,nonceStr_,package_,signType_,paySign_,out_trade_no_){
	    	//alert(payment_type_+"=="+appId_+"=="+timeStamp_+"=="+nonceStr_+"=="+package_+"=="+signType_+"=="+paySign_+"=="+out_trade_no_);   
	    	WeixinJSBridge.invoke(
	    	       'getBrandWCPayRequest', {
	    	           "appId":appId_,     //公众号名称，由商户传入     
	    	           "timeStamp":timeStamp_,         //时间戳，自1970年以来的秒数     1395712654 
	    	           "nonceStr":nonceStr_, //随机串     
	    	           "package":package_,     
	    	           "signType":signType_,         //微信签名方式：     
	    	           "paySign":paySign_ //微信签名 
	    	       },
	    	       function(res){
 	    	    	   //console.log(res); 
	    	           if(res.err_msg == "get_brand_wcpay_request:ok" ) {// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
 	    	        	   // 支付成功后的回调函数
		   	    	    	if(payment_type_ == "1"){
		   	    	    		window.location.href=base_inf.base_herf+'html_member/findById.do?ordertype=2&order_id='+out_trade_no_;
 		   	    	    	}else if(payment_type_ == "2"){
 		   	    	    		window.location.href=base_inf.base_herf+'html_member/findById.do?ordertype=3&order_id='+out_trade_no_;
		   	    	    	}else if(payment_type_ == "3"){
 		   	    	    		window.location.href=base_inf.base_herf+'html_member/payOkGoJsp.do?orderno='+out_trade_no_;
		   	    	    	}else{
		   	    	    		window.location.href=base_inf.base_herf+"html_me/goMe.do?type=11";
		   	    	    	}
	    	        	   
	    	           }     
	    	       }
	    	   ); 
	    	}

	    //微信公众号支付
	    function callWxJsPay(payment_type_,appId_,timeStamp_,nonceStr_,package_,signType_,paySign_,out_trade_no_){ 
		      if (typeof WeixinJSBridge == "undefined"){ 
		        if( document.addEventListener ){ 
		          document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false); 
		        }else if (document.attachEvent){ 
		          document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
		          document.attachEvent('onWeixinJSBridgeReady', onBridgeReady); 
		        } 
		      }else{ 
		    	  onBridgeReady(payment_type_,appId_,timeStamp_,nonceStr_,package_,signType_,paySign_,out_trade_no_);
		      }
	    }
	    
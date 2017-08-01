				// 对浏览器的UserAgent进行正则匹配，不含有微信独有标识的则为其他浏览器  
	    	    var useragent = navigator.userAgent;  
	    	    if (useragent.match(/MicroMessenger/i) != 'MicroMessenger') {  
	    	        // 这里警告框会阻塞当前页面继续加载  
	    	        //alert('已禁止本次访问：您必须使用微信内置浏览器访问本页面！');  
	    	        // 以下代码是用javascript强行关闭当前页面  
	    	        var opened = window.open('about:blank', '_self');  
	    	        opened.opener = null;  
	    	        opened.close();  
	    	    }  

				//微信js
   				var len=16;
 				var now_url=location.href.split('#')[0];
  				len = len || 32;
 	 			var $chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890';    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
 	 			var maxPos = $chars.length;
 	 			var noncestr = '';
 	 			for (i = 0; i < len; i++) {
 	 			　　　　noncestr += $chars.charAt(Math.floor(Math.random() * maxPos));
 	 			}
 	 			var timestamp=Math.round(new Date().getTime()/1000);
   				$.ajax({
    	         	type:"post",
    	         	url: base_inf.base_herf+"html_member/getSignatureAjax.do", 
    	         	data:{
    	         		"timestamp":timestamp,
    	         		"noncestr":noncestr ,
    	         		"now_url":now_url 
    	         	},
    		        dataType:"json",
    		        asyn:false,
    		        success: function(data){
      		        	var signature=data.data;
     		        	wx.config({
     		        	      debug: false,
     		        	      appId: "wx62d81eec40f745b4",
     		        	      timestamp: timestamp,
     		        	      nonceStr: noncestr,
     		        	      signature: signature,
     		        	      jsApiList: [
     		        	        'checkJsApi',
     		        	        'onMenuShareTimeline',
     		        	        'onMenuShareAppMessage',
     		        	        'onMenuShareQQ',
     		        	        'onMenuShareWeibo',
     		        	        'onMenuShareQZone',
     		        	        'hideMenuItems',
     		        	        'showMenuItems',
     		        	        'hideAllNonBaseMenuItem',
     		        	        'showAllNonBaseMenuItem',
     		        	        'translateVoice',
     		        	        'startRecord',
     		        	        'stopRecord',
     		        	        'onVoiceRecordEnd',
     		        	        'playVoice',
     		        	        'onVoicePlayEnd',
     		        	        'pauseVoice',
     		        	        'stopVoice',
     		        	        'uploadVoice',
     		        	        'downloadVoice',
     		        	        'chooseImage',
     		        	        'previewImage',
     		        	        'uploadImage',
     		        	        'downloadImage',
     		        	        'getNetworkType',
     		        	        'openLocation',
     		        	        'getLocation',
     		        	        'hideOptionMenu',
     		        	        'showOptionMenu',
     		        	        'closeWindow',
     		        	        'scanQRCode',
     		        	        'chooseWXPay',
     		        	        'openProductSpecificView',
     		        	        'addCard',
     		        	        'chooseCard',
     		        	        'openCard'
     		        	      ]
     		        	  });
      		        	 
     		        }
    		   });
   				
   				wx.error(function (res) {
   					console.log(res.errMsg); 
   				});
   				
   				
   				wx.ready(function () {
   					console.log("success"); 
   				});
  	 		
  			

  		//扫一扫优惠买单
  		function saoyisao(){
  			wx.scanQRCode({
			      needResult: 1,
			      scanType: ["qrCode","barCode"],
		 	      success: function (res) {
		 	    	 var str=res.resultStr
  			          
  			      }
			});
  		}
  		
  		//微信定位
  		function wxdingwei(){
  			wx.ready(function () {
  			//微信定位
				wx.getLocation({
 				    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
 				    success: function (res) {
 				        var wxlat = res.latitude; // 纬度，浮点数，范围为90 ~ -90
 				        var wxlng = res.longitude; // 经度，浮点数，范围为180 ~ -180。
 				        var speed = res.speed; // 速度，以米/每秒计
 				        var accuracy = res.accuracy; // 位置精度
 				     	gogogo3(wxlng,wxlat);
	 				}
				});
   			});
   		}
  		
  		//微信坐标管理转换
	    function gogogo3(wxlng,wxlat){
	    		$.ajax({
		         	type:"post",
		         	url:"https://api.map.baidu.com/geoconv/v1/?coords="+wxlng+","+wxlat+"&from=1&to=5&ak=ltKk6fs4gHVPtWqtAsErXpBOd7ezHIWe", 
			        dataType:"jsonp",
			        success: function(data){
	 		        	var status=data.status;
	 		        	var result=data.result;
	 		        	if(status == 0){
	 		        		window.lat=result[0].y;
	   		        		window.lng=result[0].x;
 		 		        	pcd(lng,lat);
	 		        	}else{
	 		        		gogogo2();
	 		        	}
	 		        }
			   });
 	    }
 
	     
	    
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
	    
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<base href="<%=basePath%>">
	<title>油卡充值</title>
 	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
 	<style type="text/css">
 	body{ line-height:1.2; font: 16px Arial, "微软雅黑", sans-serif;}
	body,ul,li,p,ol,dl,dt,dd,form,input,h1,h2,h3,h4,h5,h6{ margin:0; padding:0;}
	body{
		padding-top:40px;
	}
	ul{ list-style:none}
	a{ text-decoration:none;}
	a,input,button{-webkit-tap-highlight-color:rgba(255,0,0,0); outline:none}
	button{ cursor:pointer}
	ul li{    padding: 9px; }
	.top {
	    background: #ff0600;
	    height: 40px;
	    right: 0;
	    top: 0;
	    width: 100%;
	    position: fixed;
	    z-index: 99;
	    font-size: 16px;
	}
	.land-tx-img {
	    width: 99%;
	    height: 80px;
	    border-radius: 80px;
	    overflow: hidden;
	    display: block;
	    margin: 16px auto 7px;
	}
	.land-tx-img img {
	    width: 99%;
 	}
	.land-phone, .land-yzm {
	    background: #ededed;
	    height: 40px;
	    line-height: 40px;
	    width: 70%;
	    border-radius: 5px;
	    border: none;
	    text-align: center;
	    font-size: 14px;
	}
	.Login-button {
	    background: #ff0600;
	    border: none;
	    width: 100%;
	    height: 40px;
	    line-height: 40px;
	    color: #fff;
	    text-align: center;
	    border-radius: 25px;
	    font-size: 16px;
	    margin: auto;
	    display: block;
	}
	。
 	.checkcode canvas{
		float: right;
		width: 85px;
		height: 36px;
		padding: 3px;
	}
	</style>
</head>
<body style="background:#fff;">
	<form action="" id="Form" name="Form" method="post" >
		<input name="arsid" value="4000" type="hidden">
		<input name="session_orderid" value="${session_orderid}"  type="hidden">
		<div class="onediv"  >
			<nav class="top">
	 			<div style="text-align:center;line-height:40px;color:#fff">充值石化石油</div>
			</nav>
			<div class="land-tx clearfix">
					<i class="land-tx-img"><img src="img/topbg.jpg" ></i>
	 		</div>  
			<ul class="land-list">
				<li>手机号码：<input class="land-phone" type="number"  name="phone" id="phone" placeholder="手机号码"  maxlength="11"/></li>
				<li>油卡卡号：<input class="land-phone" type="text" name="oilcard_number" id="oilcard_number" placeholder="石油卡号"/></li>
				<li>充值金额：<input class="land-phone" type="text" name="money" id="money" placeholder="充值金额"/></li>
	 			<li><input type="button" class="Login-button" style="background-color: #ff0600;" value="充值"  onclick="SurePay(this)"/></li>
	  		</ul>
		</div>
	</form>
		<script src="js/jquery-1.8.0.min.js"></script>
		<script src="js/jquery.form.js"></script>
  		<script type="text/javascript">
  		var flag=true;
 		//开始支付
		 function SurePay(obj){
 			if(flag){
  				flag=false;
 				$("#Form").ajaxSubmit({  
 				  	url : '<%=basePath%>payorder/getWxPayUrl.do',
 			        type: "POST",//提交类型  
 			      	dataType:"json",
 			   		success:function(data){
 			   			if(data.result == "1"){
 			   				window.location.href = data.data;
 			   			}else{
 			   				alert(data.message);
 			   			}
 			   			flag=true;
 	  				},
 	  				error : function() {
 	  					alert("没有得到支付地址，请检查配置文件关于公众号跳转支付的配置信息");
 	  				}
 				}); 
 			}
  		 }
		 
  	</script>
</body>
 </html>

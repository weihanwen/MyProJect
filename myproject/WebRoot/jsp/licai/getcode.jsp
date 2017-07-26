<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>绑定手机号码</title>
    <base href="<%=basePath%>">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
</head>
<style>
    body{
        
    }
    ul{
        width:90%;
        display: block;
    }
     li{
        padding: 0;
        list-style-type:none;
    }
    .inf_box{
       position: absolute;
	    margin: auto;
	    top: 26%;
	    padding: 6% 12%;
	    height: 17.6rem;
	    border-radius: 4px;
	    box-sizing: border-box;
    }
    .bd1_li{
        border-radius: 4px;
        border: 1px solid #bd7200;
    }
    .mg_b{
        margin-bottom: 0.9rem;
    }
    .li_span{
        display: inline-block;
        height: 100%;
        border-right: 1px solid #bd7200;
        width: 2.4rem;;
    }
    .li2{
        width: 63%;
        position: relative;
    }
    .li_div{
        position: absolute;
        right: -60%;
        top:0;

        background: #ffa31b;
        color: #fff;
        line-height: 2.5rem;
        min-width: 4.2rem;
    }
    .li_img{
        position: relative;
        vertical-align: middle;
        height: 80%;
        padding: 10% 0;
        font-size: 0;
    }
    .sure {
      	background: #ffa31b;
	    color: #fff;
	     padding: 5px;
	    display: block;
	    width: 13%;
	    margin: 0 auto;
	}
</style>
<body> 
<ul class="inf_box clf">
    <li class=" bd1_li mg_b">
        <span class="fwb li_span"><img class="li_img" src="img/shouji.png" style="left: 0.5rem;" alt=""></span>
        <input type="text" maxlength="13" id="phone" placeholder="请输入手机号" style="background: rgba(0,0,0,0);width: 65%" oninput="lenghtOK(this)"  >
    </li>
    <li class="  bd1_li  mg_b li2">
        <span class="fwb li_span"><img class="li_img" src="img/yanzhengma.png" style="left: 0.5rem;" alt=""></span>
        <input type="text" placeholder="请输入验证码"style="background: rgba(0,0,0,0);width: 50%" id="messagecode">
        <div class="li_div bd1_li">
            <span class="txr  code " >获取验证码</span>
        </div>
    </li>
     <li class="txc  pd2 mg_b">
        <span class="sure" onclick="submitLogin()">确认</span>
    </li>
</ul>
</body>
<script src="js/jquery-1.8.0.min.js"></script>
<script>
//判断手机号码的位数
function lenghtOK(obj){
	if($(obj).val().length == 11){
		var val=obj.value;
        if(!(/^1(3|4|5|7|8)\d{9}$/.test(val))){
            alert("手机号码有误，请重填");
            return;
        }
   		$(".code").attr("onclick","getDxCode()");
 		getTuiJianList(obj);
 	}else{
 		$(".code").attr("onclick","");
		$(".code").html("获取验证码");
	}
}

//======================================================================================================

	//获取验证码
	var validCode=true;
 	function getDxCode(){
		if($("#phone").val() == null || $("#phone").val() == "" ){
			alert("手机号码不能为空");
			return;
		} 
  		if (validCode) {
			$.ajax({
		        type:"post",
		        url:"<%=basePath%>licai/getCode.do", 
		  	 	data:{"phone":$("#phone").val()},                
		        dataType:"json",
		        success: function(data){
		        	 if(data.result == "1"){
  				       	 		var time=60;
				       			var code=$(".code");
 			       				validCode=false;
 			       				code.attr("onclick","");
			       				var t=setInterval(function() {
				       				time--;
				       				code.html(time+"秒");
				       				if (time==0) {
				       					clearInterval(t);
				       					code.html("重新获取");
				       					validCode=true;
 				       					code.attr("onclick","getDxCode()");
		 		       				}
				       			},1000);
 			       	 }else{
 			       		 alert(data.message);
 			       	 }
		         }
		    });
		}
 	}


	//验证登录
	function submitLogin(){
    		$.ajax({
                type:"post",
                url:'<%=basePath%>licai/saveOK.do', 
          	 	data:{
          	 			"messagecode":$("#messagecode").val(),
          	 		 	"text_infor":"${pd.text_infor}" ,
          	 		 	"manager_phone":"${pd.manager_phone}" ,
          	 		 	"wx_user_openid":"${pd.wx_user_openid}" ,
          	 		 	"name":"${pd.name}" ,
          	 		 	"image_url":"${pd.image_url}" ,
          	 		 	"phone":"${pd.phone}"  
          	 		 },                
                dataType:"json",
                success: function(data){
               	 	if(data.result == "1"){
               	 		window.location.href="<%=basePath%>jsp/yuyueok.jsp";
               	 	}else{
               	 		alert(data.message);
                	}
                 }
            });
		}
	
	
//===============================================================================
 
</script>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
	<title>购物车</title>
	<base href="<%=basePath%>">
	<link rel="stylesheet" href="css/wx/style.css">
	<link rel="stylesheet" href="css/fonts/iconfont.css">
	<link rel="stylesheet" href="css/wx/normalize.min.css">
</head>
<body>
	<header>
		<div class="goback" onclick="backreturn()">‹</div>
		<div class="title">
			 <div class="djs">
		        <h6>购物车</h6>
 	    	</div>
		</div>
	</header>
	<div class="shopping">
		 <ul class="shop">
		 	<c:forEach items="${shopList }" var="var">
		 		<li class=" del shop_item ${var.lunch_id}">
			 		<div class="shop_sign"> 
			 			<span  shopcart_id="${var.shopcart_id}" class="iconfont icon-dui" onclick="chose()"></span> 
			 		</div>
			 		<div class="sp">
			 			<div class="sp_chose"> <img src="${var.inside_banner}"> </div>
			 			<div class="sp_cont"> 
			 				<div class="sp_tit">${var.lunch_name}</div> 
 				 			<div class="sp_spr"> 
				 				<p class="sp_xj">￥${var.sale_money}</p> 
	 			 			</div>
	 			 			<div class="sp_sl" lunch_id="${var.lunch_id}">
			 					<a class="down sljj" onclick="reduceGoodsNumber(this)" style="height:100%;">-</a> 
			 					<span class="number" >${var.shop_number}</span> 
			 					<a class="up sljj" onclick="addGoodsNumber(this)" style="height:100%;">+</a> 
			 				</div>
			 			</div>
			 		</div>
 			 	</li>
		 	</c:forEach>
 		 </ul>
	</div>
	<div class="shopfooter">
		<span class="iconfont icon-dui foot_xz" onclick="chose()" name="quanxuan"></span><span>全选</span>
		<span>合计：</span><span class="zongjia">0元</span>
		<a class="foot_tj"  style="background:#aaa;" onclick="goReadyPayOrder()">提交订单</a>
	</div>
</body>
<script src="js/jquery-1.8.3.min.js"></script>
<script>

function backreturn(){
	window.location.href="wxmember/wxindex.do";
}
	// 选择
function chose(e){   //选择点击
		var ev=e||window.event;
        var target=ev.target||ev.srcElement;
        if ($(target).attr("check")=="checked") {
        	qxxuanze(target);
        }else{
        	xuanze(target);
        }
        zongjia();
}

function xuanze(target){    //由父级类名判断点击位置 并向相应元素添加属性
	var farther=$(target).parent()[0].className;
	switch (farther)
	{
		case "shopfooter":   //全选
		$(".icon-dui").attr("check","checked");
		xuanran();
		break;
		case "shop_sign":    //商品
		$(target).attr("check","checked");
		xuanran();
		break;
	}
	$(".foot_tj").css("background","#fc0d1b");
}
function xuanran(target){     //渲染页面
 	$(".icon-dui").removeAttr("style");
	if($("body [check=checked]").length>0){
		$("body [check=checked]").css({"background":"#f85e61","border":"2px solid #f85e61"});
	}
}
function qxxuanze(target){		//取消选择
	var farther=$(target).parent()[0].className;
	switch (farther)
	{
		case "shopfooter":    
		$(".icon-dui").removeAttr("check");
		xuanran();
		break;
		case "shop_sign":
		$(target).removeAttr("check");
		xuanran();
		break;
	}
	}

 

//购物车加
function addGoodsNumber(obj){
	var lunch_id=$(obj).parent().attr("lunch_id");
  	if(addShopCart("1",lunch_id)){
		sp_num=Number($(obj).prev()[0].innerText)+1;
		$(obj).prev()[0].innerText=sp_num;
		zongjia();
	}
}
//购物车减
function reduceGoodsNumber(obj){
	var lunch_id=$(obj).parent().attr("lunch_id");
	if(addShopCart("-1",lunch_id)){
		sp_num=$(obj).next()[0].innerText-1;
		if (sp_num > 0) {
			$(obj).next()[0].innerText=sp_num;
		}else{
 			$("."+lunch_id).remove();
		}
		zongjia();
	}
}

//添加进购物车
function addShopCart(number,lunch_id){
	var flag=false;
	$.ajax({
		type:"post",
			url:"wxmember/addshopcart.do",
			data:{ "lunch_id":lunch_id ,"number":number },
			dataType:"json",
			async: false,
			success:function(data){
				  if(data.result == "1"){
					  flag=true;
				  }else{
					  alert(data.message);
				  }
			}
	});  
 	return flag;
}


//计算总价
function zongjia(){
	var allshopcart_id="";
	var sel_span = $("span[check=checked]");
	for (var i = 0; i < sel_span.length; i++) {
		if($(sel_span[i]).attr("check")=="checked"&&$(sel_span[i]).parent()[0].className=="shop_sign") {
			var shopcart_id=$(sel_span[i]).attr("shopcart_id");
			if(shopcart_id != ""){
				allshopcart_id+=shopcart_id+",";
			}
		}
	};
	if(allshopcart_id != ''){
		$.ajax({
	        type:"post",
	        url:'wxmember/countAllShopMoney.do', 
 	  	 	data:{
	  	 		"allshopcart_id":allshopcart_id 
		  	},                
	        dataType:"json",
	        async: false,
	        success: function(data){
	        	$(".zongjia")[0].innerText=data.data+"元";
		    }
	    });
	}else{
		$(".zongjia")[0].innerText="0元";
	}
		
}

 

//去结算页面
function goReadyPayOrder(){
 	 var allshopcart_id="";
	 var sel_span = $("span[check=checked]");
	 for (var i = 0; i < sel_span.length; i++) {
			if($(sel_span[i]).attr("check")=="checked"&&$(sel_span[i]).parent()[0].className=="shop_sign") {
				var shopcart_id=$(sel_span[i]).attr("shopcart_id");
				if(shopcart_id != ""){
					allshopcart_id+=shopcart_id+",";
				}
			}
	 };
	 if(allshopcart_id != ''){
		 window.location.href="wxmember/goPayJSP.do?shop_type=1&allshopcart_id="+allshopcart_id+"&order_type=1";
	 }else{
		 alert("请选择商品");
	 }
}
 

</script>
</html>
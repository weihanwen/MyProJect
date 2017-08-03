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
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>预定</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="css/wx/labary/predefine.css">
    <link rel="stylesheet" href="css/wx/labary/swiper.min.css">
	<link rel="stylesheet" href="css/wx/style.css">
</head>
<body>
<nav class="top">
	<div>预定</div>
</nav>
<section class="onesection">
			<!--大类类别-->
		     <div class="bigsort">
		      	<c:forEach items="${leibieList}" var="var" varStatus="vs">
		      		<c:choose>
		      			<c:when test="${vs.index == 0}">
			      			<span onclick="changeShoyLb('${var.category_id}')" >
								<img src="${var.image_url}">${var.title}
							</span>
						</c:when>
		      			<c:otherwise>
			      			<span onclick="changeShoyLb('${var.category_id}')"  style="color:#909090"  >
								<img src="${var.image_url}">${var.title}
							</span>
						</c:otherwise>
		      		</c:choose>
		       	</c:forEach>
		 	</div>
		 	<c:choose>
		 		<c:when test="${!empty daypd}">
		 			<!--预定说明-->
				 	 <div class="ydinfor">
				      	 	<div class="one">预定说明</div>
				      	 	<div class="two">
				      	 		<span>当前可预定${daypd.day}（${daypd.week}）中午美味便当</span><br>
				      	 		<span>送餐时段：${daypd.starttime_slot } - ${daypd.endtime_slot } </span>
				      	 	</div>
				 	</div>
		  		</c:when>
		 		<c:otherwise>
		 			<div style="width:100%;height:65px;text-align: center;">
		 				<img src="images/fl_d.png" >
		 			</div>
		  		</c:otherwise>
		 	</c:choose>
 			<!--商品循环-->
			<div class="allgoods">
		 		 <!-- <div class="ydgoods" >
				 		<img alt="" src="images/2.jpg"   />
				 		<span >西红柿炒鸡蛋</span>
		 		 		<span >20份可预订</span>
				 		<span >66元/份</span>
				 </div> -->
		  	</div>
 </section>
<footer class="footerdi guding clf">
	<ul>
		<li class="f_whole">
			<a  href="wxmember/wxindex.do">
				<i ></i>
				点餐
			</a>
		</li>
		<li class="f_jiexiao">
			<a style=" color: #72c4f9; " href="wxmember/yuding.do">
				<i class="cur"></i>
				预定
			</a>
		</li>
		<li class="f_personal">
 			<a href="wxmember/gome.do">
				<i></i>
				我的
			</a>
		</li>
	</ul>
</footer>
</body>
<script src="js/wx/library/jquery-1.12.4.min.js"></script>
<script src="js/wx/tongyong.js"></script>
<script type="text/javascript">


//页面加载后执行
$(function(){
 	 $(".bigsort").find("span").eq(0).click();
});

//获取商品
function changeShoyLb(category_id,obj){
  	$.ajax({
		type:"post",
			url:"wxmember/getLunchList.do",
			data:{ "category_id":category_id ,"order_type":"2"},
			dataType:"json",
			success:function(data){
				 var lunchList=data.data;
				 $(".allgoods").empty();
				 for (var i = 0; i < lunchList.length; i++) {
					 var s="<div class='ydgoods' onclick='goDetail(this,2)' lunch_id='"+lunchList[i].lunch_id+"' category_id='"+lunchList[i].category_id+"' >"+
								 		"<img alt='' src='"+lunchList[i].inside_banner+"'   />"+
							 		"<span>"+lunchList[i].lunch_name+"</span>"+
					 		 		"<span>"+lunchList[i].reservation_number+"份可预订</span><span >"+lunchList[i].sale_money+"元/份</span>"+
							 "</div>";
					$(".allgoods").append(s);
				 }  
			}
	});  
}

//前往详情
function goDetail(obj,order_type){
	
}


</script>
</html>
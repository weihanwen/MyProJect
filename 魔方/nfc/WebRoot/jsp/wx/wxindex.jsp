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
    <title>点餐</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="css/wx/labary/predefine.css">
    <link rel="stylesheet" href="css/wx/labary/swiper.min.css">
	<link rel="stylesheet" href="css/wx/style.css">
</head>
<body>
<nav class="top">
	<div>点餐</div>
</nav>
<section class="onesection">
 	<div class="lunboclass">
		<!-- 轮播图 -->
		<div class="swiper-container swiper-container_2">
			<div class="swiper-wrapper" >
				 <c:forEach items="${lunboList}" var="var">
				 	<c:choose>
				 		<c:when test="${var.link_type eq '2'}"><div class='swiper-slide'><a href="${var.link_content}"><img src="${var.image_url}"></a></div></c:when>
				 		<c:when test="${var.link_type eq '3'}"><div class='swiper-slide'><a href="wxmember/godetailBygoods.do?lunch_id=${var.link_content}"><img src="${var.image_url}"></a></div></c:when>
				 		<c:otherwise><div class='swiper-slide'><a><img src="${var.image_url}"></a></div></c:otherwise>
				 	</c:choose>
 				 </c:forEach>
 			  </div>
			<div class="swiper-pagination swiper-pagination_2"  style=" width: 100%;"></div>
		</div>
	</div>
    
	<!--大类类别-->
     <div class="bigsort" style="display:none;">
      	<c:forEach items="${leibieList}" var="var" varStatus="vs">
      		<c:choose>
      			<c:when test="${vs.index == 0}">
	      			<span onclick="changeShoyLb('${var.category_id}',this)" >
						<img src="${var.image_url}">${var.title}
					</span>
				</c:when>
      			<c:otherwise>
	      			<span onclick="changeShoyLb('${var.category_id}',this)"  style="color:#909090"  >
						<img src="${var.image_url}">${var.title}
					</span>
				</c:otherwise>
      		</c:choose>
       	</c:forEach>
 	</div>
	
	<!--商品循环-->
	<div class="allgoods">
		 
 	</div>
 	
</section>
<footer class="footerdi guding clf">
	<ul>
		<li class="f_whole">
			<a style=" color: #72c4f9; " href="wxmember/wxindex.do">
				<i class="cur"></i>
				点餐
			</a>
		</li>
		<li class="f_jiexiao">
			<a href="wxmember/yuding.do">
				<i></i>
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
<script src="js/wx/library/swiper.min.js"></script>
<script src="js/wx/tongyong.js"></script>
<script type="text/javascript">
//页面加载后执行
$(function(){
	 //构造轮播  图片
	 var swiper = new Swiper('.swiper-container_2', {
	    pagination: '.swiper-pagination_2',
	    paginationClickable: true,
	    autoplay : 5000,
	    loop:true
	 });
	 $(".bigsort").find("span").eq(0).click();
	 
});
//获取商品
function changeShoyLb(category_id,obj){
  	$.ajax({
		type:"post",
			url:"wxmember/getLunchList.do",
			data:{ "category_id":category_id ,"order_type":"1" },
			dataType:"json",
			success:function(data){
				 var lunchList=data.data;
				 $(".allgoods").empty();
				 for (var i = 0; i < lunchList.length; i++) {
					 var s="<div class='goodsshow' onclick='goDetail(this,1)' lunch_id='"+lunchList[i].lunch_id+"' category_id='"+lunchList[i].category_id+"' >"+
								"<div class='one'>"+
									"<img src='"+lunchList[i].product_cover+"' />"+
									"<div class='two'>"+
										""+lunchList[i].sale_money+"元/份 赠送积分"+lunchList[i].send_integral+"分 <span>到手价："+lunchList[i].daoshoumoney+"元</span> 仅剩"+lunchList[i].inventory_number+"份"+
									"</div>"+
								"</div>"+
							"</div>";
					$(".allgoods").append(s);
				 }
			}
	});  
}
//前往详情
function goDetail(obj,order_type){
	window.location.href="wxmember/godetailBygoods.do?lunch_id="+$(obj).attr("lunch_id")+"&order_type="+order_type+"&category_id="+$(obj).attr("category_id");
}

</script>
</html>
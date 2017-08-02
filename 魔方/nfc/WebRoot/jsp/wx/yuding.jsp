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
    <title>九鱼魔方</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="css/wx/labary/predefine.css">
    <link rel="stylesheet" href="css/wx/labary/swiper.min.css">
	<link rel="stylesheet" href="css/wx/style.css">
</head>
<body>
<section style="height:86%;">
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
 	<!--预定说明-->
 	 <div class="ydinfor">
      	 
 	</div>
	
	<!--商品循环-->
	<div class="allgoods">
		 
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
			<a style=" color: #e90000; " href="wxmember/yuding.do">
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
<script type="text/javascript">
var base_inf={
         base_herf:"<%=basePath%>" 
};
</script>
<script src="js/wx/library/jquery-1.12.4.min.js"></script>
<script src="js/wx/tongyong.js"></script>
<script type="text/javascript">
//页面加载后执行
$(function(){
 	 $(".bigsort").find("span").eq(0).click();
});
</script>
</html>
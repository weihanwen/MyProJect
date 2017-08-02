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
<nav class="top">
	<div style="text-align:center;line-height:40px;color:#fff">个人中心</div>
</nav>
<section style="height:86%;">
	<%-- <div class="my-top clearfix">
		<a href="<%=basePath%>html_me/goMe.do?type=1">
			<b class="z-arrow my-arrow fr"></b>
			<span class="my-top-tx fl" style=" width: 80px; "><img src="${empty pg.image_url?'../../img/moren.png':pg.image_url}" ></span>
			<div class="my-top-text">
				<p><i class="people-icon"></i>${pg.name }</p>
				<p>${pg.show_lookid}<b></b></p>
			</div>
	 	</a>
	</div>
	<article class="rm-list tj-list  ">
		<ul>
			<li><a href="<%=basePath%>html_me/goMe.do?type=6"><i class="my-list-one"></i>提货劵<b class="z-arrow"></b></a></li>
			<li><a href="<%=basePath%>html_me/goMe.do?type=7"><i class="my-list-three"></i>我的订单<b class="z-arrow"></b></a></li>
		</ul>
	</article> --%>
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
			<a  href="wxmember/yuding.do">
				<i class="cur"></i>
				预定
			</a>
		</li>
		<li class="f_personal">
 			<a style=" color: #e90000; " href="wxmember/gome.do">
				<i class="cur"></i>
				我的
			</a>
		</li>
	</ul>
</footer>
</body>
</html>
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
    <title>个人中心</title>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="css/wx/labary/predefine.css">
    <link rel="stylesheet" href="css/wx/labary/swiper.min.css">
	<link rel="stylesheet" href="css/wx/style.css">
</head>
<body>
<section style="height:86%;">
	<div class="my-top clearfix">
		<a href="">
			<b class="z-arrow my-arrow fr"></b>
			<span class="my-top-tx fl" style=" width: 80px; "><img src="" ></span>
			<div class="my-top-text">
				<p><i class="people-icon"></i></p>
				<p><b></b></p>
			</div>
	 	</a>
	</div>
	<article class="rm-list tj-list  ">
		<ul>
			<li><a ><i class="my-list-one"></i>提货劵<b class="z-arrow"></b></a></li>
			<li><a  ><i class="my-list-three"></i>我的订单<b class="z-arrow"></b></a></li>
		</ul>
	</article>  
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
				<i></i>
				预定
			</a>
		</li>
		<li class="f_personal">
 			<a style=" color: #72c4f9; " href="wxmember/gome.do">
				<i class="cur"></i>
				我的
			</a>
		</li>
	</ul>
</footer>
</body>
</html>
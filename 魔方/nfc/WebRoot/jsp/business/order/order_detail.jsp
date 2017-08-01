<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
 		<meta charset="utf-8" />
		<title>详情</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
        <meta name="renderer" content="webkit">
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="css/bootstrap.min.css" rel="stylesheet" />
		<link href="css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="css/font-awesome.min.css" />
		<!--[if IE 7]><link rel="stylesheet" href="css/font-awesome-ie7.min.css" /><![endif]-->
		<!--[if lt IE 9]><link rel="stylesheet" href="css/ace-ie.min.css" /><![endif]-->
 		<link rel="stylesheet" href="css/chosen.css" /><!-- 下拉框 -->
		<link rel="stylesheet" href="css/ace-responsive.min.css" />
		<link rel="stylesheet" href="css/ace-skins.min.css" />
 		<link rel="stylesheet" href="css/datepicker.css" /><!-- 日期框 -->
 		<!-- 引入 -->
		<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
	</head>
<body>
	<form action="" name="Form" id="Form" method="post">
 		<div id="zhongxin" style="width: 70%;margin: 5% auto;">
		<table>
			<c:if test="${pd.order_status eq '1' }">
				<c:if test="${pd.order_type eq '2' }">
					<tr>
						<td>预定时间 ：</td><td>${pd.reserve_day }</td>
		 			</tr>
	 			</c:if>
	 			<tr>
					<td>配送时间 ：</td><td>${pd.delivery_time }</td>
	 			</tr>
	 			<tr>
					<td>预计到达时间 ：</td><td>${pd.reserve_arrival_time }</td>
	 			</tr>
	 			<tr>
					<td>配送费 ：</td><td>${pd.delivery_fee }</td>
	 			</tr>
			</c:if>
 			<tr>
				<td>下单时间 ：</td><td>${pd.createtime }</td>
 			</tr>
			<tr>
				<td>订单号 ：</td><td>${pd.looknumber }</td>
 			</tr>
 			<tr>
				<td>订单状态 ：</td>
				<td>
					<c:if test="${pd.order_status eq '1'}">代配送</c:if>
					<c:if test="${pd.order_status eq '2'}">已完成</c:if>
					<c:if test="${pd.order_status eq '99'}">退货</c:if>
				</td>
 			</tr>
 			<tr>
				<td>收货人 ：</td><td>${pd.contacts}-${pd.contacts_number}</td>
 			</tr>
 			<tr>
				<td>收货地址 ：</td><td>${pd.address_name}-${pd.corporate_name}-${pd.floor_numbe }</td>
 			</tr>
 			<tr>
				<td class="tj">菜品列表 ：</td>
				<td>
					<c:forEach items="${lunchList}" var="var">
						<span>${var.lunch_name } X ${var.shop_number }</span>
   	            	</c:forEach>
				</td>
			</tr>
			<tr>
				<td>商品总价 ：</td><td>${pd.allmoney}</td>
 			</tr>
 			<tr>
				<td>优惠金额 ：</td>
				<td>${pd.discount_money}（使用红包/提货券）</td>
 			</tr>
			<tr>
				<td>实际支付 ：</td>
				<td>${pd.actual_money}（积分支付：${pd.use_integral}，微信支付：${pd.use_wx}）</td>
 			</tr>
 			<tr>
				<td>赠送积分 ：</td>
				<td>${pd.send_integral}</td>
 			</tr>
   		</table>
		</div>
		<div style="width:40%;padding-top:5%;margin:0 auto;">
 					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">返回</a>
		</div>
 		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
 	</form>
 
 		<script type="text/javascript">window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/ace-elements.min.js"></script>
		<script src="js/ace.min.js"></script>
		<script type="text/javascript" src="js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="js/jquery.form.js"></script>
 		<script type="text/javascript" src="js/jquery.tips.js"></script>
		<script type="text/javascript">
 			
		$(window.parent.hangge());
		$(function() {
 			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
 			//日期框
			$('.date-picker').datepicker();
 			
    	});
  		  
 
</script>
</body>
</html>
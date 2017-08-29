<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
     <title>选择地址</title>
    <link rel="stylesheet" href="css/wx/labary/predefine.css">
    <link rel="stylesheet" href="css/wx/frozen.css">
    <link rel="stylesheet" href="css/wx/use.css">
</head>
<body>
<header class="ui-header ui-header-positive ui-border-b bg_ff0600">
    <i class="ui-icon-return" onclick="history.back()"></i><h1 class="col_f">选择地址</h1>
</header>
<section>
<!-- 展示地址列表 -->
<div class="rp_item list">
	<c:forEach items="${addressList}" var="var">
		<div class="rp_box clf" onclick="sureThisAddress('${var.wxmember_address_id}')">
	        <div class="cash col_c9">
	        	<span style="font-size: 0.25rem;;">${address_name}-${corporate_name}-${floor_numbe}</span>
 	        </div>
	        <c:if test="${var.wxmember_address_id eq pd.wxmember_address_id}"><div class="hongbao"><span>✔</span>  </div></c:if>
	        <div class="ball ball_l"></div>
	        <div class="ball ball_r"></div>
	    </div>
	</c:forEach>
</div>


<!-- 搜索地址 -->
<div class="rp_item search" style="display:none">


</div>


<!-- 新增地址  -->
<div class="rp_item add" style="display:none">


</div>


</section>
<div class="header footer">
    <span class='ccyc head_tit '  onclick="openSearch()">没有地址？新增一个</span>
</div>
<script type="text/javascript">
//开启搜索页面
function openSearch(){
	$(".search").show();
	$(".add").hide();
	$(".list").hide();
}


//前往新增页面
function openAdd(){
	$(".search").hide();
	$(".add").show();
	$(".list").hide();
	//开始ajax
	
	//完成后跳转
	
	
}


//确认使用地址
function sureThisAddress(wxmember_address_id){
	window.location.href="wxmember/goPayJSP.do?shop_type=${pd.shop_type}&allshopcart_id=${pd.allshopcart_id}&order_type=${pd.order_type}&lunch_idstr=${pd.lunch_idstr}&wxmember_address_id="+wxmember_address_id+"&wxmember_redpackage_id=${wxmember_redpackage_id}&wxmember_tihuojuan_idstr=${pd.wxmember_tihuojuan_idstr}";
}

</script>
</body>
</html>
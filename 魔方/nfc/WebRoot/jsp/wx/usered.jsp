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
     <title>选择红包</title>
    <link rel="stylesheet" href="css/wx/labary/predefine.css">
    <link rel="stylesheet" href="css/wx/frozen.css">
    <link rel="stylesheet" href="css/wx/usered.css">
</head>
<body>
<header class="ui-header ui-header-positive ui-border-b bg_ff0600">
    <i class="ui-icon-return" onclick="history.back()"></i><h1 class="col_f">选择红包</h1>
</header>
<section>
<!-- 可用 -->
<div class="rp_item">
	<c:forEach items="${redList}" var="var">
		<div class="rp_box clf" onclick="sureThisRed('${var.wxmember_redpackage_id}')">
	        <div class="cash col_c9">
	        	<span>￥</span> <span style="font-size:2rem;">  ${var.money}</span>
 	        </div>
	         <c:if test="${var.wxmember_redpackage_id eq pd.wxmember_redpackage_id}"><div class="hongbao"><span>✔</span>  </div></c:if>
	        <div class="ball ball_l"></div>
	        <div class="ball ball_r"></div>
	    </div>
	</c:forEach>
</div>
</section>
<div class="header footer">
    <span class='ccyc head_tit '  onclick="sureThisRed('')">不使用红包</span>
</div>
<script type="text/javascript">
//确认使用红包
function sureThisRed(redpackage_id){
	window.location.href="wxmember/goPayJSP.do?shop_type=${pd.shop_type}&allshopcart_id=${pd.allshopcart_id}&order_type=${pd.order_type}&lunch_idstr=${pd.lunch_idstr}&wxmember_address_id=${pd.wxmember_address_id}&wxmember_redpackage_id="+redpackage_id+"&wxmember_tihuojuan_idstr=${pd.wxmember_tihuojuan_idstr}";
}

</script>
</body>
</html>
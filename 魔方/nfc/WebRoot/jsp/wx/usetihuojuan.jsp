<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
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
     <title>选择提货卷</title>
    <link rel="stylesheet" href="css/wx/labary/predefine.css">
    <link rel="stylesheet" href="css/wx/frozen.css">
    <link rel="stylesheet" href="css/wx/use.css">
 </head>
<body>
<header class="ui-header ui-header-positive ui-border-b bg_ff0600">
    <i class="ui-icon-return" onclick="history.back()"></i><h1 class="col_f">选择提货卷</h1>
</header>
<section style=" background-color: #fff; overflow-y: scroll; ">
<ul class="tihuo">
		<li><input type="radio" value="1" name="pay_type" ${empty pd.wxmember_tihuojuan_idstr or pd.wxmember_tihuojuan_idstr eq ''?'checked':''}   /> <span>在线支付</span></li>
		<li><input type="radio" value="2" name="pay_type" ${empty pd.wxmember_tihuojuan_idstr or pd.wxmember_tihuojuan_idstr eq ''?'':'checked'}   /> <span>提货卷支付（${number }）</span></li>
		<c:forEach items="${tihuoredList}" var="var">
 				<c:choose>
					<c:when test="${var.isok eq '0'}">
						<li style="padding-left:29px"> 
 						 <span style="font-size: 19px;text-decoration: line-through;">${var.money}元用餐卷 不可使用</span>
						</li>
					</c:when>
					<c:otherwise>
						<li>
						 <input id="${var.wxmember_tihuojuan_id}" type="checkbox" value="1" class="id" name="id" ${fn:contains(pd.wxmember_tihuojuan_idstr,var.wxmember_tihuojuan_id)?checked:''}   />
				 		 <span style="font-size: 19px;">${var.money}元用餐卷</span>
				 		</li>
					</c:otherwise>
				</c:choose>
  		</c:forEach>
	</ul>
</section>
<div class="header">
    <span class='ccyc head_tit '  onclick="sureThisRed()">确认选择</span>
</div>
<script src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
//确认使用红包
function sureThisRed(){
	var value  = $('input[name="pay_type"]:checked').val(); //获取被选中Radio的Value值
	var wxmember_tihuojuan_idstr="";
	if(value == "2"){
		$(".id").each(function(n,obj){
			if($(obj).is(":checked")){
				wxmember_tihuojuan_idstr+=$(obj).attr("id")+",";
			}
		});
		if(wxmember_tihuojuan_idstr == ""){
			alert("请选择提货卷");
		}
	}
 	window.location.href="wxmember/goPayJSP.do?shop_type=${pd.shop_type}&allshopcart_id=${pd.allshopcart_id}&order_type=${pd.order_type}&lunch_idstr=${pd.lunch_idstr}&wxmember_address_id=${pd.wxmember_address_id}&wxmember_redpackage_id=${pd.wxmember_redpackage_id}&wxmember_tihuojuan_idstr="+wxmember_tihuojuan_idstr;
}

</script>
</body>
</html>
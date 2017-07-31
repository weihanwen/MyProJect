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
		<title>新增</title>
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
	<form action="redpackage/save.do" name="Form" id="Form" method="post">
		<input type="hidden" name="receive_condition_idstr" id="receive_condition_idstr"  value=""/>
		<div id="zhongxin" style="width: 70%;margin: 5% auto;">
		<table>
			<tr>
				<td>红包金额 ：</td>
				<td>
					<input  name="money"  id="money" value="" type="text" >
				</td>
			</tr>
			<tr>
				<td id="dz">发送地址：</td>
				<td>
					<select class="chzn-select" id="address_id"  name="address_id"  >
 	            		<option value="">请选择地址</option>
 	            		<c:forEach items="${addressList }" var="var">
 	            			<option value="${var.address_id }">${var.address_name }</option>
 	            		</c:forEach>
 	            	</select>
				</td>
			</tr>
			<tr>
				<td class="tj">条件列表 ：</td>
				<td>
					<c:forEach items="${conditionList }" var="var">
						<input type="checkbox" name="receive_condition" class="receive_condition" value="${var.receive_condition_id }" />${var.content }
   	            	</c:forEach>
				</td>
			</tr>
  		</table>
		</div>
		<div style="width:40%;padding-top:5%;margin:0 auto;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存并发送</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
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
  		 
		
		//保存
		function save(){
			if($("#money").val()==""){
				$("#money").tips({
					side:3,
		            msg:'金额不能为空',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#money").focus();
				return false;
			}
			if($("#address_id").val()==""){
				$("#dz").tips({
					side:3,
		            msg:'地址不能为空',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#dz").focus();
				return false;
			}
			//
			var receive_condition_idstr="";
			$(".receive_condition").each(function(n,obj){
				if($(obj).is(":checked")){
					receive_condition_idstr+=$(obj).val()+",";
				}
			});
			$("#receive_condition_idstr").val(receive_condition_idstr);
			if(receive_condition_idstr == ""){
				$(".tj").tips({
					side:3,
		            msg:'条件不能为空',
		            bg:'#AE81FF',
		            time:1
		        });
				$(".tj").focus();
				return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
 		}
		
		 
		
</script>
</body>
</html>
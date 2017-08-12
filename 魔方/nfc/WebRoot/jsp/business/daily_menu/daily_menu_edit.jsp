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
		<title>修改</title>
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
	<form action="daily_menu/edit.do" name="Form" id="Form" method="post">
		<input type="hidden" name="daily_menu_id" id="daily_menu_id"  value="${pd.daily_menu_id}"/>
		<input type="hidden" name="lunch_idstr" id="lunch_idstr"  value="${pd.lunch_idstr}"/>
		<input type="hidden" name="lunchnumberstr" id="lunchnumberstr"  value=""/>
		<div id="zhongxin" style="width: 80%;margin: 5% auto;">
		<table>
			<tr>
				<td>日期 ：</td>
				<td>
					<input class="span10 date-picker" name="day"  id="day" value="${pd.day }" type="text" data-date-format="yyyy-mm-dd"   placeholder="便当日期" style="width:208px;" >
				</td>
			</tr>
			<tr>
				<td>便当列表 ：</td>
				<td>
					<c:forEach items="${lunchList}" var="var">
						<c:choose>
							<c:when test="${fn:contains(pd.lunch_idstr,var.lunch_id )}">
								<div style="width:100%;"> 
									<input type="checkbox" name="lunch" class="lunch" value="${var.lunch_id }" checked />${var.lunch_name }
									>>>>
									当前库存： <input type="number" class="lunchnumber" lunch_id="${var.lunch_id}" value="${var.inventory_number}"/>
								</div>
 							</c:when>
							<c:otherwise>
 								<div style="width:100%;"> 
									<input type="checkbox" name="lunch" class="lunch" value="${var.lunch_id }" />${var.lunch_name }
									>>>>
									当前库存： <input type="number" class="lunchnumber" lunch_id="${var.lunch_id}" value="${var.inventory_number}"/>
								</div>
							</c:otherwise>
						</c:choose>
   	            	</c:forEach>
				</td>
			</tr>
			 
 		</table>
		</div>
		<div style="width:40%;padding-top:5%;margin:0 auto;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
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
			if($("#day").val()==""){
				$("#day").tips({
					side:3,
		            msg:'日期不能为空',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#day").focus();
				return false;
			}
			//
			var lunch_idstr="";
			$(".lunch").each(function(n,obj){
				if($(obj).is(":checked")){
					lunch_idstr+=$(obj).val()+",";
				}
			});
			$("#lunch_idstr").val(lunch_idstr);
			//设置库存
			var lunchnumberstr="";
			$(".lunchnumber").each(function(n,obj){
				lunchnumberstr+=$(obj).attr("lunch_id")+"@"+$(obj).val()+",";
 			});
  			$("#lunchnumberstr").val(lunchnumberstr);
  			//======
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
 		}
		
		 
		
</script>
</body>
</html>
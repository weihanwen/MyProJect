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
  		<!-- 引入 -->
		<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
		<script src="My97DatePicker/WdatePicker.js"></script>
		<style type="text/css">
		td{
 			width:50%;
 			text-align: center;
		}
		</style>
	</head>
<body>
	<form action="scheduled_time/edit.do" name="Form" id="Form" method="post">
		<input type="hidden" name="scheduled_time_id" id="scheduled_time_id"  value="${pd.scheduled_time_id}"/>
		<input type="hidden" name="category_idstr" id="category_idstr"  value=""/>
 		<div id="zhongxin" style="width: 90%;margin: 5% auto;">
		<table style="width: 100%; ">
			<tr>
				<td colspan="2">销售日期 ：
					<input name="day"  id="day" value="${pd.day }" type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"   style="width:208px;" >
				</td>
			</tr>
			<tr>
				<td colspan="2">时段名称：
					<select name="time_name" class="chzn-select" >
						<option value="午餐" ${pd.time_name eq '午餐' ?selected:'' }>午餐</option>
						<option value="晚餐" ${pd.time_name eq '晚餐' ?selected:'' }>晚餐</option>
					</select>
				</td>
 			</tr>
			<tr>
				<td colspan="2">销售时间段 ：
					<input type="time"  value="${pd.sale_starttime}" name="sale_starttime"  id="sale_starttime" value="00:00"  style=" width: 20%; " >
					至
					<input type="time"  value="${pd.sale_endtime}"   name="sale_endtime"  id="sale_endtime" value="00:00"    style=" width: 20%; ">
 				</td>
 			</tr>
			<tr>
				<td colspan="2">预定时间段 ：
 					<input type="text" value="${pd.yd_starttime}"   name="yd_starttime"  id="yd_starttime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"    style=" width: 27%; "   >
 					至
 					<input type="text"  value="${pd.yd_endtime}"  name="yd_endtime"  id="yd_endtime"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"       style=" width: 27%; " >
				</td>
			</tr>
			 <tr style="background-color: #f3f3f3">
				<td colspan="2">--------------可销售类别---------------</td>
 			</tr> 
 			<c:forEach items="${categoryList}" var="var">
 					<c:choose>
							<c:when test="${fn:contains(pd.category_idstr,var.category_id )}">
 								<tr style="background-color: #ffb6b6">
								 	<td colspan="2">
					 			 			<input checked onclick="changecolor(this)" type="checkbox"  class="category" value="${var.category_id }" />${var.title }
					 			 	</td>
								</tr>
							</c:when>
							<c:otherwise>
 								<tr>
								 	<td colspan="2">
					 			 			<input onclick="changecolor(this)" type="checkbox"  class="category" value="${var.category_id }" />${var.title }
					 			 	</td>
								</tr>
							</c:otherwise>
						</c:choose>
   			</c:forEach>
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
 		<script type="text/javascript" src="js/jquery.form.js"></script>
 		<script type="text/javascript" src="js/jquery.tips.js"></script>
		<script type="text/javascript">
 			
		$(window.parent.hangge());
		 
		//改变选中得背景颜色
		function changecolor(obj){
			if($(obj).is(":checked")){
				$(obj).parent().parent().css("background-color","#ffb6b6");
			}else{
				$(obj).parent().parent().css("background-color","#ffffff");
			}
		}
		
		//保存
		function save(){
			 if(isokTime()){
				 
					var category_idstr="";
					$(".category").each(function(n,obj){
						if($(obj).is(":checked")){
							category_idstr+=$(obj).val()+",";
						}
					});
					$("#category_idstr").val(category_idstr);
		  			//======
					
					$("#Form").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
			 }
			
 		}
		
		function isokTime(){
			  var start=$("#day").val();
			  start=start.split('-');  
 			  var start1=new Date(start[0],start[1]-1,start[2]);    //因为当前时间的月份需要+1，故在此-1，不然和当前时间做比较会判断错误
			  var nowday=new Date();
 			  if(nowday > start1 ){ 
			    	$("#day").tips({
						side:3,
			            msg:'开始排期要从明天开始',
			            bg:'#AE81FF',
			            time:1
			        });
					$("#day").focus();
			     	return false;
			 }
			 return true;
		}
</script>
</body>
</html>
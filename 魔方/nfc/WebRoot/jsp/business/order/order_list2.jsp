<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="renderer" content="webkit">
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%> 
	</head>
<body>
		
<div class="container-fluid" id="main-container">

	<div id="breadcrumbs">
	
	<ul class="breadcrumb">
		<li><i class="icon-home"></i> <a href="login_index.do" target="self">首页</a><span class="divider"><i class="icon-angle-right"></i></span></li>
		<li class="active">待配送订单管理</li>
	</ul><!--.breadcrumb-->
	
	<div id="nav-search">
	</div><!--#nav-search-->
	
	</div><!--#breadcrumbs-->

<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
 			<!-- 检索  -->
			<form action="order/list.do" method="post" name="Form" id="Form">
			<input type="hidden" name="order_status" value="2" />
 			<%-- <table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="looknumber" value="${pd.looknumber}" placeholder="订单编号" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
 					<td style="vertical-align:top;"> 
					 	<select class="selectpicker" name="address_id" id="address_id" data-placeholder="请选择配送点" style="vertical-align:top;width: 120px;">
							 <option value="">请选择</option>
							 <c:forEach items="${addressList}" var="var">
							 	<option value="${var.address_id }" ${var.address_id eq pd.address_id?'selected':'' } >${var.address_name}</option>
							 </c:forEach>
					  	</select>
					</td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<c:if test="${QX.cha == 1 }">
						<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
					</c:if>
				</tr>
			</table> --%>
  			<!-- 检索  -->
 			<table id="table_report" class="table table-striped table-bordered table-hover">
 				<thead>
					<tr>
 						<th>订单号</th>
 						<th>菜品及数量</th>
						<th>配送地址</th>
  						<th>收货人</th>
						<th>联系电话</th>
						<th>订单金额</th>
						<th>下单时间</th>
						<th>订单类型</th>
						<th>操作</th>
  					</tr>
				</thead>
 				<tbody>
 				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr>
 								<td><a onclick="detail('${var.order_id}')" >${var.looknumber}</a></td>
								<td>
									<c:forEach items="${var.lunchList}" var="lunch"  >
										<span>${lunch.lunch_name}X${lunch.shop_number}</span>
									</c:forEach>
								</td>
								<td>${var.address_name}</td>
 								<td>${var.contacts}</td>
 								<td>${var.contacts_number }</td>
 								<td>${var.allmoney}</td>
								<td>${var.createtime}</td>
								<td>
									<c:if test="${var.order_type eq '1' }">现点</c:if>
									<c:if test="${var.order_type eq '2' }">预定</c:if>
								</td>
								<td style="width: 30px;" class="center">
										 <div class='hidden-phone visible-desktop btn-group'>
	 										<c:if test="${QX.edit != 1 && QX.del != 1 }">
											<span class="label label-large label-grey arrowed-in-right arrowed-in">无权限</span>
											</c:if>
											<c:if test="${QX.edit == 1 }">
												<a     onclick="changeStatus('${var.order_id}','3','自配送','1');"  class="btn btn-mini btn-info"  >自配送</a>
											</c:if>
											<c:if test="${QX.del == 1 }">
												<a     onclick="changeStatus('${var.order_id}','3','第三方配送','2');"  class="btn btn-mini btn-info"  >第三方配送</a>
											</c:if> 
											</div> 
 								</td>
    						</tr>
 						</c:forEach>
						</c:if>
						<c:if test="${QX.cha == 0 }">
							<tr>
								<td colspan="100" class="center">您无权查看</td>
							</tr>
						</c:if>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="100" class="center" >没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
 				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
 				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
		</form>
	</div>

	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/ace-elements.min.js"></script>
		<script src="js/ace.min.js"></script>
		
		<script type="text/javascript" src="js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
 		$(window.parent.hangge());
 		$(function() {
 			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
 			//日期框
			$('.date-picker').datepicker();
     	});
		//检索
		function search(){
			window.parent.jzts();
			$("#Form").submit();
		}
		
		//详情
		function detail(Id){
			 window.parent.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="订单详情";
			 diag.URL = '<%=basePath%>/order/goDetail.do?order_id='+Id;
			 diag.Width = 600;
			 diag.Height = 500;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 window.parent.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//改变状态
		function changeStatus(order_id,order_status,content,delivery_type){
			bootbox.confirm("确定要"+content+"吗?", function(result) {
				if(result) {
					var url = "<%=basePath%>/order/changeStatus.do?order_id="+order_id+"&order_status="+order_status+"&delivery_type="+delivery_type;
					$.get(url,function(data){
						if(data=="success"){
							nextPage(${page.currentPage});
						}
					});
				}
			});
		}
		

		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>/order/excel.do?order_status=1';
		}
		 
		</script>
		<script type="text/javascript" src="js/jquery.cookie.js"></script>
 	</body>
</html>


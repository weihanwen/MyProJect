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
		<li class="active">分类管理</li>
	</ul><!--.breadcrumb-->
	
	<div id="nav-search">
	</div><!--#nav-search-->
	
	</div><!--#breadcrumbs-->

<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="category/list.do" method="post" name="Form" id="Form">
			<!-- 检索  -->
 			<table id="table_report" class="table table-striped table-bordered table-hover">
 				<thead>
					<tr>
						<th>序号</th>
						<th>分类图片</th>
						<th>分类名称</th>
						<!-- <th>调整位置</th>-->
 						<th class="center">操作</th> 
					</tr>
				</thead>
 				<tbody>
 				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td>${var.title}</td>
								<td><img src="${var.image_url}" width="30px"></td>
 								<%-- <td category_id="${var.category_id}">
									<a style="display: block; float: left; margin-left: 3%;cursor: pointer; " title="上移" onclick="move(this,1)"> <img src="images/up.png" style="width: 18px;"></a>
									<a style=" display: block; float: left; margin-left: 3%;cursor: pointer; " title="下移" onclick="move(this,-1)"> <img src="images/down.png" style="width: 18px;"></a>
								</td>--%>
								<td style="width: 30px;" class="center">
										 <div class='hidden-phone visible-desktop btn-group'>
	 										<c:if test="${QX.edit != 1 && QX.del != 1 }">
											<span class="label label-large label-grey arrowed-in-right arrowed-in">无权限</span>
											</c:if>
											<c:if test="${QX.edit == 1 }">
												<a   title="编辑" onclick="edit('${var.category_id}');"  class="btn btn-mini btn-info"  >编辑</a>
											</c:if>
											<%-- <c:if test="${QX.del == 1 }">
												<a   title="删除" onclick="del('${var.category_id}');"  class="btn btn-mini btn-info"  >删除</a>
											</c:if> --%>
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
			
		<%-- <div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<c:if test="${QX.add == 1 }">
						<a class="btn btn-small btn-success" onclick="add();">新增类别</a>
					</c:if>
 				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div> --%>
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
		
		//检索
		function search(){
			window.parent.jzts();
			$("#Form").submit();
		}
		
		//新增
		function add(){
			 window.parent.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增类别";
			 diag.URL = '<%=basePath%>/category/goAdd.do';
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
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					var url = "<%=basePath%>/category/delete.do?category_id="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						if(data=="success"){
							nextPage(${page.currentPage});
						}
					});
				}
			});
		}
		
		//修改排序
		function move(obj,number){
			var category_id_one=$(obj).parent().attr("category_id");
			var category_id_two="";
 			if(number == "1"){
				if($(obj).parent().parent().prev() == null ){
					return;
				}
 				category_id_two=$(obj).parent().parent().prev().children("td").eq(2).attr("category_id");
			}else{
				if( $(obj).parent().parent().next() == null ){
					return;
				}
 				category_id_two=$(obj).parent().parent().next().children("td").eq(2).attr("category_id");
			}
			var url = "<%=basePath%>/category/updateSort.do?category_id_one="+category_id_one+"&category_id_two="+category_id_two;
			$.get(url,function(data){
				if(data=="success"){
					nextPage(${page.currentPage});
				}
			});
		}
		
		//修改
		function edit(Id){
			 window.parent.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑类别";
			 diag.URL = '<%=basePath%>/category/goEdit.do?category_id='+Id;
			 diag.Width = 600;
			 diag.Height = 500;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		</script>
		<script type="text/javascript" src="js/jquery.cookie.js"></script>
 	</body>
</html>


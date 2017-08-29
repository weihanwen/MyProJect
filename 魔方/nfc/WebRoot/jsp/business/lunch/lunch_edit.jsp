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
	<form action="lunch/edit.do" name="Form" id="Form" method="post">
		<input type="hidden" name="lunch_id" id="lunch_id" value="${pd.lunch_id}"/>
		<div id="zhongxin" style="width: 90%;margin: 5% auto;">
		<table>
			<tr>
				<td>商品分类 ：</td>
				<td>
					<select class="chzn-select" name="category_id" id="category_id">
	            		 <c:forEach items="${cateList}" var="var">
	            		 	<option value="${var.category_id }" ${var.category_id eq pd.category_id?'selected':''}>${var.title }</option>
	            		 </c:forEach>
 	            	</select>
				</td>
			</tr>
			<tr>
				<td>商品名称 ：</td>
				<td>
					<input  type="text" name="lunch_name" id="lunch_name" value="${pd.lunch_name}" maxlength="32" placeholder="这里输入便当名称" title="便当名称"  style="width:208px;"/>
				</td>
			</tr>
			<tr>
				<td>商品编号 ：</td>
				<td>
					<input  type="text" value="${pd.lunch_id}" disabled="disabled" style="width:208px;"/>
				</td>
			</tr>
 			 <tr>
				<td>是否上架 ：</td>
				<td>
					<input    type="radio" name="_shelves"  class="_shelves"  ${pd.is_shelves eq '1'?'checked':''} onclick="changeShelves('1')"/>是
					<input     type="radio" name="_shelves"  class="_shelves"  ${pd.is_shelves eq '1'?'':'checked'} onclick="changeShelves('99')"/>否
 					<input  type="hidden" name="is_shelves" id="is_shelves" value="${pd.is_shelves eq '1'?'1':'99'}" />
				</td>
			</tr>
			<tr>
				<td>价格 ：</td>
				<td>
					<input  class="chzn-input" value="${pd.show_sale_money}"  type="number"  id="show_sale_money" name="show_sale_money" class="show_sale_money"  /> 
   				</td>
			</tr>
			<tr>
				<td>优惠价格 ：</td>
				<td> 
				<input  class="chzn-input"  type="number" name="sale_money" id="sale_money" value="${pd.sale_money}" />
				</td>
			</tr>
			 <tr>
				<td>赠送积分 ：</td>
				<td>
					<input  type="number" name="send_integral" id="send_integral" value="${pd.send_integral}" maxlength="32" placeholder="这里输入赠送积分" title="赠送积分"  style="width:208px;"/>
				</td>
			</tr>  
 			 <tr>
				<td>主菜 ：</td>
				<td>
					<input  class="chzn-input" value="${pd.zhucai_text}"  type="text"  id="zhucai_text" name="zhucai_text" class="zhucai_text"  /> 
   				</td>
			</tr>
			<tr>
				<td>配菜 ：</td>
				<td>
					<input  class="chzn-input" value="${pd.peicai_text}"  type="text"  id="peicai_text" name="peicai_text" class="peicai_text"  /> 
   				</td>
			</tr>
			<tr>
				<td>食材 ：</td>
				<td>
					<input  class="chzn-input" value="${pd.shicai_text}"  type="text"  id="shicai_text" name="shicai_text" class="shicai_text"  /> 
   				</td>
			</tr>
			<tr>
				<td>绝不使用 ：</td>
				<td>
					<input  class="chzn-input" value="${pd.notuse_text}"  type="text"  id="notuse_text" name="notuse_text" class="notuse_text"  /> 
   				</td>
			</tr>
			<tr>
				<td>首页分类图 ：</td>
				<td>
					<span style="display:inline-block;border:1px solid #999;width: 350px;text-align: center;" onclick="upload('index_images')">
	 					<img  class="index_images" src="${empty pd.index_images ?'img/sjht_add.png':pd.index_images}"  style="width: 100%;">	
						<input type="hidden" name="index_images" id="index_images" value="${pd.index_images}" />
 					</span>
				</td>
  			</tr>
  			<tr>
				<td>产品详情图 ：</td>
				<td>
					<span style="display:inline-block;border:1px solid #999;width: 100px;height: 100px;text-align: center;" onclick="upload('dc_images')">
	 					<img  class="dc_images" src="${empty pd.dc_images ?'img/sjht_add.png':pd.dc_images}"  style="height: 100%;">	
						<input type="hidden" name="dc_images" id="dc_images"value="${pd.dc_images}"  />
 					</span>
				</td>
  			</tr>
  			<tr>
				<td>产品详情缩略图 ：</td>
				<td>
					<span style="display:inline-block;border:1px solid #999;width: 200px;text-align: center;" onclick="upload('dc_images_small')">
	 					<img  class="dc_images_small" src="${empty pd.dc_images_small ?'img/sjht_add.png':pd.dc_images_small}"  style="width: 100%;">	
						<input type="hidden" name="dc_images_small" id="dc_images_small" value="${pd.dc_images_small}"  />
 					</span>
				</td>
  			</tr>
  			<tr>
				<td>预定显示图 ：</td>
				<td>
					<span style="display:inline-block;border:1px solid #999;width: 200px;text-align: center;" onclick="upload('yd_images')">
	 					<img  class="yd_images" src="${empty pd.yd_images ?'img/sjht_add.png':pd.yd_images}"  style="width: 100%;">	
						<input type="hidden" name="yd_images" id="yd_images" value="${pd.yd_images}"  />
 					</span>
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
	  <form action="" method="post" name="imageForm" id="imageForm"  enctype="multipart/form-data"> 
         	<input type="file" style="display:none;"    name="upImage" class="upImage" onchange="fileType(this)"/>
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
		
		 

		//改变是否上架
		function changeShelves(value){
			$("#is_shelves").val(value);
		}
		
		
		
		var classId="";//class的唯一标示
	 	//上传按钮点击
		function upload(value){
			classId=value;
			$(".upImage").click();
		}

	   //上传图片
		function fileType(obj){
	  	    var d=/\.[^\.]+$/.exec(obj.value); 
	  		if(!validaImage(d)){
				alert("请上传照片gif,png,jpg,jpeg格式");
			}else{
				var url="<%=basePath%>imgChange/editLunch.do";
  				$("#imageForm").ajaxSubmit({  
				  	url :url ,
			        type: "POST",//提交类型  
			      	dataType:"json",
			      	cache:false,
			      	ifModified :true,
			   		success:function(data){
			   			 var url=data.data;
	 				     $("#"+classId).val("");
	    				 $("."+classId).attr("src","");
	 				     $("#"+classId).val(url);
	    				 $("."+classId).attr("src",url+"?timestamp=" + new Date().getTime() );
						 classId="";   
 	 				}
				}); 
	  		}
	 	}	 

		//判断图片是否符合格式
		function validaImage(filename){
			if('.gif.png.jpg.jpeg'.indexOf(filename)<0&&'.GIF.PNG.JPG.JPEG'.indexOf(filename)<0){
				return false;
			}
	 		return true;
		}
		

		
		//保存
		function save(){
 			if($("#category_id").val()==""){
				$("#category_id").tips({
					side:3,
		            msg:'请选择类别',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#category_id").focus();
				return false;
			}
			if($("#lunch_name").val()==""){
				$("#lunch_name").tips({
					side:3,
		            msg:'请输入便当名称',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#lunch_name").focus();
				return false;
			}
			if($("#show_sale_money").val()==""){
				$("#show_sale_money").tips({
					side:3,
		            msg:'请输入价格',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#show_sale_money").focus();
				return false;
			}
			if($("#sale_money").val()==""){
				$("#sale_money").tips({
					side:3,
		            msg:'请输入实际价格',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#sale_money").focus();
				return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
 		}
		
		 
		
</script>
</body>
</html>
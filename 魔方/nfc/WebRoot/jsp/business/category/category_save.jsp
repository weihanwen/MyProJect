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
		<title>类别-操作</title>
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
 		<link rel="stylesheet" href="css/ace.min.css" />
		<link rel="stylesheet" href="css/ace-responsive.min.css" />
		<link rel="stylesheet" href="css/ace-skins.min.css" />
 		<link rel="stylesheet" href="css/datepicker.css" /><!-- 日期框 -->
 		<style type="text/css">
 		.link_content{
 			display:none;
 		}
 		</style>
 		<!-- 引入 -->
		<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
	</head>
<body>
	<form action="category/save.do" name="Form" id="Form" method="post">
		<div id="zhongxin" style="width: 60%;margin: 5% auto;">
		<table>
			<tr>
				<td>类别标题 ：</td>
				<td><input  type="text" name="title" id="title" value="" maxlength="32" placeholder="这里输入标题" title="标题"  style="width:208px;"/></td>
			</tr>
			<tr>
				<td>icon ：</td>
				<td>
					<span style="display:inline-block;border:1px solid #999;max-width: 300px;height: 100px;text-align: center;" onclick="upload('image_url')">
	 					<img  class="image_url" src="img/sjht_add.png"  style="height: 100%;">	
						<input type="text" name="image_url" id="image_url" value="" style="display:none;width:1px;height:1px;"/>
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
			//
			changeLinkType("${pd.link_type}");
			
    	});
		
		
		
		
		
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
				var url="<%=basePath%>imgChange/editCate.do";
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
			if($("#title").val()==""){
				$("#title").tips({
					side:3,
		            msg:'请输入标题',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#title").focus();
				return false;
			}
			if($("#image_url").val()==""){
				$(".image_url").tips({
					side:3,
		            msg:'请上传照片',
		            bg:'#AE81FF',
		            time:1
		        });
				$("#image_url").focus();
				return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			
		}
		
		 
		
</script>
</body>
</html>
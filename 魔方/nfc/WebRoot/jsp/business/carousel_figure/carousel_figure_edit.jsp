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
		<title>轮播图-操作</title>
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
	<form action="carousel_figure/edit.do" name="Form" id="Form" method="post">
		<input type="hidden" name="carousel_figure_id" id="carousel_figure_id" value="${pd.carousel_figure_id}"/>
		<div id="zhongxin" style="width: 60%;margin: 5% auto;">
		<table>
			<tr>
				<td>轮播图标题 ：</td>
				<td><input  type="text" name="title" id="title" value="${pd.title}" maxlength="32" placeholder="这里输入轮播图标题" title="轮播图标题"  style="width:208px;"/></td>
			</tr>
			<tr>
				<td>轮播图 ：</td>
				<td>
					<span style="display:inline-block;border:1px solid #999;max-width: 300px;height: 100px;text-align: center;" onclick="upload('image_url')">
	 					<img  class="image_url" src="${empty pd.image_url ?'img/sjht_add.png':pd.image_url}"  style="height: 100%;">	
						<input type="text" name="image_url" id="image_url" value="${pd.image_url}" style="display:none;width:1px;height:1px;"/>
 					</span>
				</td>
  			</tr>
  			<tr>
				<td>开始时间 ：</td>
				<td>
					<input class="span10 date-picker" name="startdate"  id="startdate" value="${pd.startdate}" type="text" data-date-format="yyyy-mm-dd"   placeholder="开始日期" style="width:208px;" >
				</td>
			</tr>
			<tr>
				<td>结束时间 ：</td>
				<td>
					<input class="span10 date-picker" name="enddate"  id="enddate" value="${pd.enddate}" type="text" data-date-format="yyyy-mm-dd"   placeholder="开结束日期"  style="width:208px;"  >
				</td>
 			</tr>
			<tr>
				<td>轮播图链接 ：</td>
				<td>
					<select name="link_type" class="chzn-select" id="link_type"  onchange="changeLinkType(this.value)"  >
	            		<option value="1" ${pd.link_type eq '1'?'selected':''}>无连接</option>
	            		<option value="2" ${pd.link_type eq '2'?'selected':''}>外部的H5链接</option>
	            		<option value="3" ${pd.link_type eq '3'?'selected':''}>菜品</option>
	            	</select>
	            	<input type="hidden"  value="${pd.link_content}"  id="link_content" name="link_content" >
				</td>
			</tr>
			<tr class="h5_url link_content">
				<td>外部H5链接 :</td><td><input type="text"  value="${pd.link_content}" placeholder="请输入外部H5链接"  id="h5_url" ></td>
			</tr>
			<tr class="goods_id link_content">
				<td>菜品链接 ：</td>
				<td>
					<select class="chzn-select" id="goods_id"   >
	            		<option value="" >请选择菜品</option>
 	            	</select>
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
				var url="<%=basePath%>imgChange/editLb.do";
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
		
		//选择链接类型
		function changeLinkType(value){
			if(value=="1"){
				$(".h5_url").hide();
				$(".goods_id").hide();
			}else if(value=="2"){
				$(".h5_url").show();
				$(".goods_id").hide();
			}else{
				$(".h5_url").hide();
				$(".goods_id").show();
			}
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
			if(isokTime()){
				 var link_type=$("#link_type").val();
 				 if(link_type == "2"){
 					 if($("#h5_url").val()== ""){
							$("#h5_url").tips({
								side:3,
					            msg:'请填写H5的链接地址',
					            bg:'#AE81FF',
					            time:1
					        });
							$("#h5_url").focus();
							return false;
						}
					 
					 $("#link_content").val($("#h5_url").val())
				 }else if(link_type == "3"){
					 if($("#goods_id").val() == ""){
							$("#goods_id").tips({
								side:3,
					            msg:'请选择菜品',
					            bg:'#AE81FF',
					            time:1
					        });
							$("#goods_id").focus();
							return false;
						}
					 
					 $("#link_content").val($("#goods_id").val())
				 }
 				 $("#Form").submit();
				 $("#zhongxin").hide();
				 $("#zhongxin2").show();
			}
			
		}
		
		function isokTime(){
			  var start=$("#startdate").val();                                              //用jQuery从页面去的时间值
			  var end=$("#enddate").val();
 			  if(start!=''&&  end!=''){
			    start=start.split('-');                                                     //用的是时间控件格式是yyyy-MM-dd
			    end=end.split('-');
			    var start1=new Date(start[0],start[1]-1,start[2]);    //因为当前时间的月份需要+1，故在此-1，不然和当前时间做比较会判断错误
			    var end1=new Date(end[0],end[1]-1,end[2]);
			    var day=new Date();
			    if(day > start1){ 
			    	$("#startdate").tips({
						side:3,
			            msg:'开始排期要从明天开始',
			            bg:'#AE81FF',
			            time:1
			        });
					$("#startdate").focus();
 			     return false;
			    }
			    if(start1 > end1){
			    	$("#enddate").tips({
						side:3,
			            msg:'结束日期不能在开始日期之后！',
			            bg:'#AE81FF',
			            time:1
			        });
					$("#enddate").focus();
 			     	return false;
			    }
			}else{
				if(start == ""){
			    	$("#startdate").tips({
						side:3,
			            msg:'开始时间不能为空',
			            bg:'#AE81FF',
			            time:1
			        });
					$("#startdate").focus();
 			     	return false;
			    }else{
			    	$("#enddate").tips({
						side:3,
			            msg:'结束时间不能为空',
			            bg:'#AE81FF',
			            time:1
			        });
					$("#enddate").focus();
 			     	return false;
			    }
			}
 			return true;
		}
		
</script>
</body>
</html>
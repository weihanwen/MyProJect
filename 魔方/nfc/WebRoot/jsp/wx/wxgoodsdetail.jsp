<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>商品详情</title>
	<base href="<%=basePath%>">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0">
	<link rel="stylesheet" href="css/fonts/iconfont.css">
	<link rel="stylesheet" href="css/wx/style.css">
	<link rel="stylesheet" type="text/css" href="css/lunbo/slider.css" />
	<style type="text/css">
	.addshopcar {
	        display: block;
		    width: 3.4rem;
		    height: 3.4rem;
		    position: absolute;
		    right: 1rem;
		    bottom: 10.4rem;
		    /* padding: 0.7rem; */
		    border-radius: 50%;
		    color: #fff;
		    opacity: 0.6;
		    z-index: 1;
	}
	.shopcarnumber {
	        display: block;
		    width: 1.4rem;
		    height: 1.4rem;
		    position: absolute;
		    left: 5rem;
		    bottom: 2.4rem;
		     border-radius: 50%;
		    background-color: red;
		    color: #fff;
		    opacity: 0.6;
		    z-index: 1;
	}
	</style>
</head>
<body>
<!-- 顶部条 -->
<header>
		<div class="goback" onclick="backreturn()"><img src="img/fanhui.png" alt=""></div>
		<div class="title">
			 <div class="djs">
		       	 商品详情
	    	</div>
		</div>
</header>
<span class="addshopcar" onclick="clikthisLunch()"  ><img src="images/addshop.png" alt="" style="width: 100%;"></span>
<div class="center">

	<div id="jssor_1" style="position: relative; margin: 0 auto; top: 0px; left: 0px; width: 800px; height: 1135px; overflow: hidden; visibility: hidden; background-color: #fff;">
        <div data-u="slides" style="cursor: default; position: relative; top: 0px; left: 0px; width: 800px; height:1135px; overflow: hidden;">
            <c:forEach items="${varList}" var="var">
            	<div data-p="144.50" style="display: none;">
	                <img data-u="image" src="${var.dc_images}" />
	                <img data-u="thumb" src="${var.dc_images_small}" lunch_id="${var.lunch_id}"/>
	            </div>
            </c:forEach>
                 
           <!--<div data-p="144.50" style="display: none;">
                <img data-u="image" src="images/lunbo/02.jpg" />
                <img data-u="thumb" src="images/lunbo/thumb-02.jpg" />
            </div>
            <div data-p="144.50" style="display: none;">
                <img data-u="image" src="images/lunbo/03.jpg" />
                <img data-u="thumb" src="images/lunbo/thumb-03.jpg" />
            </div>
            <div data-p="144.50" style="display: none;">
                <img data-u="image" src="images/lunbo/04.jpg" />
                <img data-u="thumb" src="images/lunbo/thumb-04.jpg" />
            </div>
             <div data-p="144.50" style="display: none;">
                <img data-u="image" src="images/lunbo/05.jpg" />
                <img data-u="thumb" src="images/lunbo/thumb-05.jpg" />
            </div>
             <div data-p="144.50" style="display: none;">
                <img data-u="image" src="images/lunbo/06.jpg" />
                <img data-u="thumb" src="images/lunbo/thumb-06.jpg" />
            </div>
            <div data-p="144.50" style="display: none;">
                <img data-u="image" src="images/lunbo/07.jpg" />
                <img data-u="thumb" src="images/lunbo/thumb-07.jpg" />
            </div>
            <div data-p="144.50" style="display: none;">
                <img data-u="image" src="images/lunbo/08.jpg" />
                <img data-u="thumb" src="images/lunbo/thumb-08.jpg" />
            </div>
            <div data-p="144.50" style="display: none;">
                <img data-u="image" src="images/lunbo/09.jpg" />
                <img data-u="thumb" src="images/lunbo/thumb-09.jpg" />
            </div>
            <div data-p="144.50" style="display: none;">
                <img data-u="image" src="images/lunbo/10.jpg" />
                <img data-u="thumb" src="images/lunbo/thumb-10.jpg" />
            </div>
            <div data-p="144.50" style="display: none;">
                <img data-u="image" src="images/lunbo/11.jpg" />
                <img data-u="thumb" src="images/lunbo/thumb-11.jpg" />
            </div>
            <div data-p="144.50" style="display: none;">
                <img data-u="image" src="images/lunbo/12.jpg" />
                <img data-u="thumb" src="images/lunbo/thumb-12.jpg" />
            </div> -->
        </div>
        <!-- Thumbnail Navigator -->
        <div data-u="thumbnavigator" class="jssort01" style="position:absolute;left:0px;bottom:0px;width:1600px;height:172px;"  data-autocenter="1">
            <!-- Thumbnail Item Skin Begin -->
            <div data-u="slides" style="cursor: default;">
                <div data-u="prototype" class="p">
                    <div class="w">
                        <div data-u="thumbnailtemplate" class="t"></div>
                    </div>
                    <div class="c"></div>
                </div>
            </div>
            <!-- Thumbnail Item Skin End -->
        </div>
       
    </div>
    <!-- #endregion Jssor Slider End -->
</div>

<footer class="twofooter">
 	<a href="wxmember/goshopcatList.do"  class="foot_guc">
 	 	<span class="iconfont icon-gouwuche1 icon2"></span><span class="shopcarnumber"  >${pd.shopnumber}</span>
 	</a>
	<a class="foot_gm"><span>去结算</span></a>
</footer>
	<a  href="javascript:void(0)" onclick="location.reload()" class="reflash"></a>	 <!-- 刷新链接 -->
</body>
<script src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/lunbo/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/lunbo/jssor.slider.mini.js"></script>
<script type="text/javascript" src="js/lunbo/slider.js"></script>
<script type="text/javascript">
$(function(){
 	$(".p").each(function(n,obj){
		var lunch=$(this).find("img").attr("lunch_id");
		if(lunch == "${pd.lunch_id}"){
 			$(this).click();
		}
	});
	
});

//返回
function backreturn(){
	window.location.href="wxmember/wxindex.do";
}

//添加进购物车   onclick="clikthisLunch()"
function clikthisLunch(){
	var lunch_id=$(".pav").find("img").attr("lunch_id");
	//alert(lunch_id);
	$.ajax({
		type:"post",
			url:"wxmember/addshopcart.do",
			data:{ "lunch_id":lunch_id ,"number":"1" },
			dataType:"json",
			success:function(data){
				  if(data.result == "1"){
					  $(".shopcarnumber").html(data.data);
				  }else{
					  alert(data.message);
				  }
			}
	});  
}


</script>
</html>
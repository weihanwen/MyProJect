//获取商品
function changeShoyLb(category_id,obj){
  	$.ajax({
		type:"post",
			url:base_inf.base_herf+"wxmember/getLunchList.do",
			data:{ "category_id":category_id  },
			dataType:"json",
			success:function(data){
				 var lunchList=data.data;
				 $(".allgoods").empty();
				 for (var i = 0; i < lunchList.length; i++) {
					 var s="<div class='goodsshow' onclick='goDetail(this,1)' lunch_id='"+lunchList[i].lunch_id+"' category_id='"+lunchList[i].category_id+"' >"+
								"<div class='one'>"+
									"<img src='"+lunchList[i].product_cover+"' />"+
									"<div class='two'>"+
										""+lunchList[i].sale_money++"元/份 赠送积分"+lunchList[i].send_integral+"分 <span>到手价："+lunchList[i].daoshoumoney+"元</span> 仅剩"+lunchList[i].inventory_number+"份"+
									"</div>"+
								"</div>"+
							"</div>";
					$(".allgoods").append(s);
				 }
			}
	});  
}
//前往详情
function goDetail(obj,order_type){
	window.location.href=base_inf.base_herf+"wxmember/godetailBygoods.do?lunch_id="+$(obj).attr("lunch_id")+"&order_type="+order_type+"&category_id="+$(obj).attr("category_id");
}

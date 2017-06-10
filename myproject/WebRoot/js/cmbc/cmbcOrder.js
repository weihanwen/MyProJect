// 1. 获取JSON字符串
function showOrderJson() {
	var url = "CmbcPayServlet?name=getOrderJson";
	var data = {
		"platformId" : $("#platformId").val(),
		"merchantNo" : $("#merchantNo").val(),
		"merchantName" : $("#merchantName").val(),
		"merchantSeq" : $("#merchantSeq").val(),
		"defaultTradeType" : $("#defaultTradeType").val(),
		"selectTradeType" : $("#selectTradeType").val(),
		"orderInfo" : $("#orderInfoVal").val(),
		"amount" : $("#amount").val(),
		"transTime" : $("#transTime").val(),
		"transDate" : $("#transDate").val(),
		"notifyUrl" : $("#notifyUrl").val(),
		"redirectUrl" : $("#redirectUrl").val(),
		"remark" : $("#remark").val()
	};
	getOrderJson(url, data);
}

//2. 获取订单签名
function getThisSign() {
	var platformId = $("#platformId").val();
	var contextJson = $("#contextJsonText").val().trim();
	var url = "CmbcPayServlet?name=getSign";
	var data = {
		"platformId" : platformId,
		"contextJson" : contextJson
	};
	getSign(url, data);
}

// 3. 为Json串拼接签名
function signThis() {
	var url = "CmbcPayServlet?name=sign";
	sign(url);
}

// 4. 加密
function encryptThis() {
	var url = "CmbcPayServlet?name=encrypt";
	encrypt(url);
}

function topay() {
	var url = "CmbcPayServlet?name=getPayUrl";
	$.ajax({
		url : url,
		type : "POST",
		success : function(data) {
			var cipher = $("#encryptSignContextText").val().trim();
			var toUrl = data + cipher;
			window.location.href = toUrl;
		},
		error : function() {
			alert("没有得到支付地址，请检查配置文件关于公众号跳转支付的配置信息");
		}
	});
}
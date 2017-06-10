// 1. 获取订单JSON字符串
function getOrderJson(url, data) {
	var isContinue = false;
	$.ajax({
		url : url + "&begin=true",
		type : "POST",
		async : false,
		data : data,
		dataType : "text",
		success : function(data) {
			$("#contextJsonText").val(data);
			isContinue = true;
			scrollToHere($("#orderInfo"));
		},
		error : function() {
			alert("获取JSON失败");
		}
	});
	return isContinue;
}

// 2. 根据订单信息产生签名
function getSign(url, data) {
	var isContinue = false;
	$.ajax({
		url : url,
		type : "POST",
		async : false,
		data : data,
		success : function(data) {
			$("#signText").val(data);
			isContinue = true;
			scrollToHere($("#orderDiv"));
		},
		error : function(data) {
			alert("获取签名失败:" + data);
		}
	});
	return isContinue;
}

// 3. 拼接签名到订单信息当中
function sign(url) {
	var isContinue = false;
	var sign = $("#signText").val();
	if (sign == "签名私钥未找到，请检查平台号或配置文件") {
		alert(sign);
		return isContinue;
	}
	$.ajax({
		url : url,
		type : "POST",
		async : false,
		data : {
			"sign" : sign,
			"contextJson" : $("#contextJsonText").val().trim()
		},
		success : function(data) {
			$("#signContextText").val(data);
			isContinue = true;
			scrollToHere($("#signDiv"));
		},
		error : function() {
			alert("加签失败");
		}
	});
	return isContinue;
}

// 4. 加密
function encrypt(url) {
	encrypt2(url, $("#platformId").val());
}

function encrypt2(url, platformId) {
	var isContinue = false;
	$.ajax({
		url : url,
		type : "POST",
		async : false,
		data : {
			"platformId" : platformId,
			"signContext" : $("#signContextText").val().trim()
		},
		success : function(data) {
			$("#encryptSignContextText").val(data);
			isContinue = true;
			scrollToHere($("#signOrderDiv"));
		},
		error : function() {
			alert("加密失败");
		}
	});
	return isContinue;
}

//5. 获取报文
function createBusinessContext(url, data) {
	var isContinue = false;
	$.ajax({
		url : url,
		async : false,
		type : "POST",
		data : data,
		success : function(data) {
			$("#businessContextText").val(data);
			isContinue = true;
			scrollToHere($("#encryptSignContext"));
		},
		error : function() {
			alert("获取businessContext失败");
		}
	});
	return isContinue;
}

// 6. POST请求返回密文
function getPostBackContext(url, data) {
	$("#backContextText").text(" ");
	var isContinue = false;
	$.ajax({
		url : url,
		async : false,
		type : "POST",
		data : data,
		success : function(data) {
			$("#backContextText").val(" ");
			$("#backContextText").val(data);
			isContinue = true;
			scrollToHere($("#businessContext"));
		},
		error : function() {
			alert("调用失败");
		}
	});
	return isContinue;
}

// 7. 获取返回报文密文
function getBackContextCipher(url, data) {
	var isContinue = false;
	$.ajax({
		url : url,
		async : false,
		data : data,
		type : "POST",
		success : function(data) {
			$("#backCipherText").val(data);
			isContinue = true;
			scrollToHere($("#backContextDiv"));
		},
		error : function() {
			alert("获取返回密文失败");
		}
	});
	return isContinue;
}

// 8. 返回报文密文解密
function doBackCipherDecrypt(url, data) {
	var isContinue = false;
	$.ajax({
		url : url,
		type : "POST",
		async : false,
		data : data,
		success : function(data) {
			$("#backPlainText").val(data);
			isContinue = true;
			scrollToHere($("#backCipherTextDiv"));
		},
		error : function() {
			alert("支付返回密文解密失败");
		}
	});
	return isContinue;
}
// 9. 验证签名
function doSignCheck(url, data) {
	var isContinue = false;
	$.ajax({
		url : url,
		type : "POST",
		async: false,
		data : data,
		success : function(result) {
			$("#signCheckResultText").val(result);
			isContinue = true;
			scrollToHere($("#backPlainTextDiv"));
		},
		error : function () {
			alert("验证签名异常");
		}
	});
	return isContinue;
}

// 转到异步通知
function toNoticePage() {
	var merchantSeq = $("#merchantSeq").val();
	var platformId = $("#platformId").val();
	var url = "NoticeServlet?name=getNoticeContext";
	$.ajax({
		url : url,
		type : "POST",
		data : {
			"merchantSeq" : merchantSeq,
			"platformId" : platformId
		},
		success : function(data) {
			$("#contextJsonText").val(data);
			scrollToHere($("#fieldDiv"));
		},
		error : function() {
			alert("转到异步通知异常");
		}
	});
}

// 0. 滚动条滚动事件
function scrollToHere(thisObj) {
	var height = thisObj.height();
	var y = thisObj.position().top;
	scroll(0, y + height);
}

$(function() {
	var titles = $(".title");
	var maxLen = 0;
	for (var i = 0; i < titles.length; i++) {
		var text = $($(".title")[i]).text();
		var textArr = text.split("|");
		if (textArr[0].length <= 6 && textArr[0].length > maxLen) {
			maxLen = textArr[0].length;
		}
	}
	maxLen = maxLen - 1;
	for (var i = 0; i < titles.length; i++) {
		var text = $($(".title")[i]).text();
		var textArr = text.split("|");
		if (textArr[0].length <= 6) {
			var text1 = textArr[0].substring(0, textArr[0].length - 1);
			var sty = "width: " + maxLen + "em;"
				+ "letter-spacing: " + (maxLen - text1.length) / text1.length + "em;";
			var html = "<span class='tl' style='" 
				+ sty + "'>" 
				+ text1 + "</span>" + textArr[0].substring(textArr[0].length - 1)
				+ " | " + textArr[1];
			$($(".title")[i]).html(html);
		}
		
	}
//	var tls = $(".tl");
//	var standLen = maxLen * $("body").css("font-size").substring(0,2) + 10;
//	console.log(standLen);
//	for (var i = 0; i < tls.length; i++) {
////		var len = $($(".tl")[i]).css("width").substring(0,2) / $("body").css("font-size").substring(0,2);
//		var len = $($(".tl")[i]).css("width").substring(0,2);
//		
//		if (len < standLen) {
//			$($(".tl")[i]).css("width", standLen + "px");
//		}
//		
//	}
});
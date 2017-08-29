<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	//out.print(path);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>九鱼便当</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body style="margin:0;">
  	<div style="width:100%;height:100%;margin:0;padding:0;" >
  		<img alt="" src="images/sy.jpg" style="width:100%;">
  	</div>
    <script type="text/javascript">
    	document.location = "<%=path%>/login_toLogin.do";
    </script>  
  </body>
</html>

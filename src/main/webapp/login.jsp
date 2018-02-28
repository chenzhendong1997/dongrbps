<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link  rel="stylesheet" type="text/css"  href="${path }/style/alogin.css" />
<title>用户登录</title>
<script type="text/javascript">
	function loadimage(){
		document.getElementById("randImage").src="${path }/images/image.jsp?"+Math.random();
	}
</script>
</head>
<body>
<h1 style="text-align: center;margin-top: 120px;font-size: 50px;color: white;">信 息 管 理 系 统</h1>
<form id="form1" name="form1" action="${path }/login.htm" method="post">
	<div class="MAIN">
		<ul>
			<li class="topD">
			<ul class="login">
				<li><span class="left">用户名：</span> <span style=""> <input id="userName" name="userName" type="text" class="txt" value="${userName }" /> </span></li>
				<li><span class="left">密&nbsp;&nbsp;&nbsp;码：</span> <span style=""> <input id="password" name="password" type="password" class="txt" value="${password }" onkeydown= "if(event.keyCode==13)form1.submit()"/> </span></li>
				<li><span class="left">验证码：</span> <span style=""> <input type="text" value="${imageCode }" name="imageCode"  class="txtCode" id="imageCode" size="10" onkeydown= "if(event.keyCode==13)form1.submit()"/>&nbsp;<img onclick="javascript:loadimage();"  title="换一张试试" name="randImage" id="randImage" src="${path }/images/image.jsp" width="60" height="20" border="1" align="absmiddle"> </span></li>
			</ul>
			</li>
			<li class="middle_C"><span class="btn"> <img alt="" src="${path }/images/login/btnlogin.gif" onclick="javascript:document.getElementById('form1').submit()"/> </span>&nbsp;&nbsp;<span ><font color="red">${error }</font></span></li>
			</ul>
	</div>
</form>
</body>
</html>
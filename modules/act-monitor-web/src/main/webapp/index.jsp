<%@ page language="java"  pageEncoding="utf-8"%>
<%@page import="com.act.web.constant.CommonContant"%>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/common/base64.js"></script>
<link href="<%=request.getContextPath()%>/css/common/login.css" rel="stylesheet" type="text/css" />	
<!--原版登入界面  -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script language="JavaScript">
/* 解决iframe内部跳转的问题 */
if (window != top){
	top.location.href = "<%=request.getContextPath()%>"; 
}

</script>
</head>


<body>

<!-- 浏览器 提示信息 -->
<div id="pd" style='display:none;position:absolute;top:0px;left:0px;background:#ffffff;width:100%;height:20px;border-bottom:solid #ffffff 1px;font-size:12px;color:red;' >
	<marquee scrollAmount=5 onmouseover="this.stop()" onmouseout="this.start()">请使用IE8或更高版本的浏览器，分辨率大于等于1024 × 768 </marquee>
</div>

<form action="<%=request.getContextPath()%>/common/login/login.do" name="login" id="login">
	<!-- 验证码 原系统 glob 中定义 -->
	<input type="hidden" id="loginIsCode" value = "Y">
	<!-- 忘记密码  原系统 glob 中定义-->
	<input type="hidden" id="xssTip" value = "Y">
		
	<!-- 最终登入提交的数据 -->	
	<input type="hidden" name="userId" id="username">
	<input type="hidden" name="password" id="passwd">	
		
	<div style='height:100%;widht:100%;'>
	<!-- 页面顶部 系统标题在此DIV里面 -->
	  <div style='width:100%;height:202px;background:#004199 url(<%=request.getContextPath()%>/images/login/loginb1.gif) repeat-x bottom;'>
	  <!-- 原系统通过CleanupListener 来初始化 标题 现未做处理  -->	
	  <div class='sys_title'><%=CommonContant.SYSTEMNAME %></div>
	  </div> 
	  
	  <!-- 登入内容 -->
	 <div style='height:249px;background:url(<%=request.getContextPath()%>/images/login/loginb2.gif);'>
	 		<table cellpadding=2 cellspacing=0 align=center>
	 		 <tr><td>&nbsp;<td class="me" id="me"><span id="errorMessage"></span></td></tr>
	 		 <tr>
	 		 <td class=text1>用户名: </td>
          	 <td><input type="text" name="username2" id="username2" class="tbox" disabled/>
          	  <span id="userMessage"></span>
          	  </td>
         	 </tr>
         	 
         	  <tr>
         	  <td class=text1>密<span style='visibility:hidden'>户</span>码:  </td>
          	  <td><input type="password" name="passwd2" id="passwd2" class="tbox" disabled/>
          	   <span id="pwdMessage"></span></td>
          	  </tr>
          	  
          	  <tr id="validateDiv" style="display: none">
				<td class='text1'> 验证码: </td>
				<td style='position:relative;text-align:left;'>
				<input type="text" name="verifyId" id="verifyId" class=tbox style='float:left;width:80px;ime-mode:disabled;' maxlength="4" size="5" disabled />
				<img align="middle" id="randImg" onclick="changeValidateCode()" title="看不清？点击更换" style="vertical-align: middle; cursor: hand;">
				<span id="randMessage"></span>
				</td>
	         </tr>
	         
	         <tr>
	         	<td>&nbsp;</td>
	        	 <td height=70 align=left>
	        	  <input type=button value='登录' class='but' name="imageField" id="imageField" onclick='verifyInput()'> 
	        	  <input type=button value='重置' class='but' onClick="document.login.reset()">
	        	 </td>
	        	 <!-- 忘记密码提示 -->
	        	 <td id="xssTipTd" style="display: none;"></td>
	        </tr>
	 		</table>
	 </div>	

	<!-- 页面底部 -->
	  <div style='text-align:center;background:#0058a2 url(<%=request.getContextPath()%>/images/login/loginb3.gif) repeat-x  top center;'>
	  <img src='<%=request.getContextPath()%>/images/login/loginb4.gif'>
	  </div>	
	</div>
</form>

<!-- 忘记密码显示区域 -->
<div id="frameClass"></div>
	<div id="frameDiv">
		<iframe src="" scrolling="no" id="iframe">
		</iframe>
	</div>
</body>

</html>


<script src='<%=request.getContextPath()%>/assets/plugins/jquery-1.11.1/jquery.min.js'></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/index/index.js"></script>
<script type="text/javascript">
$(function(){
	release_item();
	loginLoad();
	//Enter 触发登入按钮
	$("#login").keydown(function(e){ 
		var curKey = e.which; 
		if(curKey == 13){ 
			verifyInput(); 
		} 
	});
	
});
</script>
















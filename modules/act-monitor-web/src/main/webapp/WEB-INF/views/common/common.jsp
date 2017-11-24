<!--子系统列表页面 -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<link href="<%=request.getContextPath()%>/css/common/SubSystem.css" rel="stylesheet" type="text/css" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
<head>
<title>${requestScope.title}</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />


</head>
<body>
	
<!-- 显示系统标题 -->	
<div class=sys_title>${requestScope.title}</div>

<!-- 子系统菜单 -->

<table align=center cellpadding=0 cellspacing=10 border=0 class="mt">
<tr>
<c:forEach items="${list}" var="info">
	<td valign=top width=100 style="text-align: center;">		
		<c:choose>
			<c:when test="${info.opentype=='self'}">
	    		<a href='#' onclick='toSubSystem(${info.syamanageId},"${info.sysmanageName}")'>${info.sysmanageName}</a>
			</c:when>
			<c:when test="${info.opentype=='blank'}">
				<a href='${info.pathurl}' target="_blank">${info.sysmanageName}</a>
			</c:when>
		</c:choose>
	</td>
</c:forEach>
</tr>


<tr>
<c:forEach items="${list}" var="info">
	<td valign="bottom" width=100 style="text-align: center;">
		<c:choose>
			<c:when test="${info.opentype=='self'}">
	    		<a href='#' onclick='toSubSystem(${info.syamanageId},"${info.sysmanageName}")'><img src='<%=request.getContextPath()%>${info.picname}' border=0 align="center"></a>
			</c:when>
			<c:when test="${info.opentype=='blank'}">
				<a href='${info.pathurl}' target="_blank"><img src='<%=request.getContextPath()%>${info.picname}' border=0 align="center"></a>
			</c:when>
		</c:choose>
	</td>
</c:forEach>

</tr>
</table>

<!-- 关于 退出 -->	

<table style='margin-top:180px;width:600px;border-top:solid #0a457e 1px;' align=center cellpadding=0 cellspacing=0 > 
<tr><td style='height:30px;width:100%;border-top:solid #3c7bb6 1px;color:#ffffff;'>	
	
	<!-- 默认不显示关于 -->
	<a style='color:#ffffff;font-size:12px;text-decoration:none; display: none;'  href=# onclick='showAbout()'>关于</a>
	<a style='color:#ffffff;;font-size:12px;text-decoration:none;' href='<%=request.getContextPath()%>/common/login/userLogout.do' onClick="return confirm('确认退出系统吗?')">退出</a>  
	
</td></tr>
</table>


<!-- 用POST方式发送子系统ID -->
 <form method="post" action="<%=request.getContextPath()%>/common/login/toChildSystem.do" name="subForm"> 
 	<input id = "sys_id" type="hidden" name="sys_id" ><!-- 子系统编号 -->
	<input id = "sys_name" type="hidden" name="sys_name"><!-- 子系统名称 -->
 </form>

</body>
</html>



<!-- 导入公用jsp -->
<script src='<%=request.getContextPath()%>/assets/plugins/jquery-1.11.1/jquery.min.js'></script>
<script type='text/javascript'>
//跳转子系统
function toSubSystem(sid,sname){
	$("#sys_id").val(sid);
	$("#sys_name").val(sname);
	$("form").submit();
}

</script>








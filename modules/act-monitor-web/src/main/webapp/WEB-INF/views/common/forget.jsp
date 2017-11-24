<%@ page language="java"  pageEncoding="utf-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/common/base64.js"></script>
<link href="<%=request.getContextPath()%>/css/common/forget.css" rel="stylesheet" type="text/css" />	

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>忘记密码</title>

<style type = "text/css">
*{padding:0;margin:0}
/*忘记密码界面标题*/
#pwd{
	width:100%;
	height:35px;
	background:url(<%=request.getContextPath()%>/images/login/yjcdxz.gif);
	padding-top:8px;
}
</style>

</head>

<body>
<!-- 忘记密码页面标题 -->
<div id = "pwd">
	<div style="float:left;">
	<b style="color: white; font-size: 9pt;">&nbsp;&nbsp;找回密码</b>
	</div> 	
	<div style="float:right;">
		<b><a href="#" onclick="parent.window.closePwd();" style="color: white;">关闭</a>&nbsp;&nbsp;</b>
	</div>	
</div>

<input type="hidden" id="message" value="${message}"/>

<form id="forget" name="forget" method="post" action="<%=request.getContextPath()%>/common/login/forget.do">
	<table border="0" cellspacing="0" cellpadding="0" width="100%" height="100%">
	<tr>
	 <td valign="middle" width="100%" height="100%">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	   <td valign="top">
	    <table width="100%" >
	     <tr>
            <td valign="top" align="center" >  
              <table border="0" cellspacing="0" cellpadding="5" width="100%">
                 <tr>
                  <td align="right"></td>
                  <td colspan="2" align="left">
                    <font color="#FF0000"><span id="userMessage"></span></font></td>
                 </tr>
                 
                <tr style="display:${step4}">
                  <td align="right"><b >新密码</b>：</td>
                  <td colspan="2" align="left"><input type = "password" name="newpwd" cssStyle="width:200px"/>
                    <font color="#FF0000">* 为安全起见，密码长度必须为6-15位且必须含有数字和字母</font></td>
                </tr>
                 
                 <tr><td>&nbsp;</td></tr>
                 
                 <tr style="display:${step4}">
                  <td align="right"><b>重新输入新密码</b>：</td>
                  <td colspan="2"><input type = "password" name="newpwd1" cssStyle="width:200px"/>
                    <font color="#FF0000">* </font></td>
                 </tr>
          
                
                <tr style="display:${vo.forget}" > 
                  <td width="20%" align="right"><b>用户名</b>：</td>
                  <td colspan="2" align="left">
                  	<input type="text" id="userid" name="userid" value="${vo.uid}" onKeyDown="if (event.keyCode == 13) pwdcheck();" style="width:200px" />
                    <font color="#FF0000">*</font></td>
                </tr>
                
                 <tr style="display:${step2}"> 
                  <td width="20%" align="right"><b>用户名</b>：</td>
                  <td colspan="2"><font color="#FF0000" size="3"><b>${vo.uid}</b></font></td>
                </tr>

                  <tr style="display:${step2}"> 
                  <td height="30" align="right"><b>问题</b>：</td>
                  <td colspan="2">
                   <input type = "hidden" name="question" id="question" value="${vo.question}"/>
                   ${vo.question}
                  </td>
                </tr>
                
                 <tr style="display:${step2}"> 
                  <td align="right"><b>机密答案</b>：</td>
                  <td colspan="2"><input type ="text" name="answer" value = "${vo.answer }" onkeydown="if (event.keyCode == 13) pwdcheck();" cssStyle="width:200px" />
                    <font color="#FF0000">*</font></td>
                </tr>
                
                   <tr align="center"  style="display:${step1}"> 
                  <td height="30" align="center">&nbsp;</td>
                  <td width="33%" height="30" align="center">
                  	
                    <input type = "hidden" name="act" id="act" value="${vo.act}"/>
                    <input type = "hidden" name="step" id="step" value="${vo.step}"/>
                    <input type = "hidden" name="uid" id="uid" value="${vo.uid}"/>
                     
                    <c:choose>
                    	<c:when test="${step != 4}">
		                    <input type='button' class='short_up' onclick="return pwdcheck();" 
								onmouseover="this.className='short_over';" onMouseOut="this.className='short_up';" value='下一步'/>
                    	</c:when>
                    	<c:otherwise>
                    		<input type='button' class='short_up' onclick="return pwdcheck();" 
								onmouseover="this.className='short_over';" onMouseOut="this.className='short_up';" value='确 定'/>
                    	</c:otherwise>
                    </c:choose>
                    <c:if test="${step == 2 || step == 4}">
		                <input type='button' class='short_up' onclick="parent.window.closePwd();"
                        	onMouseOver="this.className='short_over';" onMouseOut="this.className='short_up';" value='返 回'/>
                    </c:if>
                  </td>
                  <td width="47%" align="center">&nbsp;</td>
                </tr>
              
              </table>
              </td>
              </tr>
	    
	    
	    </table>
	   </td>
	  </tr>
	  
	  </table>
	 </td>
	</tr>
	</table>
	</form>

</body>

<!-- 导入公用jsp -->
<script src='<%=request.getContextPath()%>/assets/plugins/jquery-1.11.1/jquery.min.js'></script>
<script language="javascript">
if($("#message").val()=="1"){
	$("#userMessage").html('未找到相应的用户！');
}else if($("#message").val()=="2"){
	$("#userMessage").html('问题答案错误!');
}else if($("#message").val()=="4"){
	alert('密码已修改！');
	parent.window.closePwd();
}

function pwdcheck(){
	  if($("#act").val() == "forget"){
		  if($("#step").val() == "4"){
		      if(forget.newpwd.value == "" || forget.newpwd.value.length < 6){
		        $("#userMessage").html("必须输入长度至少为六位的密码");
		        return;
		      }
		      if(forget.newpwd.value != forget.newpwd1.value){
		       $("#userMessage").html("两次输入的密码不相同");
		        return;        
		      }
		      var filter = /^(?!\D+$)(?![^a-zA-Z]+$)\S{6,15}$/;
		 	  if (!filter.test(killspace(forget.newpwd.value))){
		  			alert("您的密码过于简单，长度必须为6-15位且要包含字母和数字!"); 
		  			forget.newpwd.focus();
		  			return false; 
			  }
			  var b = new Base64();          
			  forget.newpwd.value = b.encode(forget.newpwd.value);  
			  forget.newpwd1.value = b.encode(forget.newpwd1.value);
		    }else if($("#step").val() == "2"){
		      if(forget.answer.value == ""){
		        $("#userMessage").html("必须输入您的机密答案");
		        forget.answer.focus();
		        return;
		      }
		    }else{
		      if(forget.userid.value == ""){
		        $("#userMessage").html("必须输入您的用户名");
		        forget.userid.focus();
		        return;
		      }
		    }
		     var b = new Base64();
		     $("#uid").val(b.encode($("#userid").val()));
	  }
	  forget.action = "<%=request.getContextPath()%>/common/login/forget.do";
	  forget.submit();
	}
	
	
/*去除字符前尾的空格*/
function killspace(str)
{
  while( str.charAt(0)==" " )
  {
    str=str.substr(1,str.length);
  }

  while( str.charAt(str.length-1)==" " )
  {
    str=str.substr(0,str.length-1);
  }
  return str;
}

</script>


</html>
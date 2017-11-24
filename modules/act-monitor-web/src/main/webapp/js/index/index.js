var contextPath = "/act-monitor-web";

/* 所有输入框默认是 disabled 的 调用该函数将释放他们 */
function release_item(){
	 /*检查浏览器是否是IE*/
	 if( navigator.appName.indexOf("Microsoft") == -1 || window.screen.width < 1024 )
	 { 	
		 $("#pd").css("display","block");
	 }else{
		 var bb = navigator.appVersion.split(';');	
	     var version = bb[1].replace('MSIE ','');
	     if( parseInt( version ) < 8 )
	    	 $("#pd").css("display","block");
	 }
	 $("#username2").attr("disabled",false);
	 $("#passwd2").attr("disabled",false);
	 $("#verifyId").attr("disabled",false);
	 $("#username2").get(0).focus();
}

/* 验证码 && 忘记密码 */
function loginLoad(){
	if($("#loginIsCode").val() == "Y") {
		var timenow = new Date().getTime();
		$("#randImg").attr('src', contextPath + "/common/login/verification.do?d=" + timenow);
		$("#validateDiv").show();
	} else {
		$("#validateDiv").hide();
	}
	
	if($("#xssTip").val() == "Y"){
		$("#xssTipTd").html("<span><a href='#' style='color:red' onclick='return setpwdsec()'>忘记密码？</a></span>&nbsp;");
		$("#xssTipTd").show();
	} else {
		$("#xssTipTd").hide();
		$("#xssTipTd").html("");
	}
}

/*更新验证码*/
function changeValidateCode() {
	//获取当前的时间作为参数，无具体意义  
	var timenow = new Date().getTime();
	//每次请求需要一个不同的参数，否则可能会返回同样的验证码  
	//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。
	$("#randImg").attr('src', contextPath + "/common/login/verification.do?d=" + timenow); 
} 

/*忘记密码提示*/
function setpwdsec() {
	var url = contextPath + "/common/login/forget.do?act=forget";
	$("#frameDiv").css("display", "block");
	$("#frameClass").css("display", "block");
	$("#iframe").attr("src", url);
	return false;
}


/*关闭忘记密码 界面*/
function closePwd()
{
	$("#frameDiv").css("display","none");
	$("#frameClass").css("display","none");
}


/*登入方法*/
function verifyInput(){
	var username2=$("#username2");
	if(username2.val() == ""){
		$("#userMessage").html("<font color='red'>请输入用户名</font>");
		username2.focus();
	}else if(username2.val().length>20){
		$("#userMessage").html("<font color='red'>用户名长度不能超过20个字符</font>");
		username2.focus();
		return false;
	}
	
	var passwd2=$("#passwd2");
	if (passwd2.val()==""){
		$("#pwdMessage").html("<font color='red'>请输入密码</font>");
		passwd2.focus();
		return false;
	}
	
	if($("#loginIsCode").val() == "Y"){
		var verifyId=$("#verifyId");
		if (verifyId.val()==""){
			$("#randMessage").html("<font color='red'>请输入验证码</font>");
			verifyId.focus();
			return false;
		}
	}
	
	var b = new Base64();
	var username=b.encode($("#username2").val());
	$("#username").val(username);
	
	var pw=b.encode($("#passwd2").val());
	$("#passwd").val(pw);
	$('#username2').attr("disabled",true);
	$('#passwd2').attr("disabled",true);
	
	var url = contextPath+'/common/login/login.do';
	var params=$("#login").serialize();		
	$.post(url,params,isAddtion,"json"); 
}

//处理登入方法 返回的数据
function isAddtion(data){	   
	  if(data.flag=="yes"){
			 window.location.href=contextPath+"/common/login/welcome.do";	 
		  }
		  if(data.flag=="no"){
			  $('#username2').attr("disabled",false);
			  $('#passwd2').attr("disabled",false);
			  
		      $('#userMessage').html("");
	          $('#pwdMessage').html("");
	          $('#errorMessage').html("");
	          if(data.errorUser!="" ||data.errorPassword!=""){
	              $('#errorMessage').html("<font color='red'>帐号或者密码错误</font>");
	          }
	          
			  $('#ipMessage').html("");
	          $('#ipMessage').html(data.errorIp);
			  $('#stateMessage').html("");
	          $('#stateMessage').html(data.errorState);
	          if($("#loginIsCode").val() == "Y"){
			  	  var randImg = document.getElementById("randImg");
			  	  changeValidateCode(randImg); 
				  $("#passwd").val("");
				  $("#passwd2").val("");
			  	  $("#verifyId").val(""); 
	              $('#randMessage').html("");
	              $('#randMessage').html(data.errorVerifyCode);
	              if($('#randMessage').html()!=""){
	            	  $("#verifyId").focus();
	              }
	          }
		   }	 
		}  


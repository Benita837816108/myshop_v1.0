<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head></head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/jquery.validate.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

<style>
  body{
   margin-top:20px;
   margin:0 auto;
 }
 .carousel-inner .item img{
	 width:100%;
	 height:300px;
 }
 .container .row div{ 
	 /* position:relative;
	 float:left; */
 }
 
font {
    color: #3164af;
    font-size: 18px;
    font-weight: normal;
    padding: 0 10px;
}

 .error{
    	
				color: red;
			}
 </style>
</head>
<script type="text/javascript">


$(function(){
	$("#myform").validate({
		rules:{
				"username":{"required":true},
				"password":{"required":true,"rangelength":[6,12]},
				"repassword":{"required":true,"equalTo":"#password"},
				"telephone":{"required":true,"digits":true},
				"email":{"required":true,"email":true},	
				"checkCode":{"required":true}	
		},
		messages:{
			"username":{"required":"用户名不能为空"},
			"password":{"required":"密码不能为空","rangelength":"你输入的密码不是{0}-{1}"},
			"repassword":{"required":"确认密码不能为空","equalTo":"两次密码不一致"},
			"telephone":{"required":"手机号码不能为空","digits":"输入的不是数字"},
			"email":{"required":"邮箱不能为空","email":"请输入正确的邮件格式"},
			"checkCode":{"required":"验证码不能为空"}
		}
	})
});


 function fn2(obj) {
	/*
	* 1.请求路径:${pageContext.request.contextPath}/checkUserName
		* 2.参数类型:参数名为username
		* 3.响应数据:String 字符串，类似"用户名已存在"
	*/
	//使用jQuery的ajax发送一个异步的POST请求
	
	$.post("${pageContext.request.contextPath}/checkUserName",{"username":obj.value},function(result){
		
		//result就是响应数据
		$("#checkusername").text(result)
	})
} 

 
 function fn3(obj) {
		/*
		* 1.请求路径:${pageContext.request.contextPath}/checkUserName
			* 2.参数类型:参数名为username
			* 3.响应数据:String 字符串，类似"用户名已存在"
		*/
		//使用jQuery的ajax发送一个异步的POST请求
		
		$.post("${pageContext.request.contextPath}/checkCode",{"checkCode":obj.value},function(result){
			
			//result就是响应数据
			$("#vcheckCode").text(result)
		})
	} 
 
//为验证码输入框绑定一个移出的ajax事件
function fn1(obj){
	obj.src="${pageContext.request.contextPath}/vcodeServlet?time"+new Date();
};
</script>
<body>
			<%--引入head.jsp 
				静态包含是拷贝代码
			--%>
			<%@ include file="/jsp/head.jsp" %>





<div class="container" style="width:100%;background:url('${pageContext.request.contextPath}/image/regist_bg.jpg');">
<div class="row"> 

	<div class="col-md-2"></div>
	
	


	<div class="col-md-8" style="background:#fff;padding:40px 80px;margin:30px;border:7px solid #ccc;">
		<font>会员注册</font>USER REGISTER
		<form id="myform" class="form-horizontal" style="margin-top:5px;" method="post" action="${pageContext.request.contextPath }/user">
			<input type="hidden" name="methodStr" value="regist">
			  <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="username" name="username" onblur="fn2(this)"
                               placeholder="请输入用户名" ><span id="checkusername" style="color: red;"></span>
                               
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="password" name="password" 
                               placeholder="请输入密码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
                    <div class="col-sm-6">
                        <input type="password" class="form-control" id="repassword" name="repassword"
                               placeholder="请输入确认密码">
                    </div>
                </div>
                <div class="form-group">
                    <label for="telephone" class="col-sm-2 control-label">手机号码</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="telephone" name="telephone" 
                               placeholder="请输入手机号"><span id="checktelephone" style="color: red;"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">Email</label>
                    <div class="col-sm-6">
                        <input type="email" class="form-control" id="email" name="email"
                               placeholder="Email"><span id="checkemail" style="color: red;"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">姓名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="name" name="name"
                               placeholder="请输入姓名">
                    </div>
                </div>
                <div class="form-group opt">
                    <label for="sex" class="col-sm-2 control-label">性别</label>
                    <div class="col-sm-6" id="sex">
                        <label class="radio-inline"> <input type="radio" name="sex"  value="male">男</label>
                        <label class="radio-inline"> <input type="radio" name="sex"  value="female"> 女</label>
                    </div>
                </div>

                <div class="form-group opt">
                    <label for="checkbox" class="col-sm-2 control-label">爱好</label>
                    <div class="col-sm-6" id="checkbox" >
                        <label class="radio-inline"> <input type="checkbox" name="hobby"  value="running">跑步</label>
                        <label class="radio-inline"> <input type="checkbox" name="hobby"  value="reading">阅读</label>
                        <label class="radio-inline"> <input type="checkbox" name="hobby"  value="basketball">篮球</label>
                        <label class="radio-inline"> <input type="checkbox" name="hobby"  value="football">足球</label>
                       
                    </div>
                </div>

                <div class="form-group">
                    <label for="date" class="col-sm-2 control-label">出生日期</label>
                    <div class="col-sm-6" id="date">
                        <input type="date" class="form-control" name="birthday">
                    </div>
                </div>

                <div class="form-group">
                    <label for="date" class="col-sm-2 control-label">验证码</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" name="checkCode" onblur="fn3(this)">
                        <span id="vcheckCode" style="color: red;"></span>
                    </div>
                    <div class="col-sm-2">
                        <img src="${pageContext.request.contextPath }/vcodeServlet" onclick="fn1(this)"/>
                    </div>

                </div>
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <input type="submit"  width="100" value="注册" border="0"
				    style="background: url('${pageContext.request.contextPath}/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
				    height:35px;width:100px;color:white;">
			    </div>
			  </div>
			</form>
	</div>
	
	<div class="col-md-2"></div>
  
</div>
</div>

	  
	
		<%--引入head.jsp 
				静态包含是拷贝代码
			--%>
			<%@ include file="/jsp/foot.jsp" %>

</body></html>





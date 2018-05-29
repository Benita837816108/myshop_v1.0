<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
<script type="text/javascript">
//验证框ajax
function fn3(obj) {
	/*
	* 1.请求路径:${pageContext.request.contextPath}/checkCode
		* 2.参数类型:参数名为username
		* 3.响应数据:String 字符串，类似"验证码错误请从新输入"
	*/
	//使用jQuery的ajax发送一个异步的POST请求
	
	$.post("${pageContext.request.contextPath}/checkCode",{"checkCode":obj.value},function(result){
		
		//result就是响应数据
		$("#vcheckCode").text(result)
	})
} 
//验证码图片切换
function fn1(obj){
	obj.src="${pageContext.request.contextPath}/vcodeServlet?time"+new Date();
};

</script>
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
    color: #666;
    font-size: 22px;
    font-weight: normal;
    padding-right:17px;
}
 </style>
</head>
<body>
			<%--引入head.jsp 
				静态包含是拷贝代码
			--%>
			<%@ include file="/jsp/head.jsp" %>
<div class="container"  style="width:100%;height:460px;background:#FF2C4C url('${pageContext.request.contextPath}/images/loginbg.jpg') no-repeat;">
<c:if test="${not empty user }">
<jsp:forward page="/jsp/index.jsp"></jsp:forward>
</c:if>
<div class="row"> 
	<div class="col-md-7">
		<!--<img src="${pageContext.request.contextPath}/image/login.jpg" width="500" height="330" alt="会员登录" title="会员登录">-->
	</div>
	
	<div class="col-md-5">
				<div
					style="width: 440px; border: 1px solid #E7E7E7; padding: 20px 0 20px 30px; border-radius: 5px; margin-top: 60px; background: #fff;">
					<font>会员登录</font>USER LOGIN
					<div style="color:red">${msg }</div>
					<form class="form-horizontal" action="${pageContext.request.contextPath }/user" method="post">
						<input type="hidden" name="methodStr" value="login">
						<div class="form-group">
							<label for="username" class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="username" name="username"
									placeholder="请输入用户名">
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
							<label for="inputPassword3" class="col-sm-2 control-label">验证码</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="inputPassword3" name="checkCode" placeholder="请输入验证码" onblur="fn3(this)">
							</div>
							<div class="col-sm-3">
								<img src="${pageContext.request.contextPath }/vcodeServlet"  onclick="fn1(this)"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<div class="checkbox">
									<label> <input type="checkbox" name="remember"> 自动登录
									</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label> <input
										type="checkbox" > 记住用户名
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" width="100" value="登录" name="submit"
									style="background: url('${pageContext.request.contextPath}/images/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
							</div>
						</div>
					</form>
				</div>
			</div>
</div>
</div>	

		<%--引入foot.jsp 
				静态包含是拷贝代码
			--%>
			<%@ include file="/jsp/foot.jsp" %>
</body></html>
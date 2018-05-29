<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="../js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	//当文档加载完毕之后，发送一个异步的请求
	/* url:http://local:8080/store_33/category
     	请求参数:methodStr="findAll"
    * 响应数据类型:json数组字符串:[{"cid":"1","cname":"手机数码"},{"cid":"172934bd636d485c98fd2d3d9cccd409","cname":"运动户外"}] */
	$(function() {
		$.getJSON("${pageContext.request.contextPath}/category",{"methodStr":"findAll"},function(result){
			//result就是服务器响应的json数组
			$(result).each(function(index,element) {
				//index表示下标，element表示遍历出来的每一个元素
				var name = element.cname
				//每遍历出来一个cname就往ul中添加一个li
				$("#category").append("<li><a href='${pageContext.request.contextPath}/product?methodStr=page&curPage=1&cid="+element.cid+"'>"+name+"</a></li>")
			})
		})
	})
</script>
</head>
<body>
	<!--
            	时间：2015-12-30
            	描述：菜单栏
            -->
			<div class="container-fluid">
				<div class="col-md-4">
					<img src="${pageContext.request.contextPath}/img/logo2.png" />
				</div>
				<div class="col-md-4">
					<img src="${pageContext.request.contextPath}/img/header.png" />
				</div>
				<div class="col-md-4" style="padding-top:20px">
					<ol class="list-inline">
						<%--如果未登录则显示登录、注册、购物车
							未登录就是域对象session中没有user对象
						 --%>
						<c:if test="${empty user }">
							<li><a href="${pageContext.request.contextPath }/jsp/login.jsp">登录</a></li>
							<li><a href="${pageContext.request.contextPath }/jsp/register.jsp">注册</a></li>
							<li><a href="${pageContext.request.contextPath }/jsp/cart.jsp">购物车</a></li>
						</c:if>
						
						<%--如果已登录，则显示欢迎xxx登录黑马生成、购物车、订单、注销 --%>
						<c:if test="${not empty user }">
							<li>欢迎${user.name }登录黑马商城</li>
							<li><a href="${pageContext.request.contextPath }/jsp/cart.jsp">购物车</a></li>
							<li><a href="${pageContext.request.contextPath }/order?methodStr=page&curPage=1">订单</a></li>
							<li><a href="${pageContext.request.contextPath }/user?methodStr=logout">注销</a></li>
						</c:if>
					</ol>
				</div>
			</div>
			<!--
            	时间：2015-12-30
            	描述：导航条
            -->
			<div class="container-fluid">
				<nav class="navbar navbar-inverse">
					<div class="container-fluid">
						<!-- Brand and toggle get grouped for better mobile display -->
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand" href="#">首页</a>
						</div>

						<!-- Collect the nav links, forms, and other content for toggling -->
						<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							<ul class="nav navbar-nav" id="category">
								
							</ul>
							<form class="navbar-form navbar-right" role="search">
								<div class="form-group">
									<input type="text" class="form-control" placeholder="Search">
								</div>
								<button type="submit" class="btn btn-default">Submit</button>
							</form>

						</div>
						<!-- /.navbar-collapse -->
					</div>
					<!-- /.container-fluid -->
				</nav>
			</div>
</body>
</html>
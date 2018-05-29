<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
	</HEAD>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
	<!-- 引入自定义css文件 style.css -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
	<script type="text/javascript" src="../../layer/layer.js"></script>
	<script type="text/javascript">
		function fn1(oid) {
			var content = "<table border='1' cellspacing='0' width='100%'><tr><th>商品图片</th><th>商品名称</th><th>购买数量</th></tr>"
			//向服务器请求("异步请求")
			$.getJSON("${pageContext.request.contextPath}/adminOrder",{"methodStr":"findByOid","oid":oid},function(result){
				
				//遍历result中的orderItems属性
				$(result.orderItems).each(function(index,element) {
					//每一个element就相当于一个购物项，我们要关注购物项的product属性和count属性
					//每遍历出一个购物项就添加一行
					content += "<tr><td><img height='60px' width='80px' src='${pageContext.request.contextPath}/"+element.product.pimage+"'></td><td>"+element.product.pname+"</td><td>"+element.count+"</td></tr>"
				})
				
				//添加一个</table>
				content += "</table>"
				
				//弹出一个信息框
				layer.open({
					type: 1,//0:信息框; 1:页面; 2:iframe层;	3:加载层;	4:tip层
			        title:"订单详情",//标题
			        area: ['400px', '300px'],//大小
			        shadeClose: true, //点击弹层外区域 遮罩关闭
			        content: content //弹框里面的内容
				})
			})
		}
	</script>
	<body>
		<br>
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/user/list.jsp" method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>订单列表</strong>
						</TD>
					</tr>
					
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							<table cellspacing="0" cellpadding="1" rules="all"
								bordercolor="gray" border="1" id="DataGrid1"
								style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
								<tr
									style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

									<td align="center" width="10%">
										序号
									</td>
									<td align="center" width="10%">
										订单编号
									</td>
									<td align="center" width="10%">
										订单金额
									</td>
									<td align="center" width="10%">
										收货人
									</td>
									<td align="center" width="10%">
										订单状态
									</td>
									<td align="center" width="50%">
										订单详情
									</td>
								</tr>
								<c:forEach items="${page.list }" var="o" varStatus="vs">
										<tr>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="18%">
												${vs.count }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												${o.oid }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
													${o.total }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												${o.name }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												<c:if test="${o.state == 0}">
													未付款
												</c:if>
												<c:if test="${o.state == 1 }">
													<a href="${pageContext.request.contextPath }/adminOrder?methodStr=updateState&oid=${o.oid}">去发货</a>
												</c:if>
												<c:if test="${o.state == 2 }">
													等待确认收货
												</c:if>
												<c:if test="${o.state== 3 }">
													订单完成
												</c:if>
											
											</td>
											<td align="center" style="HEIGHT: 22px">
												<input type="button" value="订单详情" onclick="fn1('${o.oid}')" />
												<div id="divId">
													
												</div>
											</td>
							
										</tr>
									</c:forEach>
							</table>
						</td>
					</tr>
				</TBODY>
			</table>
			<div style="text-align: center;">
				<ul class="pagination">
					<c:if test="${page.curPage != 1 }">
						<li><a href="${pageContext.request.contextPath }/adminOrder?methodStr=page&curPage=${page.curPage - 1}&state=${param.state}" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
					</c:if>
					<c:forEach begin="1" end="${page.totalPage }" var="i">
						<c:if test="${page.curPage == i }">
							<li class="active"><a>${i }</a></li>
						</c:if>
						<c:if test="${page.curPage != i }">
						<li><a href="${pageContext.request.contextPath }/adminOrder?methodStr=page&curPage=${i}&state=${param.state}">${i }</a></li>
					</c:if>
					</c:forEach>
					
					<c:if test="${page.curPage != page.totalPage }">
						<li>
							<a href="${pageContext.request.contextPath }/adminOrder?methodStr=page&curPage=${page.curPage + 1}&state=${param.state}" aria-label="Next">
								<span aria-hidden="true">&raquo;</span>
							</a>
						</li>
					</c:if>
				</ul>
			</div>
		</form>
	</body>
</HTML>


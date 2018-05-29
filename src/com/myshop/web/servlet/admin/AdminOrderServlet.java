package com.myshop.web.servlet.admin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.msyshop.constant.Constant;
import com.myshop.bean.Order;
import com.myshop.bean.PageBean;
import com.myshop.dao.admin.IAdminOrderService;
import com.myshop.factory.ContextFactory;

import com.myshop.web.servlet.BaseServlet;
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 更新订单状态
	 * @param request
	 * @param response
	 * @return
	 */
	public String updateState(HttpServletRequest request,HttpServletResponse response){
		String oid = request.getParameter("oid");
		//调用业务层的方法，更新订单状态
		try {
			IAdminOrderService service = (IAdminOrderService) ContextFactory.getInstance("adminOrder_service");
			//1.根据oid获取订单信息
			Order order = service.findOrderByOid(oid);
			order.setState(Constant.ORDERS_PAIED_SENTED);
			
			//2.调用业务层的方法更新订单信息
			service.updateOrder(order);
			
			//更新完成之后，跳转到展示"已付款"
			response.sendRedirect(request.getContextPath()+"/adminOrder?methodStr=page&curPage=1&state=2");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 分页查看订单
	 * @param request
	 * @param response
	 * @return
	 */
	public String page(HttpServletRequest request,HttpServletResponse response){
		//1.获取请求参数
		Integer curPage = Integer.parseInt(request.getParameter("curPage"));
		String param = request.getParameter("state");
		
		Integer state = null;
		if (param != null&&!"null".equals(param)) {
			state = Integer.parseInt(param);
		}
		
		//2.调用业务层的方法，获取订单分页的PageBean对象
		try {
			IAdminOrderService service = (IAdminOrderService) ContextFactory.getInstance("adminOrder_service");
			PageBean<Order> pageBean = service.findPageBean(curPage,state);
			
			//3.将PageBean存放到session中
			HttpSession session = request.getSession();
			session.setAttribute("page", pageBean);
			
			//4.跳转展示
			response.sendRedirect(request.getContextPath()+"/admin/order/list.jsp?state="+state);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据oid查找订单详情
	 * @param request
	 * @param response
	 * @return
	 */
	public String findByOid(HttpServletRequest request,HttpServletResponse response){
		//1.获取客户端传过来的请求参数oid
		String oid = request.getParameter("oid");
		//2.调用业务层的方法，根据oid查找订单详情信息
		try {
			IAdminOrderService service = (IAdminOrderService) ContextFactory.getInstance("adminOrder_service");
			String json = service.findOrderJsonByOid(oid);
			
			//3.将json响应给客户端
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();
			writer.write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

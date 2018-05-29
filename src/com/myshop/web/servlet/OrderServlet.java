package com.myshop.web.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.msyshop.constant.Constant;
import com.myshop.bean.Car;
import com.myshop.bean.Order;
import com.myshop.bean.PageBean;
import com.myshop.bean.User;
import com.myshop.factory.ContextFactory;
import com.myshop.service.IOrderService;
import com.myshop.utils.PaymentUtil;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.IO;

import sun.print.PeekGraphics;

/**
 * 关于订单相关业务的servlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 用于处理支付成功之后的结果
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	public String callback(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//第三方支付公司会重定向到这里来
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// 身份校验 --- 判断是不是支付公司通知你
		
		String hmac = request.getParameter("hmac");//第三方公司发给我们公司服务器的hmac
		
		//获取配置文件中的密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");
		// 自己对上面数据进行加密 --- 
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 响应数据有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向
				System.out.println("111");
				request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"已经支付成功,等待发货~~");
				
			} else if (r9_BType.equals("2")) {
				// 服务器点对点 --- 支付公司通知你
				System.out.println("付款成功！222");
				// 修改订单状态 为已付款
				// 回复支付公司
				response.getWriter().print("success");
			}
			
			//修改订单状态
			//将订单状态修改成已付款
			try {
				IOrderService service = (IOrderService) ContextFactory.getInstance("order_service");
				//根据订单号查找订单
				Order order = service.findOrderByOid(r6_Order);
				order.setState(Constant.ORDERS_PAIED_COMFIRM);
				
				service.updateOrder(order);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// 数据无效,说明这个数据就不是第三方公司发过来的
			System.out.println("数据被篡改！");
			
		}
		return "/jsp/msg.jsp";
	}
	/**
	 * 处理支付请求的方法
	 * @param request
	 * @param response
	 * @return
	 */
	public String pay(HttpServletRequest request,HttpServletResponse response){
		//1.获取请求参数
		String oid = request.getParameter("oid");
		Map<String, String[]> map = request.getParameterMap();
		String pd_FrpId = request.getParameter("pd_FrpId");
		
		//2.根据oid获取该订单信息,调用orderServiceImpl的方法
		try {
			IOrderService service = (IOrderService) ContextFactory.getInstance("order_service");
			Order order = service.findOrderByOid(oid);
			//更新数据库，设置address、name、telephone
			BeanUtils.populate(order, map);
			
			//调用业务层方法，修改order的信息
			service.updateOrder(order);
			
			//3.进行支付，其实就是发起一个重定向，到第三方支付公司的服务器------->只差一个url地址(携带参数)
			//调用工具类的方法，生成url地址
			String url = PaymentUtil.buildUrl(pd_FrpId, oid, "0.01");
			
			//4.重定向到这个url地址
			response.sendRedirect(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String save(HttpServletRequest request,HttpServletResponse response)throws IOException {
		//调用业务层的方法将订单信息保存到orders表
		HttpSession session = request.getSession();
		Car car = (Car) session.getAttribute("car");
		User user = (User) session.getAttribute("user");
		
			
		
		try {
			IOrderService service= (IOrderService) ContextFactory.getInstance("order_service");
			Order order=service.saveOrder(car,user);
			if(order!=null){
				//清空购物车
				session.removeAttribute("car");
				
			}
			
			//将order对象保存到session中
			session.setAttribute("order", order);
			
			//重定向到order_info.jsp
			response.sendRedirect(request.getContextPath()+"/jsp/order_info.jsp");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 分页展示所有订单
	 * @param request
	 * @param response
	 * @return
	 */
	public String page(HttpServletRequest request,HttpServletResponse response){
		//1.获取客户端传过来的当前页数
		Integer curPage = Integer.parseInt(request.getParameter("curPage"));
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		//查找订单，只能查找自己的订单
		//2.要分页就是获取当前分页的pageBean对象
		try {
			IOrderService service = (IOrderService) ContextFactory.getInstance("order_service");
			PageBean<Order> pageBean = service.findPageBean(curPage,user);
			
			//将PageBean存放到session中
			session.setAttribute("page", pageBean);
			//3.跳转到order_list.jsp页面并展示
			response.sendRedirect(request.getContextPath()+"/jsp/order_list.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String findByOid(HttpServletRequest request,HttpServletResponse response){
		//1.获取客户端传过来的oid
		String oid = request.getParameter("oid");
		//2.调用业务层的方法，根据oid查找到对应的订单的详细信息
		try {
			IOrderService service = (IOrderService) ContextFactory.getInstance("order_service");
			Order order = service.findOrderByOid(oid);
			
			//将order对象存放到session中
			HttpSession session = request.getSession();
			session.setAttribute("order", order);
			
			//跳转order_info.jsp页面进行展示
			response.sendRedirect(request.getContextPath()+"/jsp/order_info.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

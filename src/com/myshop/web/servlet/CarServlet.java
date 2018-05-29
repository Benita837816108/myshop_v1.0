package com.myshop.web.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myshop.bean.Car;
import com.myshop.bean.CarItem;
import com.myshop.bean.Product;
import com.myshop.factory.ContextFactory;
import com.myshop.service.IProductService;

/**
 * 处理和购物项相关请求的Servlet
 */
public class CarServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public String add2Car(HttpServletRequest request,HttpServletResponse response){
		//1.获取客户端传入的请求参数
		String pid = request.getParameter("pid");
		Integer count = Integer.parseInt(request.getParameter("count"));
		
		//向购物车中添加购物项
		//封装pid对应的product和count到carItem中
		//根据pid找到product
		try {
			IProductService service = (IProductService) ContextFactory.getInstance("product_service");
			Product product = service.findProductByPid(pid);
			
			CarItem item = new CarItem();
			item.setProduct(product);
			item.setCount(count);
			
			//将CarItem添加到购物车中
			//怎么得到购物车，从session中拿
			HttpSession session = request.getSession();
			Car car = (Car) session.getAttribute("car");
			if (car == null) {
				//说明是第一次添加购物车
				car = new Car();
			}
			//说明已经添加过购物车
			car.add2Car(item);
			//从新设置到session中
			session.setAttribute("car",car);
			//添加完之后，跳转到购物车页面展示所有的购物车
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从购物车中删除商品
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String delete(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//1.获取要删除的购物项的pid
		String pid = request.getParameter("pid");
		//2.从购物车中移除key为pid的那个键值对
		HttpSession session = request.getSession();
		Car car = (Car) session.getAttribute("car");
		car.removeFromCar(pid);
		
		//将car添加到session中
		session.setAttribute("car", car);
		
		//3.跳转到cart.jsp展示
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	/**
	 * 清空购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public String clear(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//1.获取session对象
		HttpSession session = request.getSession();
		//2.移除session中的car
		session.removeAttribute("car");
		//3.跳转到cart.jsp
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
}

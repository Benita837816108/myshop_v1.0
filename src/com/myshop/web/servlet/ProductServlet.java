package com.myshop.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myshop.bean.PageBean;
import com.myshop.bean.Product;
import com.myshop.factory.ContextFactory;
import com.myshop.service.IProductService;

/**
 * 处理和商品有关的所有请求
 * 接口文档
 * url:http://localhost:8080/myshop_v1.1/product
 * 参数:methodStr="hot"
 * 响应数据:json数组
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 分页显示商品信息的方法
	 * @param request
	 * @param response
	 * @return
	 */
	public String page(HttpServletRequest request,HttpServletResponse response){
		//1.获取客户端传过来的请求参数curPage
		Integer curPage = Integer.parseInt(request.getParameter("curPage"));
		String cid = request.getParameter("cid");
		//2.调用service层的方法，获取PageBean对象
		try {
			IProductService service = (IProductService) ContextFactory.getInstance("product_service");
			PageBean<Product> pageBean = service.findPageBean(curPage,cid);
			//将pageBean对象存放到session中
			HttpSession session = request.getSession();
			session.setAttribute("page", pageBean);
			//重定向到product_list.jsp
			response.sendRedirect(request.getContextPath()+"/jsp/product_list.jsp?cid="+cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public String findByPid(HttpServletRequest request,HttpServletResponse response){
		//1.获取客户端传过来的pid
		String pid = request.getParameter("pid");
		//2.调用业务层的方法，根据pid查找商品信息
		try {
			IProductService service = (IProductService) ContextFactory.getInstance("product_service");
			Product product = service.findProductByPid(pid);
			
			//使用原始的jsp来完成
			//将product存储到域对象session中
			HttpSession session = request.getSession();
			session.setAttribute("p", product);
			//重定向跳转到product_info.jsp
			response.sendRedirect(request.getContextPath()+"/jsp/product_info.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 处理和商品有关的所有请求
	 * 接口文档
	 * url:http://localhost:8080/myshop_v1.1/product
	 * 参数:methodStr="hot"
	 * 响应数据:json数组
	 */
	public String hot(HttpServletRequest request,HttpServletResponse response){

		try {
			//调用业务层的方法查找热门商品
			IProductService service= (IProductService) ContextFactory.getInstance("product_service");
			String json=service.findHotProducts();
			//将json字符串输出到客户端
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 处理和商品有关的所有请求
	 * 接口文档
	 * url:http://localhost:8080/myshop_v1.1/product
	 * 参数:methodStr="latest"
	 * 响应数据:json数组
	 */
	public String latest(HttpServletRequest request,HttpServletResponse response){

		try {
			//调用业务层的方法查找热门商品
			IProductService service= (IProductService) ContextFactory.getInstance("product_service");
			String json=service.findLatestProducts();
			//将json字符串输出到客户端
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

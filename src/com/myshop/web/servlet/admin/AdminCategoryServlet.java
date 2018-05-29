package com.myshop.web.servlet.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myshop.bean.Category;
import com.myshop.factory.ContextFactory;

import com.myshop.service.admin.IAdminCategoryService;
import com.myshop.utils.UUIDUtils;
import com.myshop.web.servlet.BaseServlet;

/**
 * Servlet implementation class AdminCategoryServlet
 */
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 后台所有分类显示
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response){
		//调用业务层的findall方法
		try {
			IAdminCategoryService service =(IAdminCategoryService) ContextFactory.getInstance("adminCategory_service");
			List<Category> list=service.findAll();
			
			//将结果集存储到域对象session中到 category中的list页面显示
			HttpSession session = request.getSession();
			session.setAttribute("list", list);
			response.sendRedirect(request.getContextPath()+"/admin/category/list.jsp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 后台所有分类显示
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll1(HttpServletRequest request, HttpServletResponse response){
		//调用业务层的findall方法
		try {
			IAdminCategoryService service =(IAdminCategoryService) ContextFactory.getInstance("adminCategory_service");
			String json=service.findAll1();
			response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 处理添加分类
	 * @param request
	 * @param response
	 * @return
	 */
	public String add(HttpServletRequest request, HttpServletResponse response){
		//获取客户端的参数
		 String cname = request.getParameter("cname");
		Category category= new Category();
		category.setCid(UUIDUtils.getId());
		category.setCname(cname);
		//调用业务层的方法
		IAdminCategoryService service;
		try {
			service = (IAdminCategoryService) ContextFactory.getInstance("adminCategory_service");
			service.addCategory(category);
			
			//重定向
			response.sendRedirect(request.getContextPath()+"/adminCategory?methodStr=findAll");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				return null;
	}
	/**
	 * 处理删除分类
	 * @param request
	 * @param response
	 * @return
	 */
	public String del(HttpServletRequest request, HttpServletResponse response){
		//获取客户端传过来参数
		String cid = request.getParameter("cid");
		//调用业务层的方法
		try {
			IAdminCategoryService service = (IAdminCategoryService) ContextFactory.getInstance("adminCategory_service");
			service.delCategory(cid);
			//重定向
			response.sendRedirect(request.getContextPath()+"/adminCategory?methodStr=findAll");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 修改分类先找到分类并到跳转到edit.jsp显示
	 * @param request
	 * @param response
	 * @return
	 */
	public String findCategoryByCid(HttpServletRequest request, HttpServletResponse response){
		//获得cid
		String cid = request.getParameter("cid");
		
		Category category=null;
		//调用业务层的方法
		try {
			IAdminCategoryService service=(IAdminCategoryService) ContextFactory.getInstance("adminCategory_service");
		category=service.findCategoryByCid(cid);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("category", category);
	
		return "/admin/category/edit.jsp";
	}
	public String edit(HttpServletRequest request, HttpServletResponse response){
		//获取修改的参数
		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");
		
		//调用业务层的方法
		try {
			IAdminCategoryService service= (IAdminCategoryService) ContextFactory.getInstance("adminCategory_service");
			service.editCategory(cid,cname);
			
			//重定向
			response.sendRedirect(request.getContextPath()+"/adminCategory?methodStr=findAll");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

}

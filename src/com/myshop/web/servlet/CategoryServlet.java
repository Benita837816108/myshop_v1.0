package com.myshop.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myshop.bean.Category;
import com.myshop.factory.ContextFactory;
import com.myshop.service.ICategoryService;

/**
 * 处理分类请求的servlet
 * 接口文档
 * url:http://localhost:8080/myshop_v1.1/category
 * 请求参数:methodStr="findAll"
 * 响应数据类型:json数组字符串
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String findAll(HttpServletRequest request,HttpServletResponse response){
		//调用业务层的方法查找所有分类
		try {
			ICategoryService	service = (ICategoryService) ContextFactory.getInstance("category_service");
			String json=service.findAllCategory();
			//将json字符串响应给客户端
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

package com.myshop.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 校验验证码
 * 1.请求地址:http://localhost:8080/myshop_v1.0/checkCode
 * 2.参数 checkCode
 * 3.响应 "验证码错误,请重新输入"
 */
public class CheckCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   //获取客户端输入的验证
        String checkCode = request.getParameter("checkCode");
        //从session中取出生成的验证码
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
       if(checkCode.equalsIgnoreCase(code)==false) {
    	   response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("验证码错误,请重新输入");
       }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

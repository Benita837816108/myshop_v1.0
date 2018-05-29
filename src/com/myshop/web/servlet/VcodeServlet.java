package com.myshop.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.dsna.util.images.ValidateCode;

/**
 * 生成验证码图片的servlet
 */
public class VcodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//生成验证码图片
		ValidateCode validateCode= new ValidateCode(90, 45, 4, 20);
		//生成的验证码
		String code = validateCode.getCode();
		HttpSession session = request.getSession();
		session.setAttribute("code", code);
		//输出到页面
		ServletOutputStream outputStream = response.getOutputStream();
		validateCode.write(outputStream);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

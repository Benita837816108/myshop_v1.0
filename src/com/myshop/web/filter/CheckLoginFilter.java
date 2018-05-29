package com.myshop.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myshop.bean.User;

/**
 * 先判断你的请求路径是否是购物车 订单
 */
public class CheckLoginFilter implements Filter {
	private String[] urls={"car","order"};

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//获取请求的人url
		HttpServletRequest rqs=(HttpServletRequest) request;
		String url = rqs.getRequestURL().toString();
		//判断url中是否包含上述数组中的字符
		for (String str : urls) {
			if(url.contains(str)){
				//表示这个请求确实是访问购物车或者是订单
				//判断其是否已登陆 也就是session中是否 有user
				HttpSession session = rqs.getSession();
				User user = (User) session.getAttribute("user");
				if(user==null){
					//说明未登陆
					//访问的是购物车或者订单而且你又未登陆
					//直接跳转到login.jsp
					HttpServletResponse rps=(HttpServletResponse) response;
					rps.sendRedirect(rqs.getContextPath()+"/jsp/login.jsp");
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}

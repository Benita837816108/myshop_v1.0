package com.myshop.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.myshop.bean.User;
import com.myshop.factory.ContextFactory;
import com.myshop.service.IUserService;
import com.myshop.utils.CommonUtil;
import com.myshop.utils.CookieUtil;

/**
 * 自动登陆过滤器
 * 1.如果当前已登陆就直接放行
 * 2.如果当前的客户短传过来的cookie中又info,那么表示需要自动登陆
 * 3/如果传过来的cookie中没有info表示不需要自动登陆则直接放行
 */
public class AotuLoginFilter implements Filter {

    
	public void destroy() {
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//怎么判断是否已登陆?session中有没有user对象
		HttpServletRequest rqs= (HttpServletRequest) request;
		HttpSession session = rqs.getSession();
		User user = (User) session.getAttribute("user");
		if(user==null){
			//说明未登陆
			//再判断是否需要自动登陆
			String info =CookieUtil.getCookieValue(rqs, "info");
			if(info!=null){
				//说明cookie中已经存放了用户名和密码需要自动登陆
				//取出用户名和密码
			String username=info.split("#")[0];
			String password = info.split("#")[1];
			//调用业务层的方法执行登陆操作
			try {
				IUserService service= (IUserService) ContextFactory.getInstance("user_service");
				user = service.dologin(username, password);
				session.setAttribute("user", user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}

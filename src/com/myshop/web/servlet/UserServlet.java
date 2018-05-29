package com.myshop.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.msyshop.constant.Constant;
import com.myshop.bean.User;
import com.myshop.factory.ContextFactory;
import com.myshop.service.IUserService;

import com.myshop.utils.CommonUtil;
import com.myshop.utils.CookieUtil;
import com.myshop.utils.MailUtills;
import com.sun.xml.internal.bind.CycleRecoverable.Context;

/**
 * 这个servlet用来处理用户的注册,登录,退出
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 退出登陆的方法
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String logout(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		//退出登陆其实就是将当前用户的session销毁
		HttpSession session = request.getSession();
		session.invalidate();
		
		//清空cookie中存放的info
		Cookie cookie = CookieUtil.createCookie("info", "da", 0, request.getContextPath());
		response.addCookie(cookie);
		
		//然后重定向到登陆页面
		response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
		return null;
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @return 要跳转到的路径
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		//1.获取客户端传过来的请求参数
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        //2.将所有的请求参数封装到User对象中
        boolean isRegisterSuccess=false;
        try {
            BeanUtils.populate(user,map);
            //uid
            user.setUid(CommonUtil.getUUID());
            //hobby
            String[] hobbies = request.getParameterValues("hobby");
            StringBuffer buffer= new StringBuffer();
            for (int i = 0; i < hobbies.length; i++) {
                if(i==hobbies.length-1){
                    buffer.append(hobbies[i]);
                }else {
                    buffer.append(hobbies[i]+",");
                }
            }
            user.setHobby(buffer.toString());
            //state
            user.setState(Constant.UNACTIVE);
            //code
            user.setCode(CommonUtil.getUUID());

         
           
            //调业务层的方法
            IUserService service = (IUserService) ContextFactory.getInstance("user_service");           
            isRegisterSuccess=service.register(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //判断是否注册成功
        if(isRegisterSuccess){
        	//注册成功
        	//跳转到msg.jsp,提示"注册成功了,请赶紧到邮箱进行激活"
        	request.setAttribute("msg", "注册成功了,请赶紧到邮箱进行激活成功");
	}else{
		//注册失败
		//跳转到msg.jsp.提示注册失败请重新注册
		request.setAttribute("msg", "注册失败请重新注册");
	}
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 激活
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String active(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		//激活操作
		//1.获取客户端携带过来的激活码
		String code = request.getParameter("code");
	
		//2.调用业务层的方法完成激活操作
		boolean flag=false;
		try {
			IUserService service=(IUserService) ContextFactory.getInstance("user_service");
			flag=service.doActive(code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//判断是否激活成功
		if(flag){
			//激活成功
			request.setAttribute("msg", "激活成功,请抓紧上登陆去挥霍!~~~");
		}else{
			//激活失败
			request.setAttribute("msg", "激活失败,请重新激活!!!");
		}
		return "/jsp/msg.jsp";
	}
	
	public String login(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		//1.获取客户端传过来的username和password
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String code = request.getParameter("checkCode");//客户端传过来的验证码
		
		//获取是否自动登陆
		String remember = request.getParameter("remember");
		//登陆成功的时候判断是否需要自动登陆
		
		//2.对比验证码
		HttpSession session = request.getSession();
		String vcode = (String) session.getAttribute("code");
		
		//返回的跳转路径
		String path=null;
		if(vcode.equalsIgnoreCase(code)){
			//验证码正确
			//校验用户名和密码是否正确
			//调用业务层的方法根据用户名和密码查找用户
			User user=null;
			try{
				IUserService service = (IUserService) ContextFactory.getInstance("user_service");
				user=service.dologin(username,password);	
			}catch(Exception e){
				e.printStackTrace();
			}
			//判断用户是否登陆成功
			if(user != null){
				//登陆成功
				//判断该用户是否激活
				//获取激活状态的state
				Integer state = user.getState();
				if(state == Constant.UNACTIVE){
					//用户未激活
					request.setAttribute("msg", "请先激活!!!");
					path="/jsp/login.jsp";
				}else{
					//登陆成功判断是否需要自动登陆
					Cookie cookie= CommonUtil.createCookie("info", username+"#"+password, 7*24*60*60, request.getContextPath());
					if(!"on".equals(remember)){
						//说明不需要自动登陆,则将以前存储好的cookie清空
						cookie.setMaxAge(0);
					}
					
					//将cookie添加response中
					response.addCookie(cookie);
					//将user对象存到seesion中
					session.setAttribute("user", user);
					//重定向跳转到index.jsp
					response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
				}
				
			}else{
					//登陆失败
				request.setAttribute("msg", "用户名或者密码错误");
				path="/jsp/login.jsp";
				
				}
		}else{
			//验证码错误
			//跳转回到登陆页面
			request.setAttribute("msg", "验证码错误");
			path="/jsp/login.jsp";
		}
		return path;
			
		}
	
}

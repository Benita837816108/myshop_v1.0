package com.myshop.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
	/**
	 * 创建和配置cookie
	 * @param name
	 * @param value
	 * @param time
	 * @param path
	 * @return
	 */
	public static Cookie createCookie(String name,String value,int time,String path){
		//1.创建Cookie对象
		Cookie cookie = new Cookie(name, value);
		//2.设置该cookie的最大有效期
		cookie.setMaxAge(time);
		//3.设置有效范围
		cookie.setPath(path);
		return cookie;
	}
	/**
	 * 获取某个cookie的值
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValue(HttpServletRequest request,String cookieName){
		//Cookie的默认有效期是一次会话中。
		Cookie[] cookies = request.getCookies();
		String value = null;
		if(cookies != null){
			for (Cookie cookie : cookies) {
				//遍历出每一个cookie对象，咱们怎么去判断该cookie是否是我们想要的那个呢?
				//通过cookie的name进行判断
				String name = cookie.getName();
				if (name.equals(cookieName)) {
					//获取cookie的value
					value = cookie.getValue();
				}
			}
		}
		return value;
	}
}

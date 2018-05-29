package com.myshop.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class CommonUtil {

    /**
     * 创建和配置cookie
     * @param name
     * @param value
     * @param time
     * @param path
     * @return
     */
    public static Cookie createCookie(String name ,String value,int time,String path){
        //1.创建cookie对象
        Cookie cookie= new Cookie(name,value);
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

    /**
     * 获取当前时间
     * 时间格式"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String getCurrentTime(){
        Date date= new Date();
        //将date类型转换成时间字符串
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(date);
        return currentTime;
    }

    /**
     * 获取当前日期
     * 时间格式"yyyy-MM-dd"
     * @return
     */
    public static String getCurrentDate(){
        Date date= new Date();
        //将date类型转换成时间字符串
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(date);
        return currentDate;
    }

    /**
     * 获取第二天零点零秒的Date
     * @return
     */
    public static Date getTomorrowZeroTime(){
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        return calendar.getTime();
    }

    /**
     * 获取当前时间的月和日的字符串
     * @return
     */
    public static String getCurrentMonthAndDay(){
        Calendar calendar= Calendar.getInstance();
        //获取月
        int month = calendar.get(Calendar.MONTH)+1;
        //判断如果月份小于10则在前面拼接0
        String mon = month+"";
        if(month<10){
            mon=0+mon;
        }
        //获取日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String dy = day + "";
        if(day<10){
            dy=0+dy;
        }

    return  mon+"-"+dy;
    }

    /**
     * 获得一个UUID的字符串
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }


}

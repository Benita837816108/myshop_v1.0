package com.myshop.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String getCurrentTime(){
		Date date = new Date();
		//将date类型转换成时间字符串
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = dateFormat.format(date);
		return currentTime;
	}
	public static String getCurrentDate(){
		Date date = new Date();
		//将date类型转换成时间字符串
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentTime = dateFormat.format(date);
		return currentTime;
	}
}

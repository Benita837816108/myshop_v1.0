package com.myshop.service.impl;

import com.msyshop.constant.Constant;
import com.myshop.bean.User;
import com.myshop.dao.IUserDao;
import com.myshop.dao.impl.UserDaoImpl;
import com.myshop.factory.ContextFactory;
import com.myshop.service.IUserService;
import com.myshop.utils.MailUtills;

import java.sql.SQLException;

public class UserServiceImpl implements IUserService{

	@Override
	public boolean register(User user) {
		boolean	isRegisterSuccess=false;
				try {
					IUserDao dao = (IUserDao)ContextFactory.getInstance("user_dao");
					dao.regist(user);
					//注册成功之后,向用户发送邮件
					MailUtills.sendMail(user.getEmail(), "尊敬的:"+user.getName()+"欢迎注册黑马商城,请点击下面的链接进行激活<a href='localhost:8080/myshop_v1.1/user?methodStr=active&code="+user.getCode()+"'>用户激活</a>", "用户激活");
					//没有异常则返回true
					isRegisterSuccess=true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return isRegisterSuccess;
	}

	@Override
	public boolean doActive(String code) {
		//激活就是以激活码去查找对应的用户 如果能查到就修改激活状态state为1并且设置激活码code为null
		boolean flag=false;
	
		try {
			IUserDao dao= (IUserDao) ContextFactory.getInstance("user_dao");
			User user=dao.findUserByCode(code);
			if(user!=null){
				//找到用户 需要被激活
				user.setState(Constant.ACTIVED);
				user.setCode(null);
				//到数据库进行修改
				dao.updateUser(user);
				flag=true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public User dologin(String username, String password) {
		//调用业务层的方法根据用户名到数据查找用户
		User user= null;
		try {
			IUserDao dao = (IUserDao) ContextFactory.getInstance("user_dao");
			user=dao.findUser(username,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
  
}

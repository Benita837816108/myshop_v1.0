package com.myshop.utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
public class TransactionManager {
	private static ThreadLocal<Connection> local = new ThreadLocal<>();
	public static Connection getConnectionFromThreadLocal() throws Exception{
		Connection conn = local.get();
		
		//判断conn是否为null
		if (conn == null) {
			conn = C3P0Util.getConnection();
			
			//存到local中去
			local.set(conn);
		}
		return conn;
	}
	/**
	 * 开启事务
	 * @throws PropertyVetoException 
	 * @throws SQLException 
	 */
	public static void startTransaction() throws Exception{
		getConnectionFromThreadLocal().setAutoCommit(false);
	}
	/**
	 * 提交事务
	 */
	public static void commit() throws Exception{
		getConnectionFromThreadLocal().commit();
	}
	/**
	 * 回滚事务
	 * @throws Exception 
	 */
	public static void rollback() throws Exception{
		getConnectionFromThreadLocal().rollback();
	}
	/**
	 * 关闭连接
	 * @throws Exception 
	 */
	public static void close() throws Exception{
		getConnectionFromThreadLocal().close();
		//连接关闭之后，将该连接从ThreadLocal中移除掉
		local.remove();
	}
}

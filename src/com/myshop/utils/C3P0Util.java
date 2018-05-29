package com.myshop.utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Util {
	private static  ComboPooledDataSource dataSource;
	static{
		 //从C3P0连接池中获取连接对象
		dataSource = new ComboPooledDataSource();
	}
	/**
	 * 获得连接对象
	 * @return
	 * @throws SQLException
	 * @throws PropertyVetoException
	 */
	public static Connection getConnection() throws SQLException, PropertyVetoException{
		 return dataSource.getConnection();
	}
	public static void close(ResultSet resultSet,Statement statement,Connection connection) throws SQLException{
		if (resultSet != null) {
			resultSet.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (connection != null) {
			connection.close();
		}
	}
	/**
	 * 获得连接池对象
	 * @return
	 */
	public static DataSource getDataSource(){
		return dataSource;
	}
}

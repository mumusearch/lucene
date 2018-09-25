package com.kingbase.lucene.db.utils;

import java.sql.Connection;

/**
 * <p>Title: 数据库连接池</p>
 * <p>Description: 数据库连接池，集中管理所有数据库连接对象</p>
 */

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class ConnectionPool {
	static DataSource dataSource = null;

	public ConnectionPool() {
	}

	/**
	 * 释放连接,调用者归还连接
	 */
	public static synchronized void freeConnection(Connection vConnection)
			throws SQLException {
		try {
			if (vConnection != null) {
				vConnection.close();
			}
		} catch (SQLException ex) {
			throw new SQLException("无法关闭连接，错误原因：" + ex.getMessage());
		}

	}

	/**
	 * 获取数据库连接
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection("proxool.myData");
		return conn;
	}

	/**
	 * 关闭资源
	 * @param rs 
	 * @param connection
	 * @param pst
	 */
	public static void closeResources(ResultSet rs,Connection connection,PreparedStatement...pst){
		try {
			if(rs!=null){
				rs.close();
			}
			if(connection!=null){
				connection.close();
			}
			if(pst!=null&&pst.length>1){
				for (PreparedStatement preparedStatement : pst) {
					if(preparedStatement!=null){
						preparedStatement.close();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
		}
	}
}

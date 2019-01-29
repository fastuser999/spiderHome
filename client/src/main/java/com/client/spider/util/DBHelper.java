package com.client.spider.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@RestController
//@RequestMapping(value = "/DBController")
public class DBHelper {
	private static Logger logger = LoggerFactory.getLogger(DBHelper.class);

	private Connection conn = null;


	// 加载数据库数据
	public DBHelper(String url, String name, String user, String password)  throws ClassNotFoundException, SQLException {
		conn = DriverManager.getConnection(url, user, password);// 获取连接
		logger.info("db connected");
	}

	public int executeNonQuery(String sql) {
		//logger.info("executeNonQuery: " + sql);
		PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			return pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		return 0;

	}

	// 写Override会报错，于是我就给他重起了一个名字
	public ResultSet executeQuery(String sql) throws SQLException {
		logger.debug("executeQuery: " + sql);
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.executeQuery();
		ResultSet rs = pst.getResultSet();
		return rs;
	}
}

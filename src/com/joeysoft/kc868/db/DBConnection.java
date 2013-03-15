package com.joeysoft.kc868.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joeysoft.kc868.SystemConfig;

/**
 * 数据库连接，因为只需要一个连接，所以用了static
 * @author JOEY
 *
 */
public class DBConnection {
	
	private static Logger logger = LoggerFactory.getLogger(DBConnection.class);
	
	private static Connection conn = null;
	
	private DBConnection(){};

	public static Connection getConnection(){
		try {
			if(conn == null || conn.isClosed()){
				Driver myDriver = (Driver)Class.forName ("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
			    Properties props = new Properties();
			    props.put("charSet", "GBK");    
			    props.put("user", "kincony852");
			    props.put("password", "kincony852");
			    //conn = myDriver.connect("jdbc:odbc:driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=./config/DB.accdb", props);
			    conn = myDriver.connect("jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=./config/"+SystemConfig.getInstance().getDbFileName(), props);
			    if (conn == null || conn.isClosed())  {
			    	return null;
			    }else{
			    	logger.debug("db connectioned");
			    }
			    conn.setAutoCommit(false);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void freeConnection() throws Exception  {
		if (conn != null) {
			conn.rollback();
			conn.close();
			conn = null;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Connection conn = DBConnection.getConnection();
			
			Map htParam = new HashMap();
			htParam.put("FLOOR", "4");
			htParam.put("FLOOR_NAME", "一楼");
			
			SQLUtil.insertSQL(conn, "FLOOR", htParam);
			conn.commit();
			System.out.println(conn);
			DBConnection.freeConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}

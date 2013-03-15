package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.SetRestart;

public class BOSetRestart {
	private static Logger logger = LoggerFactory.getLogger(BOSetRestart.class);
	
	public List<SetRestart> getList(){
		List<SetRestart> list = new ArrayList<SetRestart>();
		try {
			String sql = "SELECT * FROM SET_RESTART ORDER BY ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				
				SetRestart SetRestart = new SetRestart(SQLUtil.returnInt(mp, "ID"), SQLUtil.returnStr(mp, "RE_WEEK"), 
						SQLUtil.returnStr(mp, "RE_HOUR"), SQLUtil.returnStr(mp, "RE_MINUTE"), 
						SQLUtil.returnStr(mp, "RE_SECOND"));
				
				list.add(SetRestart);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取记录总数
	 * @param DBConnection.getConnection()
	 * @return
	 */
	public int getCount(){
		int count = 0;
		try {
			Map htData =  SQLUtil.getFirstRecord(DBConnection.getConnection(), null, true, "SELECT COUNT(*) AS COUNT FROM SET_RESTART");
			if(htData != null){
				return SQLUtil.returnInt(htData, "COUNT");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 得到Key
	 * @param id
	 * @return
	 */
	public SetRestart get(int id){
		SetRestart rm = new SetRestart();
		try {
			String sql = "SELECT * FROM SetRestart WHERE SetRestart_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), id, true, sql);
			if(htData != null){
				rm.setId(SQLUtil.returnInt(htData, "ID"));
				rm.setReWeek(SQLUtil.returnStr(htData, "RE_WEEK"));
				rm.setReHour(SQLUtil.returnStr(htData, "RE_HOUR"));
				rm.setReMinute(SQLUtil.returnStr(htData, "RE_MINUTE"));
				rm.setReSecond(SQLUtil.returnStr(htData, "RE_SECOND"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return rm;
	}
	
	/**
	 * 得到Key
	 * @param keyId
	 * @return
	 */
	public boolean isExistSetRestart(int Id){
		try {
			String sql = "SELECT * FROM SET_RESTART WHERE ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), Id, true, sql);
			if(htData != null && !htData.isEmpty()){
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 插入
	 * @param DBConnection.getConnection()
	 * @param floorName
	 * @return
	 */
	public boolean insert(SetRestart SetRestart){
		try {
			SQLUtil.insertSQL(DBConnection.getConnection(), "SET_RESTART", SetRestart.fillMap());
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			try {
				DBConnection.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(SetRestart setRestart){
		try {
			delete();
			insert(setRestart);
			
			DBConnection.getConnection().commit();
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				DBConnection.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SET_RESTART", htParam);
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				DBConnection.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(int Id){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("ID", Id);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SET_RESTART", htParam);
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				DBConnection.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}
}

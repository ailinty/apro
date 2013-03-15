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
import com.joeysoft.kc868.db.bean.Alarm;

public class BOAlarm {
	private static Logger logger = LoggerFactory.getLogger(BOAlarm.class);
	private BOUnused boUnused = new BOUnused();
	
	public List<Alarm> getList(){
		List<Alarm> list = new ArrayList<Alarm>();
		try {
			String sql = "SELECT * FROM ALARM ORDER BY ALARM_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				
				Alarm alarm = new Alarm(SQLUtil.returnInt(mp, "ALARM_ID"), SQLUtil.returnInt(mp, "ALARM_WEEK"), 
						SQLUtil.returnStr(mp, "ALARM_HOUR"), SQLUtil.returnStr(mp, "ALARM_MINUTE"), 
						SQLUtil.returnStr(mp, "ALARM_SECOND"), SQLUtil.returnStr(mp, "ALARM_STATUS"));
				
				list.add(alarm);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Alarm> getListValid(){
		List<Alarm> list = new ArrayList<Alarm>();
		try {
			String sql = "SELECT * FROM ALARM WHERE ALARM_WEEK IS NOT NULL AND ALARM_WEEK <> '0' ORDER BY ALARM_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				
				Alarm alarm = new Alarm(SQLUtil.returnInt(mp, "ALARM_ID"), SQLUtil.returnInt(mp, "ALARM_WEEK"), 
						SQLUtil.returnStr(mp, "ALARM_HOUR"), SQLUtil.returnStr(mp, "ALARM_MINUTE"), 
						SQLUtil.returnStr(mp, "ALARM_SECOND"), SQLUtil.returnStr(mp, "ALARM_STATUS"));
				
				list.add(alarm);
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
			Map htData =  SQLUtil.getFirstRecord(DBConnection.getConnection(), null, true, "SELECT COUNT(*) AS COUNT FROM ALARM");
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
	 * @param keyId
	 * @return
	 */
	public Alarm get(int alarmId){
		Alarm rm = new Alarm();
		try {
			String sql = "SELECT * FROM ALARM WHERE ALARM_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), alarmId, true, sql);
			if(htData != null){
				rm.setAlarmId(SQLUtil.returnInt(htData, "ALARM_ID"));
				rm.setAlarmWeek(SQLUtil.returnInt(htData, "ALARM_WEEK"));
				rm.setAlarmHour(SQLUtil.returnStr(htData, "ALARM_HOUR"));
				rm.setAlarmMinute(SQLUtil.returnStr(htData, "ALARM_MINUTE"));
				rm.setAlarmSecond(SQLUtil.returnStr(htData, "ALARM_SECOND"));
				rm.setAlarmStatus(SQLUtil.returnStr(htData, "ALARM_STATUS"));
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
	public boolean isExistAlarm(int alarmId){
		try {
			String sql = "SELECT * FROM ALARM WHERE ALARM_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), alarmId, true, sql);
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
	public boolean insert(Alarm alarm){
		try {
			SQLUtil.insertSQL(DBConnection.getConnection(), "ALARM", alarm.fillMap());
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
	
	public boolean update(Alarm alarm){
		try {
			
			if(isExistAlarm(alarm.getAlarmId())){
				Map<String, Object> htUpdateParam = new HashMap<String, Object>();
				htUpdateParam.put("ALARM_WEEK", alarm.getAlarmWeek());
				htUpdateParam.put("ALARM_HOUR", alarm.getAlarmHour());
				htUpdateParam.put("ALARM_MINUTE", alarm.getAlarmMinute());
				htUpdateParam.put("ALARM_SECOND", alarm.getAlarmSecond());
				htUpdateParam.put("ALARM_STATUS", alarm.getAlarmStatus());
				Map<String, Object> htKeyParam = new HashMap<String, Object>();
				htKeyParam.put("ALARM_ID", alarm.getAlarmId());
				
				SQLUtil.updateSQL(DBConnection.getConnection(), "ALARM", htUpdateParam, htKeyParam);
			}else{
				if(alarm.getAlarmId() > 0){
					insert(alarm);
				}
			}
			
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
	
	public boolean delete(int alarmId){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("ALARM_ID", alarmId);
			SQLUtil.deleteSQL(DBConnection.getConnection(), "ALARM", htParam);
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

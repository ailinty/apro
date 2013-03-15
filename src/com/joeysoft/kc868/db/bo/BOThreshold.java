package com.joeysoft.kc868.db.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.Threshold;

public class BOThreshold {
	
	public List<Threshold> getList(){
		List<Threshold> list = new ArrayList<Threshold>();
		try {
			String sql = "SELECT * FROM THRESHOLD";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				Threshold td = new Threshold(SQLUtil.returnInt(mp, "THRESHOLD_ID"), SQLUtil.returnStr(mp, "THRESHOLD_TYPE"), SQLUtil.returnInt(mp, "THRESHOLD"));
				list.add(td);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 得到Threshold
	 * @param DBConnection.getConnection()
	 * @param floor
	 * @return
	 */
	public Threshold get(String thresholdType){
		Threshold th = new Threshold();
		try {
			String sql = "SELECT * FROM THRESHOLD WHERE THRESHOLD_TYPE=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), thresholdType, true, sql);
			if(htData != null){
				th.setThresholdId(SQLUtil.returnInt(htData, "THRESHOLD_ID"));
				th.setThresholdType(thresholdType);
				th.setThreshold(SQLUtil.returnInt(htData, "THRESHOLD"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return th;
	}
	
	/**
	 * 插入楼层
	 * @param DBConnection.getConnection()
	 * @param floorName
	 * @return
	 */
	public boolean insert(Threshold threshold){
		try {
			SQLUtil.insertSQL(DBConnection.getConnection(), "THRESHOLD", threshold.fillMap());
			DBConnection.getConnection().commit();
			return true;
		} catch (Exception e) {
			try {
				DBConnection.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Threshold threshold){
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("THRESHOLD", threshold.getThreshold());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("THRESHOLD_TYPE", threshold.getThresholdType());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "THRESHOLD", htUpdateParam, htKeyParam);
			DBConnection.getConnection().commit();
			return true;
		} catch (Exception e) {
			try {
				DBConnection.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(String thresholdType){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("THRESHOLD", thresholdType);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "THRESHOLD", htParam);
			DBConnection.getConnection().commit();
			return true;
		} catch (Exception e) {
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

package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.SmsIn;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.db.util.UnusedUtil;

public class BOSmsIn {
	
	private BOUnused boUnused = new BOUnused();
	
	public List<SmsIn> getList(){
		List<SmsIn> list = new ArrayList<SmsIn>();
		try {
			if(SystemConfig.getInstance().isHardSoftVer2030()){
				String sql = "SELECT * FROM SMS_IN ORDER BY SMS_ID";
				List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
				for(Map mp : lt){
					
					SmsIn SmsIn = new SmsIn(SQLUtil.returnStr(mp, "SMS_ID"), SQLUtil.returnStr(mp, "SMS_CONTENT"), 
							SQLUtil.returnStr(mp, "SMS_NAME"));
					
					list.add(SmsIn);
				}
			}else{
				String sql = "SELECT * FROM SMS_IN ORDER BY SMS_INDEX";
				List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
				for(Map mp : lt){
					
					SmsIn SmsIn = new SmsIn(SQLUtil.returnStr(mp, "SMS_ID"), SQLUtil.returnStr(mp, "SMS_CONTENT"), 
							SQLUtil.returnStr(mp, "SMS_NAME"), SQLUtil.returnInt(mp, "SMS_INDEX"));
					
					list.add(SmsIn);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<SmsIn> getListValid(){
		List<SmsIn> list = new ArrayList<SmsIn>();
		try {
			if(SystemConfig.getInstance().isHardSoftVer2030()){
				String sql = "SELECT * FROM SMS_IN WHERE SMS_CONTENT IS NOT NULL ORDER BY SMS_ID";
				List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
				for(Map mp : lt){
					
					SmsIn SmsIn = new SmsIn(SQLUtil.returnStr(mp, "SMS_ID"), SQLUtil.returnStr(mp, "SMS_CONTENT"), 
							SQLUtil.returnStr(mp, "SMS_NAME"));
					
					list.add(SmsIn);
				}
			}else{
				String sql = "SELECT * FROM SMS_IN WHERE SMS_CONTENT IS NOT NULL ORDER BY SMS_INDEX";
				List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
				for(Map mp : lt){
					
					SmsIn SmsIn = new SmsIn(SQLUtil.returnStr(mp, "SMS_ID"), SQLUtil.returnStr(mp, "SMS_CONTENT"), 
							SQLUtil.returnStr(mp, "SMS_NAME"), SQLUtil.returnInt(mp, "SMS_INDEX"));
					
					list.add(SmsIn);
				}
			}
			
		} catch (Exception e) {
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
			Map htData =  SQLUtil.getFirstRecord(DBConnection.getConnection(), null, true, "SELECT COUNT(*) AS COUNT FROM SMS_IN");
			if(htData != null){
				return SQLUtil.returnInt(htData, "COUNT");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * 得到Key
	 * @param keyId
	 * @return
	 */
	public SmsIn get(int smsId){
		SmsIn rm = new SmsIn();
		try {
			String sql = "SELECT * FROM SMS_IN WHERE SMS_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), smsId, true, sql);
			if(htData != null){
				rm.setSmsId(SQLUtil.returnStr(htData, "SMS_ID"));
				rm.setSmsContent(SQLUtil.returnStr(htData, "SMS_CONTENT"));
				rm.setSmsName(SQLUtil.returnStr(htData, "SMS_NAME"));
				if(!SystemConfig.getInstance().isHardSoftVer2030()){
					rm.setSmsIndex(SQLUtil.returnInt(htData, "SMS_INDEX"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rm;
	}
	
	/**
	 * 插入
	 * @param DBConnection.getConnection()
	 * @param floorName
	 * @return
	 */
	public boolean insert(SmsIn smsIn){
		try {
			Unused unused = boUnused.get("SMS_IN");
			
			Unused next = UnusedUtil.getNextUnused(unused);
			smsIn.setSmsId(DictConst.TABLE_PREFIX_SMS_IN + next.getNextUnused());
			if(!SystemConfig.getInstance().isHardSoftVer2030()){
				smsIn.setSmsIndex(next.getNextUnused());
			}
			SQLUtil.insertSQL(DBConnection.getConnection(), "SMS_IN", smsIn.fillMap());
			boUnused.updateUnused(next);
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
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(SmsIn smsIn) throws Exception{
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("SMS_CONTENT", smsIn.getSmsContent());
			htUpdateParam.put("SMS_NAME", smsIn.getSmsName());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("SMS_ID", smsIn.getSmsId());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "SMS_IN", htUpdateParam, htKeyParam);
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			DBConnection.getConnection().rollback();
			throw e;
		}
	}
	
	public boolean delete(int smsId){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("SMS_ID", smsId);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SMS_IN", htParam);
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
			e.printStackTrace();
		}
		return false;
	}
}

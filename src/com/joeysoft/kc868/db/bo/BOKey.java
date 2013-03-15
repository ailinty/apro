package com.joeysoft.kc868.db.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.Key;

public class BOKey {
	
	public List<Key> getList(){
		List<Key> list = new ArrayList<Key>();
		try {
			String sql = "SELECT * FROM KEY";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				
				Key key = new Key(SQLUtil.returnInt(mp, "KEY_ID"), SQLUtil.returnStr(mp, "KEY_NAME"), 
						SQLUtil.returnStr(mp, "KEY_ICON"), SQLUtil.returnInt(mp, "KEY_HEIGHT"), SQLUtil.returnInt(mp, "KEY_WIDTH"));
				
				list.add(key);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 得到Key
	 * @param keyId
	 * @return
	 */
	public Key get(int keyId){
		Key rm = new Key();
		try {
			String sql = "SELECT * FROM KEY WHERE KEY_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), keyId, true, sql);
			if(htData != null){
				rm.setKeyId(keyId);
				rm.setKeyName(SQLUtil.returnStr(htData, "KEY_NAME"));
				rm.setKeyIcon(SQLUtil.returnStr(htData, "KEY_ICON"));
				rm.setKeyWidth(SQLUtil.returnInt(htData, "KEY_WIDTH"));
				rm.setKeyHeight(SQLUtil.returnInt(htData, "KEY_HEIGHT"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rm;
	}
	
	/**
	 * 按按键编号返回固定按键
	 * @param keyCode
	 * @return
	 */
	public Key getByKeyCode(String keyCode){
		Key rm = new Key();
		try {
			String sql = "SELECT * FROM KEY WHERE KEY_CODE=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), keyCode, true, sql);
			if(htData != null){
				rm.setKeyId(SQLUtil.returnInt(htData, "KEY_ID"));
				rm.setKeyName(SQLUtil.returnStr(htData, "KEY_NAME"));
				rm.setKeyIcon(SQLUtil.returnStr(htData, "KEY_ICON"));
				rm.setKeyWidth(SQLUtil.returnInt(htData, "KEY_WIDTH"));
				rm.setKeyHeight(SQLUtil.returnInt(htData, "KEY_HEIGHT"));
				rm.setKeyCode(keyCode);
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
	public boolean insert(Key key){
		try {
			SQLUtil.insertSQL(DBConnection.getConnection(), "KEY", key.fillMap());
			DBConnection.getConnection().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Key key){
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("KEY_NAME", key.getKeyName());
			htUpdateParam.put("KEY_ICON", key.getKeyIcon());
			htUpdateParam.put("KEY_WIDTH", key.getKeyWidth());
			htUpdateParam.put("KEY_HEIGHT", key.getKeyHeight());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("KEY_ID", key.getKeyId());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "KEY", htUpdateParam, htKeyParam);
			DBConnection.getConnection().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(int keyId){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("KEY_ID", keyId);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "KEY", htParam);
			DBConnection.getConnection().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

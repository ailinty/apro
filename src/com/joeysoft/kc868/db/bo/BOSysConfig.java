package com.joeysoft.kc868.db.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;

public class BOSysConfig {
	
	public void initConfig(){
		try {
			String sql = "SELECT * FROM SYSCONFIG";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				SystemConfig.getInstance().setSw(SQLUtil.returnStr(mp, "SW"));
				SystemConfig.getInstance().setActionInterval(SQLUtil.returnInt(mp, "ACTION_INTERVAL"));
				SystemConfig.getInstance().setSystemTitle(SQLUtil.returnStr(mp, "SYSTEM_TITLE"));
				SystemConfig.getInstance().setCenterTitle(SQLUtil.returnStr(mp, "CENTER_TITLE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean update(String field, String value){
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put(field, value);
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("ID", "SYS");
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "SYSCONFIG", htUpdateParam, htKeyParam);
			DBConnection.getConnection().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

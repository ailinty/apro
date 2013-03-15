package com.joeysoft.kc868.db.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.Icon;

public class BOIcon {
	
	public List<Icon> getList(){
		List<Icon> list = new ArrayList<Icon>();
		try {
			String sql = "SELECT * FROM ICON ORDER BY ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				
				Icon icon = new Icon(SQLUtil.returnStr(mp, "ICON_NAME"), SQLUtil.returnStr(mp, "ICON_TYPE"), SQLUtil.returnInt(mp, "ICON_HEIGHT"), SQLUtil.returnInt(mp, "ICON_WIDTH"));
				list.add(icon);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public List<Icon> getListByType(String iconType){
		List<Icon> list = new ArrayList<Icon>();
		try {
			String sql = "SELECT * FROM ICON WHERE ICON_TYPE=? ORDER BY ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), iconType, sql);
			for(Map mp : lt){
				
				Icon icon = new Icon(SQLUtil.returnStr(mp, "ICON_NAME"), SQLUtil.returnStr(mp, "ICON_TYPE"), SQLUtil.returnInt(mp, "ICON_HEIGHT"), SQLUtil.returnInt(mp, "ICON_WIDTH"));
				list.add(icon);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 得到Icon
	 * @param iconId
	 * @return
	 */
	public Icon get(String iconName){
		Icon rm = new Icon();
		try {
			String sql = "SELECT * FROM ICON WHERE ICON_NAME=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), iconName, true, sql);
			if(htData != null){
				rm.setIconName(SQLUtil.returnStr(htData, "ICON_NAME"));
				rm.setIconWidth(SQLUtil.returnInt(htData, "ICON_WIDTH"));
				rm.setIconHeight(SQLUtil.returnInt(htData, "ICON_HEIGHT"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rm;
	}
	
	/**
	 * 插入
	 * @param icon
	 * @return
	 */
	public boolean insert(Icon icon){
		try {
			SQLUtil.insertSQL(DBConnection.getConnection(), "ICON", icon.fillMap());
			DBConnection.getConnection().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Icon icon){
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("ICON_WIDTH", icon.getIconWidth());
			htUpdateParam.put("ICON_HEIGHT", icon.getIconHeight());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("ICON_NAME", icon.getIconName());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "ICON", htUpdateParam, htKeyParam);
			DBConnection.getConnection().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(String iconName){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("ICON_NAME", iconName);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "ICON", htParam);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

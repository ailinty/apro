package com.joeysoft.kc868.db.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.IconFixed;

public class BOIconFixed {
	
	public List<IconFixed> getList(){
		List<IconFixed> list = new ArrayList<IconFixed>();
		try {
			String sql = "SELECT * FROM ICON_FIXED";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				
				IconFixed iconFixed = new IconFixed();
				iconFixed.setIconName(SQLUtil.returnStr(mp, "ICON_NAME"));
				iconFixed.setIconCode(SQLUtil.returnStr(mp, "ICON_CODE"));
				iconFixed.setIconHeight(SQLUtil.returnInt(mp, "ICON_HEIGHT"));
				iconFixed.setIconWidth(SQLUtil.returnInt(mp, "ICON_WIDTH"));
				
				list.add(iconFixed);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public IconFixed getByCodeId(String iconCode){
		IconFixed iconFixed = new IconFixed();
		try {
			String sql = "SELECT * FROM ICON_FIXED WHERE ICON_FIXED.ICON_CODE=?";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), iconCode, sql);
			if(lt != null && lt.size() > 0){
				Map mp = lt.get(0);
				iconFixed.setIconName(SQLUtil.returnStr(mp, "ICON_NAME"));
				iconFixed.setIconCode(SQLUtil.returnStr(mp, "ICON_CODE"));
				iconFixed.setIconHeight(SQLUtil.returnInt(mp, "ICON_HEIGHT"));
				iconFixed.setIconWidth(SQLUtil.returnInt(mp, "ICON_WIDTH"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iconFixed;
	}
	
	public String getIconNameByCodeId(String iconCode){
		IconFixed iconFixed = getByCodeId(iconCode);
		return iconFixed.getIconName();
	}
}

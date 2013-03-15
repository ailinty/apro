package com.joeysoft.kc868.db.bo;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.Threshold;
import com.joeysoft.kc868.db.bean.Unused;

public class BOUnused {
	
	private BOThreshold boThreshold = new BOThreshold();
	
	/**
	 * 判断是否超过阀值，超过返回true, 否则返回false
	 * @param next
	 * @return
	 */
	public boolean isThreshold(Unused next){
		String tableName = next.getTableName();
		int nextId = next.getNextUnused();
		if("RA".equals(next.getTableName()) || "RB".equals(next.getTableName()) 
				|| "RC".equals(next.getTableName()) ||"RD".equals(next.getTableName())){
			tableName = "SENSOR";
			
			if("RA".equals(next.getTableName())){
				nextId +=  get("RB").getMaxUnused() + get("RC").getMaxUnused() + get("RD").getMaxUnused();
			}else if("RB".equals(next.getTableName())){
				nextId +=  get("RA").getMaxUnused() + get("RC").getMaxUnused() + get("RD").getMaxUnused();
			}else if("RC".equals(next.getTableName())){
				nextId +=  get("RA").getMaxUnused() + get("RB").getMaxUnused() + get("RD").getMaxUnused();
			}else if("RC".equals(next.getTableName())){
				nextId +=  get("RA").getMaxUnused() + get("RB").getMaxUnused() + get("RC").getMaxUnused();
			}
		}
		
		Threshold threshold = boThreshold.get(tableName);
		
		if(nextId > threshold.getThreshold()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 得到Unused
	 * @param DBConnection.getConnection()
	 * @param floor
	 * @return
	 */
	public Unused get(String tableName){
		Unused un = new Unused();
		un.setTableName(tableName);
		un.setNew(true);
		try {
			String sql = "SELECT * FROM UNUSED WHERE TABLE_NAME=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), tableName, true, sql);
			if(htData != null && !htData.isEmpty()){
				un.setNew(false);
				un.setUnused(SQLUtil.returnStr(htData, "UNUSED"));
				un.setMaxUnused(SQLUtil.returnInt(htData, "MAXUNUSED"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return un;
	}
	
	/**
	 * 增加可用id，在表删除的时候调用此功能
	 * @param tableName
	 * @param id
	 * @return
	 */
	public boolean addUnused(String tableName, int id){
		Unused unused = get(tableName);
		if(StringUtils.isEmpty(unused.getUnused())){
			unused.setUnused(String.valueOf(id));
		}else{
			unused.setUnused(unused.getUnused() + "," + id);
		}
		return update(unused);
	}
	
	/**
	 * 更新可用编号
	 * @param DBConnection.getConnection()
	 * @param unused
	 * @return
	 */
	public boolean updateUnused(Unused unused){
		// 第一条记录，添加
		if(unused.isNew()){
			return insert(unused);
		}else{ // 更新
			return update(unused);
		}
	}
	
	/**
	 * 插入楼层
	 * @param DBConnection.getConnection()
	 * @param floorName
	 * @return
	 */
	private boolean insert(Unused unused){
		try {
			SQLUtil.insertSQL(DBConnection.getConnection(), "UNUSED", unused.fillMap());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean update(Unused unused){
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("UNUSED", unused.getUnused());
			htUpdateParam.put("MAXUNUSED", unused.getMaxUnused());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("TABLE_NAME", unused.getTableName());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "UNUSED", htUpdateParam, htKeyParam);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}

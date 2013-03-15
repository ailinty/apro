package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.SceneAction;

public class BOSceneAction {
	
	private BOUnused boUnused = new BOUnused();
	
	public List<SceneAction> getList(){
		List<SceneAction> list = new ArrayList<SceneAction>();
		try {
			String sql = "SELECT * FROM SCENE_ACTION ORDER BY ACTION_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				
				SceneAction alarm = new SceneAction(SQLUtil.returnInt(mp, "ACTION_ID"), SQLUtil.returnInt(mp, "SCENE_ID"), 
						SQLUtil.returnInt(mp, "FLOOR"), SQLUtil.returnInt(mp, "ROOM_ID"), SQLUtil.returnInt(mp, "DEVICE_ID"),
						SQLUtil.returnInt(mp, "DEVICE_KEY_ID"), SQLUtil.returnStr(mp, "SENSOR_TABLE"), SQLUtil.returnStr(mp, "SENSOR_ID"),
						SQLUtil.returnStr(mp, "SCENE_TEXT"));
				
				list.add(alarm);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<SceneAction> getListBySceneId(int sceneId){
		List<SceneAction> list = new ArrayList<SceneAction>();
		try {
			String sql = "SELECT * FROM SCENE_ACTION WHERE SCENE_ID = ? ORDER BY ACTION_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sceneId, sql);
			for(Map mp : lt){
				
				SceneAction action = new SceneAction(SQLUtil.returnInt(mp, "ACTION_ID"), SQLUtil.returnInt(mp, "SCENE_ID"), 
						SQLUtil.returnInt(mp, "FLOOR"), SQLUtil.returnInt(mp, "ROOM_ID"), SQLUtil.returnInt(mp, "DEVICE_ID"),
						SQLUtil.returnInt(mp, "DEVICE_KEY_ID"), SQLUtil.returnStr(mp, "SENSOR_TABLE"), SQLUtil.returnStr(mp, "SENSOR_ID"),
						SQLUtil.returnStr(mp, "SCENE_TEXT"));
				
				list.add(action);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 判断是否存在动作
	 * @param sceneId
	 * @param sensorTable
	 * @param sensorId
	 * @return
	 */
	public boolean isExistAction(int deviceId){
		try {
			String sql = "SELECT 1 FROM SCENE_ACTION WHERE SENSOR_ID IN(SELECT K.SENSOR_ID FROM DEVICE D, DEVICE_KEY K " +
					"WHERE D.DEVICE_ID = K.DEVICE_ID AND D.DEVICE_ID= ? )";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), deviceId, true, sql);
			if(htData != null && htData.size() > 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断是否存在动作
	 * @param sceneId
	 * @param sensorTable
	 * @param sensorId
	 * @return
	 */
	public boolean isExistAction(String sensorTable, String sensorId){
		try {
			String sql = "SELECT 1 FROM SCENE_ACTION WHERE SENSOR_TABLE = ? AND SENSOR_ID = ?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), sensorTable, sensorId, true, sql);
			if(htData != null && htData.size() > 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断是否存在动作
	 * @param sceneId
	 * @param sensorTable
	 * @param sensorId
	 * @return
	 */
	public boolean isExistAction(int sceneId, String sensorTable, String sensorId){
		try {
			String sql = "SELECT 1 FROM SCENE_ACTION WHERE SCENE_ID = ? AND SENSOR_TABLE = ? AND SENSOR_ID = ?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), sceneId, sensorTable, sensorId, true, sql);
			if(htData != null && htData.size() > 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 获取记录总数
	 * @param DBConnection.getConnection()
	 * @return
	 */
	public int getCount(){
		int count = 0;
		try {
			Map htData =  SQLUtil.getFirstRecord(DBConnection.getConnection(), null, true, "SELECT COUNT(*) AS COUNT FROM SCENE_ACTION");
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
	public SceneAction get(int actionId){
		SceneAction rm = new SceneAction();
		try {
			String sql = "SELECT * FROM SCENE_ACTION WHERE ACTION_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), actionId, true, sql);
			if(htData != null){
				rm.setActionId(actionId);
				rm.setSceneId(SQLUtil.returnInt(htData, "SCENE_ID"));
				rm.setDeviceId(SQLUtil.returnInt(htData, "DEVICE_ID"));
				rm.setDeviceKeyId(SQLUtil.returnInt(htData, "DEVICE_KEY_ID"));
				rm.setFloor(SQLUtil.returnInt(htData, "FLOOR"));
				rm.setRoomId(SQLUtil.returnInt(htData, "ROOM_ID"));
				rm.setSensorId(SQLUtil.returnStr(htData, "SENSOR_ID"));
				rm.setSensorTable(SQLUtil.returnStr(htData, "SENSOR_TABLE"));
				rm.setText(SQLUtil.returnStr(htData, "SCENE_TEXT"));
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
	 * @throws Exception 
	 */
	public void insert(SceneAction sceneAction) throws Exception{
		SQLUtil.insertSQL(DBConnection.getConnection(), "SCENE_ACTION", sceneAction.fillMap());
	}
	
	public boolean update(SceneAction action){
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("SCENE_ID", action.getSensorId());
			htUpdateParam.put("FLOOR", action.getFloor());
			htUpdateParam.put("ROOM_ID", action.getRoomId());
			htUpdateParam.put("DEVICE_ID", action.getDeviceId());
			htUpdateParam.put("DEVICE_KEY_ID", action.getDeviceKeyId());
			htUpdateParam.put("SENSOR_ID", action.getSensorId());
			htUpdateParam.put("SENSOR_TABLE", action.getSensorTable());
			htUpdateParam.put("SCENE_TEXT", action.getText());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("ACTION_ID", action.getActionId());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "SCENE_ACTION", htUpdateParam, htKeyParam);
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
	
	public boolean delete(int actionId){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("ACTION_ID", actionId);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SCENE_ACTION", htParam);
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
	
	public void deleteBySenceId(int senceId) throws Exception{
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("SCENE_ID", senceId);
		
		SQLUtil.deleteSQL(DBConnection.getConnection(), "SCENE_ACTION", htParam);
	}
}

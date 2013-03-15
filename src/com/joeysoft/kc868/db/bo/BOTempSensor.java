package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.TempSensor;
import com.joeysoft.kc868.db.bean.Threshold;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.util.UnusedUtil;
import com.joeysoft.kc868.exception.DataExistException;

public class BOTempSensor {
	
	private BOUnused boUnused = new BOUnused();
	private BOThreshold boThreshold = new BOThreshold();
	
	public List<TempSensor> getList(){
		List<TempSensor> list = new ArrayList<TempSensor>();
		try {
			String sql = "SELECT TEMP_SENSOR.*, FLOOR.*, ROOM.ROOM_NAME FROM TEMP_SENSOR, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = TEMP_SENSOR.ROOM_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				
				TempSensor tempSensor = new TempSensor(SQLUtil.returnInt(mp, "SENSOR_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), SQLUtil.returnStr(mp, "SENSOR_NAME"), 
						SQLUtil.returnInt(mp, "SENSOR_ADDR"), SQLUtil.returnInt(mp, "SENSOR_UPPER"), SQLUtil.returnInt(mp, "SENSOR_LOWER"),
						SQLUtil.returnStr(mp, "SENSOR_STUDY"), SQLUtil.returnStr(mp, "SENSOR_ALERT"),
						SQLUtil.returnInt(mp, "HUMIDITY_UPPER"), SQLUtil.returnInt(mp, "HUMIDITY_LOWER"));
				
				tempSensor.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				tempSensor.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				tempSensor.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				
				list.add(tempSensor);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<TempSensor> getListByRoomId(int roomId){
		List<TempSensor> list = new ArrayList<TempSensor>();
		try {
			String sql = "SELECT TEMP_SENSOR.*, FLOOR.*, ROOM.ROOM_NAME FROM TEMP_SENSOR, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR " +
					"AND ROOM.ROOM_ID = TEMP_SENSOR.ROOM_ID AND TEMP_SENSOR.ROOM_ID = ?";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), roomId, sql);
			for(Map mp : lt){
				
				TempSensor tempSensor = new TempSensor(SQLUtil.returnInt(mp, "SENSOR_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), SQLUtil.returnStr(mp, "SENSOR_NAME"), 
						SQLUtil.returnInt(mp, "SENSOR_ADDR"), SQLUtil.returnInt(mp, "SENSOR_UPPER"), SQLUtil.returnInt(mp, "SENSOR_LOWER"),
						SQLUtil.returnStr(mp, "SENSOR_STUDY"), SQLUtil.returnStr(mp, "SENSOR_ALERT"),
						SQLUtil.returnInt(mp, "HUMIDITY_UPPER"), SQLUtil.returnInt(mp, "HUMIDITY_LOWER"));
				
				tempSensor.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				tempSensor.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				tempSensor.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				
				list.add(tempSensor);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 得到TempSensor
	 * @param TempSensorId
	 * @return
	 */
	public TempSensor get(int sensorId){
		TempSensor rm = new TempSensor();
		try {
			String sql = "SELECT * FROM TEMP_SENSOR WHERE SENSOR_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), sensorId, true, sql);
			if(htData != null){
				rm.setSensorId(sensorId);
				rm.setRoomId(SQLUtil.returnInt(htData, "ROOM_ID"));
				rm.setSensorName(SQLUtil.returnStr(htData, "SENSOR_NAME"));
				rm.setSensorAddr(SQLUtil.returnInt(htData, "SENSOR_ADDR"));
				rm.setSensorUpper(SQLUtil.returnInt(htData, "SENSOR_UPPER"));
				rm.setSensorLower(SQLUtil.returnInt(htData, "SENSOR_LOWER"));
				rm.setSensorStudy(SQLUtil.returnStr(htData, "SENSOR_STUDY"));
				rm.setSensorAlert(SQLUtil.returnStr(htData, "SENSOR_ALERT"));
				
				rm.setSensorUpper(SQLUtil.returnInt(htData, "HUMIDITY_UPPER"));
				rm.setSensorLower(SQLUtil.returnInt(htData, "HUMIDITY_LOWER"));
				
				rm.setFloor(SQLUtil.returnInt(htData, "FLOOR"));
				rm.setFloorName(SQLUtil.returnStr(htData, "FLOOR_NAME"));
				rm.setRoomName(SQLUtil.returnStr(htData, "ROOM_NAME"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rm;
	}
	
	/**
	 * 判断是否存在地址码
	 * @param roomId
	 * @param sensorName
	 */
	public boolean isExistsAddr(int sensorAddr){
		try {
			String sql = "SELECT 1 FROM TEMP_SENSOR WHERE SENSOR_ADDR= ?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), sensorAddr, true, sql);
			if(htData != null && htData.size() > 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isValidAddr(int sensorAddr){
		if(isExistsAddr(sensorAddr)){
			return false;
		}else{
			Threshold Threshold = boThreshold.get("TEMP_SENSOR_ADDR");
			if(sensorAddr < 1 ||sensorAddr>Threshold.getThreshold()){
				return false;
			}
		}
		return true;
	}
	
	public Unused insertBefore(TempSensor tempSensor)throws Exception{
		Unused unused = boUnused.get("TEMP_SENSOR");
		
		Unused next = UnusedUtil.getNextUnused(unused);
		tempSensor.setSensorId(next.getNextUnused());
		if(!isValidAddr(tempSensor.getSensorAddr())){
			throw new DataExistException("地址码已经存在或不合法！");
		}
		tempSensor.setSensorStudy("Y");
		tempSensor.setSensorAlert("Y");
		
		return next;
	}
	
	/**
	 * 插入
	 * @param DBConnection.getConnection()
	 * @param floorName
	 * @return
	 * @throws Exception 
	 */
	public void insert(TempSensor tempSensor, Unused next) throws Exception{
		try {
			boUnused.updateUnused(next);
			
			SQLUtil.insertSQL(DBConnection.getConnection(), "TEMP_SENSOR", tempSensor.fillMap());
			//-----------------------------------------设置传感器参数-----------------------------
			
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
		} catch (Exception e) {
			DBConnection.getConnection().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	public void update(TempSensor tempSensor) throws Exception{
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("SENSOR_NAME", tempSensor.getSensorName());
			htUpdateParam.put("ROOM_ID", tempSensor.getRoomId());
			htUpdateParam.put("SENSOR_ADDR", tempSensor.getSensorAddr());
			htUpdateParam.put("SENSOR_UPPER", tempSensor.getSensorUpper());
			htUpdateParam.put("SENSOR_LOWER", tempSensor.getSensorLower());
			htUpdateParam.put("SENSOR_STUDY", tempSensor.getSensorStudy());
			htUpdateParam.put("HUMIDITY_UPPER", tempSensor.getHumidityUpper());
			htUpdateParam.put("HUMIDITY_LOWER", tempSensor.getHumidityLower());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("SENSOR_ID", tempSensor.getSensorId());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "TEMP_SENSOR", htUpdateParam, htKeyParam);
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
		} catch (Exception e) {
			DBConnection.getConnection().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 设置已学习
	 * @param sensorId
	 * @throws Exception
	 */
	public void updateStudyOK(int sensorId)throws Exception{
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("SENSOR_STUDY", "Y");
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("SENSOR_ID", sensorId);
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "TEMP_SENSOR", htUpdateParam, htKeyParam);
			
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
		} catch (Exception e) {
			DBConnection.getConnection().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 设置已预警
	 * @param sensorId
	 * @throws Exception
	 */
	public void updateAlertOK(int sensorId)throws Exception{
		try{
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("SENSOR_ALERT", "Y");
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("SENSOR_ID", sensorId);
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "TEMP_SENSOR", htUpdateParam, htKeyParam);
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
		} catch (Exception e) {
			DBConnection.getConnection().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	public void delete(int tempSensorId) throws Exception{
		try{
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("SENSOR_ID", tempSensorId);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "TEMP_SENSOR", htParam);
			
			// 收回主键
			boUnused.addUnused("TEMP_SENSOR", tempSensorId);
						
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
		} catch (Exception e) {
			DBConnection.getConnection().rollback();
			e.printStackTrace();
			throw e;
		}
	}
}

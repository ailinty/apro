package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.SensorIn;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.util.DataAddrCodeManager;
import com.joeysoft.kc868.db.util.DataAddrCodeUtil;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.db.util.SensorUtil;
import com.joeysoft.kc868.db.util.UnusedUtil;
import com.joeysoft.kc868.exception.DataExistException;

public class BOSensorIn {
	private BOUnused boUnused = new BOUnused();
	
	public List<SensorIn> getList(){
		List<SensorIn> list = new ArrayList<SensorIn>();
		try {
			String sql = "SELECT SENSOR_IN.*, FLOOR.*, ROOM.ROOM_NAME FROM SENSOR_IN, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = SENSOR_IN.ROOM_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				SensorIn sensorIn = new SensorIn(SQLUtil.returnStr(mp, "SENSOR_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), 
						SQLUtil.returnStr(mp, "SENSOR_NAME"), SQLUtil.returnStr(mp, "FREQ_TYPE"), SQLUtil.returnStr(mp, "CODE_TYPE"),
						SQLUtil.returnStr(mp, "RES_TYPE"), SQLUtil.returnInt(mp, "ADDR_CODE"), SQLUtil.returnInt(mp, "DATA_CODE"), 
						SQLUtil.returnStr(mp, "SENSOR_STUDY"), SQLUtil.returnStr(mp, "SENSOR_ALERT"));
				
				sensorIn.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				sensorIn.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				sensorIn.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				list.add(sensorIn);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<SensorIn> getListByRoomId(int roomId){
		List<SensorIn> list = new ArrayList<SensorIn>();
		try {
			String sql = "SELECT SENSOR_IN.*, FLOOR.*, ROOM.ROOM_NAME FROM SENSOR_IN, FLOOR, ROOM " +
					"WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = SENSOR_IN.ROOM_ID AND SENSOR_IN.ROOM_ID=?";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), roomId, sql);
			for(Map mp : lt){
				SensorIn sensorIn = new SensorIn(SQLUtil.returnStr(mp, "SENSOR_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), 
						SQLUtil.returnStr(mp, "SENSOR_NAME"), SQLUtil.returnStr(mp, "FREQ_TYPE"), SQLUtil.returnStr(mp, "CODE_TYPE"),
						SQLUtil.returnStr(mp, "RES_TYPE"), SQLUtil.returnInt(mp, "ADDR_CODE"), SQLUtil.returnInt(mp, "DATA_CODE"), 
						SQLUtil.returnStr(mp, "SENSOR_STUDY"), SQLUtil.returnStr(mp, "SENSOR_ALERT"));
				
				sensorIn.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				sensorIn.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				sensorIn.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				list.add(sensorIn);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 得到WirelessSecut
	 * @param sensorInId
	 * @return
	 */
	public SensorIn get(String sensorId){
		SensorIn rm = new SensorIn();
		try {
			String sql = "SELECT SENSOR_IN.*, FLOOR.*, ROOM.ROOM_NAME FROM SENSOR_IN, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = SENSOR_IN.ROOM_ID AND SENSOR_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), sensorId, true, sql);
			if(htData != null){
				rm.setSensorId(sensorId);
				rm.setRoomId(SQLUtil.returnInt(htData, "ROOM_ID"));
				rm.setSensorName(SQLUtil.returnStr(htData, "SENSOR_NAME"));
				rm.setFreqType(SQLUtil.returnStr(htData, "FREQ_TYPE"));
				rm.setCodeType(SQLUtil.returnStr(htData, "CODE_TYPE"));
				rm.setResType(SQLUtil.returnStr(htData, "RES_TYPE"));
				rm.setAddrCode(SQLUtil.returnInt(htData, "ADDR_CODE"));
				rm.setDataCode(SQLUtil.returnInt(htData, "DATA_CODE"));
				
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
	 * 判断房间是否存在sensorName
	 * @param roomId
	 * @param sensorName
	 */
	public boolean isExistByName(int roomId, String sensorName){
		try {
			String sql = "SELECT SENSOR_IN.*, FLOOR.*, ROOM.ROOM_NAME FROM SENSOR_IN, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = SENSOR_IN.ROOM_ID " +
					"AND SENSOR_IN.ROOM_ID=? AND SENSOR_IN.SENSOR_NAME=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), roomId, sensorName, true, sql);
			if(htData != null && htData.size() > 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 
	 * @param sensorIn
	 * @return
	 * @throws Exception
	 */
	public Unused insertBefore(SensorIn sensorIn)throws Exception{
		if(isExistByName(sensorIn.getRoomId(), sensorIn.getSensorName())){
			throw new DataExistException("已经存在名称为"+sensorIn.getSensorName()+"的无线输入设备");
		}
		
		// 取无线的编号前缀[SRA,SRB,SRC,SRD]
		String tableName = SensorUtil.getSensorCodeIn(sensorIn.getFreqType(), sensorIn.getCodeType());
				
		Unused unused = boUnused.get(tableName);
		
		Unused next = UnusedUtil.getNextUnused(unused);
		sensorIn.setSensorId(tableName + next.getNextUnused());
		return next;
	}
	
	/**
	 * 插入
	 * @param DBConnection.getConnection()
	 * @param floorName
	 * @return
	 * @throws Exception 
	 */
	public void insert(SensorIn sensorIn, Unused next) throws Exception{
		try {
			
			SQLUtil.insertSQL(DBConnection.getConnection(), "SENSOR_IN", sensorIn.fillMap());
			
			boUnused.updateUnused(next);
			
			// 判断地址码是否存在
			if(DataAddrCodeManager.getInstance().isContains(sensorIn.getResType(), sensorIn.getAddrCode(), sensorIn.getDataCode())){
				throw new DataExistException("此地址码已经存在，建议使用" + DataAddrCodeUtil.getUnusedAddrCode(sensorIn.getCodeType(), sensorIn.getResType()));
			}
			
			// 把地址码加入缓存
			DataAddrCodeManager.getInstance().addDataAddrCode(sensorIn.getResType(), sensorIn.getAddrCode(), sensorIn.getDataCode());
						
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
		} catch (SQLException e) {
			DBConnection.getConnection().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 插入
	 * @param DBConnection.getConnection()
	 * @param floorName
	 * @return
	 * @throws Exception 
	 */
	public void insertRFSensor(SensorIn sensorIn, Unused next) throws Exception{
		try {
			
			SQLUtil.insertSQL(DBConnection.getConnection(), "SENSOR_IN", sensorIn.fillMap());
			
			boUnused.updateUnused(next);
			
			// 判断地址码是否存在
			/*if(DataAddrCodeManager.getInstance().isContains(sensorIn.getResType(), sensorIn.getAddrCode(), sensorIn.getDataCode())){
				throw new DataExistException("此地址码已经存在，建议使用" + DataAddrCodeUtil.getUnusedAddrCode(sensorIn.getCodeType(), sensorIn.getResType()));
			}*/
			
			// 把地址码加入缓存
			//DataAddrCodeManager.getInstance().addDataAddrCode(sensorIn.getResType(), sensorIn.getAddrCode(), sensorIn.getDataCode());
						
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
		} catch (SQLException e) {
			DBConnection.getConnection().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	public void update(SensorIn sensorIn) throws Exception{
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("SENSOR_NAME", sensorIn.getSensorName());
			htUpdateParam.put("ROOM_ID", sensorIn.getRoomId());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("SENSOR_ID", sensorIn.getSensorId());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "SENSOR_IN", htUpdateParam, htKeyParam);
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
		} catch (SQLException e) {
			DBConnection.getConnection().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	public void delete(SensorIn sensorIn) throws Exception{
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("SENSOR_ID", sensorIn.getSensorId());
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SENSOR_IN", htParam);
			
			// 收回资源
			DataAddrCodeManager.getInstance().removeDataAddrCode(sensorIn.getResType(), sensorIn.getAddrCode(), sensorIn.getDataCode());
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

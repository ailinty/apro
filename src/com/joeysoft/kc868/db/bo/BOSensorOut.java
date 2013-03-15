package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.Device;
import com.joeysoft.kc868.db.bean.DeviceKey;
import com.joeysoft.kc868.db.bean.Key;
import com.joeysoft.kc868.db.bean.SensorOut;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.util.DataAddrCodeManager;
import com.joeysoft.kc868.db.util.DataAddrCodeUtil;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.db.util.KeyCodeUtil;
import com.joeysoft.kc868.db.util.SensorUtil;
import com.joeysoft.kc868.db.util.UnusedUtil;
import com.joeysoft.kc868.exception.DataExistException;
import com.joeysoft.kc868.exception.ThresholdException;

/**
 * 无线输出非常规设备[RA,RB,RC,RD]
 * @author JOEY
 *
 */
public class BOSensorOut {
	
	private BOUnused boUnused = new BOUnused();
	
	/**
	 * 获取所有非常规设备记录
	 * @return
	 */
	public List<SensorOut> getList(){
		List<SensorOut> list = new ArrayList<SensorOut>();
		try {
			StringBuffer sb = new StringBuffer(200);
			sb.append("SELECT SENSOR.*, ROOM.*, FLOOR.* FROM SENSOR, ROOM, FLOOR");
			sb.append(" WHERE SENSOR.ROOM_ID = ROOM.ROOM_ID AND ROOM.FLOOR = FLOOR.FLOOR");
			sb.append(" ORDER BY POSITION DESC");
			
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sb.toString());
			for(Map mp : lt){
				SensorOut sensorOut = new SensorOut();
				sensorOut.setSensorId(SQLUtil.returnStr(mp, "SENSOR_ID"));
				sensorOut.setSensorName(SQLUtil.returnStr(mp, "SENSOR_NAME"));
				sensorOut.setAddrCode(SQLUtil.returnInt(mp, "ADDR_CODE"));
				sensorOut.setCodeType(SQLUtil.returnStr(mp, "CODE_TYPE"));
				sensorOut.setDataCode(SQLUtil.returnInt(mp, "DATA_CODE"));
				sensorOut.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				sensorOut.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				sensorOut.setFreqType(SQLUtil.returnStr(mp, "FREQ_TYPE"));
				sensorOut.setIconName(SQLUtil.returnStr(mp, "ICON_NAME"));
				sensorOut.setResType(SQLUtil.returnStr(mp, "RES_TYPE"));
				sensorOut.setRoomId(SQLUtil.returnInt(mp, "ROOM_ID"));
				sensorOut.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				sensorOut.setIconName(SQLUtil.returnStr(mp, "ICON_NAME"));
				
				sensorOut.setPosition(SQLUtil.returnInt(mp, "POSITION"));
				list.add(sensorOut);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取房间里的非常规设备
	 * @return
	 */
	public List<SensorOut> getListByRoomId(int roomId){
		List<SensorOut> list = new ArrayList<SensorOut>();
		try {
			StringBuffer sb = new StringBuffer(200);
			sb.append("SELECT SENSOR.*, ROOM.*, FLOOR.* FROM SENSOR, ROOM, FLOOR");
			sb.append(" WHERE SENSOR.ROOM_ID = ROOM.ROOM_ID AND ROOM.FLOOR = FLOOR.FLOOR");
			sb.append(" AND SENSOR.ROOM_ID = ? ");
			sb.append(" ORDER BY POSITION DESC");
			
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), roomId, sb.toString());
			for(Map mp : lt){
				SensorOut sensorOut = new SensorOut();
				sensorOut.setSensorId(SQLUtil.returnStr(mp, "SENSOR_ID"));
				sensorOut.setSensorName(SQLUtil.returnStr(mp, "SENSOR_NAME"));
				sensorOut.setAddrCode(SQLUtil.returnInt(mp, "ADDR_CODE"));
				sensorOut.setCodeType(SQLUtil.returnStr(mp, "CODE_TYPE"));
				sensorOut.setDataCode(SQLUtil.returnInt(mp, "DATA_CODE"));
				sensorOut.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				sensorOut.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				sensorOut.setFreqType(SQLUtil.returnStr(mp, "FREQ_TYPE"));
				sensorOut.setIconName(SQLUtil.returnStr(mp, "ICON_NAME"));
				sensorOut.setResType(SQLUtil.returnStr(mp, "RES_TYPE"));
				sensorOut.setRoomId(SQLUtil.returnInt(mp, "ROOM_ID"));
				sensorOut.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				sensorOut.setIconName(SQLUtil.returnStr(mp, "ICON_NAME"));
				
				sensorOut.setPosition(SQLUtil.returnInt(mp, "POSITION"));
				
				list.add(sensorOut);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 得到Sensor
	 * @param sensorId
	 * @return
	 */
	public SensorOut get(String sensorId){
		SensorOut fl = new SensorOut();
		try {
			String sql = "SELECT SENSOR.* FLOOR.*, ROOM.ROOM_NAME FROM SENSOR, FLOOR, ROOM" +
					" WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = SENSOR.ROOM_ID AND SENSOR.SENSOR_ID=?" +
					" ORDER BY POSITION DESC";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), sensorId, true, sql);
			if(htData != null){
				fl.setSensorId(sensorId);
				fl.setSensorName(SQLUtil.returnStr(htData, "SENSOR_NAME"));
				fl.setRoomId(SQLUtil.returnInt(htData, "ROOM_ID"));
				fl.setCodeType(SQLUtil.returnStr(htData, "CODE_TYPE"));
				fl.setDataCode(SQLUtil.returnInt(htData, "DATA_CODE"));
				fl.setFreqType(SQLUtil.returnStr(htData, "FREQ_TYPE"));
				fl.setResType(SQLUtil.returnStr(htData, "RES_TYPE"));
				fl.setAddrCode(SQLUtil.returnInt(htData, "ADDR_CODE"));
				fl.setIconName(SQLUtil.returnStr(htData, "ICON_NAME"));
				fl.setFloor(SQLUtil.returnInt(htData, "FLOOR"));
				fl.setFloorName(SQLUtil.returnStr(htData, "FLOOR_NAME"));
				fl.setRoomName(SQLUtil.returnStr(htData, "ROOM_NAME"));
				fl.setPosition(SQLUtil.returnInt(htData, "POSITION"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fl;
	}
	
	/**
	 * 判断房间是否存在非常规设备
	 * @param roomId
	 * @return
	 */
	public boolean isExistsByRoomId(int roomId){
		try {
			String sql = "SELECT SENSOR.*, FLOOR.*, ROOM.ROOM_NAME FROM SENSOR, FLOOR, ROOM" +
					" WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = SENSOR.ROOM_ID AND SENSOR.ROOM_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), roomId, true, sql);
			if(htData != null && htData.size() > 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 判断房间是否存在sensorName
	 * @param roomId
	 * @param sensorName
	 */
	public boolean isExistByName(int roomId, String sensorName){
		try {
			String sql = "SELECT SENSOR.*, FLOOR.*, ROOM.ROOM_NAME FROM SENSOR, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = SENSOR.ROOM_ID " +
					"AND SENSOR.ROOM_ID=? AND SENSOR.SENSOR_NAME=?";
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
	 * 补全代码，主要指地址码、数据码
	 * @param sensor
	 */
	public Unused insertBefore(SensorOut sensorOut) throws Exception{
		
		if(isExistByName(sensorOut.getRoomId(), sensorOut.getSensorName())){
			throw new DataExistException("已经存在名称为"+sensorOut.getSensorName()+"的无线输出设备");
		}
		int addrCode = -1;
		int dataCode = -1;
		
		if(sensorOut.getAddrCode() == -1 && sensorOut.getDataCode()== -1){ //自动生成
			addrCode = DataAddrCodeUtil.getUnusedAddrCode(sensorOut.getCodeType(), sensorOut.getResType());
			dataCode = DataAddrCodeUtil.getUnusedDataCode(sensorOut.getCodeType(), sensorOut.getResType(), addrCode);
		}else{// 输入了地址码、数据码
			addrCode = sensorOut.getAddrCode();
			dataCode = sensorOut.getDataCode();
			if(DataAddrCodeManager.getInstance().isContains(sensorOut.getResType(), addrCode, dataCode)){
				throw new DataExistException("此地址码、数据码已经存在，建议使用" + DataAddrCodeUtil.getUnusedAddrCode(sensorOut.getCodeType(), sensorOut.getResType()));
			}
		}
		
		sensorOut.setAddrCode(addrCode);
		sensorOut.setDataCode(dataCode);
		
		// 取无线的编号前缀[RA,RB,RC,RD]
		String tableName = SensorUtil.getSensorCodeOut(sensorOut.getFreqType(), sensorOut.getCodeType());
			
		Unused unused = boUnused.get(tableName);
		
		Unused next = UnusedUtil.getNextUnused(unused);
		boolean isThreshold = boUnused.isThreshold(next);
		if(isThreshold){
			throw new ThresholdException();
		}
		// 编号前缀[RA,RB,RC,RD] 加编号
		sensorOut.setSensorId(tableName + next.getNextUnused());
		
		return next;
	}
	
	/**
	 * 插入楼层
	 * @param DBConnection.getConnection()
	 * @param sensorName
	 * @return
	 * @throws SQLException 
	 */
	public void insert(SensorOut sensorOut, Unused next) throws Exception{
		try{
			//----------------------------------无线电添加-----------------------------------------
			
			SQLUtil.insertSQL(DBConnection.getConnection(), "SENSOR", sensorOut.fillMap());
			
			boUnused.updateUnused(next);
			// 缓存地址码、数据码
			DataAddrCodeManager.getInstance().addDataAddrCode(sensorOut.getResType(), sensorOut.getAddrCode(), sensorOut.getDataCode());
				
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
		} catch (Exception e) {
			DBConnection.getConnection().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	public void update(SensorOut sensorOut) throws Exception{
		Map<String, Object> htUpdateParam = new HashMap<String, Object>();
		htUpdateParam.put("ROOM_ID", sensorOut.getRoomId());
		htUpdateParam.put("SENSOR_NAME", sensorOut.getSensorName());
		htUpdateParam.put("ICON_NAME", sensorOut.getIconName());
		htUpdateParam.put("FREQ_TYPE", sensorOut.getFreqType());
		htUpdateParam.put("CODE_TYPE", sensorOut.getCodeType());
		htUpdateParam.put("RES_TYPE", sensorOut.getResType());
		htUpdateParam.put("ADDR_CODE", sensorOut.getAddrCode());
		htUpdateParam.put("DATA_CODE", sensorOut.getDataCode());
		
		htUpdateParam.put("POSITION", sensorOut.getPosition());
		
		Map<String, Object> htKeyParam = new HashMap<String, Object>();
		htKeyParam.put("SENSOR_ID", sensorOut.getSensorId());
		
		SQLUtil.updateSQL(DBConnection.getConnection(), "SENSOR", htUpdateParam, htKeyParam);
		try {
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
		} catch (SQLException e) {
			e.printStackTrace();
			DBConnection.getConnection().rollback();
			throw e;
		}
	}
	
	public void delete(SensorOut sensor) throws Exception{
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("SENSOR_ID", sensor.getSensorId());
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SENSOR", htParam);
			// 收回地址码、数据码
			DataAddrCodeManager.getInstance().removeDataAddrCode(sensor.getResType(), sensor.getAddrCode(), sensor.getDataCode());
			
			// 收回主键
			boUnused.addUnused(sensor.getSensorId().substring(0,2), Integer.valueOf(sensor.getSensorId().substring(2)));
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

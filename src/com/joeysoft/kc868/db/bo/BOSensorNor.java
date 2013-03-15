package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.SensorNor;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.util.DataAddrCodeManager;
import com.joeysoft.kc868.db.util.DataAddrCodeUtil;
import com.joeysoft.kc868.db.util.SensorUtil;
import com.joeysoft.kc868.db.util.UnusedUtil;
import com.joeysoft.kc868.exception.DataExistException;
import com.joeysoft.kc868.exception.ThresholdException;

/**
 * 无线输出非常规设备[RA,RB,RC,RD]
 * @author JOEY
 *
 */
public class BOSensorNor {
	
	private BOUnused boUnused = new BOUnused();
	
	/**
	 * 获取所有非常规设备记录
	 * @return
	 */
	public List<SensorNor> getList(){
		List<SensorNor> list = new ArrayList<SensorNor>();
		try {
			StringBuffer sb = new StringBuffer(200);
			sb.append("SELECT SENSOR_NOR.*, ROOM.*, FLOOR.* FROM SENSOR_NOR, ROOM, FLOOR");
			sb.append(" WHERE SENSOR_NOR.ROOM_ID = ROOM.ROOM_ID AND ROOM.FLOOR = FLOOR.FLOOR");
			
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sb.toString());
			for(Map mp : lt){
				SensorNor sensorNor = new SensorNor();
				sensorNor.setSensorId(SQLUtil.returnStr(mp, "SENSOR_ID"));
				sensorNor.setRoomId(SQLUtil.returnInt(mp, "ROOM_ID"));
				sensorNor.setAddrCode(SQLUtil.returnInt(mp, "ADDR_CODE"));
				sensorNor.setCodeType(SQLUtil.returnStr(mp, "CODE_TYPE"));
				sensorNor.setDataCode(SQLUtil.returnInt(mp, "DATA_CODE"));
				sensorNor.setFreqType(SQLUtil.returnStr(mp, "FREQ_TYPE"));
				sensorNor.setResType(SQLUtil.returnStr(mp, "RES_TYPE"));
				list.add(sensorNor);
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
	public SensorNor get(String sensorId){
		SensorNor fl = new SensorNor();
		try {
			String sql = "SELECT SENSOR_NOR.*, FLOOR.*, ROOM.ROOM_NAME FROM SENSOR_NOR, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = SENSOR_NOR.ROOM_ID AND SENSOR_NOR.SENSOR_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), sensorId, true, sql);
			if(htData != null){
				fl.setSensorId(sensorId);
				fl.setRoomId(SQLUtil.returnInt(htData, "ROOM_ID"));
				fl.setCodeType(SQLUtil.returnStr(htData, "CODE_TYPE"));
				fl.setDataCode(SQLUtil.returnInt(htData, "DATA_CODE"));
				fl.setFreqType(SQLUtil.returnStr(htData, "FREQ_TYPE"));
				fl.setResType(SQLUtil.returnStr(htData, "RES_TYPE"));
				fl.setAddrCode(SQLUtil.returnInt(htData, "ADDR_CODE"));
				fl.setFloor(SQLUtil.returnInt(htData, "FLOOR"));
				fl.setFloorName(SQLUtil.returnStr(htData, "FLOOR_NAME"));
				fl.setRoomName(SQLUtil.returnStr(htData, "ROOM_NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fl;
	}
	
	/**
	 * 插入楼层
	 * @param DBConnection.getConnection()
	 * @param sensorName
	 * @return
	 * @throws SQLException 
	 */
	public void insert(SensorNor sensorNor, Unused next) throws Exception{
		//----------------------------------无线电添加-----------------------------------------
		
		SQLUtil.insertSQL(DBConnection.getConnection(), "SENSOR_NOR", sensorNor.fillMap());
		
		boUnused.updateUnused(next);
		// 缓存地址码、数据码
		DataAddrCodeManager.getInstance().addDataAddrCode(sensorNor.getResType(), sensorNor.getAddrCode(), sensorNor.getDataCode());
	}
	
	
	public boolean delete(SensorNor sensor) throws Exception{
		try {
			DBConnection.getConnection().setAutoCommit(false);
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("SENSOR_ID", sensor.getSensorId());
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "SENSOR_NOR", htParam);
			// 收回地址码、数据码
			DataAddrCodeManager.getInstance().removeDataAddrCode(sensor.getResType(), sensor.getAddrCode(), sensor.getDataCode());
			
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			DBConnection.getConnection().rollback();
			e.printStackTrace();
		}
		return false;
	}
}

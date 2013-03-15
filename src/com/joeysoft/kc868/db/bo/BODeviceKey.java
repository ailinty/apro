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
import com.joeysoft.kc868.db.bean.DeviceKey;
import com.joeysoft.kc868.db.bean.vo.SensorVo;
import com.joeysoft.kc868.db.util.DataAddrCodeManager;

public class BODeviceKey {
	
	private BOUnused boUnused = new BOUnused();
	
	public List<DeviceKey> getList(){
		List<DeviceKey> list = new ArrayList<DeviceKey>();
		try {
			String sql = "SELECT DEVICE_KEY.*, DEVICE.ROOM_ID FROM DEVICE_KEY, DEVICE WHERE DEVICE_KEY.DEVICE_ID = DEVICE.DEVICE_ID ORDER BY DEVICE_KEY.DEVICE_KEY_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				DeviceKey deviceKey = new DeviceKey(SQLUtil.returnInt(mp, "DEVICE_KEY_ID"), SQLUtil.returnInt(mp, "DEVICE_ID"), 
						SQLUtil.returnStr(mp, "KEY_POINT"), SQLUtil.returnStr(mp, "SENSOR_TABLE"), SQLUtil.returnStr(mp, "SENSOR_ID"), 
						SQLUtil.returnStr(mp, "KEY_NAME"),  SQLUtil.returnStr(mp, "KEY_TYPE"), SQLUtil.returnStr(mp, "ICON_NAME"));
				list.add(deviceKey);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<DeviceKey> getListByDeviceId(int deviceId){
		List<DeviceKey> list = new ArrayList<DeviceKey>();
		try {
			String sql = "SELECT DEVICE_KEY.*, DEVICE.ROOM_ID FROM DEVICE_KEY, DEVICE WHERE DEVICE_KEY.DEVICE_ID = DEVICE.DEVICE_ID " +
					"AND DEVICE_KEY.DEVICE_ID=? ORDER BY DEVICE_KEY.DEVICE_KEY_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), deviceId, sql);
			for(Map mp : lt){
				DeviceKey deviceKey = new DeviceKey(SQLUtil.returnInt(mp, "DEVICE_KEY_ID"), SQLUtil.returnInt(mp, "DEVICE_ID"), 
						SQLUtil.returnStr(mp, "KEY_POINT"), SQLUtil.returnStr(mp, "SENSOR_TABLE"), SQLUtil.returnStr(mp, "SENSOR_ID"), 
						SQLUtil.returnStr(mp, "KEY_NAME"),  SQLUtil.returnStr(mp, "KEY_TYPE"), SQLUtil.returnStr(mp, "ICON_NAME"));
				deviceKey.setRoomId(SQLUtil.returnInt(mp, "ROOM_ID"));
				list.add(deviceKey);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 有效的按钮
	 * @param deviceId
	 * @return
	 */
	public List<DeviceKey> getListValidByDeviceId(int deviceId){
		List<DeviceKey> list = new ArrayList<DeviceKey>();
		try {
			String sql = "SELECT DEVICE_KEY.*, DEVICE.ROOM_ID FROM DEVICE_KEY, DEVICE WHERE DEVICE_KEY.DEVICE_ID = DEVICE.DEVICE_ID " +
					"AND DEVICE_KEY.SENSOR_ID IS NOT NULL " + 
					"AND DEVICE_KEY.DEVICE_ID=? ORDER BY DEVICE_KEY.DEVICE_KEY_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), deviceId, sql);
			for(Map mp : lt){
				DeviceKey deviceKey = new DeviceKey(SQLUtil.returnInt(mp, "DEVICE_KEY_ID"), SQLUtil.returnInt(mp, "DEVICE_ID"), 
						SQLUtil.returnStr(mp, "KEY_POINT"), SQLUtil.returnStr(mp, "SENSOR_TABLE"), SQLUtil.returnStr(mp, "SENSOR_ID"), 
						SQLUtil.returnStr(mp, "KEY_NAME"),  SQLUtil.returnStr(mp, "KEY_TYPE"), SQLUtil.returnStr(mp, "ICON_NAME"));
				deviceKey.setRoomId(SQLUtil.returnInt(mp, "ROOM_ID"));
				list.add(deviceKey);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<DeviceKey> getListBySensorTableTansferId(int transferId){
		List<DeviceKey> list = new ArrayList<DeviceKey>();
		try {
			String sql = "SELECT DEVICE_KEY.* FROM DEVICE_KEY, TRANSFER, TSF_SENSOR" +
					" WHERE TRANSFER.TRANSFER_ID = TSF_SENSOR.TRANSFER_ID" +
					" AND DEVICE_KEY.SENSOR_TABLE = 'TSF_SENSOR' AND DEVICE_KEY.SENSOR_ID = TSF_SENSOR.SENSOR_ID" +
					" AND TSF_SENSOR.TRANSFER_ID=?" +
					" ORDER BY DEVICE_KEY.DEVICE_KEY_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), transferId, sql);
			for(Map mp : lt){
				DeviceKey deviceKey = new DeviceKey(SQLUtil.returnInt(mp, "DEVICE_KEY_ID"), SQLUtil.returnInt(mp, "DEVICE_ID"), 
						SQLUtil.returnStr(mp, "KEY_POINT"), SQLUtil.returnStr(mp, "SENSOR_TABLE"), SQLUtil.returnStr(mp, "SENSOR_ID"), 
						SQLUtil.returnStr(mp, "KEY_NAME"),  SQLUtil.returnStr(mp, "KEY_TYPE"), SQLUtil.returnStr(mp, "ICON_NAME"));
				list.add(deviceKey);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 得到DeviceKey
	 * @param DBConnection.getConnection()
	 * @param deviceKey
	 * @return
	 */
	public DeviceKey get(Integer deviceKeyId){
		DeviceKey fl = new DeviceKey();
		try {
			String sql = "SELECT DEVICE_KEY.*, DEVICE.ROOM_ID FROM DEVICE_KEY, DEVICE WHERE DEVICE_KEY.DEVICE_ID = DEVICE.DEVICE_ID AND DEVICE_KEY.DEVICE_KEY_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), deviceKeyId, true, sql);
			if(htData != null){
				fl.setDeviceKeyId(deviceKeyId);
				fl.setDeviceId(SQLUtil.returnInt(htData, "DEVICE_ID"));
				fl.setKeyPoint(SQLUtil.returnStr(htData, "KEY_POINT"));
				fl.setSensorId(SQLUtil.returnStr(htData, "SENSOR_ID"));
				fl.setSensorTable(SQLUtil.returnStr(htData, "SENSOR_TABLE"));
				fl.setKeyName(SQLUtil.returnStr(htData, "KEY_NAME"));
				fl.setKeyType(SQLUtil.returnStr(htData, "KEY_TYPE"));
				fl.setIconName(SQLUtil.returnStr(htData, "ICON_NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fl;
	}
	
	/**
	 * 插入楼层
	 * @param DBConnection.getConnection()
	 * @param deviceKeyName
	 * @return
	 */
	public boolean insert(DeviceKey deviceKey){
		try {
			SQLUtil.insertSQL(DBConnection.getConnection(), "DEVICE_KEY", deviceKey.fillMap());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void updateSensor(DeviceKey deviceKey) throws Exception{
		Map<String, Object> htUpdateParam = new HashMap<String, Object>();
		htUpdateParam.put("SENSOR_TABLE", deviceKey.getSensorTable());
		htUpdateParam.put("SENSOR_ID", deviceKey.getSensorId());
		
		Map<String, Object> htKeyParam = new HashMap<String, Object>();
		htKeyParam.put("DEVICE_KEY_ID", deviceKey.getDeviceKeyId());
		
		SQLUtil.updateSQL(DBConnection.getConnection(), "DEVICE_KEY", htUpdateParam, htKeyParam);
	}
	
	public boolean update(DeviceKey deviceKey){
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("KEY_POINT", deviceKey.getKeyPoint());
			htUpdateParam.put("KEY_NAME", deviceKey.getKeyName());
			htUpdateParam.put("KEY_TYPE", deviceKey.getKeyType());
			htUpdateParam.put("ICON_NAME", deviceKey.getIconName());
			htUpdateParam.put("SENSOR_TABLE", deviceKey.getSensorTable());
			htUpdateParam.put("SENSOR_ID", deviceKey.getSensorId());
			
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("DEVICE_KEY_ID", deviceKey.getDeviceKeyId());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "DEVICE_KEY", htUpdateParam, htKeyParam);
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(int deviceKeyId){
		try {
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("DEVICE_KEY", deviceKeyId);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "DEVICE_KEY", htParam);
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
	
	/**
	 * 删除所有的deviceId的按键，删除sensor表，移除地址码、数据码缓存
	 * @param deviceId
	 * @return
	 */
	public boolean deleteByDeviceId(int deviceId){
		try {
			// 删除按键对应的sensor
			List<DeviceKey> listKey = getListByDeviceId(deviceId);
			for(DeviceKey key : listKey){
				if(StringUtils.isNotEmpty(key.getSensorId()) && StringUtils.isNotEmpty(key.getSensorTable())){
					// 得到sensor记录
					deleteSensor(key.getSensorTable(), key.getSensorId());
				}
			}
			
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("DEVICE_ID", deviceId);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "DEVICE_KEY", htParam);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 按senosrTable表与sensorId删除相应的sensor记录
	 * @param senosrId
	 * @param sensorTable
	 * @return
	 * @throws Exception 
	 */
	public void deleteSensor(String sensorTable, String senosrId) throws Exception{
		SensorVo sensor = getSensor(sensorTable, senosrId);
		
		if(sensor != null){
			// 移除数据码、地址码
			DataAddrCodeManager.getInstance().removeDataAddrCode(sensor.getResType(), sensor.getAddrCode(), sensor.getDataCode());
			// 收回主键
			boUnused.addUnused(senosrId.substring(0,2), Integer.valueOf(senosrId.substring(2)));
			
			// 删除sensor
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("SENSOR_ID", senosrId);
			SQLUtil.deleteSQL(DBConnection.getConnection(), sensorTable, htParam);
		}
	}
	
	/**
	 * 按senosrTable表与sensorId得到相应的sensor记录
	 * @param senosrId
	 * @param sensorTable
	 * @return
	 */
	public SensorVo getSensor(String sensorTable, String senosrId){
		SensorVo fl = null;
		try {
			String sql = "SELECT * FROM "+sensorTable+" WHERE SENSOR_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), senosrId, true, sql);
			if(htData != null){
				fl = new SensorVo();
				fl.setSensorId(SQLUtil.returnStr(htData, "SENSOR_ID"));
				fl.setAddrCode(SQLUtil.returnInt(htData, "ADDR_CODE"));
				fl.setDataCode(SQLUtil.returnInt(htData, "DATA_CODE"));
				fl.setResType(SQLUtil.returnStr(htData, "RES_TYPE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fl;
	}
}

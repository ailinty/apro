package com.joeysoft.kc868.db.bo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joeysoft.kc868.SystemConfig;
import com.joeysoft.kc868.db.DBConnection;
import com.joeysoft.kc868.db.SQLUtil;
import com.joeysoft.kc868.db.bean.Device;
import com.joeysoft.kc868.db.bean.DeviceKey;
import com.joeysoft.kc868.db.bean.IconFixed;
import com.joeysoft.kc868.db.bean.SensorNor;
import com.joeysoft.kc868.db.bean.SensorOut;
import com.joeysoft.kc868.db.bean.Unused;
import com.joeysoft.kc868.db.bean.Vidicon;
import com.joeysoft.kc868.db.bean.vo.DeviceLtWnCl;
import com.joeysoft.kc868.db.util.DataAddrCodeManager;
import com.joeysoft.kc868.db.util.DataAddrCodeUtil;
import com.joeysoft.kc868.db.util.DictConst;
import com.joeysoft.kc868.db.util.SensorUtil;
import com.joeysoft.kc868.db.util.UnusedUtil;
import com.joeysoft.kc868.exception.DataExistException;
import com.joeysoft.kc868.exception.ThresholdException;

public class BODevice {
	private static Logger logger = LoggerFactory.getLogger(BODevice.class);
	private BOIconFixed boIconFixed = new BOIconFixed();
	private BODeviceKey boDeviceKey = new BODeviceKey();
	private BOSensorNor boSensorNor = new BOSensorNor();
	private BOVidicon boVidicon = new BOVidicon();
	
	private BOUnused boUnused = new BOUnused();
	
	public List<Device> getList(){
		List<Device> list = new ArrayList<Device>();
		try {
			String sql = "SELECT DEVICE.*, FLOOR.*, ROOM.ROOM_NAME FROM DEVICE, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = DEVICE.ROOM_ID";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), sql);
			for(Map mp : lt){
				
				Device device = new Device(SQLUtil.returnInt(mp, "DEVICE_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), 
						SQLUtil.returnStr(mp, "DEVICE_NAME"), SQLUtil.returnStr(mp, "DEVICE_TYPE"), SQLUtil.returnStr(mp, "DEVICE_ICON"), SQLUtil.returnStr(mp, "DEVICE_RMK"));
				device.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				device.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				device.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				
				if(DictConst.DEVICE_TYPE_SX.equals(device.getDeviceType())){
					Vidicon vidicon = boVidicon.getByDeviceId(device.getDeviceId());
					device.setVidiconId(vidicon.getVidiconId());
					device.setVidiconPort(vidicon.getVidiconPort());
					device.setVidiconPwd(vidicon.getVidiconPwd());
					device.setVidiconUrl(vidicon.getVidiconUrl());
					device.setVidiconUser(vidicon.getVidiconUser());
				} else if(DictConst.DEVICE_TYPE_TD.equals(device.getDeviceType())){
					List<DeviceKey> keyList = boDeviceKey.getListByDeviceId(device.getDeviceId());
					if(keyList != null && keyList.size() > 0){
						DeviceKey key = keyList.get(0);
						SensorNor sensorNor = boSensorNor.get(key.getSensorId());
						
						device.setAddrCode(sensorNor.getAddrCode());
						device.setCodeType(sensorNor.getCodeType());
						device.setFreqType(sensorNor.getFreqType());
						device.setResType(sensorNor.getResType());
					}
				}
				
				list.add(device);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Device> getListByRoomId(int roomId){
		List<Device> list = new ArrayList<Device>();
		try {
			String sql = "SELECT DEVICE.*, FLOOR.*, ROOM.ROOM_NAME FROM DEVICE, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = DEVICE.ROOM_ID AND ROOM.ROOM_ID=?";
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), roomId, sql);
			for(Map mp : lt){
				
				Device device = new Device(SQLUtil.returnInt(mp, "DEVICE_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), 
						SQLUtil.returnStr(mp, "DEVICE_NAME"), SQLUtil.returnStr(mp, "DEVICE_TYPE"), SQLUtil.returnStr(mp, "DEVICE_ICON"), SQLUtil.returnStr(mp, "DEVICE_RMK"));
				device.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				device.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				device.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				list.add(device);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 返回所有的房间普通灯
	 * @param roomId
	 * @return
	 */
	public List<Device> getListByLightPD(int roomId){
		List<Device> list = new ArrayList<Device>();
		try {
			StringBuffer sb = new StringBuffer(200);
			sb.append("SELECT DEVICE.*, FLOOR.*, ROOM.ROOM_NAME FROM DEVICE, FLOOR, ROOM ");
			sb.append("WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = DEVICE.ROOM_ID ");
			sb.append("AND ROOM.ROOM_ID=? ");
			sb.append("AND (DEVICE.DEVICE_TYPE=");
			sb.append("'");
			sb.append(DictConst.DEVICE_TYPE_PD);
			sb.append("'");
			sb.append(")");
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), roomId, sb.toString());
			for(Map mp : lt){
				
				Device device = new Device(SQLUtil.returnInt(mp, "DEVICE_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), 
						SQLUtil.returnStr(mp, "DEVICE_NAME"), SQLUtil.returnStr(mp, "DEVICE_TYPE"), SQLUtil.returnStr(mp, "DEVICE_ICON"), SQLUtil.returnStr(mp, "DEVICE_RMK"));
				device.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				device.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				device.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				list.add(device);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 返回所有的房间调光灯
	 * @param roomId
	 * @return
	 */
	public List<Device> getListByLightTD(int roomId){
		List<Device> list = new ArrayList<Device>();
		try {
			StringBuffer sb = new StringBuffer(200);
			sb.append("SELECT DEVICE.*, FLOOR.*, ROOM.ROOM_NAME FROM DEVICE, FLOOR, ROOM ");
			sb.append("WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = DEVICE.ROOM_ID ");
			sb.append("AND ROOM.ROOM_ID=? ");
			sb.append("AND (DEVICE.DEVICE_TYPE=");
			sb.append("'");
			sb.append(DictConst.DEVICE_TYPE_TD);
			sb.append("'");
			sb.append(")");
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), roomId, sb.toString());
			for(Map mp : lt){
				
				Device device = new Device(SQLUtil.returnInt(mp, "DEVICE_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), 
						SQLUtil.returnStr(mp, "DEVICE_NAME"), SQLUtil.returnStr(mp, "DEVICE_TYPE"), SQLUtil.returnStr(mp, "DEVICE_ICON"), SQLUtil.returnStr(mp, "DEVICE_RMK"));
				device.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				device.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				device.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				list.add(device);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 返回所有的房间幕布
	 * @param roomId
	 * @return
	 */
	public List<Device> getListByScreen(int roomId){
		List<Device> list = new ArrayList<Device>();
		try {
			StringBuffer sb = new StringBuffer(200);
			sb.append("SELECT DEVICE.*, FLOOR.*, ROOM.ROOM_NAME FROM DEVICE, FLOOR, ROOM ");
			sb.append("WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = DEVICE.ROOM_ID ");
			sb.append("AND ROOM.ROOM_ID=? ");
			sb.append("AND (DEVICE.DEVICE_TYPE=");
			sb.append("'");
			sb.append(DictConst.DEVICE_TYPE_MB);
			sb.append("'");
			sb.append(")");
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), roomId, sb.toString());
			for(Map mp : lt){
				
				Device device = new Device(SQLUtil.returnInt(mp, "DEVICE_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), 
						SQLUtil.returnStr(mp, "DEVICE_NAME"), SQLUtil.returnStr(mp, "DEVICE_TYPE"), SQLUtil.returnStr(mp, "DEVICE_ICON"), SQLUtil.returnStr(mp, "DEVICE_RMK"));
				device.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				device.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				device.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				list.add(device);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 返回所有的房间窗帘
	 * @param roomId
	 * @return
	 */
	public List<Device> getListByWindow(int roomId){
		List<Device> list = new ArrayList<Device>();
		try {
			StringBuffer sb = new StringBuffer(200);
			sb.append("SELECT DEVICE.*, FLOOR.*, ROOM.ROOM_NAME FROM DEVICE, FLOOR, ROOM ");
			sb.append("WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = DEVICE.ROOM_ID ");
			sb.append("AND ROOM.ROOM_ID=? ");
			sb.append("AND (DEVICE.DEVICE_TYPE=");
			sb.append("'");
			sb.append(DictConst.DEVICE_TYPE_CL);
			sb.append("'");
			sb.append(")");
			List<Map> lt = (List<Map>)SQLUtil.selectSQLInList(DBConnection.getConnection(), roomId, sb.toString());
			for(Map mp : lt){
				
				Device device = new Device(SQLUtil.returnInt(mp, "DEVICE_ID"), SQLUtil.returnInt(mp, "ROOM_ID"), 
						SQLUtil.returnStr(mp, "DEVICE_NAME"), SQLUtil.returnStr(mp, "DEVICE_TYPE"), SQLUtil.returnStr(mp, "DEVICE_ICON"), SQLUtil.returnStr(mp, "DEVICE_RMK"));
				device.setFloor(SQLUtil.returnInt(mp, "FLOOR"));
				device.setFloorName(SQLUtil.returnStr(mp, "FLOOR_NAME"));
				device.setRoomName(SQLUtil.returnStr(mp, "ROOM_NAME"));
				list.add(device);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 得到Device
	 * @param deviceId
	 * @return
	 */
	public Device get(int deviceId){
		Device rm = new Device();
		try {
			String sql = "SELECT DEVICE.*, FLOOR.*, ROOM.ROOM_NAME FROM DEVICE, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = DEVICE.ROOM_ID AND DEVICE_ID=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), deviceId, true, sql);
			if(htData != null){
				rm.setDeviceId(deviceId);
				rm.setRoomId(SQLUtil.returnInt(htData, "ROOM_ID"));
				rm.setDeviceName(SQLUtil.returnStr(htData, "DEVICE_NAME"));
				rm.setDeviceType(SQLUtil.returnStr(htData, "DEVICE_TYPE"));
				rm.setDeviceIcon(SQLUtil.returnStr(htData, "DEVICE_ICON"));
				rm.setDeviceRmk(SQLUtil.returnStr(htData, "DEVICE_RMK"));
				rm.setFloor(SQLUtil.returnInt(htData, "FLOOR"));
				rm.setFloorName(SQLUtil.returnStr(htData, "FLOOR_NAME"));
				rm.setRoomName(SQLUtil.returnStr(htData, "ROOM_NAME"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return rm;
	}
	
	/**
	 * 得到Device
	 * @param deviceId
	 * @return
	 */
	public Device getByRoomIdAndType(int roomId, String deviceType){
		Device rm = new Device();
		try {
			String sql = "SELECT DEVICE.*, FLOOR.*, ROOM.ROOM_NAME FROM DEVICE, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR " +
					"AND ROOM.ROOM_ID = DEVICE.ROOM_ID AND DEVICE.ROOM_ID=? AND DEVICE.DEVICE_TYPE=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), roomId, deviceType, true, sql);
			if(htData != null){
				rm.setDeviceId(SQLUtil.returnInt(htData, "DEVICE_ID"));
				rm.setRoomId(SQLUtil.returnInt(htData, "ROOM_ID"));
				rm.setDeviceName(SQLUtil.returnStr(htData, "DEVICE_NAME"));
				rm.setDeviceType(SQLUtil.returnStr(htData, "DEVICE_TYPE"));
				rm.setDeviceIcon(SQLUtil.returnStr(htData, "DEVICE_ICON"));
				rm.setDeviceRmk(SQLUtil.returnStr(htData, "DEVICE_RMK"));
				rm.setFloor(SQLUtil.returnInt(htData, "FLOOR"));
				rm.setFloorName(SQLUtil.returnStr(htData, "FLOOR_NAME"));
				rm.setRoomName(SQLUtil.returnStr(htData, "ROOM_NAME"));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return rm;
	}
	
	public int getMax(){
		try {
			Map htData =  SQLUtil.getFirstRecord(DBConnection.getConnection(), null, true, "SELECT MAX(DEVICE_ID) AS MAX FROM DEVICE");
			if(htData != null){
				return SQLUtil.returnInt(htData, "MAX");
			}
			return -1;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 判断房间是否存在设备名
	 * @param roomId
	 * @param sensorName
	 */
	public boolean isExistByName(int roomId, String deviceName){
		try {
			String sql = "SELECT DEVICE.*, FLOOR.*, ROOM.ROOM_NAME FROM DEVICE, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = DEVICE.ROOM_ID " +
					"AND DEVICE.ROOM_ID=? AND DEVICE.DEVICE_NAME=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), roomId, deviceName, true, sql);
			if(htData != null && htData.size() > 0){
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 插入
	 * @param DBConnection.getConnection()
	 * @param floorName
	 * @return
	 * @throws Exception 
	 */
	public void insert(Device device) throws Exception{
		try {
			if(DictConst.DEVICE_TYPE_PD.equals(device.getDeviceType()) 
					|| DictConst.DEVICE_TYPE_TD.equals(device.getDeviceType()) 
					|| DictConst.DEVICE_TYPE_MB.equals(device.getDeviceType()) 
					|| DictConst.DEVICE_TYPE_CL.equals(device.getDeviceType())){
				
			}else{
				// 判断是否已经存在相应设备
				boolean isExist = isExists(device.getRoomId(), device.getDeviceType());
				if(isExist){
					throw new DataExistException("设备已经存在不能继续添加！");
				}
			}
			
			device.setDeviceId(getMax() + 1); // 编码+1
			IconFixed icon = boIconFixed.getByCodeId(device.getDeviceType());
			device.setDeviceIcon(icon.getIconName());
			SQLUtil.insertSQL(DBConnection.getConnection(), "DEVICE", device.fillMap());
			
			//------------------------------------按键添加---------------------------------------
			if(DictConst.DEVICE_TYPE_KT.equals(device.getDeviceType())){ // 空调, 添加10个按键, 13个滑块
				
				DeviceKey deviceKey = new DeviceKey();
				deviceKey.setDeviceId(device.getDeviceId());
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_THUMB1);
				boDeviceKey.insert(deviceKey);
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_THUMB2);
				boDeviceKey.insert(deviceKey);
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_THUMB3);
				boDeviceKey.insert(deviceKey);
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_THUMB4);
				boDeviceKey.insert(deviceKey);
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_THUMB5);
				boDeviceKey.insert(deviceKey);
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_THUMB6);
				boDeviceKey.insert(deviceKey);
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_THUMB7);
				boDeviceKey.insert(deviceKey);
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_THUMB8);
				boDeviceKey.insert(deviceKey);
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_THUMB9);
				boDeviceKey.insert(deviceKey);
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_THUMB10);
				boDeviceKey.insert(deviceKey);
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_THUMB11);
				boDeviceKey.insert(deviceKey);
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_THUMB12);
				boDeviceKey.insert(deviceKey);
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_THUMB13);
				boDeviceKey.insert(deviceKey);
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_THUMB14);
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setDeviceId(device.getDeviceId());
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_CON1);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_KT_CON1);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_CON2);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_KT_CON2);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_CON3);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_KT_CON3);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_CON4);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_KT_CON4);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_CON5);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_KT_CON5);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_CON6);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_KT_CON6);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_CON7);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_KT_CON7);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_CON8);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_KT_CON8);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_CON9);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_KT_CON9);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_KT_CON10);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_KT_CON10);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
			}else if(DictConst.DEVICE_TYPE_MT.equals(device.getDeviceType())){ // 多媒体, 添加30个按键
				 // 多媒体, 添加30个按键
				DeviceKey deviceKey = new DeviceKey();
				deviceKey.setDeviceId(device.getDeviceId());
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_ON);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_ON);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_OFF);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_OFF);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_VOL_X);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_VOL_X);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_VOL_U);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_VOL_U);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_VOL_D);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_VOL_D);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_KEY);
				deviceKey.setKeyName("1");
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_KEY);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_KEY);
				deviceKey.setKeyName("2");
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_MENU);
				deviceKey.setKeyName("");
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_AV);
				deviceKey.setKeyName("");
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_BACK);
				deviceKey.setKeyName("");
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_KEY);
				deviceKey.setKeyName("3");
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_KEY);
				deviceKey.setKeyName("4");
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_KEY);
				deviceKey.setKeyName("5");
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_KEY);
				deviceKey.setKeyName("6");
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_KEY);
				deviceKey.setKeyName("7");
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_KEY);
				deviceKey.setKeyName("8");
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_KEY);
				deviceKey.setKeyName("9");
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_KEY);
				deviceKey.setKeyName("0");
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_KEY);
				deviceKey.setKeyName("CH+");
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_KEY);
				deviceKey.setKeyName("CH-");
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_STOP);
				deviceKey.setKeyName("");
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_STOP);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_PLAY);
				deviceKey.setKeyName("");
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_PLAY);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_BACK_UP);
				deviceKey.setKeyName("");
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_BACK_UP);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_FORWARD);
				deviceKey.setKeyName("");
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_FORWARD);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_PREVIOUS);
				deviceKey.setKeyName("");
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_PREVIOUS);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_NEXT);
				deviceKey.setKeyName("");
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_NEXT);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_UP);
				deviceKey.setKeyName("");
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_UP);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_DOWN);
				deviceKey.setKeyName("");
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_DOWN);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_LEFT);
				deviceKey.setKeyName("");
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_LEFT);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_RIGHT);
				deviceKey.setKeyName("");
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MT_RIGHT);
				deviceKey.setIconName(icon.getIconName());
				boDeviceKey.insert(deviceKey);
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MT_OK);
				deviceKey.setKeyName("");
				deviceKey.setIconName("");
				boDeviceKey.insert(deviceKey);
			
			}else if(DictConst.DEVICE_TYPE_SX.equals(device.getDeviceType())){ // 添加摄像头
				Vidicon vidicon = new Vidicon();
				vidicon.setDeviceId(device.getDeviceId());
				vidicon.setVidiconPort(device.getVidiconPort());
				vidicon.setVidiconPwd(device.getVidiconPwd());
				vidicon.setVidiconUrl(device.getVidiconUrl());
				vidicon.setVidiconUser(device.getVidiconUser());
				boVidicon.insert(vidicon);
			}
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			DBConnection.getConnection().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 
	 * @param sensor
	 */
	public List<DeviceLtWnCl> insertLtWnClBefore(Device device) throws Exception{
		List<DeviceLtWnCl> list = new ArrayList<DeviceLtWnCl>();
		
		if(isExistByName(device.getRoomId(), device.getDeviceName())){
			throw new DataExistException("已经存在名称为"+device.getDeviceName()+"的设备");
		}
		
		// 取无线的编号前缀[RA,RB,RC,RD]
		String tableName = SensorUtil.getSensorCodeOut(device.getFreqType(), device.getCodeType());
					
		if(DictConst.DEVICE_TYPE_PD.equals(device.getDeviceType()) 
				|| DictConst.DEVICE_TYPE_MB.equals(device.getDeviceType())
				){ // 普通灯、幕布，添加两个按键
			
			Unused unused1 = boUnused.get(tableName);
			
			Unused next1 = UnusedUtil.getNextUnused(unused1);
			if(boUnused.isThreshold(next1)){
				throw new ThresholdException();
			}
			
			int addrCode = DataAddrCodeUtil.getUnusedAddrCode(device.getCodeType(), device.getResType());
			
			int dataCode = DataAddrCodeUtil.getUnusedDataCode(device.getCodeType(), device.getResType(), addrCode);
			
			SensorNor sensorNor1 = new SensorNor(tableName + next1.getNextUnused(), device.getRoomId() , device.getFreqType(),
					device.getCodeType(), device.getResType(), dataCode, addrCode);
			
			
			list.add(new DeviceLtWnCl(sensorNor1, next1));
			
			Unused next2 = UnusedUtil.getNextUnused(next1);
			if(boUnused.isThreshold(next2)){
				throw new ThresholdException();
			}
			
			List<Integer> scheduledList = new ArrayList<Integer>();
			scheduledList.add(dataCode);
			
			int dataCode2 = DataAddrCodeUtil.getUnusedDataCode(device.getCodeType(), device.getResType(), addrCode, scheduledList);
			
			SensorNor sensorNor2 = new SensorNor(tableName + next2.getNextUnused(), device.getRoomId(), device.getFreqType(),
					device.getCodeType(), device.getResType(), dataCode2, addrCode);
			
			list.add(new DeviceLtWnCl(sensorNor2, next2));
		}else if(DictConst.DEVICE_TYPE_CL.equals(device.getDeviceType())){// 窗帘 添加三个按钮
			Unused unused1 = boUnused.get(tableName);
			
			Unused next1 = UnusedUtil.getNextUnused(unused1);
			if(boUnused.isThreshold(next1)){
				throw new ThresholdException();
			}
			
			int addrCode = DataAddrCodeUtil.getUnusedAddrCode(device.getCodeType(), device.getResType());
			
			int dataCode = DataAddrCodeUtil.getUnusedDataCode(device.getCodeType(), device.getResType(), addrCode);
			
			SensorNor sensorNor1 = new SensorNor(tableName + next1.getNextUnused(), device.getRoomId() , device.getFreqType(),
					device.getCodeType(), device.getResType(), dataCode, addrCode);
			
			
			list.add(new DeviceLtWnCl(sensorNor1, next1));
			
			Unused next2 = UnusedUtil.getNextUnused(next1);
			if(boUnused.isThreshold(next2)){
				throw new ThresholdException();
			}
			
			List<Integer> scheduledList = new ArrayList<Integer>();
			scheduledList.add(dataCode);
			
			int dataCode2 = DataAddrCodeUtil.getUnusedDataCode(device.getCodeType(), device.getResType(), addrCode, scheduledList);
			
			SensorNor sensorNor2 = new SensorNor(tableName + next2.getNextUnused(), device.getRoomId(), device.getFreqType(),
					device.getCodeType(), device.getResType(), dataCode2, addrCode);
			
			list.add(new DeviceLtWnCl(sensorNor2, next2));
			
			Unused next3 = UnusedUtil.getNextUnused(next2);
			if(boUnused.isThreshold(next3)){
				throw new ThresholdException();
			}
			
			scheduledList.add(dataCode2);
			
			int dataCode3 = DataAddrCodeUtil.getUnusedDataCode(device.getCodeType(), device.getResType(), addrCode, scheduledList);
			
			SensorNor sensorNor3 = new SensorNor(tableName + next3.getNextUnused(), device.getRoomId(), device.getFreqType(),
					device.getCodeType(), device.getResType(), dataCode3, addrCode);
			
			list.add(new DeviceLtWnCl(sensorNor3, next3));
		}else if(DictConst.DEVICE_TYPE_TD.equals(device.getDeviceType())){// 调光灯， 添加五个滑动块
			Unused unused1 = boUnused.get(tableName);
			
			Unused next1 = UnusedUtil.getNextUnused(unused1);
			if(boUnused.isThreshold(next1)){
				throw new ThresholdException();
			}
			
			//int addrCode = DataAddrCodeUtil.getUnusedAddrCode(device.getCodeType(), device.getResType());
			int addrCode = device.getAddrCode();
			/*if(DataAddrCodeManager.getInstance().isContains(device.getResType(), addrCode)){
				throw new DataExistException("此地址码已经存在，建议使用" + 
						DataAddrCodeUtil.getUnusedAddrCode(device.getCodeType(), device.getResType()));
			}*/
			
			int dataCode = DataAddrCodeUtil.getUnusedDataCode10(device.getCodeType(), device.getResType(), addrCode);
			
			if(dataCode == -1){
				throw new DataExistException("此地址码中的数据码已经被使用完！");
			}
			
			SensorNor sensorNor1 = new SensorNor(tableName + next1.getNextUnused(), device.getRoomId(), device.getFreqType(),
					device.getCodeType(), device.getResType(), dataCode, addrCode);
			
			list.add(new DeviceLtWnCl(sensorNor1, next1));
			
			Unused next2 = UnusedUtil.getNextUnused(next1);
			if(boUnused.isThreshold(next2)){
				throw new ThresholdException();
			}
			
			List<Integer> scheduledList = new ArrayList<Integer>();
			scheduledList.add(dataCode);
			
			int dataCode2 = DataAddrCodeUtil.getUnusedDataCode10(device.getCodeType(), device.getResType(), addrCode, scheduledList);
			
			if(dataCode2 == -1){
				throw new DataExistException("此地址码中的数据码已经被使用完！");
			}
			
			SensorNor sensorNor2 = new SensorNor(tableName + next2.getNextUnused(), device.getRoomId(), device.getFreqType(),
					device.getCodeType(), device.getResType(), dataCode2, addrCode);
			
			list.add(new DeviceLtWnCl(sensorNor2, next2));
			
			Unused next3 = UnusedUtil.getNextUnused(next2);
			if(boUnused.isThreshold(next3)){
				throw new ThresholdException();
			}
			
			scheduledList.add(dataCode2);
			
			int dataCode3 = DataAddrCodeUtil.getUnusedDataCode10(device.getCodeType(), device.getResType(), addrCode, scheduledList);
			
			if(dataCode3 == -1){
				throw new DataExistException("此地址码中的数据码已经被使用完！");
			}
			
			SensorNor sensorNor3 = new SensorNor(tableName + next3.getNextUnused(), device.getRoomId(), device.getFreqType(),
					device.getCodeType(), device.getResType(), dataCode3, addrCode);
			
			list.add(new DeviceLtWnCl(sensorNor3, next3));
			
			Unused next4 = UnusedUtil.getNextUnused(next3);
			if(boUnused.isThreshold(next4)){
				throw new ThresholdException();
			}
			
			scheduledList.add(dataCode3);
			int dataCode4 = DataAddrCodeUtil.getUnusedDataCode10(device.getCodeType(), device.getResType(), addrCode, scheduledList);
			
			if(dataCode4 == -1){
				throw new DataExistException("此地址码中的数据码已经被使用完！");
			}
			
			SensorNor sensorNor4 = new SensorNor(tableName + next4.getNextUnused(), device.getRoomId(), device.getFreqType(),
					device.getCodeType(), device.getResType(), dataCode4, addrCode);
			
			list.add(new DeviceLtWnCl(sensorNor4, next4));
			
			Unused next5 = UnusedUtil.getNextUnused(next4);
			if(boUnused.isThreshold(next5)){
				throw new ThresholdException();
			}

			scheduledList.add(dataCode4);
			int dataCode5 = DataAddrCodeUtil.getUnusedDataCode10(device.getCodeType(), device.getResType(), addrCode, scheduledList);
			
			if(dataCode5 == -1){
				throw new DataExistException("此地址码中的数据码已经被使用完！");
			}
			
			SensorNor sensorNor5 = new SensorNor(tableName + next5.getNextUnused(), device.getRoomId(), device.getFreqType(),
					device.getCodeType(), device.getResType(), dataCode5, addrCode);
			
			list.add(new DeviceLtWnCl(sensorNor5, next5));
			
		}
			
		return list;
	}
	
	/**
	 * 插入
	 * @param DBConnection.getConnection()
	 * @param floorName
	 * @return
	 * @throws Exception 
	 */
	public void insertLtWnCl(Device device, List<DeviceLtWnCl> ltWnClList) throws Exception{
		try {
			if(DictConst.DEVICE_TYPE_PD.equals(device.getDeviceType()) 
					|| DictConst.DEVICE_TYPE_TD.equals(device.getDeviceType()) 
					|| DictConst.DEVICE_TYPE_MB.equals(device.getDeviceType()) 
					|| DictConst.DEVICE_TYPE_CL.equals(device.getDeviceType())){
				
			}else{
				// 判断是否已经存在相应设备
				boolean isExist = isExists(device.getRoomId(), device.getDeviceType());
				if(isExist){
					throw new DataExistException("设备已经存在不能继续添加！");
				}
			}
			
			device.setDeviceId(getMax() + 1); // 编码+1
			IconFixed icon = boIconFixed.getByCodeId(device.getDeviceType());
			device.setDeviceIcon(icon.getIconName());
			SQLUtil.insertSQL(DBConnection.getConnection(), "DEVICE", device.fillMap());
			
			//------------------------------------按键添加---------------------------------------
			if(DictConst.DEVICE_TYPE_PD.equals(device.getDeviceType())){ // 普通灯，添加两个按键
				DeviceLtWnCl deviceLtWnCl = ltWnClList.get(0);
				SensorNor sensorNor1 = deviceLtWnCl.getSensorNor();
				
				
				DeviceKey deviceKey = new DeviceKey();
				deviceKey.setDeviceId(device.getDeviceId());
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_PD_OFF);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_PD_OFF);
				deviceKey.setIconName(icon.getIconName());
				deviceKey.setSensorTable("SENSOR_NOR");
				deviceKey.setSensorId(sensorNor1.getSensorId());
				
				boDeviceKey.insert(deviceKey);
				
				boSensorNor.insert(sensorNor1, deviceLtWnCl.getNext());
				
				deviceLtWnCl = ltWnClList.get(1);
				SensorNor sensorNor2 = deviceLtWnCl.getSensorNor();
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_PD_ON);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_PD_ON);
				deviceKey.setIconName(icon.getIconName());
				deviceKey.setSensorTable("SENSOR_NOR");
				deviceKey.setSensorId(sensorNor2.getSensorId());
				
				boDeviceKey.insert(deviceKey);
				
				boSensorNor.insert(sensorNor2, deviceLtWnCl.getNext());
				
			}else if(DictConst.DEVICE_TYPE_TD.equals(device.getDeviceType())){ // 调光灯， 添加五个滑动块
				DeviceKey deviceKey = new DeviceKey();
				DeviceLtWnCl deviceLtWnCl = ltWnClList.get(0);
				SensorNor sensorNor1 = deviceLtWnCl.getSensorNor();
				
				deviceKey.setDeviceId(device.getDeviceId());
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_TD_THUMB1);
				
				deviceKey.setSensorTable("SENSOR_NOR");
				deviceKey.setSensorId(sensorNor1.getSensorId());
				
				boDeviceKey.insert(deviceKey);
				
				boSensorNor.insert(sensorNor1, deviceLtWnCl.getNext());
				
				deviceLtWnCl = ltWnClList.get(1);
				SensorNor sensorNor2 = deviceLtWnCl.getSensorNor();
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_TD_THUMB2);
				deviceKey.setSensorTable("SENSOR_NOR");
				deviceKey.setSensorId(sensorNor2.getSensorId());
				boDeviceKey.insert(deviceKey);
				
				boSensorNor.insert(sensorNor2, deviceLtWnCl.getNext());
				
				deviceLtWnCl = ltWnClList.get(2);
				SensorNor sensorNor3 = deviceLtWnCl.getSensorNor();
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_TD_THUMB3);
				deviceKey.setSensorTable("SENSOR_NOR");
				deviceKey.setSensorId(sensorNor3.getSensorId());
				boDeviceKey.insert(deviceKey);
				
				boSensorNor.insert(sensorNor3, deviceLtWnCl.getNext());
				
				deviceLtWnCl = ltWnClList.get(3);
				SensorNor sensorNor4 = deviceLtWnCl.getSensorNor();
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_TD_THUMB4);
				deviceKey.setSensorTable("SENSOR_NOR");
				deviceKey.setSensorId(sensorNor4.getSensorId());
				boDeviceKey.insert(deviceKey);
				
				boSensorNor.insert(sensorNor4, deviceLtWnCl.getNext());
				
				deviceLtWnCl = ltWnClList.get(4);
				SensorNor sensorNor5 = deviceLtWnCl.getSensorNor();
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_TD_THUMB5);
				deviceKey.setSensorTable("SENSOR_NOR");
				deviceKey.setSensorId(sensorNor5.getSensorId());
				boDeviceKey.insert(deviceKey);
				
				boSensorNor.insert(sensorNor5, deviceLtWnCl.getNext());
			}else if(DictConst.DEVICE_TYPE_CL.equals(device.getDeviceType())){ // 窗帘, 添加3个按键
				DeviceKey deviceKey = new DeviceKey();
				DeviceLtWnCl deviceLtWnCl = ltWnClList.get(0);
				SensorNor sensorNor1 = deviceLtWnCl.getSensorNor();
				
				deviceKey.setDeviceId(device.getDeviceId());
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_CL_ON);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_CL_ON);
				deviceKey.setIconName(icon.getIconName());
				deviceKey.setSensorTable("SENSOR_NOR");
				deviceKey.setSensorId(sensorNor1.getSensorId());
				boDeviceKey.insert(deviceKey);
				
				boSensorNor.insert(sensorNor1, deviceLtWnCl.getNext());
				
				deviceLtWnCl = ltWnClList.get(1);
				SensorNor sensorNor2 = deviceLtWnCl.getSensorNor();
				
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_CL_STOP);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_CL_STOP);
				deviceKey.setIconName(icon.getIconName());
				deviceKey.setSensorTable("SENSOR_NOR");
				deviceKey.setSensorId(sensorNor2.getSensorId());
				
				boDeviceKey.insert(deviceKey);
				
				boSensorNor.insert(sensorNor2, deviceLtWnCl.getNext());
				
				deviceLtWnCl = ltWnClList.get(2);
				SensorNor sensorNor3 = deviceLtWnCl.getSensorNor();
				
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_CL_OFF);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_CL_OFF);
				deviceKey.setIconName(icon.getIconName());
				deviceKey.setSensorTable("SENSOR_NOR");
				deviceKey.setSensorId(sensorNor3.getSensorId());
				
				boDeviceKey.insert(deviceKey);
				
				boSensorNor.insert(sensorNor3, deviceLtWnCl.getNext());
			}else if(DictConst.DEVICE_TYPE_MB.equals(device.getDeviceType())){ // 幕布, 添加2个按键
				DeviceKey deviceKey = new DeviceKey();
				
				DeviceLtWnCl deviceLtWnCl = ltWnClList.get(0);
				SensorNor sensorNor1 = deviceLtWnCl.getSensorNor();
				
				deviceKey.setDeviceId(device.getDeviceId());
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MB_ON);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MB_ON);
				deviceKey.setIconName(icon.getIconName());
				deviceKey.setSensorTable("SENSOR_NOR");
				deviceKey.setSensorId(sensorNor1.getSensorId());
				boDeviceKey.insert(deviceKey);
				
				boSensorNor.insert(sensorNor1, deviceLtWnCl.getNext());
				
				deviceLtWnCl = ltWnClList.get(1);
				SensorNor sensorNor2 = deviceLtWnCl.getSensorNor();
				
				deviceKey.setKeyType(DictConst.DEVICE_TYPE_MB_OFF);
				icon = boIconFixed.getByCodeId(DictConst.DEVICE_TYPE_MB_OFF);
				deviceKey.setIconName(icon.getIconName());
				deviceKey.setSensorTable("SENSOR_NOR");
				deviceKey.setSensorId(sensorNor2.getSensorId());
				
				boDeviceKey.insert(deviceKey);
				
				boSensorNor.insert(sensorNor2, deviceLtWnCl.getNext());
			}
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			DBConnection.getConnection().rollback();
			e.printStackTrace();
			throw e;
		}
	}
	
	public boolean isExists(int roomId, String deviceType){
		try {
			String sql = "SELECT DEVICE.*, FLOOR.*, ROOM.ROOM_NAME FROM DEVICE, FLOOR, ROOM WHERE FLOOR.FLOOR = ROOM.FLOOR AND ROOM.ROOM_ID = DEVICE.ROOM_ID " +
					"AND DEVICE.ROOM_ID=? AND DEVICE.DEVICE_TYPE=?";
			Map htData = SQLUtil.getFirstRecord(DBConnection.getConnection(), roomId, deviceType, true, sql);
			if(htData != null && htData.size() > 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Device device){
		try {
			Map<String, Object> htUpdateParam = new HashMap<String, Object>();
			htUpdateParam.put("DEVICE_NAME", device.getDeviceName());
			htUpdateParam.put("DEVICE_RMK", device.getDeviceRmk());
			htUpdateParam.put("ROOM_ID", device.getRoomId());
			Map<String, Object> htKeyParam = new HashMap<String, Object>();
			htKeyParam.put("DEVICE_ID", device.getDeviceId());
			
			SQLUtil.updateSQL(DBConnection.getConnection(), "DEVICE", htUpdateParam, htKeyParam);
			
			 if(DictConst.DEVICE_TYPE_SX.equals(device.getDeviceType())){ // 添加摄像头
					Vidicon vidicon = new Vidicon();
					vidicon.setVidiconId(device.getVidiconId());
					vidicon.setDeviceId(device.getDeviceId());
					vidicon.setVidiconPort(device.getVidiconPort());
					vidicon.setVidiconPwd(device.getVidiconPwd());
					vidicon.setVidiconUrl(device.getVidiconUrl());
					vidicon.setVidiconUser(device.getVidiconUser());
					boVidicon.update(vidicon);
				}
			 DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			 
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				DBConnection.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(int deviceId){
		try {
			
			Map<String, Object> htParam = new HashMap<String, Object>();
			htParam.put("DEVICE_ID", deviceId);
		
			// 删除所有按键
			boDeviceKey.deleteByDeviceId(deviceId);
			
			SQLUtil.deleteSQL(DBConnection.getConnection(), "DEVICE", htParam);
			
			DBConnection.getConnection().commit();
			// 设置系统被修改标志
			SystemConfig.getInstance().setSysChanged(true);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				DBConnection.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return false;
	}
}

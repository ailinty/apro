package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class DeviceKey {
	private int deviceKeyId;
	
	private int deviceId;
	
	private String keyPoint;
	
	private String sensorTable;
	
	private String sensorId;
	
	private String keyName;
	
	private String keyType;
	
	private String iconName;
	
	private int roomId;
	
	public DeviceKey(){
		
	}

	public DeviceKey(int deviceKeyId, int deviceId, String keyPoint, String sensorTable, String sensorId, 
			String keyName, String keyType, String iconName){
		this.deviceKeyId = deviceKeyId;
		this.deviceId = deviceId;
		this.keyPoint = keyPoint;
		this.sensorTable = sensorTable;
		this.sensorId = sensorId;
		this.keyName = keyName;
		this.keyType = keyType;
		this.iconName = iconName;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("DEVICE_ID", this.deviceId);
		htParam.put("KEY_POINT", this.keyPoint);
		htParam.put("SENSOR_TABLE", this.sensorTable);
		htParam.put("SENSOR_ID", this.sensorId);
		htParam.put("KEY_NAME", this.keyName);
		htParam.put("KEY_TYPE", this.keyType);
		htParam.put("ICON_NAME", this.iconName);
		
		return htParam;
	}
	
	public int getDeviceKeyId() {
		return deviceKeyId;
	}

	public void setDeviceKeyId(int deviceKeyId) {
		this.deviceKeyId = deviceKeyId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getKeyPoint() {
		return keyPoint;
	}

	public void setKeyPoint(String keyPoint) {
		this.keyPoint = keyPoint;
	}

	public String getSensorTable() {
		return sensorTable;
	}

	public void setSensorTable(String sensorTable) {
		this.sensorTable = sensorTable;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
}

package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 无线传感器 输出
 * @author JOEY
 *
 */
public class SensorOut {
	private String sensorId;
	
	private int roomId;
	
	private String roomName;
	
	private String sensorName;
	
	private String freqType;
	
	private String codeType;
	
	private String resType;
	
	private int addrCode = -1;
	
	private int dataCode = -1;
	
	private String iconName;
	
	private int position;
	
	private int floor;
	private String floorName;
	
	public SensorOut(){
		
	}

	public SensorOut(String sensorId, int roomId, String sensorName, String freqType, String codeType,
			String resType, int dataCode, int addrCode, int position){
		this.sensorId = sensorId;
		this.roomId = roomId;
		this.sensorName = sensorName;
		this.freqType = freqType;
		this.codeType = codeType;
		this.resType = resType;
		this.addrCode = addrCode;
		this.dataCode = dataCode;
		this.position = position;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("SENSOR_ID", this.sensorId);
		htParam.put("ROOM_ID", this.roomId);
		htParam.put("SENSOR_NAME", this.sensorName);
		htParam.put("FREQ_TYPE", this.freqType);
		htParam.put("CODE_TYPE", this.codeType);
		htParam.put("RES_TYPE", this.resType);
		htParam.put("ADDR_CODE", this.addrCode);
		htParam.put("DATA_CODE", this.dataCode);
		htParam.put("POSITION", this.position);
		return htParam;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	public String getFreqType() {
		return freqType;
	}
	public void setFreqType(String freqType) {
		this.freqType = freqType;
	}
	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public int getAddrCode() {
		return addrCode;
	}

	public void setAddrCode(int addrCode) {
		this.addrCode = addrCode;
	}

	public int getDataCode() {
		return dataCode;
	}

	public void setDataCode(int dataCode) {
		this.dataCode = dataCode;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}

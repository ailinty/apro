package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class SensorNor {
	private String sensorId;
	
	private int roomId;
	
	private String freqType;
	
	private String codeType;
	
	private String resType;
	
	private int addrCode;
	
	private int dataCode;
	
	private String roomName;
	private int floor;
	private String floorName;
	
	public SensorNor(){
		
	}
	
	public SensorNor(String sensorId, int roomId, String freqType, 
			String codeType, String resType, int dataCode, int addrCode){
		this.sensorId = sensorId;
		this.roomId = roomId;
		this.freqType = freqType;
		this.codeType = codeType;
		this.resType = resType;
		this.addrCode = addrCode;
		this.dataCode = dataCode;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("SENSOR_ID", this.sensorId);
		htParam.put("ROOM_ID", this.roomId);
		htParam.put("FREQ_TYPE", this.freqType);
		htParam.put("CODE_TYPE", this.codeType);
		htParam.put("RES_TYPE", this.resType);
		htParam.put("ADDR_CODE", this.addrCode);
		htParam.put("DATA_CODE", this.dataCode);
		
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
	
}

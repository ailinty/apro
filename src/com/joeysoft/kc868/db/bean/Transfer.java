package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class Transfer {
	private int transferId;
	private String sensorId;
	private String transferName;
	private int roomId;
	private String freqType;
	private String codeType;
	private String resType;
	private int addrCode;
	
	private int floor;
	private String floorName;
	private String roomName;
	
	public Transfer(){
		
	}
			
	public Transfer(int transferId, int roomId, String sensorId, String transferName, String freqType, 
			String codeType, String resType, int addrCode){
		this.transferId = transferId;
		this.roomId = roomId;
		this.sensorId= sensorId;
		this.transferName = transferName;
		this.freqType = freqType;
		this.codeType = codeType;
		this.resType = resType;
		this.addrCode = addrCode;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("TRANSFER_ID", this.transferId);
		htParam.put("ROOM_ID", this.roomId);
		htParam.put("SENSOR_ID", this.sensorId);
		htParam.put("TRANSFER_NAME", this.transferName);
		htParam.put("FREQ_TYPE", this.freqType);
		htParam.put("CODE_TYPE", this.codeType);
		htParam.put("RES_TYPE", this.resType);
		htParam.put("ADDR_CODE", this.addrCode);
		return htParam;
	}
	
	public int getTransferId() {
		return transferId;
	}
	public void setTransferId(int transferId) {
		this.transferId = transferId;
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

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getTransferName() {
		return transferName;
	}

	public void setTransferName(String transferName) {
		this.transferName = transferName;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}
	
}

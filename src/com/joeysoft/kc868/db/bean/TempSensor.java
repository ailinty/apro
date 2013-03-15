package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class TempSensor {
	
	private int sensorId;
	
	private String sensorName;
	
	private int roomId;
	
	private int sensorAddr;
	
	private int sensorUpper;
	
	private int sensorLower;
	
	private int humidityUpper;
	
	private int humidityLower;
	
	private String sensorAlert;
	
	private String sensorStudy;
	
	private int floor;
	private String floorName;
	private String roomName;
	
	public TempSensor(){
		
	}
	
	public TempSensor(int sensorId, int roomId, String sensorName, int sensorAddr, 
			int sensorUpper, int sensorLower, String sensorStudy,  String sensorAlert, int humidityUpper, int humidityLower){
		this.sensorId = sensorId;
		this.roomId = roomId;
		this.sensorName = sensorName;
		this.sensorAddr = sensorAddr;
		this.sensorUpper = sensorUpper;
		this.sensorLower = sensorLower;
		this.sensorAlert = sensorAlert;
		this.sensorStudy = sensorStudy;
		this.humidityUpper = humidityUpper;
		this.humidityLower = humidityLower;
		
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("SENSOR_ID", this.sensorId);
		htParam.put("ROOM_ID", this.roomId);
		htParam.put("SENSOR_NAME", this.sensorName);
		htParam.put("SENSOR_ADDR", this.sensorAddr);
		htParam.put("SENSOR_UPPER", this.sensorUpper);
		htParam.put("SENSOR_LOWER", this.sensorLower);
		htParam.put("SENSOR_ALERT", this.sensorAlert);
		htParam.put("SENSOR_STUDY", this.sensorStudy);
		htParam.put("HUMIDITY_UPPER", this.humidityUpper);
		htParam.put("HUMIDITY_LOWER", this.humidityLower);
		
		return htParam;
	}

	public int getSensorId() {
		return sensorId;
	}


	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}


	public String getSensorName() {
		return sensorName;
	}


	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}


	public int getRoomId() {
		return roomId;
	}


	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}


	public int getSensorAddr() {
		return sensorAddr;
	}


	public void setSensorAddr(int sensorAddr) {
		this.sensorAddr = sensorAddr;
	}


	public int getSensorUpper() {
		return sensorUpper;
	}


	public void setSensorUpper(int sensorUpper) {
		this.sensorUpper = sensorUpper;
	}


	public int getSensorLower() {
		return sensorLower;
	}


	public void setSensorLower(int sensorLower) {
		this.sensorLower = sensorLower;
	}


	public String getSensorStudy() {
		return sensorStudy;
	}


	public void setSensorStudy(String sensorStudy) {
		this.sensorStudy = sensorStudy;
	}

	public String getSensorAlert() {
		return sensorAlert;
	}

	public void setSensorAlert(String sensorAlert) {
		this.sensorAlert = sensorAlert;
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

	public int getHumidityUpper() {
		return humidityUpper;
	}

	public void setHumidityUpper(int humidityUpper) {
		this.humidityUpper = humidityUpper;
	}

	public int getHumidityLower() {
		return humidityLower;
	}

	public void setHumidityLower(int humidityLower) {
		this.humidityLower = humidityLower;
	}
}

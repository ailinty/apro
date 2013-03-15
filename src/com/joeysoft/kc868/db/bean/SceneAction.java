package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class SceneAction {
	private int actionId = -1;
	private int sceneId = -1;
	private int floor = -1;
	private int roomId = -1;
	private int deviceId = -1;
	private int deviceKeyId = -1;
	private String sensorTable = "";
	private String sensorId = "";
	
	private String text = "";
	
	public SceneAction(){
		
	}
			
	public SceneAction(int actionId, int sceneId, int floor, int roomId, 
			int deviceId, int deviceKeyId, String sensorTable, String sensorId, String text){
		this.actionId = actionId;
		this.sceneId = sceneId;
		this.floor = floor;
		this.roomId = roomId;
		this.deviceId = deviceId;
		this.deviceKeyId = deviceKeyId;
		this.sensorTable = sensorTable;
		this.sensorId = sensorId;
		this.text = text;
	}
	
	public SceneAction(String sensorTable, String sensorId, String text){
		this.sensorTable = sensorTable;
		this.sensorId = sensorId;
		this.text = text;
	}
	
	public SceneAction(int floor, int roomId, String sensorTable, String sensorId, String text){
		this.floor = floor;
		this.roomId = roomId;
		this.sensorTable = sensorTable;
		this.sensorId = sensorId;
		this.text = text;
	}
	
	public SceneAction(int floor, int roomId, 
			int deviceId, int deviceKeyId, String sensorTable, String sensorId, String text){
		this.floor = floor;
		this.roomId = roomId;
		this.deviceId = deviceId;
		this.deviceKeyId = deviceKeyId;
		this.sensorTable = sensorTable;
		this.sensorId = sensorId;
		this.text = text;
	}

	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("SCENE_ID", this.sceneId);
		htParam.put("FLOOR", this.floor);
		htParam.put("ROOM_ID", this.roomId);
		htParam.put("DEVICE_ID", this.deviceId);
		htParam.put("DEVICE_KEY_ID", this.deviceKeyId);
		htParam.put("SENSOR_TABLE", this.sensorTable);
		htParam.put("SENSOR_ID", this.sensorId);
		htParam.put("SCENE_TEXT", this.text);
		
		return htParam;
	}

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public int getDeviceKeyId() {
		return deviceKeyId;
	}

	public void setDeviceKeyId(int deviceKeyId) {
		this.deviceKeyId = deviceKeyId;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}

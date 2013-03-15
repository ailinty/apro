package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class Relay {
	private int relayId;
	private int roomId;
	
	private String relayName;
	
	private String relayOnName;
	
	private String relayOffName;
	
	private String relayStatus;
	
	private String roomName;
	private int floor;
	private String floorName;
	
	public Relay(){
		
	}
			
	public Relay(int relayId, int roomId, String relayName, String relayOnName, String relayOffName, String relayStatus){
		this.relayId = relayId;
		this.roomId = roomId;
		this.relayName = relayName;
		this.relayOnName = relayOnName;
		this.relayOffName = relayOffName;
		this.relayStatus = relayStatus;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("RELAY_ID", this.relayId);
		htParam.put("ROOM_ID", this.roomId);
		htParam.put("RELAY_NAME", this.relayName);
		htParam.put("RELAY_ON_NAME", this.relayOnName);
		htParam.put("RELAY_OFF_NAME", this.relayOffName);
		htParam.put("RELAY_STATUS", this.relayStatus);
		
		return htParam;
	}

	public int getRelayId() {
		return relayId;
	}

	public void setRelayId(int relayId) {
		this.relayId = relayId;
	}

	public String getRelayName() {
		return relayName;
	}

	public void setRelayName(String relayName) {
		this.relayName = relayName;
	}

	public String getRelayOnName() {
		return relayOnName;
	}

	public void setRelayOnName(String relayOnName) {
		this.relayOnName = relayOnName;
	}

	public String getRelayOffName() {
		return relayOffName;
	}

	public void setRelayOffName(String relayOffName) {
		this.relayOffName = relayOffName;
	}

	public String getRelayStatus() {
		return relayStatus;
	}

	public void setRelayStatus(String relayStatus) {
		this.relayStatus = relayStatus;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
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

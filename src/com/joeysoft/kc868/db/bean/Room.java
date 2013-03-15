package com.joeysoft.kc868.db.bean;

import java.util.HashMap;
import java.util.Map;

public class Room {
	private int roomId;
	
	private int floor;
	
	private String floorName;
	
	private String roomName;
	
	public Room(){
		
	}

	public Room(int floor, int roomId, String roomName){
		this.floor = floor;
		this.roomId = roomId;
		this.roomName = roomName;
	}
	
	public Room(int roomId, String roomName){
		this.roomId = roomId;
		this.roomName = roomName;
	}
	
	public Map<String, Object> fillMap(){
		Map<String, Object> htParam = new HashMap<String, Object>();
		htParam.put("ROOM_ID", this.roomId);
		htParam.put("FLOOR", this.floor);
		htParam.put("ROOM_NAME", this.roomName);
		
		return htParam;
	}
	
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
}
